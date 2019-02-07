package com.the_ninth_age.t9acombatcrunch.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.text.TextWatcher;

import com.google.gson.Gson;
import com.the_ninth_age.t9acombatcrunch.R;
import com.the_ninth_age.t9acombatcrunch.Service.SimulatorService;
import com.the_ninth_age.t9acombatcrunch.Service.Units.Unit;
import com.the_ninth_age.t9acombatcrunch.Service.Units.WeaponType;
import com.the_ninth_age.t9acombatcrunch.Service.Units.WeaponTypeShooting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MainActivity extends AppCompatActivity {

    Gson gson;
    SimulatorService simulatorService;

    //interface elements
    private TextView title;
    private TextView desc_armybook;
    private Spinner spinner_armybook;
    private TextView desc_unit_type;
    private Spinner spinner_unit_type;
    private TextView desc_unit_size;
    private EditText edit_unit_size;
    private TextView desc_row_size;
    private EditText edit_row_size;
    private TextView title_weapons;
    private Spinner spinner_weapon_cc;
    private Spinner spinner_weapon_shooting;
    private TextView title_lost_hp;
    private Button button_lost_hp_decrease;
    private Button button_lost_hp_increase;
    private TextView lost_hp_textview;
    private TextView title_command_group;
    private TextView desc_champion;
    private CheckBox checkbox_champion;
    private TextView desc_musician;
    private CheckBox checkbox_musician;
    private TextView desc_standard;
    private CheckBox checkbox_standard;
    private TextView title_special;
    private TextView desc_BSB;
    private CheckBox checkbox_BSB;
    private TextView desc_charge;
    private CheckBox checkbox_charge;
    private TextView desc_general;
    private CheckBox checkbox_general;
    private TextView desc_general_dis;
    private TextView general_dis_textview;
    private Button button_general_dis_decrease;
    private Button button_general_dis_increase;
    private Button button_done;

    //interface needed variables
    private int unitNumber;
    private List<String> armybookList;
    private List<String> armybookEntryList;
    private List<String> weaponCCList;
    private List<String> weaponShootingList;
    private ArrayAdapter<String> dataAdapterArmybooks;
    private ArrayAdapter<String> dataAdapterArmybookEntries;
    private ArrayAdapter<String> dataAdapterWeaponsCC;
    private ArrayAdapter<String> dataAdapterWeaponShooting;
    private boolean armybookSpinnerInitialized = false;
    private boolean armybookEntrySpinnerInitialized = false;
    private boolean weaponCCSpinnerInitialized = false;
    private boolean weaponShootingSpinnerInitialized = false;

    //unit values
    private String armybook;
    private String armybookEntry;
    private int modelCount;
    private int rowModels;
    private int champion = 0;
    private int musician = 0;
    private int standard = 0;
    private int bsb = 0;
    private int general = 0;
    private int generalDiscipline = 9;
    private int charge = 0;
    private int standAndShoot = 0;
    private String weaponCC;
    private String weaponShooting;
    private int lostHp = 0;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new Gson();

        //identify interface elements
        title = (TextView) findViewById(R.id.title);
        desc_armybook = (TextView) findViewById(R.id.desc_armybook);
        spinner_armybook = (Spinner) findViewById(R.id.spinner_armybook);
        desc_unit_type = (TextView) findViewById(R.id.desc_unit_type);
        spinner_unit_type = (Spinner) findViewById(R.id.spinner_unit_type);
        desc_unit_size = (TextView) findViewById(R.id.desc_unit_size);
        edit_unit_size = (EditText) findViewById(R.id.edit_unit_size);
        desc_row_size = (TextView) findViewById(R.id.desc_row_size);
        edit_row_size = (EditText) findViewById(R.id.edit_row_size);
        title_weapons = (TextView) findViewById(R.id.title_weapons);
        spinner_weapon_cc = (Spinner) findViewById(R.id.spinner_weapon_cc);
        spinner_weapon_shooting = (Spinner) findViewById(R.id.spinner_weapon_shooting);
        title_lost_hp = (TextView) findViewById(R.id.title_lost_hp);
        button_lost_hp_decrease = (Button) findViewById(R.id.button_lost_hp_decrease);
        button_lost_hp_increase = (Button) findViewById(R.id.button_lost_hp_increase);
        lost_hp_textview = (TextView) findViewById(R.id.lost_hp_textview);
        title_command_group = (TextView) findViewById(R.id.title_command_group);
        desc_champion = (TextView) findViewById(R.id.desc_champion);
        checkbox_champion = (CheckBox) findViewById(R.id.checkbox_champion);
        desc_musician = (TextView) findViewById(R.id.desc_musician);
        checkbox_musician = (CheckBox) findViewById(R.id.checkbox_musician);
        desc_standard = (TextView) findViewById(R.id.desc_standard);
        checkbox_standard = (CheckBox) findViewById(R.id.checkbox_standard);
        title_special = (TextView) findViewById(R.id.title_special);
        desc_BSB = (TextView) findViewById(R.id.desc_BSB);
        checkbox_BSB = (CheckBox) findViewById(R.id.checkbox_BSB);
        desc_general = (TextView) findViewById(R.id.desc_general);
        checkbox_general = (CheckBox) findViewById(R.id.checkbox_general);
        desc_general_dis = (TextView) findViewById(R.id.desc_general_dis);
        general_dis_textview = (TextView) findViewById(R.id.general_dis_textview);
        button_general_dis_decrease = (Button) findViewById(R.id.button_general_dis_decrease);
        button_general_dis_increase = (Button) findViewById(R.id.button_general_dis_increase);
        button_done = (Button) findViewById(R.id.button_done);

        //set watchers for the two edit text fields
        edit_unit_size.addTextChangedListener(unitSizeWatcher);
        edit_row_size.addTextChangedListener(rowSizeWatcher);
        edit_unit_size.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edit_row_size.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        //onClick listeners for all five buttons
        button_general_dis_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseGeneralDiscipline(v);
            }
        });
        button_general_dis_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseGeneralDiscipline(v);
            }
        });
        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUnit(v);
            }
        });
        button_lost_hp_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseLostHp(v);
            }
        });
        button_lost_hp_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseLostHp(v);
            }
        });

        //prepare spinners for choices
        armybookList = new ArrayList<>();
        dataAdapterArmybooks = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, armybookList);
        dataAdapterArmybooks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_armybook.setAdapter(dataAdapterArmybooks);

        armybookEntryList = new ArrayList<>();
        dataAdapterArmybookEntries = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, armybookEntryList);
        dataAdapterArmybookEntries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_unit_type.setAdapter(dataAdapterArmybookEntries);

        weaponCCList = new ArrayList<>();
        dataAdapterWeaponsCC = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weaponCCList);
        dataAdapterWeaponsCC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_weapon_cc.setAdapter(dataAdapterWeaponsCC);

        weaponShootingList = new ArrayList<>();
        dataAdapterWeaponShooting = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weaponShootingList);
        dataAdapterWeaponShooting.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_weapon_shooting.setAdapter(dataAdapterWeaponShooting);

        //set spinner listeners
        spinner_armybook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!armybookSpinnerInitialized) {
                    armybookSpinnerInitialized = true;
                } else {
                    armybook = (String) parentView.getItemAtPosition(position);
                    updateArmybookEntryChoices();
                    armybookEntry = (String) spinner_unit_type.getItemAtPosition(0);
                    spinner_unit_type.setSelection((getIndex(spinner_unit_type, armybookEntry)));
                    updateWeaponSpinners();
                    if (spinner_weapon_cc.getVisibility() == View.VISIBLE) {
                        weaponCC = (String) spinner_weapon_cc.getItemAtPosition(0);
                    }
                    if (spinner_weapon_shooting.getVisibility() == View.VISIBLE) {
                        weaponShooting = (String) spinner_weapon_shooting.getItemAtPosition(0);
                    }
                    updateLostHpChooser();
                    updateEditFields();
                    updateCommandOptions();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        spinner_unit_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!armybookEntrySpinnerInitialized) {
                    armybookEntrySpinnerInitialized = true;
                } else {
                    armybookEntry = (String) parentView.getItemAtPosition(position);
                    updateWeaponSpinners();
                    if (spinner_weapon_cc.getVisibility() == View.VISIBLE) {
                        weaponCC = (String) spinner_weapon_cc.getItemAtPosition(0);
                    }
                    if (spinner_weapon_shooting.getVisibility() == View.VISIBLE) {
                        weaponShooting = (String) spinner_weapon_shooting.getItemAtPosition(0);;
                    }
                    updateLostHpChooser();
                    updateEditFields();
                    updateCommandOptions();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        spinner_weapon_cc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!weaponCCSpinnerInitialized) {
                    weaponCCSpinnerInitialized = true;
                } else {
                    weaponCC = (String) parentView.getItemAtPosition(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        spinner_weapon_shooting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!weaponShootingSpinnerInitialized) {
                    weaponShootingSpinnerInitialized = true;
                } else {
                    weaponShooting = (String) parentView.getItemAtPosition(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getIntent().hasExtra("simulatorService")) {
            simulatorService = gson.fromJson(getIntent().getStringExtra("simulatorService"), SimulatorService.class);
        } else {
            simulatorService = new SimulatorService();
        }

        //add choices for spinners
        armybookList = simulatorService.getArmybookNames();
        dataAdapterArmybooks.clear();
        dataAdapterArmybooks.addAll(armybookList);
        dataAdapterArmybooks.notifyDataSetChanged();
        armybookEntryList = simulatorService.getArmybookEntryNames();
        dataAdapterArmybookEntries.clear();
        dataAdapterArmybookEntries.addAll(armybookEntryList);
        dataAdapterArmybookEntries.notifyDataSetChanged();

        //find out which unit to create/update and load it
        unitNumber = simulatorService.unitNumber();
        Unit unit = simulatorService.getUnit(unitNumber);

        //load values from specified unit
        if (unit != null) {
            armybook = unit.getArmybookEntry().getArmybook().toString();
            armybookEntry = unit.getArmybookEntry().getName();
            modelCount = unit.getModelCount();
            rowModels = unit.getRowModels();
            lostHp = unit.getLostHitPoints();
            champion = unit.getChampion();
            musician = unit.getMusician();
            standard = unit.getStandard();
            bsb = unit.getBsb();
            charge = unit.getCharge();
            standAndShoot = unit.getStandAndShoot();
            if (unit.getGeneralLeadership() > 0) {
                general = 1;
                generalDiscipline = unit.getGeneralLeadership();
            }
            weaponCC = getCCWeaponFromUnit(unit);
            weaponShooting = getShootingWeaponFromUnit(unit);
        } else { //in case no unit is loaded
            general_dis_textview.setText("" + generalDiscipline);
            desc_general_dis.setVisibility(View.GONE);
            button_general_dis_decrease.setVisibility(View.GONE);
            general_dis_textview.setVisibility(View.GONE);
            button_general_dis_increase.setVisibility(View.GONE);
            title_weapons.setVisibility(View.GONE);
            spinner_weapon_cc.setVisibility(View.GONE);
            spinner_weapon_shooting.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //display last saved values
        title.setText(getString(R.string.title_create_unit, unitNumber));
        if (armybook != null) {
            updateArmybookEntryChoices();
            spinner_armybook.setSelection(getIndex(spinner_armybook, armybook));
        } else {
            armybook = (String) spinner_armybook.getItemAtPosition(0);
            updateArmybookEntryChoices();
        }
        if (armybookEntry != null) {
            spinner_unit_type.setSelection(getIndex(spinner_unit_type, armybookEntry));
            updateLostHpChooser();
            updateWeaponSpinners();
            updateEditFields();
            updateCommandOptions();
        } else {
            armybookEntry = (String) spinner_unit_type.getItemAtPosition(0);
            updateWeaponSpinners();
            if (spinner_weapon_cc.getVisibility() == View.VISIBLE) {
                weaponCC = (String) spinner_weapon_cc.getItemAtPosition(0);
            }
            if (spinner_weapon_shooting.getVisibility() == View.VISIBLE) {
                weaponShooting = (String) spinner_weapon_shooting.getItemAtPosition(0);
            }
        }
        if (weaponCC != null) {
            if (spinner_weapon_cc.getVisibility() == View.VISIBLE) {
                spinner_weapon_cc.setSelection(getIndex(spinner_weapon_cc, weaponCC));
            }
        } else {
            if (spinner_weapon_cc.getVisibility() == View.VISIBLE) {
                weaponCC = (String) spinner_weapon_cc.getItemAtPosition(0);
            }
        }
        if (weaponShooting != null) {
            if (spinner_weapon_shooting.getVisibility() == View.VISIBLE) {
                spinner_weapon_shooting.setSelection(getIndex(spinner_weapon_shooting, weaponShooting));
            }
        } else {
            if (spinner_weapon_shooting.getVisibility() == View.VISIBLE) {
                weaponShooting = (String) spinner_weapon_shooting.getItemAtPosition(0);
            }
        }
        edit_unit_size.setText("" + modelCount);
        edit_row_size.setText("" + rowModels);
        lost_hp_textview.setText("" + lostHp);
        if (champion == 1) {checkbox_champion.setChecked(true);}
        if (musician == 1) {checkbox_musician.setChecked(true);}
        if (standard == 1) {checkbox_standard.setChecked(true);}
        if (bsb == 1) {checkbox_BSB.setChecked(true);}
        if (general == 1) {
            checkbox_general.setChecked(true);
            general_dis_textview.setText("" + generalDiscipline);
            desc_general_dis.setVisibility(View.VISIBLE);
            button_general_dis_decrease.setVisibility(View.VISIBLE);
            general_dis_textview.setVisibility(View.VISIBLE);
            button_general_dis_increase.setVisibility(View.VISIBLE);
        } else {
            checkbox_general.setChecked(false);
            general_dis_textview.setText("" + generalDiscipline);
            desc_general_dis.setVisibility(View.GONE);
            button_general_dis_decrease.setVisibility(View.GONE);
            general_dis_textview.setVisibility(View.GONE);
            button_general_dis_increase.setVisibility(View.GONE);
        }
        updateLostHpChooser();
        updateWeaponSpinners();
    }

    //method to update boolean variables when any checkbox is checked
    //general checkbox also displays or hides the discipline fields
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_BSB:
                if (checked)
                    bsb = 1;
                else
                    bsb = 0;
                break;
            case R.id.checkbox_general:
                if (checked) {
                    general = 1;
                    desc_general_dis.setVisibility(View.VISIBLE);
                    button_general_dis_decrease.setVisibility(View.VISIBLE);
                    general_dis_textview.setVisibility(View.VISIBLE);
                    button_general_dis_increase.setVisibility(View.VISIBLE);
                }
                else {
                    general = 0;
                    desc_general_dis.setVisibility(View.GONE);
                    button_general_dis_decrease.setVisibility(View.GONE);
                    general_dis_textview.setVisibility(View.GONE);
                    button_general_dis_increase.setVisibility(View.GONE);
                }
                break;
            case R.id.checkbox_champion:
                if (checked)
                    champion = 1;
                else
                    champion = 0;
                break;
            case R.id.checkbox_musician:
                if (checked)
                    musician = 1;
                else
                    musician = 0;
                break;
            case R.id.checkbox_standard:
                if (checked)
                    standard = 1;
                else
                    standard = 0;
                break;
        }
    }

    //edit text field watchers
    //watcher for the unit size field - updates unitSize variable when edit field is changed
    private final TextWatcher unitSizeWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        public void afterTextChanged(Editable s) {
            try {
                modelCount = Integer.parseInt(edit_unit_size.getText().toString());
            } catch(NumberFormatException ex){}
        }
    };
    //watcher for the row size field - updates rowModels variable when edit field is changed
    private final TextWatcher rowSizeWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        public void afterTextChanged(Editable s) {

            try {
                rowModels = Integer.parseInt(edit_row_size.getText().toString());
            } catch(NumberFormatException ex){}
        }
    };

    //button methods
    private void increaseGeneralDiscipline(View view) {
        generalDiscipline = min(generalDiscipline + 1, 10);
        general_dis_textview.setText("" + generalDiscipline);
    }

    private void decreaseGeneralDiscipline(View view) {
        generalDiscipline = max(generalDiscipline - 1, 1);
        general_dis_textview.setText("" + generalDiscipline);
    }

    private void increaseLostHp(View view) {
        lostHp = min(lostHp + 1, simulatorService.getHp(armybookEntry) - 1);
        lost_hp_textview.setText("" + lostHp);
    }

    private void decreaseLostHp(View view) {
        lostHp = max(lostHp - 1, 0);
        lost_hp_textview.setText("" + lostHp);
    }

    private void saveUnit(View view) {
        simulatorService.setUnitMakerAccessed(simulatorService.getUnitMakerAccessed() + 1);
        int generalDisciplineLoaded;
        if (general == 1) {
            generalDisciplineLoaded = generalDiscipline;
        } else {
            generalDisciplineLoaded = 0;
        }
        simulatorService.saveUnit(unitNumber, armybookEntry, modelCount, rowModels, champion, musician, standard, generalDisciplineLoaded, bsb, charge, standAndShoot, weaponCC, weaponShooting, lostHp);
        if (unitNumber == 1) {
            repeatUnitCreation();
        } else {
            confirmation();
        }
    }

    //launching activities
    private void repeatUnitCreation() {
        Intent intent = new Intent(this, MainActivity.class);
        gson = new Gson();
        String serviceJson = gson.toJson(simulatorService);
        intent.putExtra("simulatorService", serviceJson);
        startActivity(intent);
    }

    //launch conformation
    private void confirmation() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        gson = new Gson();
        String serviceJson = gson.toJson(simulatorService);
        intent.putExtra("simulatorService", serviceJson);
        startActivity(intent);
    }

    //helper methods
    private void updateEditFields() {
        if (simulatorService.isSingleModel(armybookEntry)) {
            modelCount = 1;
            rowModels = 1;
            edit_unit_size.setText("" + modelCount);
            edit_row_size.setText("" + rowModels);
            disableEditText(edit_unit_size);
            disableEditText(edit_row_size);
        } else {
            enableEditText(edit_unit_size);
            enableEditText(edit_row_size);
        }
    }

    private void updateCommandOptions() {
        if (simulatorService.championDisabled(armybookEntry)) {
            desc_champion.setVisibility(View.GONE);
            checkbox_champion.setVisibility(View.GONE);
        } else {
            desc_champion.setVisibility(View.VISIBLE);
            checkbox_champion.setVisibility(View.VISIBLE);
        }
        if (simulatorService.musicianDisabled(armybookEntry)) {
            desc_musician.setVisibility(View.GONE);
            checkbox_musician.setVisibility(View.GONE);
        } else {
            desc_musician.setVisibility(View.VISIBLE);
            checkbox_musician.setVisibility(View.VISIBLE);
        }
        if (simulatorService.standardDisabled(armybookEntry)) {
            desc_standard.setVisibility(View.GONE);
            checkbox_standard.setVisibility(View.GONE);
        } else {
            desc_standard.setVisibility(View.VISIBLE);
            checkbox_standard.setVisibility(View.VISIBLE);
        }
        if (simulatorService.championDisabled(armybookEntry) && simulatorService.musicianDisabled(armybookEntry) && simulatorService.standardDisabled(armybookEntry)) {
            title_command_group.setVisibility(View.GONE);
        } else {
            title_command_group.setVisibility(View.VISIBLE);
        }
    }

    private void updateArmybookEntryChoices() {
        armybookEntryList = simulatorService.getArmybookEntryNames(armybook);
        dataAdapterArmybookEntries.clear();
        dataAdapterArmybookEntries.addAll(armybookEntryList);
        dataAdapterArmybookEntries.notifyDataSetChanged();
    }

    private void updateWeaponCCChoices() {
        if (armybookEntry != null) {
            weaponCCList = simulatorService.getWeaponChoices(armybookEntry);
            dataAdapterWeaponsCC.clear();
            dataAdapterWeaponsCC.addAll(weaponCCList);
            dataAdapterWeaponsCC.notifyDataSetChanged();
        }
    }

    private void updateWeaponShootingChoices() {
        if (armybookEntry != null) {
            weaponShootingList = simulatorService.getShootingWeaponChoices(armybookEntry);
            dataAdapterWeaponShooting.clear();
            dataAdapterWeaponShooting.addAll(weaponShootingList);
            dataAdapterWeaponShooting.notifyDataSetChanged();
        }
    }

    private void updateWeaponSpinners() {
        if (armybookEntry != null) {
            if (simulatorService.weaponCCSelection(armybookEntry)) {
                updateWeaponCCChoices();
                spinner_weapon_cc.setVisibility(View.VISIBLE);
            } else {
                spinner_weapon_cc.setVisibility(View.GONE);
                weaponCC = null;
            }
            if (simulatorService.weaponShootingSelection(armybookEntry)) {
                updateWeaponShootingChoices();
                spinner_weapon_shooting.setVisibility(View.VISIBLE);
            } else {
                spinner_weapon_shooting.setVisibility(View.GONE);
                weaponShooting = null;
            }
            if (simulatorService.weaponCCSelection(armybookEntry) || simulatorService.weaponShootingSelection(armybookEntry)) {
                title_weapons.setVisibility(View.VISIBLE);
            } else {
                title_weapons.setVisibility(View.GONE);
            }
        }
    }

    private String getCCWeaponFromUnit(Unit unit) {
        List<WeaponType> actualWeapons = unit.getActualWeapons();
        if (actualWeapons.size() == 1) {
            return actualWeapons.get(0).toString();
        } else if (actualWeapons.size() > 1) {
            List<List<WeaponType>> allPossibilities = unit.getArmybookEntry().getPossibleWeaponsList();
            if (allPossibilities != null) {
                for (List<WeaponType> possibleWeapons : allPossibilities) {
                    if (possibleWeapons.size() > 1) {
                        int index = allPossibilities.indexOf(possibleWeapons);
                        return actualWeapons.get(index).toString();
                    }
                }
                return null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private String getShootingWeaponFromUnit(Unit unit) {
        List<WeaponTypeShooting> actualWeapons = unit.getActualShootingWeapons();
        if (actualWeapons.size() == 1) {
            return actualWeapons.get(0).toString();
        } else if (actualWeapons.size() > 1) {
            List<List<WeaponTypeShooting>> allPossibilities = unit.getArmybookEntry().getPossibleShootingWeaponsList();
            if (allPossibilities != null) {
                for (List<WeaponTypeShooting> possibleWeapons : allPossibilities) {
                    if (possibleWeapons.size() > 1) {
                        int index = allPossibilities.indexOf(possibleWeapons);
                        return actualWeapons.get(index).toString();
                    }
                }
                return null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    private void disableEditText(EditText editText) {
        editText.setEnabled(false);
    }

    private void enableEditText(EditText editText) {
        editText.setEnabled(true);
    }

    private void updateLostHpChooser() {
        if (simulatorService.getHp(armybookEntry) > 1) {
            title_lost_hp.setVisibility(View.VISIBLE);
            button_lost_hp_decrease.setVisibility(View.VISIBLE);
            lost_hp_textview.setVisibility(View.VISIBLE);
            button_lost_hp_increase.setVisibility(View.VISIBLE);
        } else {
            title_lost_hp.setVisibility(View.GONE);
            button_lost_hp_decrease.setVisibility(View.GONE);
            lost_hp_textview.setVisibility(View.GONE);
            button_lost_hp_increase.setVisibility(View.GONE);
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
