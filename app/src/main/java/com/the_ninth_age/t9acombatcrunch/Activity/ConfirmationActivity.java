package com.the_ninth_age.t9acombatcrunch.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.the_ninth_age.t9acombatcrunch.R;
import com.the_ninth_age.t9acombatcrunch.Service.SimulatorService;
import com.the_ninth_age.t9acombatcrunch.Service.Units.SpecialRule;
import com.the_ninth_age.t9acombatcrunch.Service.Units.Unit;
import com.the_ninth_age.t9acombatcrunch.Service.Units.WeaponType;
import com.the_ninth_age.t9acombatcrunch.Service.Units.WeaponTypeShooting;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ConfirmationActivity extends AppCompatActivity {

    SimulatorService simulatorService;
    Gson gson;
    Resources res;

    //interface elements
    TextView title_unit1;
    TextView title_unit2;
    TextView desc_unit1;
    TextView desc_unit2;
    TextView desc_unit1_charge;
    TextView desc_unit2_charge;
    CheckBox checkbox_unit1_charge;
    CheckBox checkbox_unit2_charge;
    TextView desc_unit1_shoot;
    TextView desc_unit2_shoot;
    CheckBox checkbox_unit1_shoot;
    CheckBox checkbox_unit2_shoot;
    TextView title_combat_phases;
    Button button_rounds_decrease;
    TextView rounds_textview;
    Button button_rounds_increase;
    Button button_redo_units;
    Button button_run_simulator;
    Button button_run_debug;

    //variables;
    Unit unit1;
    Unit unit2;
    int rounds = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        res = getResources();
        gson = new Gson();
        simulatorService = gson.fromJson(getIntent().getStringExtra("simulatorService"), SimulatorService.class);

        //identify interface elements
        title_unit1 = (TextView) findViewById(R.id.title_unit1);
        title_unit2 = (TextView) findViewById(R.id.title_unit2);
        desc_unit1 = (TextView) findViewById(R.id.desc_unit1);
        desc_unit2 = (TextView) findViewById(R.id.desc_unit2);
        desc_unit1_charge = (TextView) findViewById(R.id.desc_unit1_charge);
        desc_unit2_charge = (TextView) findViewById(R.id.desc_unit2_charge);
        checkbox_unit1_charge = (CheckBox) findViewById(R.id.checkbox_unit1_charge);
        checkbox_unit2_charge = (CheckBox) findViewById(R.id.checkbox_unit2_charge);
        desc_unit1_shoot = (TextView) findViewById(R.id.desc_unit1_shoot);
        desc_unit2_shoot = (TextView) findViewById(R.id.desc_unit2_shoot);
        checkbox_unit1_shoot = (CheckBox) findViewById(R.id.checkbox_unit1_shoot);
        checkbox_unit2_shoot = (CheckBox) findViewById(R.id.checkbox_unit2_shoot);
        title_combat_phases = (TextView) findViewById(R.id.title_combat_phases);
        button_rounds_decrease = (Button) findViewById(R.id.button_rounds_decrease);
        button_rounds_increase = (Button) findViewById(R.id.button_rounds_increase);
        rounds_textview = (TextView) findViewById(R.id.rounds_textview);
        button_redo_units = (Button) findViewById(R.id.button_redo_units_confirmation);
        button_run_simulator = (Button) findViewById(R.id.button_run_simulator);
        button_run_debug = (Button) findViewById(R.id.button_run_debug);

        //onClick listeners for the buttons
        button_rounds_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseRounds(v);
            }
        });
        button_rounds_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseRounds(v);
            }
        });
        button_redo_units.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatUnitCreation();
            }
        });
        button_run_simulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSimulator();
            }
        });
        button_run_debug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runDebug();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        //load units from simulator service
        unit1 = simulatorService.getUnit(1);
        unit2 = simulatorService.getUnit(2);

        //load unit names and descriptions
        title_unit1.setText(unit1.getName());
        title_unit2.setText(unit2.getName());
        String unit1Count = res.getQuantityString(R.plurals.confirmation_unit_count, unit1.getModelCount(), unit1.getModelCount(), unit1.getRowModels());
        String unit1LostHp = getLostHp(unit1);
        String unit1Weapons = getChosenWeapons(unit1);
        String unit1Special = getCommandGeneralBsb(unit1);
        desc_unit1.setText(unit1Count + unit1LostHp + unit1Weapons + unit1Special);
        String unit2Count = res.getQuantityString(R.plurals.confirmation_unit_count, unit2.getModelCount(), unit2.getModelCount(), unit2.getRowModels());
        String unit2LostHp = getLostHp(unit2);
        String unit2Weapons = getChosenWeapons(unit2);
        String unit2Special = getCommandGeneralBsb(unit2);
        desc_unit2.setText(unit2Count + unit2LostHp + unit2Weapons + unit2Special);
        //if warmachine, hide charge checkbox
        if (unit1.getSpecialRules().contains(SpecialRule.WARMACHINE)) {
            checkbox_unit1_charge.setVisibility(View.GONE);
            desc_unit1_charge.setVisibility(View.GONE);
        }
        if (unit2.getSpecialRules().contains(SpecialRule.WARMACHINE)) {
            checkbox_unit2_charge.setVisibility(View.GONE);
            desc_unit2_charge.setVisibility(View.GONE);
        }
        //display stand and shoot if applicable
        if (!standAndShootPossible(unit1)) {
            desc_unit1_shoot.setVisibility(View.GONE);
            checkbox_unit1_shoot.setVisibility(View.GONE);
        }
        if (!standAndShootPossible(unit2)) {
            desc_unit2_shoot.setVisibility(View.GONE);
            checkbox_unit2_shoot.setVisibility(View.GONE);
        }
        //load status of checkboxes
        if (unit1.getCharge() == 1) {
            checkbox_unit1_charge.setChecked(true);
            checkbox_unit1_shoot.setEnabled(false);
            checkbox_unit2_shoot.setEnabled(true);
        } else {
            checkbox_unit1_charge.setChecked(false);
            checkbox_unit2_shoot.setEnabled(false);
        }
        if (unit2.getCharge() == 1) {
            checkbox_unit2_charge.setChecked(true);
            checkbox_unit2_shoot.setEnabled(false);
            checkbox_unit1_shoot.setEnabled(true);
        } else {
            checkbox_unit2_charge.setChecked(false);
            checkbox_unit1_shoot.setEnabled(false);
        }
        if (unit1.getStandAndShoot() == 1) {
            checkbox_unit1_shoot.setChecked(true);
        } else {
            checkbox_unit1_shoot.setChecked(false);
        }
        if (unit2.getStandAndShoot() == 1) {
            checkbox_unit2_shoot.setChecked(true);
        } else {
            checkbox_unit2_shoot.setChecked(false);
        }
    }

    //method to update units when any checkbox is checked
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_unit1_charge:
                if (checked) {
                    unit1.setCharge(1);
                    unit2.setCharge(0);
                    unit1.setStandAndShoot(0);
                    checkbox_unit2_charge.setChecked(false);
                    checkbox_unit1_shoot.setChecked(false);
                    checkbox_unit1_shoot.setEnabled(false);
                    checkbox_unit2_shoot.setEnabled(true);
                }
                else {
                    unit1.setCharge(0);
                    unit2.setStandAndShoot(0);
                    checkbox_unit2_shoot.setEnabled(false);
                    checkbox_unit2_shoot.setChecked(false);
                }
                break;
            case R.id.checkbox_unit2_charge:
                if (checked) {
                    unit2.setCharge(1);
                    unit1.setCharge(0);
                    unit2.setStandAndShoot(0);
                    checkbox_unit1_charge.setChecked(false);
                    checkbox_unit2_shoot.setChecked(false);
                    checkbox_unit2_shoot.setEnabled(false);
                    checkbox_unit1_shoot.setEnabled(true);
                }
                else {
                    unit2.setCharge(0);
                    unit1.setStandAndShoot(0);
                    checkbox_unit1_shoot.setEnabled(false);
                    checkbox_unit1_shoot.setChecked(false);
                }
                break;
            case R.id.checkbox_unit1_shoot:
                if (checked) {
                    unit1.setStandAndShoot(1);
                }
                else {
                    unit1.setStandAndShoot(0);
                }
                break;
            case R.id.checkbox_unit2_shoot:
                if (checked) {
                    unit2.setStandAndShoot(1);
                }
                else {
                    unit2.setStandAndShoot(0);
                }
                break;
        }
    }

    //button methods
    private void increaseRounds(View view) {
        rounds = min(rounds + 1, 12);
        rounds_textview.setText("" + rounds);
    }

    private void decreaseRounds(View view) {
        rounds = max(rounds - 1, 1);
        rounds_textview.setText("" + rounds);
    }

    //launching activities
    private void repeatUnitCreation() {
        saveChanges();
        Intent intent = new Intent(this, MainActivity.class);
        gson = new Gson();
        String serviceJson = gson.toJson(simulatorService);
        intent.putExtra("simulatorService", serviceJson);
        startActivity(intent);
    }

    private void startSimulator() {
        simulatorService.setRoundsRequested(rounds);
        saveChanges();
        Intent intent = new Intent(this, ResultActivity.class);
        gson = new Gson();
        String serviceJson = gson.toJson(simulatorService);
        intent.putExtra("simulatorService", serviceJson);
        startActivity(intent);
    }

    private void runDebug() {
        simulatorService.setRoundsRequested(rounds);
        saveChanges();
        Intent intent = new Intent(this, DebugActivity.class);
        gson = new Gson();
        String serviceJson = gson.toJson(simulatorService);
        intent.putExtra("simulatorService", serviceJson);
        startActivity(intent);
    }

    //helper methods
    private void saveChanges() {
        simulatorService.setUnit(1, unit1);
        simulatorService.setUnit(2, unit2);
    }

    private String getCCWeaponFromUnit(Unit unit) {
        List<List<WeaponType>> allPossibilities = unit.getArmybookEntry().getPossibleWeaponsList();
        List<WeaponType> chosenWeapons = unit.getActualWeapons();
        if (allPossibilities != null && chosenWeapons != null) {
            for (List<WeaponType> list : allPossibilities) {
                if (list.size() > 1) {
                    int index = allPossibilities.indexOf(list);
                    return chosenWeapons.get(index).toString();
                }
            }
            return null;
        } else {
            return null;
        }
    }

    private String getShootingWeaponFromUnit(Unit unit) {
        List<List<WeaponTypeShooting>> allPossibilities = unit.getArmybookEntry().getPossibleShootingWeaponsList();
        List<WeaponTypeShooting> chosenWeapons = unit.getActualShootingWeapons();
        if (allPossibilities != null && chosenWeapons != null) {
            for (List<WeaponTypeShooting> list : allPossibilities) {
                if (list.size() > 1) {
                    int index = allPossibilities.indexOf(list);
                    return chosenWeapons.get(index).toString();
                }
            }
            return null;
        } else {
            return null;
        }
    }

    private String getLostHp(Unit unit) {
        if (unit.getLostHitPoints() > 0) {
            return "\n" + getString(R.string.lost_hp) + ": " + unit.getLostHitPoints();
        } else {
            return "";
        }
    }

    private String getChosenWeapons(Unit unit) {
        String text = "";
        if (getCCWeaponFromUnit(unit) != null) {
            text = "\n" + getString(R.string.chosen_weapons) + " " + getCCWeaponFromUnit(unit);
        }
        if (getShootingWeaponFromUnit(unit) != null) {
            if (!text.equals("")) {
                text += ", ";
            } else {
                text = "\n" + getString(R.string.chosen_weapons) + " ";
            }
            text += getShootingWeaponFromUnit(unit);
        }
        return text;
    }

    private String getCommandGeneralBsb(Unit unit) {
        String text = "";
        if (unit.getChampion() == 1) {
            text += "\n" + getString(R.string.champion);
        }
        if (unit.getMusician() == 1) {
            if (!text.equals("")) {
                text += ", ";
            } else {
                text = "\n";
            }
            text += getString(R.string.musician);
        }
        if (unit.getStandard() == 1) {
            if (!text.equals("")) {
                text += ", ";
            } else {
                text = "\n";
            }
            text += getString(R.string.standard);
        }
        if (unit.getGeneralLeadership() > 0) {
            if (!text.equals("")) {
                text += ", ";
            } else {
                text = "\n";
            }
            text += getString(R.string.general) + " (Dis " + unit.getGeneralLeadership() + ")";
        }
        if (unit.getBsb() == 1) {
            if (!text.equals("")) {
                text += ", ";
            } else {
                text = "\n";
            }
            text += getString(R.string.bsb);
        }
        return text;
    }

    private boolean standAndShootPossible(Unit unit) {
        if (unit.getSpecialRules().contains(SpecialRule.RELOAD)) {
            return false;
        }
        List<WeaponTypeShooting> shootingWeapons = unit.getActualShootingWeapons();
        for (WeaponTypeShooting weapon : shootingWeapons) {
            if (!weapon.equals(WeaponTypeShooting.NONE)) {
                return true;
            }
        }
        return false;
    }



}
