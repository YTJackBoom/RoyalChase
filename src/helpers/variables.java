package helpers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;

public class variables { //class to define different variables for thing

    public static class Enemies {

        public static final int SLIME = 0;
        public static final int SKELETON = 1;
        public static final int ZOMBIE = 2;
        public static final int WITCH = 3;
        public static final int WIZARD = 4;
        public static final int SKELETON_KING = 5;
        public static final int ZOMBIE_KING = 6;
        public static final int WITCH_QUEEN = 7;
        public static final int WIZARD_KING = 8;


        public static int getEnemyReward(int enemyType) {
            return switch (enemyType) {
                case SLIME -> 5;
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
                case SLIME -> 50;
                case SKELETON -> 100;
                case ZOMBIE -> 100;
                case WITCH -> 100;
                case WIZARD -> 100;
                case SKELETON_KING -> 100;
                case ZOMBIE_KING -> 100;
                case WITCH_QUEEN -> 100;
                case WIZARD_KING -> 100;
                default -> throw new IllegalStateException("variables: getEnemyHealth: Unexpected value:  " + enemyType);
            };
        }

        public static String getEnemyPassiveGifPath(int enemyType) {
            return switch (enemyType) {
                case SLIME -> "res/images/enemies/passive/skull_e_passive/";
                case SKELETON -> "res/images/enemies/passive/skeleton_e_passive/";
                case ZOMBIE -> "res/images/enemies/passive/zombie_e_passive/";
                case WITCH -> "res/images/enemies/passive/witch_e_passive/";
                case WIZARD -> "res/images/enemies/passive/wizard_e_passive/";
                case SKELETON_KING -> "res/images/enemies/passive/skeleton_king_e_passive/";
                case ZOMBIE_KING -> "res/images/enemies/passive/zombie_king_e_passive/";
                case WITCH_QUEEN -> "res/images/enemies/passive/witch_queen_e_passive/";
                case WIZARD_KING -> "res/images/enemies/passive/wizard_king_e_passive/";
                default -> throw new IllegalStateException("variables: getEnemyPassiveGifFile: Unexpected value:  " + enemyType);

            };
        }

        public static String getEnemyActiveGifPath(int enemyType) {
            return switch (enemyType) {
                case SLIME -> "res/images/enemies/active/slime_e_active/";
                case SKELETON -> "res/images/enemies/active/skeleton_e_active/";
                case ZOMBIE -> "res/images/enemies/active/zombie_e_active/";
                case WITCH -> "res/images/enemies/active/witch_e_active/";
                case WIZARD -> "res/images/enemies/active/wizard_e_active/";
                case SKELETON_KING -> "res/images/enemies/active/skeleton_king_e_active/";
                case ZOMBIE_KING -> "res/images/enemies/active/zombie_king_e_active/";
                case WITCH_QUEEN -> "res/images/enemies/active/witch_queen_e_active/";
                case WIZARD_KING -> "res/images/enemies/active/wizard_king_e_active/";
                default -> throw new IllegalStateException("variables: getEnemyActiveGifFile: Unexpected value:  " + enemyType);

            };
        }

        public static int getEnemySpeed(int enemyType) {
            return switch (enemyType) {
                case SLIME -> 2;
                case SKELETON -> 5;
                case ZOMBIE -> 2;
                case WITCH -> 5;
                case WIZARD -> 5;
                case SKELETON_KING -> 5;
                case ZOMBIE_KING -> 5;
                case WITCH_QUEEN -> 5;
                case WIZARD_KING -> 5;
                default -> throw new IllegalStateException("variables: getEnemySpeed: Unexpected value:  " + enemyType);
            };
        }

        public static int getEnemyDamage(int enemyType) {
            return switch (enemyType) {
                case SLIME -> 100;
                case SKELETON -> 5;
                case ZOMBIE -> 5;
                case WITCH -> 5;
                case WIZARD -> 5;
                case SKELETON_KING -> 5;
                case ZOMBIE_KING -> 5;
                case WITCH_QUEEN -> 5;
                case WIZARD_KING -> 5;
                default -> throw new IllegalStateException("variables: getEnemyDamage: Unexpected value:  " + enemyType);
            };
        }

