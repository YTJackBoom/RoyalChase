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


        public static int getEnemyHealth(int enemyType) {
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
                default -> throw new IllegalStateException("getEnemyHealth: Unexpected value:  " + enemyType);
            };
        }
        public static String getEnemyPassiveGifPath(int enemyType) {
            return switch (enemyType) {
                case SKULL -> "res/images/enemies/passive/skull_e_passive/";
                case SKELETON -> "res/images/enemies/passive/skeleton_e_passive/";
                case ZOMBIE ->  "res/images/enemies/passive/zombie_e_passive/";
                case WITCH -> "res/images/enemies/passive/witch_e_passive/";
                case WIZARD ->  "res/images/enemies/passive/wizard_e_passive/";
                case SKELETON_KING -> "res/images/enemies/passive/skeleton_king_e_passive/";
                case ZOMBIE_KING -> "res/images/enemies/passive/zombie_king_e_passive/";
                case WITCH_QUEEN -> "res/images/enemies/passive/witch_queen_e_passive/";
                case WIZARD_KING -> "res/images/enemies/passive/wizard_king_e_passive/";
                default -> throw new IllegalStateException("getEnemyPassiveGifFile: Unexpected value:  " + enemyType);

            };
        }
        public static String getEnemyActiveGifPath(int enemyType) {
            return switch (enemyType) {
                case SKULL -> "res/images/enemies/active/skull_e_active/";
                case SKELETON -> "res/images/enemies/active/skeleton_e_active/";
                case ZOMBIE ->  "res/images/enemies/active/zombie_e_active/";
                case WITCH -> "res/images/enemies/active/witch_e_active/";
                case WIZARD ->  "res/images/enemies/active/wizard_e_active/";
                case SKELETON_KING -> "res/images/enemies/active/skeleton_king_e_active/";
                case ZOMBIE_KING -> "res/images/enemies/active/zombie_king_e_active/";
                case WITCH_QUEEN -> "res/images/enemies/active/witch_queen_e_active/";
                case WIZARD_KING -> "res/images/enemies/active/wizard_king_e_active/";
                default -> throw new IllegalStateException("getEnemyActiveGifFile: Unexpected value:  " + enemyType);

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
                default -> throw new IllegalStateException("getEnemySpeed: Unexpected value:  " + enemyType);
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
                default -> throw new IllegalStateException("getPMapFile: Unexpected value:  " + mapType);
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
                default -> throw new IllegalStateException("getMapBufferedImage: Unexpected value:  " + mapType);
            };
            try {
                mapImage = javax.imageio.ImageIO.read(mapFile);
                return mapImage;
            } catch (Exception e) {
                System.out.println("When reading MapBufferedImage: " + e);
                return null;
            }

        }
        public static File getMapWaveFile(int waveType) {
            return switch (waveType) {
                case 0 -> new File("res/text/waveFiles/waves0.txt");
                case 1 -> new File("res/text/waveFiles/waves1.txt");
                case 2 -> new File("res/text/waveFiles/waves2.txt");
                case 3 -> new File("res/text/waveFiles/waves3.txt");
                case 4 -> new File("res/text/waveFiles/waves4.txt");
                case 5 -> new File("res/text/waveFiles/waves5.txt");
                case 6 -> new File("res/text/waveFiles/waves6.txt");
                case 7 -> new File("res/text/waveFiles/waves7.txt");
                case 8 -> new File("res/text/waveFiles/waves8.txt");
                case 9 -> new File("res/text/waveFiles/waves9.txt");
                default -> throw new IllegalStateException("getMapWaveFile: Unexpected value:  " + waveType);
            };
        }

    }
    public static class Towers{
        public static final int Foundation_T = 0;
        public static final int ARROW_T = 1;
        public static final int MAGE_T = 2;
        public static final int ROCKET_T = 3;
        public static final int SNIP_T = 4;

        public static String getTowerPassiveGifPath(int towerType) {
            return switch (towerType) {
                case Foundation_T -> "res/images/towers/passive/foundation_t_passive/";
                case ARROW_T -> "res/images/towers/passive/arrow_t_passive/";
                case MAGE_T -> "res/images/towers/passive/mage_t_passive/";
                case ROCKET_T -> "res/images/towers/passive/rocket_t_passive/";
                case SNIP_T -> "res/images/towers/passive/snip_t_passive/";
                default -> throw new IllegalStateException("getTowerPassiveGifFile: Unexpected value:  " + towerType);

            };
        }
        public static String getTowerActiveGifPath(int towerType) {
            return switch (towerType) {
                case Foundation_T -> "res/images/towers/active/foundation_t_active/";
                case ARROW_T -> "res/images/towers/active/arrow_t_active/";
                case MAGE_T -> "res/images/towers/active/mage_t_active/";
                case ROCKET_T -> "res/images/towers/active/rocket_t_active/";
                case SNIP_T -> "res/images/towers/active/snip_t_active/";
                default -> throw new IllegalStateException("getTowerActiveGifFile: Unexpected value:  " + towerType);

            };
        }
        public static int getRange(int towerType) {
            return switch (towerType) {
                case Foundation_T -> 100;
                case ARROW_T -> 100;
                case MAGE_T -> 0;
                case ROCKET_T -> 0;
                case SNIP_T -> 0;
                default -> throw new IllegalStateException("getRange: Unexpected value:  " + towerType);


            };
        }
    }

    public static class Projectiles {
        public static final int ARROW = 1;




        public static int getProjectileSpeed(int projectileType) {
            return switch (projectileType) {
                case ARROW -> 10;
                default -> throw new IllegalStateException("getProjectileSpeed: Unexpected value:  " + projectileType);
            };
        }
        public static int getProjectileDamage(int projectileType) {
            return switch (projectileType) {
                case ARROW -> 10;
                default -> throw new IllegalStateException("getProjectileDamage: Unexpected value:  " + projectileType);
            };
        }

        public static String getProjectileGifPath(int projectileType) {
            return switch (projectileType) {
                case ARROW -> "res/images/projectiles/active/arrow_p_active/";
                default -> throw new IllegalStateException("getProjectileGifFile: Unexpected value:  " + projectileType);
            };
        }
    }

    public static class Buttons {
        public static final int Foundation_T = 0;
        public static final int ARROW_T = 1;
        public static final int MAGE_T = 2;
        public static final int ROCKET_T = 3;
        public static final int SNIP_T = 4;



        public static File getButtonImageFile(int buttonType) {
            return switch (buttonType) {
                case Foundation_T -> new File("res/images/buttons/yellow_square.png");
                case ARROW_T -> new File("res/images/buttons/arrow_t_button.png");

                default -> throw new IllegalStateException("getButtonImageFile: Unexpected value: " + buttonType);
            };
        }
    }

}
