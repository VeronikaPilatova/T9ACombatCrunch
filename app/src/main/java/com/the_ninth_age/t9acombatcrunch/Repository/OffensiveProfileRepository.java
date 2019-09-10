package com.the_ninth_age.t9acombatcrunch.Repository;

import com.the_ninth_age.t9acombatcrunch.Service.Units.DevastatingCharge;
import com.the_ninth_age.t9acombatcrunch.Service.Units.Dice;
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
            new OffensiveProfile("Wildhorn", 1, 4, 3, 0, 3, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.PRIMAL_INSTINCT), new DevastatingCharge()),
            new OffensiveProfile("Mongrel", 1, 3, 3, 0, 3, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.PRIMAL_INSTINCT), new DevastatingCharge()),
            new OffensiveProfile("Feral Hound", 1, 4, 3, 0, 3, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Longhorn", 1, 4, 4, 1, 3, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.PRIMAL_INSTINCT), new DevastatingCharge()),
            new OffensiveProfile("Minotaur", 3, 4, 5, 2, 3, 3, Dice.NONE, Dice.ONE, Dice.ONE, Dice.NONE, Arrays.asList(SpecialRule.BATTLE_FOCUS, SpecialRule.PRIMAL_INSTINCT), new DevastatingCharge()),
            new OffensiveProfile("Centaur", 2, 4, 4, 1, 3, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.PRIMAL_INSTINCT), new DevastatingCharge()),
            new OffensiveProfile("Chassis (Light Chariot)", 0, 0, 5, 2, 0, 0, Dice.NONE, Dice.D6, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.INANIMATE), new DevastatingCharge()),
            new OffensiveProfile("Chassis (Heavy Chariot)", 0, 0, 5, 2, 0, 0, Dice.NONE, Dice.D6AND1, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.INANIMATE), new DevastatingCharge()),
            new OffensiveProfile("War Hog", 1, 3, 4, 1, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Razortusk", 4, 3, 5, 2, 2, 3, Dice.NONE, Dice.NONE, Dice.ONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Briar Beast", 0, 3, 4, 1, 2, 1, Dice.NONE, Dice.NONE, Dice.ONE, Dice.NONE, Arrays.asList(SpecialRule.BRIAR_BEAST), new DevastatingCharge()),
            new OffensiveProfile("Gargoyle", 2, 4, 3, 0, 3, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.PRIMAL_INSTINCT), new DevastatingCharge()),
            new OffensiveProfile("Cyclops", 5, 2, 6, 3, 3, 1, Dice.NONE, Dice.NONE, Dice.D6, Dice.NONE, Arrays.asList(SpecialRule.DIVINE_ATTACKS, SpecialRule.HURL_ATTACK), new DevastatingCharge()),
            new OffensiveProfile("Gortach", 6, 4, 6, 3, 3, 1, Dice.NONE, Dice.D3, Dice.D6, Dice.NONE, Arrays.asList(SpecialRule.BATTLE_FOCUS, SpecialRule.LETHAL_STRIKE, SpecialRule.PRIMAL_INSTINCT, SpecialRule.STRENGTH_FROM_FLESH), new DevastatingCharge()),
            new OffensiveProfile("Jabberwock", 4, 4, 5, 2, 3, 1, Dice.NONE, Dice.NONE, Dice.D6, Dice.NONE, Arrays.asList(SpecialRule.POISON), new DevastatingCharge()),
            new OffensiveProfile("Beast Giant", 5, 3, 5, 2, 3, 1, Dice.NONE, Dice.NONE, Dice.D6, Dice.NONE, Arrays.asList(SpecialRule.RAGE), new DevastatingCharge()),

            new OffensiveProfile("Imp", 1, 2, 2, 0, 3, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Succubus", 3, 4, 3, 1, 5, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Lemure", 1, 3, 3, 0, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Myrmidon", 1, 5, 4, 1, 3, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Eidolon", 1, 2, 2, 0, 3, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.DARK_FIRE_4), new DevastatingCharge()),
            new OffensiveProfile("Hellhound", 3, 5, 3, 0, 4, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.LETHAL_STRIKE), new DevastatingCharge()),
            new OffensiveProfile("Tiller", 2, 4, 3, 3, 3, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Draft Beast", 1, 3, 3, 0, 3, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Chassis (Threshing Engine)", 0, 0, 4, 3, 0, 0, Dice.NONE, Dice.D3TIMES2, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Chtonic Machinator", 3, 3, 6, 3, 3, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Chassis (Titanslayer Chariot)", 0, 0, 7, 2, 0, 0, Dice.NONE, Dice.D3AND1, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Clawed Fiend", 3, 4, 4, 2, 4, 3, Dice.NONE, Dice.NONE, Dice.ONE, Dice.NONE, Arrays.asList(SpecialRule.SMOTHER), new DevastatingCharge()),
            new OffensiveProfile("Mageblight Gremlin", 5, 3, 2, 0, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.POISON, SpecialRule.SPELL_CRAVING), new DevastatingCharge()),
            new OffensiveProfile("Hoarder", 3, 3, 4, 1, 2, 1, Dice.NONE, Dice.NONE, Dice.ONE, Dice.NONE, Arrays.asList(SpecialRule.TIGHTENING_GRASP), new DevastatingCharge()),
            new OffensiveProfile("Siren", 2, 5, 4, 1, 4, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Pale Steed", 1, 3, 3, 0, 3, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Blazing Glory", 5, 10, 5, 5, 5, 3, Dice.NONE, Dice.NONE, Dice.ONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Hope Harvester", 4, 4, 6, 3, 1, 3, Dice.NONE, Dice.NONE, Dice.ONE, Dice.NONE, Arrays.asList(SpecialRule.AETHER_BATTERY), new DevastatingCharge()),
            new OffensiveProfile("Daredevil", 1, 5, 3, 0, 4, 3, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.BATTLE_FOCUS), new DevastatingCharge()),
            new OffensiveProfile("Brazen Beast", 2, 4, 5, 2, 2, 3, Dice.NONE, Dice.TWO, Dice.ONE, Dice.NONE, Arrays.asList(SpecialRule.BATTLE_FOCUS), new DevastatingCharge()),
            new OffensiveProfile("Fury", 1, 3, 4, 1, 4, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Veil Serpent", 3, 4, 4, 1, 4, 3, Dice.NONE, Dice.NONE, Dice.ONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Bloat Fly", 2, 3, 5, 1, 1, 3, Dice.NONE, Dice.NONE, Dice.ONE, Dice.NONE, Arrays.asList(SpecialRule.ACID_BLOOD), new DevastatingCharge()),

            new OffensiveProfile("Dread Legionnaire", 1, 4, 3, 0, 5, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.KILLER_INSTINCT, SpecialRule.LIGHTNING_REFLEXES), new DevastatingCharge()),
            new OffensiveProfile("Corsair", 1, 4, 3, 0, 5, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.KILLER_INSTINCT, SpecialRule.LIGHTNING_REFLEXES), new DevastatingCharge()),
            new OffensiveProfile("Blade of Nabh", 2, 4, 3, 0, 6, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.CULT_NABH, SpecialRule.LIGHTNING_REFLEXES, SpecialRule.POISON), new DevastatingCharge()),
            new OffensiveProfile("Repeater Auxiliary", 1, 4, 3, 0, 5, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.KILLER_INSTINCT, SpecialRule.LIGHTNING_REFLEXES), new DevastatingCharge()),
            new OffensiveProfile("Dark Raider", 1, 4, 3, 0, 5, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.CULT_CADARON, SpecialRule.KILLER_INSTINCT, SpecialRule.LIGHTNING_REFLEXES), new DevastatingCharge()),
            new OffensiveProfile("Elven Horse", 1, 3, 3, 0, 4, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Raven Cloak", 1, 5, 3, 0, 5, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.CULT_CADARON, SpecialRule.LIGHTNING_REFLEXES), new DevastatingCharge()),
            new OffensiveProfile("Tower Guard", 2, 6, 3, 1, 6, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.CULT_OLARON, SpecialRule.KILLER_INSTINCT, SpecialRule.LIGHTNING_REFLEXES), new DevastatingCharge()),
            new OffensiveProfile("Dread Knight", 1, 5, 4, 1, 6, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.KILLER_INSTINCT, SpecialRule.LIGHTNING_REFLEXES), new DevastatingCharge()),
            new OffensiveProfile("Raptor", 2, 3, 4, 1, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Harpy", 2, 3, 3, 0, 5, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Dread Judge", 1, 5, 4, 1, 5, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.LIGHTNING_REFLEXES), new DevastatingCharge()),
            new OffensiveProfile("Dancer of Yema", 1, 5, 3, 0, 5, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.LIGHTNING_REFLEXES), new DevastatingCharge()),
            new OffensiveProfile("Medusa", 5, 5, 4, 1, 5, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.PETRIFYING_STARE), new DevastatingCharge()),
            new OffensiveProfile("Dark Acolyte", 2, 4, 4, 1, 5, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.KILLER_INSTINCT, SpecialRule.LIGHTNING_REFLEXES, SpecialRule.POISON), new DevastatingCharge()),
            new OffensiveProfile("Altar Disciple", 1, 4, 3, 0, 5, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.KILLER_INSTINCT, SpecialRule.LIGHTNING_REFLEXES, SpecialRule.POISON), new DevastatingCharge()),
            new OffensiveProfile("Avatar of the Gods", 4, 5, 5, 2, 5, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Kraken", 4, 5, 7, 4, 3, 5, Dice.D3, Dice.NONE, Dice.D6, Dice.NONE, Arrays.asList(SpecialRule.POISON), new DevastatingCharge()),
            new OffensiveProfile("Hydra", 7, 5, 5, 2, 2, 5, Dice.NONE, Dice.NONE, Dice.D6, Dice.NONE, Arrays.asList(), new DevastatingCharge()),

            new OffensiveProfile("Clan Warrior", 1, 4, 3, 0, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.STURDY), new DevastatingCharge()),
            new OffensiveProfile("Greybeard", 1, 5, 4, 1, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.STURDY), new DevastatingCharge()),
            new OffensiveProfile("Clan Marksman", 1, 4, 3, 0, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.STURDY), new DevastatingCharge()),
            new OffensiveProfile("Deep Watch", 1, 5, 4, 1, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.STURDY), new DevastatingCharge()),
            new OffensiveProfile("King's Guard", 2, 5, 4, 1, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.STURDY), new DevastatingCharge()),
            new OffensiveProfile("Miner", 1, 4, 4, 1, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.STURDY), new DevastatingCharge()),
            new OffensiveProfile("Ranger", 1, 4, 3, 0, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.STURDY), new DevastatingCharge()),
            new OffensiveProfile("Seeker", 1, 4, 4, 1, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.STURDY, SpecialRule.WEAPONMASTER, SpecialRule.COMIN_WITH_ME), new DevastatingCharge()),
            new OffensiveProfile("Vengeance Seeker", 3, 5, 4, 1, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.COMIN_WITH_ME), new DevastatingCharge()),
            new OffensiveProfile("Hold Guardian", 3, 4, 5, 2, 2, 3, Dice.NONE, Dice.NONE, Dice.ONE, Dice.NONE, Arrays.asList(SpecialRule.MAGIC_ATTACK), new DevastatingCharge()),
            new OffensiveProfile("Chassis (Grudge Buster)", 0, 0, 5, 2, 2, 3, Dice.NONE, Dice.D6AND1, Dice.NONE, Dice.D3TIMES3, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Dwarf Crew", 1, 4, 4, 1, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(), new DevastatingCharge()),
            new OffensiveProfile("Forge Warden", 1, 4, 4, 1, 2, 1, Dice.NONE, Dice.NONE, Dice.NONE, Dice.NONE, Arrays.asList(SpecialRule.STURDY), new DevastatingCharge())

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
