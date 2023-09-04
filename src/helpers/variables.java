package helpers;

import gameObjects.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;

public class variables { //class to define different variables for thing

    public static class Enemies {

        public static final int SLIME = 0;
        public static final int SKELETON = 1;
        public static final int GOBLIN = 2;
        public static final int WITCH = 3;
        public static final int WIZARD = 4;
        public static final int SKELETON_KING = 5;
        public static final int GOBLIN_KING = 6;
        public static final int WITCH_QUEEN = 7;
        public static final int WIZARD_KING = 8;


        public static int getEnemyReward(int enemyType) {
            return switch (enemyType) {
                case SLIME -> 5;
                case SKELETON -> 5;
                case GOBLIN -> 50;
                case WITCH -> 10;
                case WIZARD -> 10;
                case SKELETON_KING -> 50;
                case GOBLIN_KING -> 50;
                case WITCH_QUEEN -> 50;
                case WIZARD_KING -> 50;
                default -> 0;
            };
        }


        public static int getEnemyHealth(int enemyType) {
            return switch (enemyType) {
                case SLIME -> 50;
                case SKELETON -> 100;
                case GOBLIN -> 100;
                case WITCH -> 100;
                case WIZARD -> 100;
                case SKELETON_KING -> 100;
                case GOBLIN_KING -> 100;
                case WITCH_QUEEN -> 100;
                case WIZARD_KING -> 100;
                default -> throw new IllegalStateException("variables: getEnemyHealth: Unexpected value:  " + enemyType);
            };
        }

        public static String getEnemyPassiveGifPath(int enemyType) {
            return switch (enemyType) {
                case SLIME -> "res/images/enemies/passive/slime_e_passive/";
                case SKELETON -> "res/images/enemies/passive/skeleton_e_passive/";
                case GOBLIN -> "res/images/enemies/passive/goblin_e_passive/";
                case WITCH -> "res/images/enemies/passive/witch_e_passive/";
                case WIZARD -> "res/images/enemies/passive/wizard_e_passive/";
                case SKELETON_KING -> "res/images/enemies/passive/skeleton_king_e_passive/";
                case GOBLIN_KING -> "res/images/enemies/passive/goblin_king_e_passive/";
                case WITCH_QUEEN -> "res/images/enemies/passive/witch_queen_e_passive/";
                case WIZARD_KING -> "res/images/enemies/passive/wizard_king_e_passive/";
                default -> throw new IllegalStateException("variables: getEnemyPassiveGifFile: Unexpected value:  " + enemyType);

            };
        }

        public static String getEnemyActiveGifPath(int enemyType) {
            return switch (enemyType) {
                case SLIME -> "res/images/enemies/active/slime_e_active/";
                case SKELETON -> "res/images/enemies/active/skeleton_e_active/";
                case GOBLIN -> "res/images/enemies/active/goblin_e_active/";
                case WITCH -> "res/images/enemies/active/witch_e_active/";
                case WIZARD -> "res/images/enemies/active/wizard_e_active/";
                case SKELETON_KING -> "res/images/enemies/active/skeleton_king_e_active/";
                case GOBLIN_KING -> "res/images/enemies/active/goblin_king_e_active/";
                case WITCH_QUEEN -> "res/images/enemies/active/witch_queen_e_active/";
                case WIZARD_KING -> "res/images/enemies/active/wizard_king_e_active/";
                default ->
                        throw new IllegalStateException("variables: getEnemyActiveGifFile: Unexpected value:  " + enemyType);

            };
        }

        public static double getEnemySpeed(int enemyType) {
            return switch (enemyType) {
                case SLIME -> 0.5;
                case SKELETON -> 1;
                case GOBLIN -> 2;
                case WITCH -> 5;
                case WIZARD -> 5;
                case SKELETON_KING -> 5;
                case GOBLIN_KING -> 5;
                case WITCH_QUEEN -> 5;
                case WIZARD_KING -> 5;
                default -> throw new IllegalStateException("variables: getEnemySpeed: Unexpected value:  " + enemyType);
            };
        }

