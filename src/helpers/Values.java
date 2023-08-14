package helpers;

import java.io.Serializable;

public class Values implements Serializable {


    public enum VALUES {
        MANA,IRON,WOOD,STONE;
    }
    private int gold = 100;
    private double health = 100;
    private int score = 0;
    private int level = 1;
    private double mana = 100;
    private double iron = 100;
    private double wood = 100;
    private double stone = 100;
    private int levelscleared = 0;
    private double rewardmultiplyer = 1;

    public Values(double mana,double iron, double wood, double stone) {
        this.mana = mana;
        this.iron = iron;
        this.wood = wood;
        this.stone = stone;
    }
    public  Values() {}
    public void reset(){
        gold = 100;
        health = 100;
        score = 0;
        level = 1;
        levelscleared = 0;
        mana = 11500;
        iron = 11500;
        wood = 11500;
        stone = 11500;
    }

    public void increase(Values v){
        mana += v.getMana();
        iron += v.getIron();
        wood += v.getWood();
        stone += v.getStone();
    }

    public void decrease(Values v){
        mana -= v.getMana();
        iron -= v.getIron();
        wood -= v.getWood();
        stone -= v.getStone();
    }

    public Values getUpgradeCost() {
        return new Values(mana+mana*Constants.ObjectConstants.UPGRADEMULTIPLYER,iron+iron* Constants.ObjectConstants.UPGRADEMULTIPLYER,wood+wood* Constants.ObjectConstants.UPGRADEMULTIPLYER,stone+stone* Constants.ObjectConstants.UPGRADEMULTIPLYER);
    }



    public boolean canAfford(Values v){
        return mana >= v.getMana()&& iron >= v.getIron()&&wood >=v.getWood() && stone >=v.getStone();
    }
    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getMana() {
        return mana;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }

    public double getIron() {
        return iron;
    }

    public void setIron(double iron) {
        this.iron = iron;
    }

    public double getWood() {
        return wood;
    }

    public void setWood(double wood) {
        this.wood = wood;
    }

    public double getStone() {
        return stone;
    }

    public void setStone(double stone) {
        this.stone = stone;
    }

    public int getLevelscleared() {
        return levelscleared;
    }

    public void setLevelscleared(int levelscleared) {
        this.levelscleared = levelscleared;
    }

    public double getRewardmultiplyer() {
        return rewardmultiplyer;
    }

    public void setRewardmultiplyer(double rewardmultiplyer) {
        this.rewardmultiplyer = rewardmultiplyer;
    }


}
