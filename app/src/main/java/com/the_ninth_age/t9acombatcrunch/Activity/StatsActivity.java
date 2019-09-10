package com.the_ninth_age.t9acombatcrunch.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.the_ninth_age.t9acombatcrunch.R;
import com.the_ninth_age.t9acombatcrunch.Service.SimulatorService;
import com.the_ninth_age.t9acombatcrunch.Service.Units.ArmybookEntry;
import com.the_ninth_age.t9acombatcrunch.Service.Units.Dice;
import com.the_ninth_age.t9acombatcrunch.Service.Units.OffensiveProfile;
import com.the_ninth_age.t9acombatcrunch.Service.Units.SpecialRule;
import com.the_ninth_age.t9acombatcrunch.Service.Units.Unit;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatsActivity extends AppCompatActivity {
    Gson gson;
    SimulatorService simulatorService;

    //interface elements
    Spinner spinner_armybook_stats;
    Spinner spinner_unit_type_stats;
    TextView title_unit_name;
    TextView textview_stats_general;
    TextView textview_stats_adv;
    TextView textview_stats_mar;
    TextView textview_stats_dis;
    TextView textview_stats_specialrules;
    TextView textview_stats_hp;
    TextView textview_stats_def;
    TextView textview_stats_res;
    TextView textview_stats_arm;
    TextView textview_stats_fortitude;
    TextView textview_stats_aegis;
    TextView textview_stats_armor_type;
    TextView textview_stats_fortitude_title;
    TextView textview_stats_aegis_title;
    TableRow tablerow_stats_specialSaves;
    TableRow tablerow_stats_specialSaves_title;
    List<TableRow> tablerow_stats_list;
    List<TextView> textview_stats_profileName_list;
    List<TextView> textview_stats_att_list;
    List<TextView> textview_stats_off_list;
    List<TextView> textview_stats_str_list;
    List<TextView> textview_stats_ap_list;
    List<TextView> textview_stats_agi_list;
    List<TableRow> tablerow_stats_specialrulesOP_list;
    List<TextView> textview_stats_specialrulesOP_list;

    //interface needed variables
    private List<String> armybookList;
    private List<String> armybookEntryList;
    private ArrayAdapter<String> dataAdapterArmybooks;
    private ArrayAdapter<String> dataAdapterArmybookEntries;
    private boolean armybookSpinnerInitialized = false;
    private boolean armybookEntrySpinnerInitialized = false;

    //unit values
    String armybookEntryName;
    String armybook;
    String unitHeight;
    String unitType;
    int baseWidth;
    int baseLength;
    String adv;
    String mar;
    int dis;
    int hp;
    int def;
    int res;
    int arm;
    String armorType;
    int fortitude;
    int aegis;
    List<OffensiveProfile> offensiveProfileList;
    List<SpecialRule> specialRuleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        gson = new Gson();
        simulatorService = gson.fromJson(getIntent().getStringExtra("simulatorService"), SimulatorService.class);

        //identify interface elements
        spinner_armybook_stats = (Spinner) findViewById(R.id.spinner_armybook_stats);
        spinner_unit_type_stats = (Spinner) findViewById(R.id.spinner_unit_type_stats);
        title_unit_name = (TextView) findViewById(R.id.title_unit_name);
        textview_stats_general = (TextView) findViewById(R.id.textview_stats_general);
        textview_stats_adv = (TextView) findViewById(R.id.textview_stats_adv);
        textview_stats_mar = (TextView) findViewById(R.id.textview_stats_mar);
        textview_stats_dis = (TextView) findViewById(R.id.textview_stats_dis);
        textview_stats_specialrules = (TextView) findViewById(R.id.textview_stats_specialrules_global);
        textview_stats_hp = (TextView) findViewById(R.id.textview_stats_hp);
        textview_stats_def = (TextView) findViewById(R.id.textview_stats_def);
        textview_stats_res = (TextView) findViewById(R.id.textview_stats_res);
        textview_stats_arm = (TextView) findViewById(R.id.textview_stats_arm);
        textview_stats_fortitude = (TextView) findViewById(R.id.textview_stats_fortitude);
        textview_stats_aegis = (TextView) findViewById(R.id.textview_stats_aegis);
        textview_stats_fortitude_title = (TextView) findViewById(R.id.textview_stats_fortitude_title);
        textview_stats_aegis_title = (TextView) findViewById(R.id.textview_stats_aegis_title);
        tablerow_stats_specialSaves = (TableRow) findViewById(R.id.tablerow_stats_specialSaves);
        tablerow_stats_specialSaves_title = (TableRow) findViewById(R.id.tablerow_stats_specialSaves_title);
        textview_stats_armor_type = (TextView) findViewById(R.id.textview_stats_armor_type);
        tablerow_stats_list = Arrays.asList((TableRow) findViewById(R.id.tablerow_stats_OP1), (TableRow) findViewById(R.id.tablerow_stats_OP2), (TableRow) findViewById(R.id.tablerow_stats_OP3), (TableRow) findViewById(R.id.tablerow_stats_OP4), (TableRow) findViewById(R.id.tablerow_stats_OP5), (TableRow) findViewById(R.id.tablerow_stats_OP6));
        textview_stats_profileName_list = Arrays.asList((TextView) findViewById(R.id.textview_stats_profileName_OP1), (TextView) findViewById(R.id.textview_stats_profileName_OP2), (TextView) findViewById(R.id.textview_stats_profileName_OP3), (TextView) findViewById(R.id.textview_stats_profileName_OP4), (TextView) findViewById(R.id.textview_stats_profileName_OP5), (TextView) findViewById(R.id.textview_stats_profileName_OP6));
        textview_stats_att_list = Arrays.asList((TextView) findViewById(R.id.textview_stats_att_OP1), (TextView) findViewById(R.id.textview_stats_att_OP2), (TextView) findViewById(R.id.textview_stats_att_OP3), (TextView) findViewById(R.id.textview_stats_att_OP4), (TextView) findViewById(R.id.textview_stats_att_OP5), (TextView) findViewById(R.id.textview_stats_att_OP6));
        textview_stats_off_list = Arrays.asList((TextView) findViewById(R.id.textview_stats_off_OP1), (TextView) findViewById(R.id.textview_stats_off_OP2), (TextView) findViewById(R.id.textview_stats_off_OP3), (TextView) findViewById(R.id.textview_stats_off_OP4), (TextView) findViewById(R.id.textview_stats_off_OP5), (TextView) findViewById(R.id.textview_stats_off_OP6));
        textview_stats_str_list = Arrays.asList((TextView) findViewById(R.id.textview_stats_str_OP1), (TextView) findViewById(R.id.textview_stats_str_OP2), (TextView) findViewById(R.id.textview_stats_str_OP3), (TextView) findViewById(R.id.textview_stats_str_OP4), (TextView) findViewById(R.id.textview_stats_str_OP5), (TextView) findViewById(R.id.textview_stats_str_OP6));
        textview_stats_ap_list = Arrays.asList((TextView) findViewById(R.id.textview_stats_ap_OP1), (TextView) findViewById(R.id.textview_stats_ap_OP2), (TextView) findViewById(R.id.textview_stats_ap_OP3), (TextView) findViewById(R.id.textview_stats_ap_OP4), (TextView) findViewById(R.id.textview_stats_ap_OP5), (TextView) findViewById(R.id.textview_stats_ap_OP6));
        textview_stats_agi_list = Arrays.asList((TextView) findViewById(R.id.textview_stats_agi_OP1), (TextView) findViewById(R.id.textview_stats_agi_OP2), (TextView) findViewById(R.id.textview_stats_agi_OP3), (TextView) findViewById(R.id.textview_stats_agi_OP4), (TextView) findViewById(R.id.textview_stats_agi_OP5), (TextView) findViewById(R.id.textview_stats_agi_OP6));
        tablerow_stats_specialrulesOP_list = Arrays.asList((TableRow) findViewById(R.id.tablerow_stats_specialrules_OP1), (TableRow) findViewById(R.id.tablerow_stats_specialrules_OP2), (TableRow) findViewById(R.id.tablerow_stats_specialrules_OP3), (TableRow) findViewById(R.id.tablerow_stats_specialrules_OP4), (TableRow) findViewById(R.id.tablerow_stats_specialrules_OP5), (TableRow) findViewById(R.id.tablerow_stats_specialrules_OP6));
        textview_stats_specialrulesOP_list = Arrays.asList((TextView) findViewById(R.id.textview_stats_specialrules_OP1), (TextView) findViewById(R.id.textview_stats_specialrules_OP2), (TextView) findViewById(R.id.textview_stats_specialrules_OP3), (TextView) findViewById(R.id.textview_stats_specialrules_OP4), (TextView) findViewById(R.id.textview_stats_specialrules_OP5), (TextView) findViewById(R.id.textview_stats_specialrules_OP6));

        //prepare spinners for choices
        armybookList = new ArrayList<>();
        dataAdapterArmybooks = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, armybookList);
        dataAdapterArmybooks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_armybook_stats.setAdapter(dataAdapterArmybooks);

        armybookEntryList = new ArrayList<>();
        dataAdapterArmybookEntries = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, armybookEntryList);
        dataAdapterArmybookEntries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_unit_type_stats.setAdapter(dataAdapterArmybookEntries);

        //set spinner listeners
        spinner_armybook_stats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!armybookSpinnerInitialized) {
                    armybookSpinnerInitialized = true;
                } else {
                    armybook = (String) parentView.getItemAtPosition(position);
                    updateArmybookEntryChoices();
                    armybookEntryName = (String) spinner_unit_type_stats.getItemAtPosition(0);
                    spinner_unit_type_stats.setSelection((getIndex(spinner_unit_type_stats, armybookEntryName)));
                    updateUnitValues(armybookEntryName);
                    displayUnitValues();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        spinner_unit_type_stats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!armybookEntrySpinnerInitialized) {
                    armybookEntrySpinnerInitialized = true;
                } else {
                    armybookEntryName = (String) parentView.getItemAtPosition(position);
                    updateUnitValues(armybookEntryName);
                    displayUnitValues();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        //add choices for spinners
        armybookList = simulatorService.getArmybookNames();
        dataAdapterArmybooks.clear();
        dataAdapterArmybooks.addAll(armybookList);
        dataAdapterArmybooks.notifyDataSetChanged();
        armybookEntryList = simulatorService.getArmybookEntryNames();
        dataAdapterArmybookEntries.clear();
        dataAdapterArmybookEntries.addAll(armybookEntryList);
        dataAdapterArmybookEntries.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        super.onResume();

        //load unit and its values
        armybookEntryName = getIntent().getStringExtra("unitName");
        updateUnitValues(armybookEntryName);
        updateArmybookEntryChoices();
        displayUnitValues();
    }

    //to send data back to parent activity on pressing back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra("armybook", armybook);
                intent.putExtra("unitName", armybookEntryName);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //helper methods
    private void updateUnitValues(String unitName) {
        ArmybookEntry armybookEntry = simulatorService.getArmybookEntry(armybookEntryName);
        armybook = armybookEntry.getArmybook().toString();
        unitHeight = armybookEntry.getModelHeight().toString();
        unitType = armybookEntry.getModelType().toString();
        baseWidth = armybookEntry.getBaseWidth();
        baseLength = armybookEntry.getBaseLength();
        adv = armybookEntry.getAdv();
        mar = armybookEntry.getMar();
        dis = armybookEntry.getLeadership();
        hp = armybookEntry.getHp();
        def = armybookEntry.getDef();
        res = armybookEntry.getRes();
        arm = armybookEntry.getArm();
        fortitude = armybookEntry.getFortitudeSave();
        aegis = armybookEntry.getAegisSave();
        armorType = armybookEntry.getArmorType().toString();
        offensiveProfileList = armybookEntry.getOffensiveProfiles();
        specialRuleList = armybookEntry.getSpecialRules();
    }

    private void displayUnitValues() {
        spinner_armybook_stats.setSelection(getIndex(spinner_armybook_stats, armybook));
        spinner_unit_type_stats.setSelection(getIndex(spinner_unit_type_stats, armybookEntryName));
        title_unit_name.setText(armybookEntryName);
        textview_stats_general.setText(getString(R.string.stats_general, unitHeight, unitType, baseWidth, baseLength));
        textview_stats_adv.setText(adv);
        textview_stats_mar.setText(mar);
        textview_stats_dis.setText("" + dis);
        textview_stats_hp.setText("" + hp);
        textview_stats_def.setText("" + def);
        textview_stats_res.setText("" + res);
        textview_stats_arm.setText("" + arm);
        //display special saves is available, hide fields when not
        if (fortitude != 0) {
            textview_stats_fortitude.setText("" + fortitude);
            textview_stats_fortitude_title.setVisibility(View.VISIBLE);
            textview_stats_fortitude.setVisibility(View.VISIBLE);
        } else {
            textview_stats_fortitude_title.setVisibility(View.GONE);
            textview_stats_fortitude.setVisibility(View.GONE);
        }
        if (aegis != 0) {
            textview_stats_aegis.setText("" + aegis);
            textview_stats_aegis_title.setVisibility(View.VISIBLE);
            textview_stats_aegis.setVisibility(View.VISIBLE);
        } else {
            textview_stats_aegis_title.setVisibility(View.GONE);
            textview_stats_aegis.setVisibility(View.GONE);
        }
        if (fortitude == 0 && aegis == 0) {
            tablerow_stats_specialSaves_title.setVisibility(View.GONE);
            tablerow_stats_specialSaves.setVisibility(View.GONE);
        } else {
            tablerow_stats_specialSaves_title.setVisibility(View.VISIBLE);
            tablerow_stats_specialSaves.setVisibility(View.VISIBLE);
        }

        textview_stats_armor_type.setText(armorType);
        textview_stats_specialrules.setText(specialRulesToString(specialRuleList));

        for (int i = 0; i < tablerow_stats_list.size(); i++) {
            if (i < offensiveProfileList.size()) {
                OffensiveProfile profile = offensiveProfileList.get(i);
                textview_stats_profileName_list.get(i).setText(profile.getName());
                textview_stats_att_list.get(i).setText("" + profile.getAtt());
                textview_stats_off_list.get(i).setText("" + profile.getOff());
                textview_stats_str_list.get(i).setText("" + profile.getStr());
                textview_stats_ap_list.get(i).setText("" + profile.getAp());
                textview_stats_agi_list.get(i).setText("" + profile.getAgi());
                textview_stats_specialrulesOP_list.get(i).setText(specialProfileRulesToString(profile));
                tablerow_stats_list.get(i).setVisibility(View.VISIBLE);
                tablerow_stats_specialrulesOP_list.get(i).setVisibility(View.VISIBLE);
            } else {
                tablerow_stats_list.get(i).setVisibility(View.GONE);
                tablerow_stats_specialrulesOP_list.get(i).setVisibility(View.GONE);
            }
        }

    }

    private void updateArmybookEntryChoices() {
        armybookEntryList = simulatorService.getArmybookEntryNames(armybook);
        dataAdapterArmybookEntries.clear();
        dataAdapterArmybookEntries.addAll(armybookEntryList);
        dataAdapterArmybookEntries.notifyDataSetChanged();
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }

    private String specialRulesToString(List<SpecialRule> list) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!list.isEmpty()) {
            stringBuilder.append(list.get(0));
            if (list.size() > 1) {
                for (int i = 1; i < list.size(); i++) {
                    stringBuilder.append(", " + list.get(i));
                }
            }
            return stringBuilder.toString();
        } else {
            return "";
        }
    }

    private String specialProfileRulesToString(OffensiveProfile profile) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> list = new ArrayList<>();
        if (profile.getMultipleWounds() != Dice.NONE) {
            stringBuilder.append("Multiple wounds " + profile.getMultipleWounds().toString() + ", ");
        }
        if (profile.getImpactHits() != Dice.NONE) {
            stringBuilder.append("Impact hits " + profile.getImpactHits().toString() + ", ");
        }
        if (profile.getGrindingHits() != Dice.NONE) {
            stringBuilder.append("Grinding attack " + profile.getGrindingHits().toString() + ", ");
        }
        stringBuilder.append(specialRulesToString(profile.getSpecialRules()));
        return stringBuilder.toString();
    }


}