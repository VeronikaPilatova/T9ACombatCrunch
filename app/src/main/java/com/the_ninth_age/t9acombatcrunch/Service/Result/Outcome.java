package com.the_ninth_age.t9acombatcrunch.Service.Result;

import com.the_ninth_age.t9acombatcrunch.Service.Result.CombatOutcome;

import java.util.List;

public class Outcome {
    private int round;
    private CombatOutcome outcome;
    private List<String> combatDescription;

    public Outcome(int round, CombatOutcome outcome, List<String> combatDescription) {
        this.round = round;
        this.outcome = outcome;
        this.combatDescription = combatDescription;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int newValue) {
        round = newValue;
    }

    public CombatOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(CombatOutcome newValue) {
        outcome = newValue;
    }

    public List<String> getCombatDescription() {
        return combatDescription;
    }

    public void setCombatDescription(List<String> combatDescription) {
        this.combatDescription = combatDescription;
    }
}
