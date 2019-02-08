package com.the_ninth_age.t9acombatcrunch.Service;

import com.the_ninth_age.t9acombatcrunch.Service.Result.CombatOutcome;
import com.the_ninth_age.t9acombatcrunch.Service.Result.Outcome;
import com.the_ninth_age.t9acombatcrunch.Service.Units.OffensiveProfile;
import com.the_ninth_age.t9acombatcrunch.Service.Units.SpecialRule;
import com.the_ninth_age.t9acombatcrunch.Service.Units.Unit;
import com.the_ninth_age.t9acombatcrunch.Service.Units.WeaponType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Combat {

    private Unit unit1;
    private Unit unit2;
    private Unit unit1Original;
    private Unit unit2Original;
    private List<Unit> units;

    private int round;
    private CombatOutcome outcome;
    private int rolled6;
    private List<String> combatDescription;

    public Combat(Unit unit1, Unit unit2) {
        this.unit1 = unit1;
        this.unit1Original = unit1;
        this.unit2 = unit2;
        this.unit2Original = unit2;
        this.units = new ArrayList<>();
        this.units.add(this.unit1);
        this.units.add(this.unit2);

        this.round = 0;
        this.rolled6 = 0;
        this.combatDescription = new ArrayList<String>();
    }

    public Outcome fullCombat(int combatPhases) {
        round = 0;
        outcome = null;
        rolled6 = 0;

        //special rules

        //discipline modifiers
        for (Unit unit : units) {
            if (unit.getGeneralLeadership() > 0) {
                int leadership = max(unit.getGeneralLeadership(), unit.getLeadership());
                unit.setLeadership(leadership);
            }
            if (unit.getSpecialRules().contains(SpecialRule.AURA_OF_MADNESS)) {
                identifyOpposingUnit(unit).setLeadership(unit.getLeadership() - 1);
            }
            if (unit.getSpecialRules().contains(SpecialRule.FEAR) && !identifyOpposingUnit(unit).getSpecialRules().contains(SpecialRule.FEAR) && !identifyOpposingUnit(unit).getSpecialRules().contains(SpecialRule.FEARLESS) && !identifyOpposingUnit(unit).getSpecialRules().contains(SpecialRule.DRUNK)) {
                identifyOpposingUnit(unit).setLeadership(unit.getLeadership() - 1);
            }
        }

        //go through combat phases
        for (int i = 0; i < combatPhases; i++) {
            combatPhase();
            if (outcome != null) {
                break;
            }
        }
        if (outcome == null) {
            outcome = CombatOutcome.COMBAT_CONTINUES;
        }
        combatDescription.add("Final result is " + outcome + " in combat phase number " + round);
        Outcome fullOutcome = new Outcome(round, outcome, combatDescription);

        unit1.resetUnit();
        unit2.resetUnit();
        return fullOutcome;
    }

    public void combatPhase() {
        //preparation
        round += 1;
        unit1.setWoundsInRound(0);
        unit2.setWoundsInRound(0);
        combatDescription.add("");
        combatDescription.add("- ROUND " + round + " -");
        combatDescription.add(unit1.getName() + ": " + unit1.getModelCount() + " models, " + unit1.getAllRows() + "x" + unit1.getRowModels()
                + " fighting " + unit2.getName() + ": " + unit2.getModelCount() + " models, " + unit2.getAllRows() + "x" + unit2.getRowModels());
        List<OffensiveProfile> allProfiles = sortByAgi(); //prepare a list of offensive profiles in the correct order

        //special rules
        for (Unit unit : units) {
            if (unit.getSpecialRules().contains(SpecialRule.FEAR) && !identifyOpposingUnit(unit).getSpecialRules().contains(SpecialRule.FEAR) && !identifyOpposingUnit(unit).getSpecialRules().contains(SpecialRule.FEARLESS) && !identifyOpposingUnit(unit).getSpecialRules().contains(SpecialRule.DRUNK)) {
                if (breakTestPassed(identifyOpposingUnit(unit), 0)) {
                    identifyOpposingUnit(unit).setFailedFear(0);
                } else {
                    identifyOpposingUnit(unit).setFailedFear(1);
                }
            }
        }

        //roll the dice
        for (OffensiveProfile profile : allProfiles) {
            combatDescription.add(profile.getName() + " attacking");
            killedModelsOP(profile, identifyOpposingUnit(identifyUnit(profile)));
            if (!nextProfileSimultaneous(allProfiles, profile)) {
                removeCasualties(unit1);
                removeCasualties(unit2);
            }
            if (unit1.getModelCount() <= 0 && unit2.getModelCount() <= 0) {
                outcome = CombatOutcome.MUTUAL_DESTRUCTION;
                combatDescription.add("Both units destroyed, combat ended");
                break;
            } else if (unit1.getModelCount() <= 0) {
                outcome = CombatOutcome.UNIT1_DESTROYED;
                combatDescription.add("Unit " + unit1.getName() + " destroyed, combat ended");
                break;
            } else if (unit2.getModelCount() <= 0) {
                outcome = CombatOutcome.UNIT2_DESTROYED;
                combatDescription.add("Unit " + unit2.getName() + " destroyed, combat ended");
                break;
            }
        }
        if (outcome == null) {
            combatScoreAndBreakTest();
        }

    }

    public int getUnitWidth(Unit unit) {
        int width = unit.getBaseWidth() * unit.getRowModelsCurrent();
        return width;
    }

    public int getModelsInContact(Unit unit) {
        int thisUnitWidth = getUnitWidth(unit);
        int otherUnitWidth = getUnitWidth(identifyOpposingUnit(unit));
        int contactWidth = min(thisUnitWidth, otherUnitWidth);
        int modelsInContact;

        modelsInContact = contactWidth / unit.getBaseWidth();
            if (unit.getRowModelsCurrent() - modelsInContact <= 2) {
                modelsInContact += (unit.getRowModelsCurrent() - modelsInContact);
            } else {
                modelsInContact += 2;
            }
        return modelsInContact;
    }

    private List<OffensiveProfile> listAllProfiles() {
        // put all offensive profiles in combat into one list
        List<OffensiveProfile> allProfiles = new ArrayList<>();
        allProfiles.addAll(unit1.getOffensiveProfiles());
        allProfiles.addAll(unit2.getOffensiveProfiles());
        return allProfiles;
    }

    public List<OffensiveProfile> sortByAgi() {
        List<OffensiveProfile> profiles = listAllProfiles();
        //add agi bonus for charge if applicable
        if (round == 1) {
            for (OffensiveProfile profile : profiles) {
                Unit unit = identifyUnit(profile);
                profile.setAgiCurrent(profile.getAgi() + unit.getCharge());
            }
        } else {
            for (OffensiveProfile profile : profiles) {
                profile.setAgiCurrent(profile.getAgi());
            }
        }
        //sort profiles by current agility
        Collections.sort(profiles);
        Collections.reverse(profiles);
        return profiles;
    }

    public Unit identifyUnit(OffensiveProfile profile) {
        if (profile.getUnitId().equals(unit1.getUnitId())) {
            return unit1;
        } else if (profile.getUnitId().equals(unit2.getUnitId())) {
            return unit2;
        } else {
            return null;
        }
    }

    public Unit identifyOpposingUnit(Unit unit) {
        if (unit.equals(unit1)) {
            return unit2;
        } else if (unit.equals(unit2)) {
            return unit1;
        } else {
            return null;
        }
    }

    public int getSupportingAttacks(OffensiveProfile attacker) {
        int supportingRows = identifyUnit(attacker).getSupportingRows();
        Unit attackingUnit = identifyUnit(attacker);
        //speak gives fight in extra rank
        if (attacker.getActualWeapon().equals(WeaponType.SPEAR)) {
            supportingRows += 1;
        }
        //line formation gives fight in extra rank
        if (attackingUnit.getRowModelsCurrent() >= 8) {
            supportingRows += 1;
        }
        // if there are more rows behind the first than can support, calculate attacks for full supporting rows,
        // otherwise calculate support attacks for all models not in first row
        if (attackingUnit.getAllRows() - 1 > supportingRows) {
            return getModelsInContact(attackingUnit) * supportingRows * min(attacker.getAtt(), attacker.getSupportingAttacks());
        } else if (attackingUnit.getAllRows() == 1) {
            return 0;
        } else {
            return (getModelsInContact(attackingUnit) * (attackingUnit.getAllRows() - 2) + min(attackingUnit.getLastRow(), getModelsInContact(attackingUnit))) * min(attacker.getAtt(), attacker.getSupportingAttacks());
        }
    }

    public int getAllAttacks(OffensiveProfile attacker) {
        Unit attackingUnit = identifyUnit(attacker);
        int attacksPerProfile = attacker.getAtt();
        //in case of a giant with Rage
        if (attacker.getSpecialRules().contains(SpecialRule.RAGE)) {
            attacksPerProfile = attacker.getAtt() + (attackingUnit.getLostHitPoints());
        }
        int frontAttacks = getModelsInContact(attackingUnit) * attacksPerProfile + attackingUnit.getChampion() * attacker.getChampionApplicable();
        int supportingAttacks = getSupportingAttacks(attacker);
        combatDescription.add("Attacks from " + attacker.getName() + ": " + frontAttacks + " front + " + supportingAttacks + " supporting");
        return (frontAttacks + supportingAttacks) * attacker.getCountInUnit();
    }

    public int getHitDifficulty(OffensiveProfile attacker, Unit defender) {
        combatDescription.add("Rolling for hit");
        int diff = attacker.getOff() - defender.getDef();
        int difficulty;
        if (diff > 3) {
            difficulty = 2;
        } else if (diff > 0) {
            difficulty = 3;
        } else if (diff > -4) {
            difficulty = 4;
        } else if (diff > -8) {
            difficulty = 5;
        } else {
            difficulty = 6;
        }
        difficulty = difficulty + identifyUnit(attacker).getFailedFear() - defender.getFailedFear();
        combatDescription.add("Needs to roll at least " + difficulty);
        return difficulty;
    }

    public int getWoundDifficulty(OffensiveProfile attacker, Unit defender) {
        combatDescription.add("Rolling for wound");
        int diff = attacker.getStr() - defender.getRes();
        int difficulty;
        if (diff > 1) {
            difficulty = 2;
        } else if (diff > 0) {
            difficulty = 3;
        } else if (diff > -1) {
            difficulty = 4;
        } else if (diff > -2) {
            difficulty = 5;
        } else {
            difficulty = 6;
        }
        combatDescription.add("Needs to roll at least " + difficulty);
        return difficulty;
    }

    public int getArmorDifficulty(OffensiveProfile attacker, Unit defender) {
        combatDescription.add("Rolling for armor");
        int difficulty = max((defender.getArm() + attacker.getAp()), 2);
        combatDescription.add("Needs to roll at least " + difficulty);
        return difficulty;
    }

    public int getSpecialSavesDifficulty(OffensiveProfile attacker, Unit defender) {
        combatDescription.add("Rolling for special saves");
        if (defender.getAegisSave() == 0 && defender.getFortitudeSave() != 0) {
            combatDescription.add("Needs to roll at least " + defender.getFortitudeSave());
            return defender.getFortitudeSave();
        } else if (defender.getAegisSave() != 0 && defender.getFortitudeSave() == 0) {
            combatDescription.add("Needs to roll at least " + defender.getAegisSave());
            return defender.getAegisSave();
        } else if (defender.getAegisSave() != 0 && defender.getFortitudeSave() != 0) {
            combatDescription.add("Needs to roll at least " + min(defender.getAegisSave(), defender.getFortitudeSave()));
            return min(defender.getAegisSave(), defender.getFortitudeSave());
        } else {
            combatDescription.add("No special saves");
            return 0;
        }
    }

    public int rollSuccess(int times, int difficulty, boolean reroll, boolean rerollNegative) {
        int result = 0;
        rolled6 = 0;
        if (difficulty < 7) {
            for (int i = 0; i < times; i++) {
                int roll = d6();
                //ckeck if roll was a success
                if (roll >= difficulty) {
                    if (rerollNegative) {
                        int roll2 = d6();
                        if (roll2 == 6) { rolled6 += 1; }
                        if (roll2 >= difficulty) { result += 1; }
                    } else {
                        //check if rolled 6
                        if (roll == 6) {  rolled6 += 1;  }
                        result += 1;
                    }
                } else if (reroll) { //if unsuccessful and reroll is applicable
                    int roll2 = d6();
                    if (roll2 == 6) { rolled6 += 1; }
                    if (roll2 >= difficulty) { result += 1; }
                }
            }
        }
        combatDescription.add("Number of successes rolled: " + result);
        return result;
    }

    public void killedModelsOP(OffensiveProfile attacker, Unit defender) {
        int autowound = 0;
        int noArmor = 0;
        boolean rerollAegisNeg = false;

        //roll the dice
        Unit attackingUnit = identifyUnit(attacker);
        int attacks = getAllAttacks(attacker);
        //hit
        int hits = rollSuccess(attacks, getHitDifficulty(attacker, defender), attacker.isRerollHit(), false);

        //wound
        int wounds = rollSuccess(hits, getWoundDifficulty(attacker, defender), attacker.isRerollWound(), false) + autowound;

        //armor
        int woundsAfterArmor = wounds - rollSuccess(wounds, getArmorDifficulty(attacker, defender), false, false) + noArmor;
        //special saves
        int woundsAfterSpecialSaves = woundsAfterArmor;

        if (getSpecialSavesDifficulty(attacker, defender) != 0) {
            int woundsToSave = woundsAfterArmor;
            woundsAfterSpecialSaves -= rollSuccess(woundsToSave, getSpecialSavesDifficulty(attacker, defender), false, rerollAegisNeg);
        }
        //save the numbers
        defender.setWoundsOnAgiStep(defender.getWoundsOnAgiStep() + woundsAfterSpecialSaves);
        defender.setWoundsInRound(defender.getWoundsInRound() + woundsAfterSpecialSaves);
        combatDescription.add("Inflicted " + woundsAfterSpecialSaves + " wounds");
    }

    public void removeCasualties(Unit defender) {
        int lostHitPoints = defender.getWoundsOnAgiStep() + defender.getLostHitPoints();
        int killedModels = lostHitPoints / defender.getHp();
        defender.setLostHitPoints(lostHitPoints % defender.getHp());
        defender.setModelCount(defender.getModelCount() - killedModels);
        combatDescription.add(killedModels + " casualties removed from unit " + defender.getName() + ", " + defender.getModelCount() + " models remaining");
        defender.setWoundsOnAgiStep(0);
    }

    public boolean nextProfileSimultaneous(List<OffensiveProfile> allProfiles, OffensiveProfile currentProfile) {
        OffensiveProfile nextProfile = findNextProfile(allProfiles, currentProfile);
        if (nextProfile != null) {
            if (currentProfile.getAgiCurrent() == nextProfile.getAgiCurrent()) {
                combatDescription.add("Next attack is simultaneous");
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public OffensiveProfile findNextProfile(List<OffensiveProfile> allProfiles, OffensiveProfile currentProfile) {
        int index1 = allProfiles.indexOf(currentProfile);
        int index2 = index1 + 1;
        if (index2 < 0 || index2 >= allProfiles.size()) {
            return null;
        } else {
            return allProfiles.get(index2);
        }
    }

    public void combatScoreAndBreakTest() {
        int unit1Score = getCombatScore(unit1);
        combatDescription.add("Combat result for " + unit1.getName() + " is " + unit1Score);
        int unit2Score = getCombatScore(unit2);
        combatDescription.add("Combat result for " + unit2.getName() + " is " + unit2Score);
        int combatScoreDiff = abs(unit1Score - unit2Score);

        Unit loser = determineLoser(unit1Score, unit2Score);
        if (loser != null) {
            boolean breakTestPassed;
            breakTestPassed = breakTestPassed(loser, combatScoreDiff);
                if (breakTestPassed == false && loser.equals(unit1)) {
                    combatDescription.add(loser.getName() + " fled");
                    outcome = CombatOutcome.UNIT1_FLED;
                    } else if (breakTestPassed == false && loser.equals(unit2)) {
                    combatDescription.add(loser.getName() + " fled");
                    outcome = CombatOutcome.UNIT2_FLED;
                    }
                }
            }

    public int getCombatScore(Unit unit) {
        //get rank bonus only if not in line formation
        int rankBonus = 0;
        if (unit.getRowModelsCurrent() < 8 && !unit.getSpecialRules().contains(SpecialRule.LIGHT_TROOPS) && !unit.getSpecialRules().contains(SpecialRule.SOBER)) {
            rankBonus = min(unit.getFullRanks(), 3);
        }
        //get charge bonus in forst combat phase only
        int chargeBonus = 0;
        if (round == 1) {
            chargeBonus = unit.getCharge();
        }
        int combatScore = identifyOpposingUnit(unit).getWoundsInRound() + unit.getStandard() + chargeBonus + rankBonus;
        return combatScore;
    }

    public Unit determineLoser(int unit1Score, int unit2Score) {
        if (unit1Score > unit2Score) {
            combatDescription.add(unit2.getName() + " lost");
            return unit2;
        } else if (unit2Score > unit1Score) {
            combatDescription.add(unit1.getName() + " lost");
            return unit1;
        } else {
            combatDescription.add("There is no winner");
            return null;
        }
    }

    public boolean breakTestPassed(Unit unit, int combatScoreDiff) {
        //get roll difficulty
        int modifier = combatScoreDiff;
        if (unit.getFullRanks() > identifyOpposingUnit(unit).getFullRanks() || unit.getSpecialRules().contains(SpecialRule.STUBBORN)) {
            modifier = 0;
        }
        int difficulty = unit.getLeadership() - modifier;

        //roll and return result
        combatDescription.add("Must roll less than " + difficulty);
        int roll = d6() + d6();
        combatDescription.add("Roll result is " + roll);
        //if unit has access to BSB reroll and take better
        if (unit.getBsb() == 1) {
            int roll2 = d6() + d6();
            combatDescription.add("Second foll for BSB is " + roll2);
            roll = min(roll, roll2);
        }
        if (roll > difficulty) {
            return false;
        } else {
            combatDescription.add(unit.getName() + " passed discipline test");
            return true;
        }
    }

    public int d6() {
        Random randomGenerator = new Random();
        int d6 = randomGenerator.nextInt(6) + 1;
        combatDescription.add("rolled " + d6);
        return d6;
    }

    public int d3() {
        Random randomGenerator = new Random();
        int d3 = randomGenerator.nextInt(3) + 1;
        combatDescription.add("rolled " + d3);
        return d3;
    }
}