        public static double getEnemyDamage(int enemyType) {
            return switch (enemyType) {
                case SLIME -> 100;
                case SKELETON -> 2.5;
                case GOBLIN -> 5;
                case WITCH -> 5;
                case WIZARD -> 5;
                case SKELETON_KING -> 5;
                case GOBLIN_KING -> 5;
                case WITCH_QUEEN -> 5;
                case WIZARD_KING -> 5;
                default -> throw new IllegalStateException("variables: getEnemyDamage: Unexpected value:  " + enemyType);
            };
        }
        public static double getEnemyReloadTime(int enemyType) { //Entweder nachlade(range) oder angriffgeschwindigkeit(melee) beider in sekunden/angriff
            return switch (enemyType) {
                case SLIME -> 0;
                case SKELETON -> 100;
                case GOBLIN -> 5;
                case WITCH -> 400;
                case WIZARD -> 400;
                case SKELETON_KING -> 800;
                case GOBLIN_KING -> 0;
                case WITCH_QUEEN -> 1600;
                case WIZARD_KING -> 1600;
                default -> throw new IllegalStateException("variables: getEnemyRelaodTime: Unexpected value: "+ enemyType);
            };
        }
        public static double getEnemyRange(int enemyType) {
            return switch (enemyType) {
                case SLIME -> 0;
                case SKELETON -> 150;
                case GOBLIN -> 1;
                case WITCH -> 50;
                case WIZARD -> 50;
                case SKELETON_KING -> 100;
                case GOBLIN_KING -> 1;
                case WITCH_QUEEN -> 100;
                case WIZARD_KING -> 100;
                default -> throw new IllegalStateException("variables: getEnemyRange: Unexpected value: "+ enemyType);
            };
        }

        public static int getNumberOfIntDeclarations() {
            return variables.getHighestInt(Enemies.class);
        }
        public static int getLowestInt(){return variables.getLowestInt(Enemies.class);}



    }

    public static class Maps {
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


        public static File getTileTextFile(int level) {
            return switch (level) {
                case 0 -> new File("res/text/tileFiles/tiles0.txt");
                case 1 -> new File("res/text/tileFiles/tiles1.txt");
                case 2 -> new File("res/text/tileFiles/tiles2.txt");
                case 3 -> new File("res/text/tileFiles/tiles3.txt");
                case 4 -> new File("res/text/tileFiles/tiles4.txt");
                case 5 -> new File("res/text/tileFiles/tiles5.txt");
                case 6 -> new File("res/text/tileFiles/tiles6.txt");
                case 7 -> new File("res/text/tileFiles/tiles7.txt");
                case 8 -> new File("res/text/tileFiles/tiles8.txt");
                case 9 -> new File("res/text/tileFiles/tiles9.txt");
                default -> throw new IllegalStateException("variables: getTileTextFile: Unexpected value:  " + level);
            };
        }
    }

    public static class Tiles {
        public static final int grass = 0;
        public static final int wood = 1;
        public static final int path_buttom_to_topright_grass = 2;
        public static final int path_buttom_to_topright_wood = 3;
        public static final int path_buttomleft_to_topright_grass = 4;
        public static final int path_buttomleft_to_topright_wood = 5;
        public static final int path_left_to_right_grass = 6;
        public static final int path_left_to_right_wood = 7;
        public static final int path_top_to_right_grass = 8;
        public static final int path_top_to_right_wood = 9;
        public static final int spawn_to_buttom_grass = 10;
        public static final int spawn_to_buttom_wood = 11;
        public static final int gate_to_buttom_grass = 12;
        public static final int gate_to_buttom_wood = 13;
        public static final int path_buttom_to_right_grass = 14;
        public static final int path_buttom_to_right_wood = 15;
        public static final int path_topleft_to_buttom_grass = 16;
        public static final int path_topleft_to_buttom_wood = 17;
        public static final int path_buttomleft_to_top_grass = 18;
        public static final int path_buttomleft_to_top_wood = 19;
        public static final int path_buttomright_to_top_grass = 20;
        public static final int path_buttomright_to_top_wood = 21;
        public static final int path_topleft_to_buttomright_grass = 22;
        public static final int path_topleft_to_buttomright_wood = 23;
        public static final int path_left_to_top_grass = 24;
        public static final int path_left_to_top_wood = 25;


