package helpers;

import java.awt.image.BufferedImage;
import java.io.File;

public class variables { //class to define different variables for thing

    public static class Enemies {

        public static final int SKULL = 0;
        public static final int SKELETON = 1;
        public static final int ZOMBIE = 2;
        public static final int WITCH = 3;
        public static final int WIZARD = 4;
        public static final int SKELETON_KING = 5;
        public static final int ZOMBIE_KING = 6;
        public static final int WITCH_QUEEN = 7;
        public static final int WIZARD_KING = 8;



        public static int GetReward(int enemyType) {
            return switch (enemyType) {
                case SKULL -> 5;
                case SKELETON -> 5;
                case ZOMBIE -> 25;
                case WITCH -> 10;
                case WIZARD -> 10;
                case SKELETON_KING -> 50;
                case ZOMBIE_KING -> 50;
                case WITCH_QUEEN -> 50;
                case WIZARD_KING -> 50;
                default -> 0;
            };
        }

        public static float GetSpeed(int enemyType) {
            return switch (enemyType) {
                case SKULL -> 0.5f;
                case SKELETON -> 0.7f;
                case ZOMBIE -> 0.45f;
                case WITCH -> 0.85f;
                case WIZARD -> 0.85f;
                case SKELETON_KING -> 0.5f;
                case ZOMBIE_KING -> 0.45f;
                case WITCH_QUEEN -> 0.85f;
                case WIZARD_KING -> 0.85f;
                default -> 0;
            };
        }

        public static int GetStartHealth(int enemyType) {
            return switch (enemyType) {
                case SKULL -> 100;
                case SKELETON -> 100;
                case ZOMBIE -> 100;
                case WITCH -> 100;
                case WIZARD -> 100;
                case SKELETON_KING -> 100;
                case ZOMBIE_KING -> 100;
                case WITCH_QUEEN -> 100;
                case WIZARD_KING -> 100;
                default -> 0;
            };
        }
        public static File getEnemyPassiveGifFile(int enemyType) {
            return switch (enemyType) {
                case SKULL -> new File("res/images/enemies/passive/skull_e_passive.gif");
                case SKELETON -> new File("res/images/enemies/passive/skeleton_e_passive.gif");
                case ZOMBIE -> new File("res/images/enemies/passive/zombie_e_passive.gif");
                case WITCH -> new File("res/images/enemies/passive/witch_e_passive.gif");
                case WIZARD -> new File("res/images/enemies/passive/wizard_e_passive.gif");
                case SKELETON_KING -> new File("res/images/enemies/passive/skeleton_king_e_passive.gif");
                case ZOMBIE_KING -> new File("res/images/enemies/passive/zombie_king_e_passive.gif");
                case WITCH_QUEEN -> new File("res/images/enemies/passive/witch_queen_e_passive.gif");
                case WIZARD_KING -> new File("res/images/enemies/passive/wizard_king_e_passive.gif");
                default -> null;

            };
        }
        public static File getEnemyActiveGifFile(int enemyType) {
            return switch (enemyType) {
                case SKULL -> new File("res/images/enemies/active/skull_e_active.gif");
                case SKELETON -> new File("res/images/enemies/active/skeleton_e_active.gif");
                case ZOMBIE -> new File("res/images/enemies/active/zombie_e_active.gif");
                case WITCH -> new File("res/images/enemies/active/witch_e_active.gif");
                case WIZARD -> new File("res/images/enemies/active/wizard_e_active.gif");
                case SKELETON_KING -> new File("res/images/enemies/active/skeleton_king_e_active.gif");
                case ZOMBIE_KING -> new File("res/images/enemies/active/zombie_king_e_active.gif");
                case WITCH_QUEEN -> new File("res/images/enemies/active/witch_queen_e_active.gif");
                case WIZARD_KING -> new File("res/images/enemies/active/wizard_king_e_active.gif");
                default -> null;

            };
        }
        public static int getEnemySpeed(int enemyType) {
            return switch (enemyType) {
                case SKULL -> 5;
                case SKELETON -> 5;
                case ZOMBIE -> 5;
                case WITCH -> 5;
                case WIZARD -> 5;
                case SKELETON_KING -> 5;
                case ZOMBIE_KING -> 5;
                case WITCH_QUEEN -> 5;
                case WIZARD_KING -> 5;
                default -> 0;
            };
        }
    }
    public static class Maps {

