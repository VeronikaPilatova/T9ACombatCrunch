package com.the_ninth_age.t9acombatcrunch.Service.Result;

import com.the_ninth_age.t9acombatcrunch.Service.Result.CombatOutcome;

public class Outcome {
    private int round;
    private CombatOutcome outcome;

    public Outcome(int round, CombatOutcome outcome) {
        this.round = round;
        this.outcome = outcome;
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
}
