package helpers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;

/**
 * Klasse zum Speichern der Asset-Pfade
 */
public class AssetLocation {
    public static class Enemies {
        private static final String ENEMY_BASE_PATH = "res/images/enemies/";
        public static String getEnemyActiveGifPath(int enemyType) {
            return ENEMY_BASE_PATH + ObjectValues.Enemies.getEnemyName(enemyType).toLowerCase() + "_e_active/";
        }
        public static int getNumberOfIntDeclarations() {
            return AssetLocation.getHighestInt(ObjectValues.Enemies.class);
        }
        public static int getLowestInt() {
            return AssetLocation.getLowestInt(ObjectValues.Enemies.class);
        }
    }
    public static class Maps {

        private static final String MAP_WAVE_BASE_PATH = "res/text/waveFiles/";
        private static final String TILE_TEXT_BASE_PATH = "res/text/tileFiles/";

        public static File getMapWaveFile(int waveType) {
            return new File(MAP_WAVE_BASE_PATH + "waves" + waveType + ".txt");
        }

        public static File getTileTextFile(int level) {
            return new File(TILE_TEXT_BASE_PATH + "tiles" + level + ".txt");
        }

    }

    public static class Tiles {

        private static final String TILE_BASE_PATH = "res/images/tiles/";

        public static File getTileFile(int tileType) {
            return new File(TILE_BASE_PATH + tileType + ".png");
        }
        public static int getNumberOfIntDeclarations() {return AssetLocation.getHighestInt(ObjectValues.Tiles.class);}
        public static int getLowestInt(){return AssetLocation.getLowestInt(ObjectValues.Tiles.class);}
    }

    public static class Towers {

        private static final String TOWER_BASE_PATH = "res/images/towers/";

        public static File getTowerPassiveGifFile(int towerType) {
            return new File(TOWER_BASE_PATH + ObjectValues.Towers.getTowerName(towerType).toLowerCase() + "/passive.gif");
        }

        public static String getTowerActiveGifPath(int towerType) {
            return TOWER_BASE_PATH + ObjectValues.Towers.getTowerName(towerType).toLowerCase() + "/";
        }
        public static int getNumberOfIntDeclarations() {return AssetLocation.getHighestInt(ObjectValues.Towers.class);}
        public static int getLowestInt(){return AssetLocation.getLowestInt(ObjectValues.Towers.class);}
    }


    public static class Projectiles {
        private static final String PROJECTILE_BASE_PATH = "res/images/projectiles/";
        public static File getProjectileGifFile(int projectileType) {
            return new File(PROJECTILE_BASE_PATH + ObjectValues.Projectiles.getProjectileName(projectileType).toLowerCase() + ".gif");
            };
        public static int getNumberOfIntDeclarations() {return AssetLocation.getHighestInt(ObjectValues.Projectiles.class);}
        public static int getLowestInt(){return AssetLocation.getLowestInt(ObjectValues.Projectiles.class);}
        }
    public static class Town {
        public static BufferedImage getBackgroundImage() {
            File mapFile = new File("res/images/town/town_background.png");
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
        private static final String BUILDING_BASE_PATH = "res/images/buildings/";

        public static File getBuildingGifFile(int buildingType) {
            try {
                return new File(BUILDING_BASE_PATH + ObjectValues.Buildings.getBuildingName(buildingType).toLowerCase() + ".gif");
            } catch (Exception e) {
                System.out.println("Failed to get the gif file for buildingType: " + buildingType);
                e.printStackTrace();
                return null;
            }
        }
        public static int getNumberOfIntDeclarations() {
            return AssetLocation.getHighestInt(ObjectValues.Buildings.class);
        }
        public static int getLowestInt(){return AssetLocation.getLowestInt(ObjectValues.Buildings.class);}
    }

    public static class Buttons {
        private static final String BUTTON_BASE_PATH = "res/images/icons/interactables/";
        private static final String TOOLTIP_BASE_PATH = "res/text/tooltips/";
        public static final int ARROW_T_B = 1;
        public static final int MAGE_T_B = 2;
        public static final int ROCKET_T_B = 3;
        public static final int SNIP_T_B = 4;
        public static final int MANA_B_B = 5;
        public static final int IRON_B_B = 6;
        public static final int STONE_B_B = 7;
        public static final int WOOD_B_B = 8;
        public static final int HOUSE_B_B = 9;
        public static final int MINER_B_B = 10;

        public final static int EXTEND_U_B = 11;
        public static final int COLLAPSE_U_B = 12;


        public static File getButtonImageFile(int buttonType) {
            try {
                return new File(BUTTON_BASE_PATH + AssetLocation.getFieldNameByValue(Buttons.class,buttonType).toLowerCase() + ".png");
            } catch (Exception e) {
                System.out.println("Failed to get the png file for buttonType: " + buttonType);
                e.printStackTrace();
                return null;
            }

        }

        public static File getTooltipTextFile(int buttonType) {
            return new File(TOOLTIP_BASE_PATH + AssetLocation.getFieldNameByValue(AssetLocation.Buttons.class,buttonType).toLowerCase() + ".txt");
        }