        public static File getPMapFile(int mapType) {
            return switch (mapType) {
                case 0 -> new File("res/images/maps/pathMaps/path0.bmp");
                case 1 -> new File("res/images/maps/pathMaps/path1.bmp");
                case 2 -> new File("res/images/maps/pathMaps/path2.bmp");
                case 3 -> new File("res/images/maps/pathMaps/path3.bmp");
                case 4 -> new File("res/images/maps/pathMaps/path4.bmp");
                case 5 -> new File("res/images/maps/pathMaps/path5.bmp");
                case 6 -> new File("res/images/maps/pathMaps/path6.bmp");
                case 7 -> new File("res/images/maps/pathMaps/path7.bmp");
                case 8 -> new File("res/images/maps/pathMaps/path8.bmp");
                case 9 -> new File("res/images/maps/pathMaps/path9.bmp");
                default -> null;
            };
        }
        public static BufferedImage getMapBufferedImage(int mapType) {
            File mapFile;
            BufferedImage mapImage;
            switch (mapType) {
                case 0 -> mapFile = new File("res/images/maps/backgroundMaps/map0.jpg");
                case 1 -> mapFile = new File("res/images/maps/backgroundMaps/map1.jpg");
                case 2 -> mapFile = new File("res/images/maps/backgroundMaps/map2.jpg");
                case 3 -> mapFile = new File("res/images/maps/backgroundMaps/map3.jpg");
                case 4 -> mapFile = new File("res/images/maps/backgroundMaps/map4.jpg");
                case 5 -> mapFile = new File("res/images/maps/backgroundMaps/map5.jpg");
                case 6 -> mapFile = new File("res/images/maps/backgroundMaps/map6.jpg");
                case 7 -> mapFile = new File("res/images/maps/backgroundMaps/map7.jpg");
                case 8 -> mapFile = new File("res/images/maps/backgroundMaps/map8.jpg");
                case 9 -> mapFile = new File("res/images/maps/backgroundMaps/map9.jpg");
                default -> mapFile = null;
            };
            try {
                mapImage = javax.imageio.ImageIO.read(mapFile);
                return mapImage;
            } catch (Exception e) {
                System.out.println("When reading MapBufferedImage: " + e);
                return null;
            }

        }

    }
    public static class Towers{
        public static final int Foundation_T = 0;
        public static final int ARROW_T = 1;
        public static final int MAGE_T = 2;
        public static final int ROCKET_T = 3;
        public static final int SNIP_T = 4;
        public static final int WIZARD = 5;
        public static final int SKELETON_KING = 6;
        public static final int ZOMBIE_KING = 7;
        public static final int WITCH_QUEEN = 8;
        public static final int WIZARD_KING = 9;
        public static File getTowerPassiveGifFile(int towerType) {
            return switch (towerType) {
                case Foundation_T -> new File("res/images/towers/passive/foundation_t_passive.gif");
                case ARROW_T -> new File("res/images/towers/passive/arrow_t_passive.gif");
                case MAGE_T -> new File("res/images/towers/passive/mage_t_passive.gif");
                case ROCKET_T -> new File("res/images/towers/passive/rocket_t_passive.gif");
                case SNIP_T -> new File("res/images/towers/passive/snip_t_passive.gif");
                default -> new File("res/images/towers/passive/foundation_t_passive.gif");

            };
        }
        public static File getTowerActiveGifFile(int towerType) {
            return switch (towerType) {
                case Foundation_T -> new File("res/images/towers/active/foundation_t_active.gif");
                case ARROW_T -> new File("res/images/towers/active/arrow_t_active.gif");
                case MAGE_T -> new File("res/images/towers/active/mage_t_active.gif");
                case ROCKET_T -> new File("res/images/towers/active/rocket_t_active.gif");
                case SNIP_T -> new File("res/images/towers/active/snip_t_active.gif");
                default -> new File("res/images/towers/active/foundation_t_active.gif");

            };
        }
        public static int getRange(int towerType) {
            return switch (towerType) {
                case Foundation_T -> 100;
                case ARROW_T -> 0;
                case MAGE_T -> 0;
                case ROCKET_T -> 0;
                case SNIP_T -> 0;
                default -> 0;

            };
        }
    }
}
