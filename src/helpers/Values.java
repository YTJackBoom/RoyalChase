package helpers;

public class Values {
    public static enum VALUES {
        MANA,IRON,WOOD,STONE;
    }
    public static int GOLD = 100;
    public static int HEALTH = 100;
    public static int SCORE = 0;
    public static int LEVEL = 1;
    public static int MANA = 100;
    public static int IRON = 100;
    public static int WOOD = 100;
    public static int STONE = 100;
    public static int LEVELSCLEARED = 0;
    public static double REWARDMULTIPLYER = 1;

    public static void reset(){
        GOLD = 100;
        HEALTH = 100;
        SCORE = 0;
        LEVEL = 1;
        LEVELSCLEARED = 0;
        MANA = 11500;
        IRON = 11500;
        WOOD = 11500;
        STONE = 11500;
    }
}