        public static Tile getRawTile(int i) {
            return switch (i) {
                case grass -> new Tile(0, false, true, false, false);
                case wood -> new Tile(1, false, false, false, false);
                case path_buttom_to_topright_grass -> new Tile(2, true, false, false, false);
                case path_buttom_to_topright_wood -> new Tile(3, true, false, false, false);
                case path_buttomleft_to_topright_grass -> new Tile(4, true, false, false, false);
                case path_buttomleft_to_topright_wood -> new Tile(5, true, false, false, false);
                case path_left_to_right_grass -> new Tile(6, true, false, false, false);
                case path_left_to_right_wood -> new Tile(7, true, false, false, false);
                case path_top_to_right_grass -> new Tile(8, true, false, false, false);
                case path_top_to_right_wood -> new Tile(9, true, false, false, true);
                case spawn_to_buttom_grass -> new Tile(10, true, false, true, false);
                case spawn_to_buttom_wood -> new Tile(11, true, false, true, false);
                case gate_to_buttom_grass -> new Tile(12, true, false, false, true);
                case gate_to_buttom_wood -> new Tile(13, true, false, false, true);
                case path_buttom_to_right_grass -> new Tile(14, true, false, false, false);
                case path_buttom_to_right_wood -> new Tile(15, true, false, false, false);
                case path_topleft_to_buttom_grass -> new Tile(16, true, false, false, false);
                case path_topleft_to_buttom_wood -> new Tile(17, true, false, false, false);
                case path_buttomleft_to_top_grass -> new Tile(18, true, false, false, false);
                case path_buttomleft_to_top_wood -> new Tile(19, true, false, false, false);
                case path_buttomright_to_top_grass -> new Tile(20, true, false, false, false);
                case path_buttomright_to_top_wood -> new Tile(21, true, false, false, false);
                case path_topleft_to_buttomright_grass -> new Tile(22, true, false, false, false);
                case path_topleft_to_buttomright_wood -> new Tile(23, true, false, false, false);
                case path_left_to_top_grass -> new Tile(24, true, false, false, false);
                case path_left_to_top_wood -> new Tile(25, true, false, false, false);
                default -> throw new IllegalStateException("variables: getRawTile: Unexpected value:  " + i);
            };
        }

        public static File getTileFile(int tileType) {
            return new File("res/images/tiles/" + tileType + ".png");
        }
    }

    public static class Towers {
        private static final String standardpath = "res/images/towers/";
        public static final int Foundation_T = Buttons.Foundation_T_B;
        public static final int ARROW_T = Buttons.ARROW_T_B;
        public static final int MAGE_T = Buttons.MAGE_T_B;
        public static final int ROCKET_T = Buttons.ROCKET_T_B;
        public static final int SNIP_T = Buttons.SNIP_T_B;


        public static double getTowerDamage(int towerType) {
            return switch (towerType) {
                case Foundation_T -> 0;
                case ARROW_T -> 5;
                case MAGE_T -> 70;
                case ROCKET_T -> 50;
                case SNIP_T -> 110;
                default -> throw new IllegalStateException("variables: getTowerDamage: Unexpected value:  " + towerType);

            };
        }
        public static File getTowerPassiveGifFile(int towerType) {
            return switch (towerType) {
                case Foundation_T -> new File(standardpath + "foundation_t/passive.gif");
                case ARROW_T -> new File(standardpath + "arrow_t/passive.gif");
                case MAGE_T -> new File(standardpath+ "mage_t/passive.gif");
                case ROCKET_T -> new File(standardpath + "rocket_t/passive.gif");
                case SNIP_T -> new File(standardpath+ "snip_t/passive.gif");
                default -> throw new IllegalStateException("variables: getTowerPassiveGifFile: Unexpected value:  " + towerType);

            };
        }

