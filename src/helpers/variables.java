package helpers;

public class variables { //class to define different variables for thing

    public static class Enemies {

        public static final int ORC = 0;
        public static final int BAT = 1;
        public static final int KNIGHT = 2;
        public static final int WOLF = 3;

        public static int GetReward(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 5;
                case BAT:
                    return 5;
                case KNIGHT:
                    return 25;
                case WOLF:
                    return 10;
            }
            return 0;
        }

        public static float GetSpeed(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 0.5f;
                case BAT:
                    return 0.7f;
                case KNIGHT:
                    return 0.45f;
                case WOLF:
                    return 0.85f;
            }
            return 0;
        }

        public static int GetStartHealth(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 85;
                case BAT:
                    return 100;
                case KNIGHT:
                    return 400;
                case WOLF:
                    return 125;
            }
            return 0;
        }
    }
}
