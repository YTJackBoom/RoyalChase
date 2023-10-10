package helpers;

import gameObjects.Tile;

import java.lang.reflect.Field;

public class ObjectValues {
        public static class Enemies {

            public static final int SLIME = 0;
            public static final int SKELETON = 1;
            public static final int GOBLIN = 2;
            public static final int WITCH = 3;
            public static final int WIZARD = 4;
            public static final int SKELETON_KING = 5;
            public static final int ZOMBIE_GIANT = 6;
            public static final int WITCH_QUEEN = 7;
            public static final int WIZARD_KING = 8;


            public static int getEnemyReward(int enemyType) {
                return switch (enemyType) {
                    case SLIME -> 5;
                    case SKELETON -> 5;
                    case GOBLIN -> 50;
                    case WITCH -> 10;
                    case WIZARD -> 10;
                    case SKELETON_KING -> 100;
                    case ZOMBIE_GIANT -> 100;
                    case WITCH_QUEEN -> 100;
                    case WIZARD_KING -> 100;
                    default -> 0;
                };
            }


            public static int getEnemyHealth(int enemyType) {
                return switch (enemyType) {
                    case SLIME -> 50;
                    case SKELETON -> 100;
                    case GOBLIN -> 100;
                    case WITCH -> 100;
                    case WIZARD -> 75;
                    case SKELETON_KING -> 100;
                    case ZOMBIE_GIANT -> 500;
                    case WITCH_QUEEN -> 100;
                    case WIZARD_KING -> 100;
                    default -> throw new IllegalStateException("variables: getEnemyHealth: Unexpected value:  " + enemyType);
                };
            }
            public static double getEnemySpeed(int enemyType) {
                return switch (enemyType) {
                    case SLIME -> 0.5;
                    case SKELETON -> 1;
                    case GOBLIN -> 2;
                    case WITCH -> 5;
                    case WIZARD -> 1;
                    case SKELETON_KING -> 5;
                    case ZOMBIE_GIANT -> 0.5;
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
                    case WIZARD -> 7.5;
                    case SKELETON_KING -> 5;
                    case ZOMBIE_GIANT -> 20;
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
                    case ZOMBIE_GIANT -> 1000;
                    case WITCH_QUEEN -> 1600;
                    case WIZARD_KING -> 1600;
                    default -> throw new IllegalStateException("variables: getEnemyRelaodTime: Unexpected value: "+ enemyType);
                };
            }
            public static double getEnemyRange(int enemyType) {
                return switch (enemyType) {
                    case SLIME -> 0;
                    case SKELETON -> 75;
                    case GOBLIN -> 1;
                    case WITCH -> 50;
                    case WIZARD -> 150;
                    case SKELETON_KING -> 100;
                    case ZOMBIE_GIANT -> 500;
                    case WITCH_QUEEN -> 100;
                    case WIZARD_KING -> 100;
                    default -> throw new IllegalStateException("variables: getEnemyRange: Unexpected value: "+ enemyType);
                };
            }

            public static String getEnemyName(int enemyType) {
                return getFieldNameByValue(ObjectValues.Enemies.class, enemyType);
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
        }

        public static class Towers {
            public static final int Foundation_T = 0;
            public static final int ARROW_T = AssetLocation.Buttons.ARROW_T_B;
            public static final int MAGE_T = AssetLocation.Buttons.MAGE_T_B;
            public static final int ROCKET_T = AssetLocation.Buttons.ROCKET_T_B;
            public static final int SNIP_T = AssetLocation.Buttons.SNIP_T_B;


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
            public static String getTowerName(int towerType) {
                return getFieldNameByValue(ObjectValues.Towers.class, towerType);
            }
        }

        public static class Projectiles {
            public static final int ARROW = helpers.ObjectValues.Towers.ARROW_T;
            public static final int LIGHTNINGBALL = helpers.ObjectValues.Towers.MAGE_T;
            public static final int ROCKET = helpers.ObjectValues.Towers.ROCKET_T;
            public static final int BULLET = helpers.ObjectValues.Towers.SNIP_T;

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
            public static String getProjectileName(int projectileType) {
                return getFieldNameByValue(ObjectValues.Projectiles.class, projectileType);
            }
        }
        public static class Buildings {
            public static final int PLACEHOLDER = 0;
            public static final int MANA_MINER = AssetLocation.Buttons.MANA_B_B;
            public static final int IRON_MINER = AssetLocation.Buttons.IRON_B_B;
            public static final int STONE_MINER = AssetLocation.Buttons.STONE_B_B;
            public static final int WOOD_MINER = AssetLocation.Buttons.WOOD_B_B;
            public static final int HOUSE = AssetLocation.Buttons.HOUSE_B_B;
            public static final int MINER = AssetLocation.Buttons.MINER_B_B;
            public static final int MANA_ORE = 1;
            public static final int IRON_ORE = 2;
            public static final int STONE_ORE = 3;
            public static final int WOOD_ORE = 4;
            public static Values getProduction(int type) {
                double percent = math.DifficultyMath.calculateTownProductionPercentChange();
                return switch (type) {
                    case PLACEHOLDER -> new Values(0,0,0,0,0,0);
                    case MANA_MINER -> new Values(0,0,5*percent,0,0,0);
                    case IRON_MINER -> new Values(0,0,0,5*percent,0,0);
                    case WOOD_MINER -> new Values(0,0,0,0,5*percent,0);
                    case STONE_MINER -> new Values(0,0,0,0,0,5*percent);
                    case HOUSE -> new Values(5*percent,0,0,0,0,0);
                    default -> new Values(0,0,0,0,0,0);
                };
            }
            public static Values getCost(int buildingType) {
                return switch (buildingType) {
                    case MINER -> new Values(1,0,0,0,0,0);
                    case WOOD_MINER -> new Values(1,0,0,0,0,0);
                    case HOUSE -> new Values(2,10,10,10,10,10);
                    default -> new Values(999,999,999,999,999,999);
                };

            }
            public static String getBuildingName(int buildingType) {
                return getFieldNameByValue(ObjectValues.Buildings.class, buildingType);
            }
        }



    public static String getFieldNameByValue(Class<?> cls, int value) {
        for (Field field : cls.getDeclaredFields()) {
            if (field.getType() == int.class || field.getType() == Integer.class) { // Ensure field is of type int or Integer
                try {
                    if (field.getInt(null) == value) {
                        return field.getName();
                    }
                } catch (IllegalAccessException e) {
                    // Handle this exception if necessary
                }
            }
        }
        return null; // or throw an exception if a matching field is not found
    }
}