        public static String getTowerActiveGifPath(int towerType) {
            return switch (towerType) {
                case Foundation_T -> standardpath+ "foundation_t/";
                case ARROW_T -> standardpath + "arrow_t/";
                case MAGE_T -> standardpath + "mage_t/";
                case ROCKET_T -> standardpath + "rocket_t/";
                case SNIP_T -> standardpath + "snip_t/";
                default -> throw new IllegalStateException("variables: getTowerActiveGifFile: Unexpected value:  " + towerType);

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
                case Foundation_T -> new Values(0,0,0,0,0,0);
                case ARROW_T -> new Values(1,0,0,5,30,20);
                case MAGE_T -> new Values(1,0,10,0,20,30);
                case ROCKET_T -> new Values(2,0,0,50,20,40);
                case SNIP_T -> new Values(2,0,0,100,30,30);
                default -> new Values(999,999,999,999,999,999);


            };
        }
        public static double getTowerHealth(int towerType) {
            return switch (towerType) {
                case Foundation_T -> 9999;
                case ARROW_T -> 100;
                case MAGE_T -> 100;
                case ROCKET_T -> 100;
                case SNIP_T -> 100;
                default -> 0;
            };
        }

        public static int getNumberOfIntDeclarations() {
            return variables.getHighestInt(Towers.class);
        }
        public static int getLowestInt(){return variables.getLowestInt(Towers.class);}



    }

    public static class Projectiles {
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
        public static double getStun(int projectileType) { //Stun in seconds
            return switch (projectileType) {
                case ARROW -> 0;
                case ROCKET -> 5;
                case LIGHTNINGBALL -> 10;
                case BULLET -> 0;
                default -> throw new IllegalStateException("variables: getProjectileDamage: Unexpected value:  " + projectileType);
            };
        }

        public static File getProjectileGifFile(int projectileType) {
            return switch (projectileType) {
                case ARROW -> new File("res/images/projectiles/arrow.gif");
                case LIGHTNINGBALL -> new File("res/images/projectiles/lightningball.gif");
                case ROCKET -> new File("res/images/projectiles/rocket.gif");
                case BULLET -> new File("res/images/projectiles/bullet.gif");

                default ->
                        throw new IllegalStateException("variables: getProjectileGifPath: Unexpected value:  " + projectileType);

            };
        }

        public static int getNumberOfIntDeclarations() {
            return variables.getHighestInt(Projectiles.class);
        }
        public static int getLowestInt(){return variables.getLowestInt(Projectiles.class);}
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
        public static final int MANA = Buttons.MANA_B_B;
        public static final int IRON = Buttons.IRON_B_B;
        public static final int STONE = Buttons.STONE_B_B;
        public static final int WOOD = Buttons.WOOD_B_B;
        public static final int HOUSE = Buttons.HOUSE_B_B;
        public static final int MANAORE = 1;
        public static final int IRONORE = 2;
        public static final int STONEORE = 3;
        public static final int WOODORE = 4;
        public static File getBuidingGifFile(int buildingType) {
            return switch (buildingType) {
                case PLACEHOLDER -> new File("res/images/towers/foundation_t/passive.gif");
                case MANA -> new File("res/images/towers/arrow_t/passive.gif");
                case IRON -> new File("res/images/towers/arrow_t/passive.gif");
                case STONE -> new File("res/images/towers/arrow_t/passive.gif");
                case WOOD -> new File("res/images/towers/arrow_t/passive.gif");
                case MANAORE -> new File("res/images/towers/arrow_t/passive.gif");
                case IRONORE -> new File("res/images/towers/arrow_t/passive.gif");
                case STONEORE -> new File("res/images/towers/arrow_t/passive.gif");
                case WOODORE -> new File("res/images/towers/arrow_t/passive.gif");
                case HOUSE -> new File("res/images/towers/arrow_t/passive.gif");
                default -> throw new IllegalStateException("Evariables: rrropr when trying to read buildingGifFile "+buildingType);
            };

        }
        public static Values getProduction(int type) {
            return switch (type) {
                case PLACEHOLDER -> new Values(0,0,0,0,0,0);
                case MANA -> new Values(0,0,5,0,0,0);
                case IRON -> new Values(0,0,0,5,0,0);
                case WOOD -> new Values(0,0,0,0,5,0);
                case STONE -> new Values(0,0,0,0,0,5);
                case HOUSE -> new Values(5,0,0,0,0,0);
                default -> new Values(0,0,0,0,0,0);
            };
        }
        public static Values getCost(int buildingType) {
            return switch (buildingType) {
                case MANA -> new Values(1,10,0,10,10,25);
                case IRON -> new Values(1,10,0,0,25,10);
                case STONE -> new Values(1,10,0,5,25,0);
                case WOOD -> new Values(1,10,5,5,5,10);
                case HOUSE -> new Values(2,10,10,10,10,10);
                default -> new Values(999,999,999,999,999,999);
            };

        }
        public static int getNumberOfIntDeclarations() {
            return variables.getHighestInt(Buildings.class);
        }
        public static int getLowestInt(){return variables.getLowestInt(Buildings.class);}


    }

