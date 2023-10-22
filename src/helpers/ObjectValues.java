package helpers;

import gameObjects.Tile;

import java.lang.reflect.Field;

/**
 * Klasse zum Speichern der Werte der Objekte
 */
public class ObjectValues {
        public static class Enemies {

            public static final int SLIME = 0;
            public static final int SKELETON = 1;
            public static final int GOBLIN = 2;
            public static final int GOLEM = 3;
            public static final int WIZARD = 4;
            public static final int SKELETON_KING = 5;
            public static final int ZOMBIE_GIANT = 6;
            public static final int THE_EYE = 7;


            public static int getEnemyReward(int enemyType) {
                return switch (enemyType) {
                    case SLIME -> 5;
                    case SKELETON -> 5;
                    case GOBLIN -> 50;
                    case GOLEM -> 10;
                    case WIZARD -> 10;
                    case SKELETON_KING -> 100;
                    case ZOMBIE_GIANT -> 100;
                    case THE_EYE -> 100;
                    default -> 0;
                };
            }


            public static int getEnemyHealth(int enemyType) {
                return switch (enemyType) {
                    case SLIME -> 50;
                    case SKELETON -> 75;
                    case GOBLIN -> 100;
                    case GOLEM -> 500;
                    case WIZARD -> 75;
                    case SKELETON_KING -> 200;
                    case ZOMBIE_GIANT -> 500;
                    case THE_EYE -> 100;
                    default -> throw new IllegalStateException("variables: getEnemyHealth: Unexpected value:  " + enemyType);
                };
            }
            public static double getEnemySpeed(int enemyType) {
                return switch (enemyType) {
                    case SLIME -> 0.5;
                    case SKELETON -> 1;
                    case GOBLIN -> 2;
                    case GOLEM -> 0.25;
                    case WIZARD -> 1;
                    case SKELETON_KING -> 1.5;
                    case ZOMBIE_GIANT -> 0.5;
                    case THE_EYE -> 1;
                    default -> throw new IllegalStateException("variables: getEnemySpeed: Unexpected value:  " + enemyType);
                };
            }