        public static int getNumberOfIntDeclarations() {
            return AssetLocation.getHighestInt(Buttons.class);
        }
        public static int getLowestInt(){return AssetLocation.getLowestInt(Buttons.class);}
    }
    public static class Icons {
        private static final String ICONS_BASE_PATH = "res/images/icons/";
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
        public static final int BOSSBAR = 11;
        public static final int BUTTONBAR_RIGHT = 12;
        public static final int BUTTONBAR_DOWN = 13;

        public static File getIconImageFile(int IconType) {
            return switch (IconType) {
                case RESUME -> new File(ICONS_BASE_PATH + "infooverlay/resume_icon.png");
                case PAUSE -> new File(ICONS_BASE_PATH + "infooverlay/pause_icon.png");
                case ENEMIES -> new File(ICONS_BASE_PATH + "infooverlay/enemies_icon.png");
                case WAVES -> new File(ICONS_BASE_PATH + "infooverlay/waves_icon.png");
                case HEART -> new File(ICONS_BASE_PATH + "infooverlay/heart_icon.png");
                case GOLD -> new File(ICONS_BASE_PATH + "infooverlay/gold_icon.png");
                case WOOD -> new File(ICONS_BASE_PATH + "infooverlay/wood_icon.png");
                case IRON -> new File(ICONS_BASE_PATH + "infooverlay/iron_icon.png");
                case MANA -> new File(ICONS_BASE_PATH + "infooverlay/mana_icon.png");
                case STONE -> new File(ICONS_BASE_PATH + "infooverlay/stone_icon.png");
                case WORKERS -> new File(ICONS_BASE_PATH + "infooverlay/worker_icon.png");
                case BOSSBAR -> new File(ICONS_BASE_PATH + "infooverlay/boss_bar.png");
                case BUTTONBAR_RIGHT -> new File(ICONS_BASE_PATH + "ui/buttonbar_right.png");
                case BUTTONBAR_DOWN -> new File(ICONS_BASE_PATH + "ui/buttonbar_right.png");
                default -> throw new IllegalStateException("IconType does not exist");
            };
        }
        public static int getNumberOfIntDeclarations() {
            return AssetLocation.getHighestInt(Icons.class);
        }
        public static int getLowestInt(){return AssetLocation.getLowestInt(Icons.class);}
    }
    public static class Sounds {
        public static class Deaths {
            private static final String DEATHS_BASE_PATH = "res/sounds/deaths/";
            public static final int SLIME_DIED = 0;
            public static final int SKELETON_DIED = 1;
            public static final int GOBLIN_DIED = 2;
            public static final int WITCH_DIED = 3;
            public static final int WIZARD_DIED = 4;
            public static final int TOWER_DIED = 5 ;

            public static File getSoundFile(int DeathType) {
                return new File(DEATHS_BASE_PATH + getFieldNameByValue(AssetLocation.Sounds.Deaths.class, DeathType).toLowerCase() + ".wav");
            }

            public static int getNumberOfIntDeclarations() {
                return AssetLocation.getHighestInt(Deaths.class);
            }
            public static int getLowestInt(){return AssetLocation.getLowestInt(Deaths.class);}
        }
        public static class ProjectileFired {
            private static final String PROJECTILE_FIRED_BASE_PATH = "res/sounds/projectileFired/";
            public static final int ARROW_FIRED = ObjectValues.Projectiles.ARROW;
            public static final int ROCKET_FIRED = ObjectValues.Projectiles.ROCKET;
            public static final int SNIP_FIRED = ObjectValues.Projectiles.BULLET;
            public static final int MAGE_FIRED = ObjectValues.Projectiles.LIGHTNINGBALL;

            public static File getSoundFile(int ProjectileFiredType) {
                return new File(PROJECTILE_FIRED_BASE_PATH + getFieldNameByValue(AssetLocation.Sounds.ProjectileFired.class, ProjectileFiredType).toLowerCase() + ".wav");
            }

            public static int getNumberOfIntDeclarations() {
                return AssetLocation.getHighestInt(ProjectileFired.class);
            }
            public static int getLowestInt(){return AssetLocation.getLowestInt(ProjectileFired.class);}
        }
        private static final String SOUNDS_BASE_PATH = "res/sounds/";
        public static final int BACKGROUND_PLAYING = 0;
//        public static final int BACKGROUND_TOWN = 1;
//        public static final int BACKGROUND_MENU = 2;
//        public static final int BACKGROUND_GAMEOVER = 3;
//        public static final int BACKGROUND_WIN = 4;
//
//        public static final int ARROW_FIRED = 5;
//        public static final int ROCKET_FIRED = 6;
//        public static final int SNIP_FIRED = 7;
//        public static final int MAGE_FIRED = 8;
//        public static final int ENEMY_DIED = 9;

        public static File getSoundFile(int SoundType) {
            return new File(SOUNDS_BASE_PATH + ObjectValues.getFieldNameByValue(AssetLocation.Sounds.class, SoundType).toLowerCase() + ".wav");

        }

        ;

        public static int getNumberOfIntDeclarations() {
            return AssetLocation.getHighestInt(Sounds.class);
        }

        public static int getLowestInt() {
            return AssetLocation.getLowestInt(Sounds.class);
        }

    }


    public static int getHighestInt(Class < ? > clazz){
        int highestNumber = Integer.MIN_VALUE;

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
        int lowestNumber = Integer.MAX_VALUE;

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