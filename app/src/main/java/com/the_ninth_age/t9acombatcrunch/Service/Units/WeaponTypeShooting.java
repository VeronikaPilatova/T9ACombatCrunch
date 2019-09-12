package com.the_ninth_age.t9acombatcrunch.Service.Units;

public enum WeaponTypeShooting {
    BOW {public String toString() {return "Bow";}},
    THROWING {public String toString() {return "Throwing weapons";}},
    REPEATER_HANDBOW {public String toString() {return "Repeater handbow";}},
    REPEATER_CROSSBOW {public String toString() {return "Repeater crossbow";}},
    CROSSBOW {public String toString() {return "Crossbow";}},
    GUILD_CRAFTED_HANDGUN {public String toString() {return "Guild-crafted handgun";}},
    PISTOL {public String toString() {return "Pistol";}},
    ENERGY_BOLTS {
        public String toString() {
            return "Energy bolts";
        }
    },
    FORGE_REPEATER {
        public String toString() {
            return "Forge repeater";
        }
    },
    FORGE_GUN {
        public String toString() {
            return "Forge gun";
        }
    },
    HANDGUN {
        public String toString() {
            return "Handgun";
        }
    },
    REPEATER_GUN {
        public String toString() {
            return "Repeater gun";
        }
    },
    BRACE_OF_PISTOLS {
        public String toString() {
            return "Brace of pistols";
        }
    },
    NONE {public String toString() {return "None";}};

    public static WeaponTypeShooting fromString(String text) {
        for (WeaponTypeShooting value : WeaponTypeShooting.values()) {
            if (value.toString().equals(text)) {
                return value;
            }
        }
        return null;
    }
}
