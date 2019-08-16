package com.the_ninth_age.t9acombatcrunch.Service.Units;

public enum ArmorType {
    NONE {
        public String toString() {
            return "None";
        }
    },
    LIGHT {
        public String toString() {
            return "Light";
        }
    },
    HEAVY {
        public String toString() {
            return "Heavy";
        }
    },
    PLATE {
        public String toString() {
            return "Plate";
        }
    }
}
