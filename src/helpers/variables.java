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
        public static File getEnemyGifFile(int enemyType) {
            return switch (enemyType) {
                case SKULL -> new File("res/images/enemies/skull.gif");
                case SKELETON -> new File("src\\assets\\enemies\\skeleton.png");
                case ZOMBIE -> new File("src\\assets\\enemies\\zombie.png");
                case WITCH -> new File("src\\assets\\enemies\\witch.png");
                case WIZARD -> new File("src\\assets\\enemies\\wizard.png");
                case SKELETON_KING -> new File("src\\assets\\enemies\\skeleton_king.png");
                case ZOMBIE_KING -> new File("src\\assets\\enemies\\zombie_king.png");
                case WITCH_QUEEN -> new File("src\\assets\\enemies\\witch_queen.png");
                case WIZARD_KING -> new File("src\\assets\\enemies\\wizard_king.png");
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
}