    public static class Buttons {
        private static final String standartpath = "res/images/icons/interactables/";
        private static final String standartpathttips = "res/text/tooltips/";
        public static final int Foundation_T_B = 0;
        public static final int ARROW_T_B = 1;
        public static final int MAGE_T_B = 2;
        public static final int ROCKET_T_B = 3;
        public static final int SNIP_T_B = 4;
        public static final int MANA_B_B = 5;
        public static final int IRON_B_B = 6;
        public static final int WOOD_B_B = 7;
        public static final int STONE_B_B = 8;
        public static final int HOUSE_B_B = 9;


        public static File getButtonImageFile(int buttonType) {
            return switch (buttonType) {
                case Foundation_T_B -> new File("res/images/buttons/blue_square.png");
                case ARROW_T_B -> new File(standartpath + "arrow_t_icon.png");
                case MAGE_T_B -> new File(standartpath+ "mage_t_icon.png");
                case ROCKET_T_B -> new File(standartpath+"rocket_t_icon.png");
                case SNIP_T_B -> new File(standartpath+"snip_t_icon.png");
                case MANA_B_B -> new File("res/images/buttons/blue_square.png");
                case HOUSE_B_B -> new File("res/images/buttons/red_square.png");

                default -> throw new IllegalStateException("variables: getButtonImageFile: Unexpected value: " + buttonType);
            };
        }

        public static File getTooltipTextFile(int buttonType) {
            return switch (buttonType) {
                case ARROW_T_B -> new File(standartpathttips+"arrow_t.txt");
                case MAGE_T_B -> new File(standartpathttips+"mage_t.txt");
                case ROCKET_T_B -> new File(standartpathttips+"rocket_t.txt");
                case SNIP_T_B -> new File(standartpathttips+"snip_t.txt");
                case MANA_B_B -> new File(standartpathttips+"mana_b.txt");
                case IRON_B_B -> new File(standartpathttips+"iron_b.txt");
                case WOOD_B_B -> new File(standartpathttips+"wood_b.txt");
                case STONE_B_B -> new File(standartpathttips+"stone_b.txt");
                case HOUSE_B_B -> new File(standartpathttips+"house_b.txt");
                default -> new File(standartpathttips+"wrong.txt");
            };
        }
    }
    public static class Icons {
        private static final String standartpath = "res/images/icons/";
        public static final int RESUME = 0;
        public static final int PAUSE = 1;
        public static final int ENEMIES = 2;
        public static final int WAVES = 3;
        public static final int HEART = 4;
        public static final int WORKERS = 5;
        public static final int GOLD = 6;
        public static final int MANA = 7;
        public static final int IRON = 8;
        public static final int WOOD = 9;
        public static final int STONE = 10;

        public static File getIconImageFile(int IconType) {
            return switch (IconType) {
                case RESUME -> new File(standartpath + "infooverlay/resume_icon.png");
                case PAUSE -> new File(standartpath + "infooverlay/pause_icon.png");
                case ENEMIES -> new File(standartpath + "infooverlay/enemies_icon.png");
                case WAVES -> new File(standartpath + "infooverlay/waves_icon.png");
                case HEART -> new File(standartpath + "infooverlay/heart_icon.png");
                case GOLD -> new File(standartpath + "infooverlay/gold_icon.png");
                case WOOD -> new File(standartpath + "infooverlay/wood_icon.png");
                case IRON -> new File(standartpath + "infooverlay/iron_icon.png");
                case MANA -> new File(standartpath + "infooverlay/mana_icon.png");
                case STONE -> new File(standartpath + "infooverlay/stone_icon.png");
                case WORKERS -> new File(standartpath + "infooverlay/stone_icon.png");
                default -> throw new IllegalStateException("IconType does not exist");
            };
        }


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