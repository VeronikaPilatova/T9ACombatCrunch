package com.the_ninth_age.t9acombatcrunch.Service.Units;

import java.util.Random;

public enum Dice {

    NONE {
        public String toString() {
            return "";
        }

        @Override
        public int toNumber() {
            return 0;
        }
    },
    ONE {
        public String toString() {
            return "(1)";
        }

        @Override
        public int toNumber() {
            return 1;
        }
    },
    TWO {
        public String toString() {
            return "(2)";
        }

        @Override
        public int toNumber() {
            return 2;
        }
    },
    D3 {
        public String toString() {
            return "(D3)";
        }

        @Override
        public int toNumber() {
            Random random = new Random();
            return random.nextInt(3) + 1;
        }
    },
    D3TIMES2 {
        public String toString() {
            return "(2D3)";
        }

        @Override
        public int toNumber() {
            Random random = new Random();
            return (random.nextInt(3) + 1) + (random.nextInt(3) + 1);
        }
    },
    D3TIMES3 {
        public String toString() {
            return "(3D3)";
        }

        @Override
        public int toNumber() {
            Random random = new Random();
            return (random.nextInt(3) + 1) + (random.nextInt(3) + 1) + (random.nextInt(3) + 1);
        }
    },
    D3AND1 {
        public String toString() {
            return "(D3+1)";
        }

        @Override
        public int toNumber() {
            Random random = new Random();
            return random.nextInt(3) + 2;
        }
    },
    D6 {
        public String toString() {
            return "(D6)";
        }

        @Override
        public int toNumber() {
            Random random = new Random();
            return random.nextInt(6) + 1;
        }
    },
    D6AND1 {
        public String toString() {
            return "(D6+1)";
        }

        @Override
        public int toNumber() {
            Random random = new Random();
            return random.nextInt(6) + 2;
        }
    };

    public abstract int toNumber();
}