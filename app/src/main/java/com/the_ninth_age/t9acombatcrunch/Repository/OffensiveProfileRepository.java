package com.the_ninth_age.t9acombatcrunch.Repository;

import com.the_ninth_age.t9acombatcrunch.Service.Units.OffensiveProfile;
import com.the_ninth_age.t9acombatcrunch.Service.Units.SpecialRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class OffensiveProfileRepository {

    // Offensive profile - name, att, off, str, ap, agi, supporting attacks, special rules

    private List<OffensiveProfile> offensiveProfileList = new ArrayList<>(Arrays.asList(
            new OffensiveProfile("Imperial Guard", 1, 4, 4, 1, 3, 1, Arrays.asList()),

            new OffensiveProfile("Wildhorn", 1, 4, 3, 0, 3, 1, Arrays.asList(SpecialRule.PRIMAL_INSTINCT)),
            new OffensiveProfile("Mongrel", 1, 3, 3, 0, 3, 1, Arrays.asList(SpecialRule.PRIMAL_INSTINCT)),
            new OffensiveProfile("Feral Hound", 1, 4, 3, 0, 3, 1, Arrays.asList()),
            new OffensiveProfile("Longhorn", 1, 4, 4, 1, 3, 1, Arrays.asList(SpecialRule.PRIMAL_INSTINCT)),
            new OffensiveProfile("Minotaur", 3, 4, 5, 2, 3, 3, Arrays.asList(SpecialRule.PRIMAL_INSTINCT)),
            new OffensiveProfile("Centaur", 2, 4, 4, 1, 3, 1, Arrays.asList(SpecialRule.PRIMAL_INSTINCT)),
            new OffensiveProfile("War Hog", 1, 3, 4, 1, 2, 1, Arrays.asList()),
            new OffensiveProfile("Chassis", 0, 0, 5, 2, 0, 0, Arrays.asList()),
            new OffensiveProfile("Razortusk", 4, 3, 5, 2, 2, 3, Arrays.asList()),
            new OffensiveProfile("Briar Beast", d6()+1, 3, 4, 1, 2, 3, Arrays.asList()),
            new OffensiveProfile("Gargoyle", 2, 4, 3, 0, 3, 1, Arrays.asList(SpecialRule.PRIMAL_INSTINCT)),
            new OffensiveProfile("Cyclops", 5, 2, 6, 3, 3, 6, Arrays.asList()),
            new OffensiveProfile("Gortach", 6, 4, 6, 3, 3, 5, Arrays.asList(SpecialRule.PRIMAL_INSTINCT)),
            new OffensiveProfile("Jabberwock", 4, 4, 5, 2, 3, 5, Arrays.asList()),
            new OffensiveProfile("Beast Giant", 5, 3, 5, 2, 3, 5, Arrays.asList()),

            new OffensiveProfile("Dread Legionnaire", 1, 4, 3, 0, 5, 1, Arrays.asList()),
            new OffensiveProfile("Corsair", 1, 4, 3, 0, 5, 1, Arrays.asList()),
            new OffensiveProfile("Blade of Nabh", 2, 4, 3, 0, 6, 1, Arrays.asList()),
            new OffensiveProfile("Repeater Auxiliary", 1, 4, 3, 0, 5, 1, Arrays.asList()),
            new OffensiveProfile("Dark Raider", 1, 4, 3, 0, 5, 1, Arrays.asList()),
            new OffensiveProfile("Elven Horse", 1, 3, 3, 0, 4, 1, Arrays.asList()),
            new OffensiveProfile("Raven Cloak", 1, 5, 3, 0, 5, 1, Arrays.asList()),
            new OffensiveProfile("Tower Guard", 2, 6, 3, 1, 6, 1, Arrays.asList()),
            new OffensiveProfile("Dread Knight", 1, 5, 4, 1, 6, 1, Arrays.asList()),
            new OffensiveProfile("Raptor", 2, 3, 4, 1, 2, 1, Arrays.asList()),
            new OffensiveProfile("Harpy", 2, 3, 3, 0, 5, 1, Arrays.asList()),
            new OffensiveProfile("Dread Judge", 1, 5, 4, 1, 5, 1, Arrays.asList()),
            new OffensiveProfile("Dancer of Yema", 1, 5, 3, 0, 5, 1, Arrays.asList()),
            new OffensiveProfile("Medusa", 5, 5, 4, 1, 5, 1, Arrays.asList()),
            new OffensiveProfile("Dark Acolyte", 2, 4, 4, 1, 5, 1, Arrays.asList()),
            new OffensiveProfile("Altar Disciple", 1, 4, 3, 0, 5, 1, Arrays.asList()),
            new OffensiveProfile("Avatar of the Gods", 4, 5, 5, 2, 5, 1, Arrays.asList()),
            new OffensiveProfile("Kraken", 4, 5, 7, 4, 3, 5, Arrays.asList()),
            new OffensiveProfile("Hydra", 7, 5, 5, 2, 2, 5, Arrays.asList()),

            new OffensiveProfile("Clan Warrior", 1, 4, 3, 0, 2, 1, Arrays.asList()),
            new OffensiveProfile("Greybeard", 1, 5, 4, 1, 2, 1, Arrays.asList()),
            new OffensiveProfile("Clan Marksman", 1, 4, 3, 0, 2, 1, Arrays.asList()),
            new OffensiveProfile("Deep Watch", 1, 5, 4, 1, 2, 1, Arrays.asList()),
            new OffensiveProfile("King's Guard", 2, 5, 4, 1, 2, 1, Arrays.asList()),
            new OffensiveProfile("Miner", 1, 4, 4, 1, 2, 1, Arrays.asList()),
            new OffensiveProfile("Ranger", 1, 4, 3, 0, 2, 1, Arrays.asList()),
            new OffensiveProfile("Seeker", 1, 4, 4, 1, 2, 1, Arrays.asList())
    ));

    public OffensiveProfile findSpecific(String name) {
        for (OffensiveProfile profile : offensiveProfileList) {
            if (profile.getName().equals(name)) {
                return profile;
            }
        }
        return null;
    }

    private int d6() {
        Random randomGenerator = new Random();
        int d6 = randomGenerator.nextInt(6) + 1;
        return d6;
    }
}