        public static int getNumberOfIntDeclarations() {
            return variables.getHighestInt(Enemies.class);
        }
        public static int getLowestInt(){return variables.getLowestInt(Enemies.class);}

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
                default -> throw new IllegalStateException("variables: getPMapFile: Unexpected value:  " + mapType);
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
                default -> throw new IllegalStateException("variables: getMapBufferedImage: Unexpected value:  " + mapType);
            }
            ;
            try {
                mapImage = javax.imageio.ImageIO.read(mapFile);
                return mapImage;
            } catch (Exception e) {
                System.out.println("When reading MapBufferedImage " + mapType + ": " + e);
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
                default -> throw new IllegalStateException("variables: getMapWaveFile: Unexpected value:  " + waveType);
            };
        }

    }

    public static class Towers {
        public static final int Foundation_T = Buttons.Foundation_T_B;
        public static final int ARROW_T = Buttons.ARROW_T_B;
        public static final int MAGE_T = Buttons.MAGE_T_B;
        public static final int ROCKET_T = Buttons.ROCKET_T_B;
        public static final int SNIP_T = Buttons.SNIP_T_B;

        public static String getTowerPassiveGifPath(int towerType) {
            return switch (towerType) {
                case Foundation_T -> "res/images/towers/passive/foundation_t_passive/";
                case ARROW_T -> "res/images/towers/passive/arrow_t_passive/";
                case MAGE_T -> "res/images/towers/passive/mage_t_passive/";
                case ROCKET_T -> "res/images/towers/passive/rocket_t_passive/";
                case SNIP_T -> "res/images/towers/passive/mage_t_passive/";
                default -> throw new IllegalStateException("variables: getTowerPassiveGifFile: Unexpected value:  " + towerType);

            };
        }
        public static File getTowerPassiveGifFile(int towerType) {
            return switch (towerType) {
                case Foundation_T -> new File("res/images/towers/passive/foundation_t_passive/normal.gif");
                case ARROW_T -> new File("res/images/towers/passive/arrow_t_passive/normal.gif");
                case MAGE_T -> new File("res/images/towers/passive/mage_t_passive/normal.gif");
                case ROCKET_T -> new File("res/images/towers/passive/rocket_t_passive/normal.gif");
                case SNIP_T -> new File("res/images/towers/passive/mage_t_passive/normal.gif");
                default -> throw new IllegalStateException("variables: getTowerPassiveGifFile: Unexpected value:  " + towerType);

            };
        }

        public static String getTowerActiveGifPath(int towerType) {
            return switch (towerType) {
                case Foundation_T -> "res/images/towers/active/foundation_t_active/";
                case ARROW_T -> "res/images/towers/active/arrow_t_active/";
                case MAGE_T -> "res/images/towers/active/mage_t_active/";
                case ROCKET_T -> "res/images/towers/active/rocket_t_active/";
                case SNIP_T -> "res/images/towers/active/mage_t_active/";
                default -> throw new IllegalStateException("variables: getTowerActiveGifFile: Unexpected value:  " + towerType);

            };
        }
        public static File getTowerBaseGifFile(int i) {
            return switch (i) {
                default -> new File("res/images/towers/bases/rocket_t_base.gif");
            };
        }

        public static int getTowerRange(int towerType) {
            return switch (towerType) {
                case Foundation_T -> 0;
                case ARROW_T -> 500;
                case MAGE_T -> 350;
                case ROCKET_T -> 500;
                case SNIP_T -> 1000;
                default -> throw new IllegalStateException("variables: getRange: Unexpected value:  " + towerType);


            };
        }

        public static int getTowerReloadTime(int towerType) {
            return switch (towerType) {
                case Foundation_T -> 0;
                case ARROW_T -> 100;
                case MAGE_T -> 250;
                case ROCKET_T -> 2000;
                case SNIP_T -> 3000;
                default -> throw new IllegalStateException("variables: getTowerReload: Unexpected value:  " + towerType);


            };
        }

        public static Values getCost(int towerType) {
            return switch (towerType) {
                case Foundation_T -> new Values(0,0,0,0);
                case ARROW_T -> new Values(0,5,30,20);
                case MAGE_T -> new Values(10,0,20,30);
                case ROCKET_T -> new Values(0,50,20,40);
                case SNIP_T -> new Values(0,100,30,30);
                default -> new Values(999,999,999,999);


            };
        }

        public static int getNumberOfIntDeclarations() {
            return variables.getHighestInt(Towers.class);
        }
        public static int getLowestInt(){return variables.getLowestInt(Towers.class);}



    }

    public static class Projectiles {
        public static final int EMPTY = Towers.Foundation_T; //preloader can only start at 0
        public static final int ARROW = Towers.ARROW_T;
        public static final int LIGHTNINGBALL = Towers.MAGE_T;
        public static final int ROCKET = Towers.ROCKET_T;
        public static final int BULLET = Towers.SNIP_T;

        public static double getProjectileSpeed(int projectileType) {
            return switch (projectileType) {
                case ARROW -> 25;
                case ROCKET -> 20;
                case LIGHTNINGBALL -> 15;
                case BULLET -> 50;
                default -> throw new IllegalStateException("variables: getProjectileSpeed: Unexpected value:  " + projectileType);
            };
        }

        public static int getProjectileDamage(int projectileType) {
            return switch (projectileType) {
                case ARROW -> 5;
                case ROCKET -> 70;
                case LIGHTNINGBALL -> 90;
                case BULLET -> 110;
                default -> throw new IllegalStateException("variables: getProjectileDamage: Unexpected value:  " + projectileType);
            };
        }

        public static File getProjectileGifFile(int projectileType) {
            return switch (projectileType) {
                case EMPTY -> null;
                case ARROW -> new File("res/images/projectiles/active/arrow_p_active/normal.gif");
                case LIGHTNINGBALL -> new File("res/images/projectiles/active/lightningball_p_active/normal.gif");
                case ROCKET -> new File("res/images/projectiles/active/rocket_p_active/normal.gif");
                case BULLET ->new File("res/images/projectiles/active/bullet_p_active/normal.gif");

                default -> throw new IllegalStateException("variables: getProjectileGifPath: Unexpected value:  " + projectileType);

            };
        }

        public static int getNumberOfIntDeclarations() {
            return variables.getHighestInt(Projectiles.class);
        }
        public static int getLowestInt(){return variables.getLowestInt(Projectiles.class);}

    }

    public static class Buttons {
        public static final int Foundation_T_B = 0;
        public static final int ARROW_T_B = 1;
        public static final int MAGE_T_B = 2;
        public static final int ROCKET_T_B = 3;
        public static final int SNIP_T_B = 4;
        public static final int MANA_B_B = 5;
        public static final int IRON_B_B = 6;
        public static final int WOOD_B_B = 7;
        public static final int STONE_B_B = 8;


        public static File getButtonImageFile(int buttonType) {
            return switch (buttonType) {
                case Foundation_T_B -> new File("res/images/buttons/blue_square.png");
                case ARROW_T_B -> new File("res/images/buttons/green_square.png");
                case MAGE_T_B -> new File("res/images/buttons/purple_square.png");
                case ROCKET_T_B -> new File("res/images/buttons/red_square.png");
                case SNIP_T_B -> new File("res/images/buttons/cyan_square.png");
                case MANA_B_B -> new File("res/images/buttons/blue_square.png");

                default -> throw new IllegalStateException("variables: getButtonImageFile: Unexpected value: " + buttonType);
            };
        }
    }

    public static class Town {
        public static BufferedImage getBackgroundImage() {
            File mapFile = new File("res/images/town/town_background.jpg");
            BufferedImage mapImage;
            try {
                mapImage = ImageIO.read(mapFile);
                return mapImage;
            } catch (Exception e) {
                System.out.println("When reading townBackgroundBufferedImage :" + e);
                return null;
            }
        }
    }

    public static class Buildings {
        public static final int PLACEHOLDER = 0;
        public static final int MANA = 5;
        public static final int IRON = 6;
        public static final int STONE = 7;
        public static final int WOOD = 8;
        public static final int MANAORE = 1;
        public static final int IRONORE = 2;
        public static final int STONEORE = 3;
        public static final int WOODORE = 4;



        public static File getBuidingGifFile(int buildingType) {
            return switch (buildingType) {
                case PLACEHOLDER -> new File("res/images/towers/active/foundation_t_active/down.gif");
                case MANA -> new File("res/images/towers/active/arrow_t_active/down.gif");
                case IRON -> new File("res/images/towers/active/arrow_t_active/down.gif");
                case STONE -> new File("res/images/towers/active/arrow_t_active/down.gif");
                case WOOD -> new File("res/images/towers/active/arrow_t_active/down.gif");
                case MANAORE -> new File("res/images/towers/active/arrow_t_active/down.gif");
                case IRONORE -> new File("res/images/towers/active/arrow_t_active/down.gif");
                case STONEORE -> new File("res/images/towers/active/arrow_t_active/down.gif");
                case WOODORE -> new File("res/images/towers/active/arrow_t_active/down.gif");
//                case 9 -> new File("res/text/waveFiles/waves9.txt");
                default -> throw new IllegalStateException("Evariables: rrropr when trying to read buildingGifFile "+buildingType);
            };

        }


        public static Values getProduction(int type) {
            return switch (type) {
                case PLACEHOLDER -> new Values(0,0,0,0);
                case MANA -> new Values(5,0,0,0);
                case IRON -> new Values(0,5,0,0);
                case WOOD -> new Values(0,0,5,0);
                case STONE -> new Values(0,0,0,5);
                default -> new Values(0,0,0,0);
            };
        }
        public static Values getCost(int buildingType) {
            return switch (buildingType) {
                case MANA -> new Values(0,10,10,25);
                case IRON -> new Values(0,0,25,10);
                case STONE -> new Values(0,5,25,0);
                case WOOD -> new Values(5,5,5,10);
                default -> new Values(999,999,999,999);
            };
        }

        public static int getNumberOfIntDeclarations() {
            return variables.getHighestInt(Buildings.class);
        }
        public static int getLowestInt(){return variables.getLowestInt(Buildings.class);}


    }


    public static int getHighestInt(Class < ? > clazz){
        int highestNumber = Integer.MIN_VALUE;  // Initialize with a high value

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == int.class || field.getType() == Integer.class) {
                try {
                    field.setAccessible(true);
                    int value = field.getInt(clazz);
                    if (value > highestNumber) {
                        highestNumber = value;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return highestNumber;
    }
    public static int getLowestInt(Class < ? > clazz) {
        int lowestNumber = Integer.MAX_VALUE;  // Initialize with a high value

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == int.class || field.getType() == Integer.class) {
                try {
                    field.setAccessible(true);
                    int value = field.getInt(clazz);
                    if (value < lowestNumber) {
                        lowestNumber = value;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return lowestNumber;
    }


}