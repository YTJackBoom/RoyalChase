package helpers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;

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


        public static int getEnemyReward(int enemyType) {
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
                default -> throw new IllegalStateException("variables: getEnemyHealth: Unexpected value:  " + enemyType);
            };
        }

        public static String getEnemyPassiveGifPath(int enemyType) {
            return switch (enemyType) {
                case SKULL -> "res/images/enemies/passive/skull_e_passive/";
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
                case SKULL -> "res/images/enemies/active/skull_e_active/";
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
                case SKULL -> 2;
                case SKELETON -> 5;
                case ZOMBIE -> 5;
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
                case SKULL -> 100;
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
            return variables.getNumberOfIntDeclarations(Enemies.class);
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
        public static final int Foundation_T = 0;
        public static final int ARROW_T = 1;
        public static final int MAGE_T = 2;
        public static final int ROCKET_T = 3;
        public static final int SNIP_T = 4;

        public static String getTowerPassiveGifPath(int towerType) {
            return switch (towerType) {
                case Foundation_T -> "res/images/towers/passive/foundation_t_passive/";
                case ARROW_T -> "res/images/towers/passive/arrow_t_passive/";
                case MAGE_T -> "res/images/towers/passive/foundation_t_passive/";
                case ROCKET_T -> "res/images/towers/passive/arrow_t_passive/";
                case SNIP_T -> "res/images/towers/passive/foundation_t_passive/";
                default -> throw new IllegalStateException("variables: getTowerPassiveGifFile: Unexpected value:  " + towerType);

            };
        }

        public static String getTowerActiveGifPath(int towerType) {
            return switch (towerType) {
                case Foundation_T -> "res/images/towers/active/foundation_t_active/";
                case ARROW_T -> "res/images/towers/active/arrow_t_active/";
                case MAGE_T -> "res/images/towers/active/foundation_t_active/";
                case ROCKET_T -> "res/images/towers/active/arrow_t_active/";
                case SNIP_T -> "res/images/towers/active/foundation_t_active/";
                default -> throw new IllegalStateException("variables: getTowerActiveGifFile: Unexpected value:  " + towerType);

            };
        }

        public static int getTowerRange(int towerType) {
            return switch (towerType) {
                case Foundation_T -> 100;
                case ARROW_T -> 500;
                case MAGE_T -> 0;
                case ROCKET_T -> 500;
                case SNIP_T -> 0;
                default -> throw new IllegalStateException("variables: getRange: Unexpected value:  " + towerType);


            };
        }

        public static int getTowerReloadTime(int towerType) {
            return switch (towerType) {
                case Foundation_T -> 0;
                case ARROW_T -> 100;
                case MAGE_T -> 250;
                case ROCKET_T -> 500;
                case SNIP_T -> 500;
                default -> throw new IllegalStateException("variables: getTowerReload: Unexpected value:  " + towerType);


            };
        }

        public static int getTowerManaCost(int towerType) {
            return switch (towerType) {
                case Foundation_T -> 0;
                case ARROW_T -> 0;
                case MAGE_T -> 10;
                case ROCKET_T -> 0;
                case SNIP_T -> 0;
                default -> 0;
            };
        }
        public static int getTowerIronCost(int towerType) {
            return switch (towerType) {
                case Foundation_T -> 0;
                case ARROW_T -> 5;
                case MAGE_T -> 0;
                case ROCKET_T -> 50;
                case SNIP_T -> 100;
                default -> 0;
            };
        }
        public static int getTowerWoodCost(int towerType) {
            return switch (towerType) {
                case Foundation_T -> 0;
                case ARROW_T -> 30;
                case MAGE_T -> 20;
                case ROCKET_T -> 20;
                case SNIP_T -> 30;
                default -> 0;
            };
        }
        public static int getTowerStoneCost(int towerType) {
            return switch (towerType) {
                case Foundation_T -> 0;
                case ARROW_T -> 20;
                case MAGE_T -> 30;
                case ROCKET_T -> 40;
                case SNIP_T -> 30;
                default -> 0;
            };
        }

        public static int getNumberOfIntDeclarations() {
            return variables.getNumberOfIntDeclarations(Towers.class);
        }
    }

    public static class Projectiles {
        public static final int EMPTY = Towers.Foundation_T; //preloader can only start at 0
        public static final int ARROW = Towers.ARROW_T;
        public static final int FIREBALL = Towers.MAGE_T;
        public static final int ROCKET = Towers.ROCKET_T;
        public static final int BULLET = Towers.SNIP_T;

        public static double getProjectileSpeed(int projectileType) {
            return switch (projectileType) {
                case ARROW -> 1;
                case ROCKET -> 15;
                default -> throw new IllegalStateException("variables: getProjectileSpeed: Unexpected value:  " + projectileType);
            };
        }

        public static int getProjectileDamage(int projectileType) {
            return switch (projectileType) {
                case ARROW -> 5;
                case ROCKET -> 70;
                default -> throw new IllegalStateException("variables: getProjectileDamage: Unexpected value:  " + projectileType);
            };
        }

        public static String getProjectileGifPath(int projectileType) {
            return switch (projectileType) {
                case EMPTY -> null;
                case ARROW -> "res/images/projectiles/active/arrow_p_active/";
                case FIREBALL -> null;
                case ROCKET -> "res/images/projectiles/active/arrow_p_active/";
                case BULLET -> null;

                default -> throw new IllegalStateException("variables: getProjectileGifPath: Unexpected value:  " + projectileType);

            };
        }

        public static int getNumberOfIntDeclarations() {
            return variables.getNumberOfIntDeclarations(Projectiles.class);
        }
    }

    public static class Buttons {
        public static final int Foundation_T_B = 0;
        public static final int ARROW_T_B = 1;
        public static final int MAGE_T_B = 2;
        public static final int ROCKET_T_B = 3;
        public static final int SNIP_T_B = 4;
        public static final int MANA_B_B = -1;


        public static File getButtonImageFile(int buttonType) {
            return switch (buttonType) {
                case Foundation_T_B -> new File("res/images/buttons/blue_square.png");
                case ARROW_T_B -> new File("res/images/buttons/green_square.png");
                case ROCKET_T_B -> new File("res/images/buttons/red_square.png");
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
        public static final int MANA = 1;
        public static final int IRON = 2;
        public static final int STONE = 3;
        public static final int WOOD = 4;



        public static File getBuidingGifFile(int buildingType) {
            return switch (buildingType) {
                case 0 -> new File("res/images/towers/active/foundation_t_active/down.gif");
                case 1 -> new File("res/images/towers/active/arrow_t_active/down.gif");
                case 2 -> new File("res/images/towers/active/arrow_t_active/down.gif");
                case 3 -> new File("res/images/towers/active/arrow_t_active/down.gif");
                case 4 -> new File("res/images/towers/active/arrow_t_active/down.gif");
//                case 5 -> new File("res/text/waveFiles/waves5.txt");
//                case 6 -> new File("res/text/waveFiles/waves6.txt");
//                case 7 -> new File("res/text/waveFiles/waves7.txt");
//                case 8 -> new File("res/text/waveFiles/waves8.txt");
//                case 9 -> new File("res/text/waveFiles/waves9.txt");
                default -> throw new IllegalStateException("Evariables: rrropr when trying to read buildingGifFile");
            };

        }


        public static int getManaProduction(int buildingType) {
            return switch (buildingType) {
                case MANA -> 5;
                default -> 0;
            };
        }
        public static int getIronProduction(int buildingType) {
            return switch (buildingType) {
                case IRON -> 5;
                default -> 0;
            };
        }
        public static int getWoodProduction(int buildingType) {
            return switch (buildingType) {
                case WOOD -> 5;
                default -> 0;
            };
        }
        public static int getStoneProduction(int buildingType) {
            return switch (buildingType) {
                case STONE -> 5;
                default -> 0;
            };
        }

        public static int getBuildingIronCost(int buildingType) {
            return switch (buildingType) {
                case MANA -> 10;
                case IRON -> 0;
                case STONE -> 5;
                case WOOD -> 5;
                default -> 0;
            };
        }
        public static int getBuildingManaCost(int buildingType) {
            return switch (buildingType) {
                case MANA -> 0;
                case IRON -> 0;
                case STONE -> 0;
                case WOOD -> 5;
                default -> 0;
            };
        }
        public static int getBuildingWoodCost(int buildingType) {
            return switch (buildingType) {
                case MANA -> 10;
                case IRON -> 25;
                case STONE -> 25;
                case WOOD -> 5;
                default -> 0;
            };
        }
        public static int getBuildingStoneCost(int buildingType) {
            return switch (buildingType) {
                case MANA -> 25;
                case IRON -> 10;
                case STONE -> 0;
                case WOOD -> 10;
                default -> 0;
            };
        }
        public static int getBuildingGoldCost(int buildingType) {
            return switch (buildingType) {
                case MANA -> 50;
                case IRON -> 5;
                case STONE -> 5;
                case WOOD -> 5;
                default -> 0;
            };
        }



        public static int getNumberOfIntDeclarations() {
            return variables.getNumberOfIntDeclarations(Buildings.class);
        }
    }


    public static int getNumberOfIntDeclarations (Class < ? > clazz){
                int count = 0;
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getType() == int.class || field.getType() == Integer.class) {
                        count++;
                    }
                }
                return count;
            }

}