            public static double getEnemyDamage(int enemyType) {
                return switch (enemyType) {
                    case SLIME -> 50;
                    case SKELETON -> 2.5;
                    case GOBLIN -> 5;
                    case GOLEM -> 2.5;
                    case WIZARD -> 7.5;
                    case SKELETON_KING -> 15;
                    case ZOMBIE_GIANT -> 20;
                    case THE_EYE -> Integer.MAX_VALUE;
                    default -> throw new IllegalStateException("variables: getEnemyDamage: Unexpected value:  " + enemyType);
                };
            }
            public static double getEnemyReloadTime(int enemyType) { //Entweder nachlade(range) oder angriffgeschwindigkeit(melee) in sek/angriff
                return switch (enemyType) {
                    case SLIME -> 0;
                    case SKELETON -> 4;
                    case GOBLIN -> 2.5;
                    case GOLEM -> 7;
                    case WIZARD -> 7;
                    case SKELETON_KING -> 5;
                    case ZOMBIE_GIANT -> 8;
                    case THE_EYE -> 10;
                    default -> throw new IllegalStateException("variables: getEnemyRelaodTime: Unexpected value: "+ enemyType);
                };
            }
            public static double getEnemyRange(int enemyType) {
                return switch (enemyType) {
                    case SLIME -> 0;
                    case SKELETON -> 150;
                    case GOBLIN -> 0;
                    case GOLEM -> 0;
                    case WIZARD -> 150;
                    case SKELETON_KING -> 0;
                    case ZOMBIE_GIANT -> 400;
                    case THE_EYE -> 1000;
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
            public static final int spawn_to_buttom_grass = 10;
            public static final int spawn_to_buttom_wood = 11;
            public static final int gate_to_buttom_grass = 12;
            public static final int gate_to_buttom_wood = 13;
            public static final int gate_to_up_grass = 46;
            public static final int spawn_to_top_grass = 26;
            public static final int spawn_to_top_wood = 51;
            public static final int corner_topleft_grass = 32;
            public static final int corner_topright_grass = 34;
            public static final int corner_buttomleft_grass = 35;
            public static final int corner_buttomright_grass = 33;
            public static final int corner_topleft_wood = 42;
            public static final int corner_topright_wood = 44;
            public static final int corner_buttomleft_wood = 45;
            public static final int corner_buttomright_wood = 43;




            public static Tile getRawTile(int i) { //Pures Tile
                return switch (i) {
                    case grass -> new Tile(0, false, true, false, false);
                    case wood -> new Tile(1, false, false, false, false);
                    case spawn_to_buttom_grass -> new Tile(10, true, false, true, false);
                    case spawn_to_buttom_wood -> new Tile(11, true, false, true, false);
                    case gate_to_buttom_grass -> new Tile(12, true, false, false, true);
                    case gate_to_buttom_wood -> new Tile(13, true, false, false, true);
                    case spawn_to_top_grass -> new Tile(26, true, false, true, false);
                    case spawn_to_top_wood -> new Tile(i, true, false, true, false);
                    case gate_to_up_grass -> new Tile(i, true, false, false, true);
                    case corner_topleft_grass, corner_buttomright_grass, corner_buttomleft_grass, corner_topright_grass -> new Tile(i, false, true, false, false);
                    case corner_topleft_wood, corner_buttomright_wood, corner_buttomleft_wood, corner_topright_wood -> new Tile(i, false, false, false, false);
                    default -> new Tile(i, true, false, false, false);
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
                    case ARROW_T -> 10;
                    case MAGE_T -> 65;
                    case ROCKET_T -> 55;
                    case SNIP_T -> 110;
                    default -> throw new IllegalStateException("variables: getTowerDamage: Unexpected value:  " + towerType);
                };
            }
            public static int getTowerRange(int towerType) {
                return switch (towerType) {
                    case Foundation_T -> 0;
                    case ARROW_T -> 500;
                    case MAGE_T -> 400;
                    case ROCKET_T -> 500;
                    case SNIP_T -> 1000;
                    default -> throw new IllegalStateException("variables: getRange: Unexpected value:  " + towerType);
                };
            }
            public static int getTowerReloadTime(int towerType) {
                return switch (towerType) {
                    case Foundation_T -> 0;
                    case ARROW_T -> 50;
                    case MAGE_T -> 250;
                    case ROCKET_T -> 2000;
                    case SNIP_T -> 3000;
                    default -> throw new IllegalStateException("variables: getTowerReload: Unexpected value:  " + towerType);
                };
            }

            public static Values getCost(int towerType) {
                return switch (towerType) {
                    case Foundation_T -> new Values(0,0,0,0,0,0);
                    case ARROW_T -> new Values(1,50,15,20,70,50);
                    case MAGE_T -> new Values(2,70,100,5,25,30);
                    case ROCKET_T -> new Values(3,90,0,100,25,40);
                    case SNIP_T -> new Values(3,90,0,150,30,45);
                    default -> new Values(999,999,999,999,999,999);
                };
            }
            public static double getTowerHealth(int towerType) {
                return switch (towerType) {
                    case Foundation_T -> 9999;
                    case ARROW_T -> 100;
                    case MAGE_T -> 75;
                    case ROCKET_T -> 120;
                    case SNIP_T -> 35;
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
                    case LIGHTNINGBALL -> 1.5;
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
                    case HOUSE -> new Values(5,0,0,0,0,0);
                    default -> new Values(0,0,0,0,0,0);
                };
            }
            public static Values getCost(int buildingType) {
                return switch (buildingType) {
                    case MINER -> new Values(1,100,0,75,25,0);
                    case WOOD_MINER -> new Values(2,100,0,150,50,50);
                    case HOUSE -> new Values(2,100,100,100,100,100);
                    default -> new Values(999,999,999,999,999,999);
                };

            }
            public static String getBuildingName(int buildingType) {
                return getFieldNameByValue(ObjectValues.Buildings.class, buildingType);
            }
        }



    public static String getFieldNameByValue(Class<?> cls, int value) {
        for (Field field : cls.getDeclaredFields()) {
            if (field.getType() == int.class || field.getType() == Integer.class) {
                try {
                    if (field.getInt(null) == value) {
                        return field.getName();
                    }
                } catch (IllegalAccessException e) {

                }
            }
        }
        return null;
    }
}
