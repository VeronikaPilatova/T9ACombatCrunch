package com.the_ninth_age.t9acombatcrunch.Service;

import com.the_ninth_age.t9acombatcrunch.Repository.ArmybookEntryRepository;
import com.the_ninth_age.t9acombatcrunch.Service.Result.CombatOutcome;
import com.the_ninth_age.t9acombatcrunch.Service.Result.Outcome;
import com.the_ninth_age.t9acombatcrunch.Service.Units.ArmybookEntry;
import com.the_ninth_age.t9acombatcrunch.Service.Units.Unit;
import com.the_ninth_age.t9acombatcrunch.Service.Units.WeaponType;
import com.the_ninth_age.t9acombatcrunch.Service.Units.WeaponTypeShooting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulatorService {

    private ArmybookEntryRepository armybookEntryRepository;
    private static final int SIMULATIONS = 100;

    private int unitMakerAccessed;
    private Unit unit1;
    private Unit unit2;
    private int roundsRequested;
    private List<Map<CombatOutcome, Integer>> outcomeList;

    public SimulatorService() {
        armybookEntryRepository = new ArmybookEntryRepository();
        unitMakerAccessed = 0;
        roundsRequested = 12;
    }

    //run a specified number of simulations and save results in the list
    public List runSimulator() {
        Combat combat = new Combat(unit1, unit2);
        outcomeList = new ArrayList<>();
        for (int i = 0; i < roundsRequested; i++) {
            //prepare empty maps for results
            Map<CombatOutcome, Integer> outcomeMap = new HashMap<>();
            outcomeMap.put(CombatOutcome.UNIT1_DESTROYED, Integer.valueOf(0));
            outcomeMap.put(CombatOutcome.UNIT1_FLED, Integer.valueOf(0));
            outcomeMap.put(CombatOutcome.COMBAT_CONTINUES, Integer.valueOf(0));
            outcomeMap.put(CombatOutcome.UNIT2_FLED, Integer.valueOf(0));
            outcomeMap.put(CombatOutcome.UNIT2_DESTROYED, Integer.valueOf(0));
            outcomeMap.put(CombatOutcome.MUTUAL_DESTRUCTION, Integer.valueOf(0));
            outcomeList.add(outcomeMap);
        }
        //run simulations and log results
        for (int j = 0; j < SIMULATIONS; j++) {
            Outcome result = combat.fullCombat(roundsRequested);
            CombatOutcome combatOutcome = result.getOutcome();
            int index = result.getRound() - 1;
            Map<CombatOutcome, Integer> outcomeMap = outcomeList.get(index);
            outcomeMap.put(combatOutcome, outcomeMap.get(combatOutcome).intValue() + 1);
        }
        return outcomeList;
    }

    public Outcome runDebugCombat() {
        Combat combat = new Combat(unit1, unit2);
        return combat.fullCombat(roundsRequested);
    }

    public Unit saveUnit(int number, String armybookEntryName, int modelCount, int rowModels, int champion, int musician, int standard, int generalLeadership, int bsb, int charge, int standAndShoot, String ccWeapon, String shootingWeapon, int lostHp) {
        ArmybookEntry armybookEntry = getArmybookEntry(armybookEntryName);
        List<WeaponType> actualWeapons = new ArrayList<>();
        //if a list of possible weapons for an offensive profile only has one choice, add it to the actual list, otherwise use what the front end provided
        for (List<WeaponType> possibleWeapons : armybookEntry.getPossibleWeaponsList()) {
            if (possibleWeapons.size() == 1) {
                actualWeapons.add(possibleWeapons.get(0));
            } else {
                actualWeapons.add(WeaponType.fromString(ccWeapon));
            }
        }
        List<WeaponTypeShooting> actualShootingWeapons = new ArrayList<>();
        //if a list of possible weapons for an offensive profile only has one choice, add it to the actual list, otherwise use what the front end provided
        for (List<WeaponTypeShooting> possibleShootingWeapons : armybookEntry.getPossibleShootingWeaponsList()) {
            if (possibleShootingWeapons.size() == 1) {
                actualShootingWeapons.add(possibleShootingWeapons.get(0));
            } else {
                actualShootingWeapons.add(WeaponTypeShooting.fromString(shootingWeapon));
            }
        }
        Unit newUnit = new Unit(armybookEntry, modelCount, rowModels, champion, musician, standard, generalLeadership, bsb, charge, standAndShoot, actualWeapons, actualShootingWeapons, lostHp);
        //save the new unit in the correct slot and return it
        if (number == 1) {
            unit1 = newUnit;
        } else {
            unit2 = newUnit;
        }
        return newUnit;
    }

    //which units should be created or updated
    public int unitNumber() {
        if (unitMakerAccessed % 2 == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    //populate spinners and helper methods
    public List<String> getArmybookNames() {
        return armybookEntryRepository.getArmybookNames();
    }

    public List<String> getArmybookEntryNames() {
        return armybookEntryRepository.getNamesAll();
    }

    public List<String> getArmybookEntryNames(String armybookName) {
        return armybookEntryRepository.getNamesArmybook(armybookName);
    }

    public List<String> getWeaponChoices(String armybookEntryName) {
        if (armybookEntryName != null) {
            ArmybookEntry armybookEntry = armybookEntryRepository.findSpecific(armybookEntryName);
            List<String> choices = new ArrayList<>();
            for (List<WeaponType> weaponsList : armybookEntry.getPossibleWeaponsList()) {
                if (weaponsList.size() > 1) {
                    for (WeaponType item : weaponsList) {
                        choices.add(item.toString());
                    }
                    return choices;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public List<String> getShootingWeaponChoices(String armybookEntryName) {
        if (armybookEntryName != null) {
            ArmybookEntry armybookEntry = armybookEntryRepository.findSpecific(armybookEntryName);
            List<String> choices = new ArrayList<>();
            for (List<WeaponTypeShooting> weaponsList : armybookEntry.getPossibleShootingWeaponsList()) {
                if (weaponsList.size() > 1) {
                    for (WeaponTypeShooting item : weaponsList) {
                        choices.add(item.toString());
                    }
                    return choices;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public boolean weaponCCSelection(String armybookEntryName) {
        if (armybookEntryName != null) {
            ArmybookEntry armybookEntry = armybookEntryRepository.findSpecific(armybookEntryName);
            for (List<WeaponType> weaponsList : armybookEntry.getPossibleWeaponsList()) {
                if (weaponsList.size() > 1) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean weaponShootingSelection(String armybookEntryName) {
        if (armybookEntryName != null) {
            ArmybookEntry armybookEntry = armybookEntryRepository.findSpecific(armybookEntryName);
            for (List<WeaponTypeShooting> weaponsList : armybookEntry.getPossibleShootingWeaponsList()) {
                if (weaponsList.size() > 1) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    //what should be hidden?
    public boolean isSingleModel(String armybookEntryName) {
        ArmybookEntry armybookEntry = armybookEntryRepository.findSpecific(armybookEntryName);
        return armybookEntry.getMaxModels() == 1;
    }

    public boolean championDisabled(String armybookEntryName) {
        ArmybookEntry armybookEntry = armybookEntryRepository.findSpecific(armybookEntryName);
        return armybookEntry.getChampionPossible() == 0;
    }

    public boolean musicianDisabled(String armybookEntryName) {
        ArmybookEntry armybookEntry = armybookEntryRepository.findSpecific(armybookEntryName);
        return armybookEntry.getMusicianPossible() == 0;
    }

    public boolean standardDisabled(String armybookEntryName) {
        ArmybookEntry armybookEntry = armybookEntryRepository.findSpecific(armybookEntryName);
        return armybookEntry.getStandardPossible() == 0;
    }

    //Getters and setters
    public int getUnitMakerAccessed() {
        return unitMakerAccessed;
    }

    public void setUnitMakerAccessed(int unitMakerAccessed) {
        this.unitMakerAccessed = unitMakerAccessed;
    }

    public int getRoundsRequested() {
        return roundsRequested;
    }

    public void setRoundsRequested(int roundsRequested) {
        this.roundsRequested = roundsRequested;
    }

    public static int getSIMULATIONS() {
        return SIMULATIONS;
    }

    public Unit getUnit(int number) {
        if (number % 2 == 0) {
            return unit2;
        } else {
            return unit1;
        }
    }

    public void setUnit(int number, Unit unit) {
        if (number % 2 == 0) {
            unit2 = unit;
        } else {
            unit1 = unit;
        }
    }

    public int getHp(String armybookEntryName) {
        ArmybookEntry armybookEntry = armybookEntryRepository.findSpecific(armybookEntryName);
        return armybookEntry.getHp();
    }

    public ArmybookEntry getArmybookEntry(String name) {
        return armybookEntryRepository.findSpecific(name);
    }
}
