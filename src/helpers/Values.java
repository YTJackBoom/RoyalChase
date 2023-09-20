package helpers;

import java.io.Serializable;
import java.util.ArrayList;

public class Values implements Serializable {
    protected double gold = 100;
    protected double health = 100;
    protected int score = 0;
    protected int level = 1;
    protected double mana = 100;
    protected double iron = 100;
    protected double wood = 100;
    protected double stone = 100;
    protected double workers = 1;
    protected ArrayList<Integer> levelscleared = new ArrayList<Integer>();
    protected double rewardmultiplyer = 1;

    public Values(double workers,double gold,double mana,double iron, double wood, double stone) {
        this.workers = workers;
        this.gold = gold;
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
        levelscleared.clear();
        mana = 11500;
        iron = 11500;
        wood = 11500;
        stone = 11500;
    }

    public void increase(Values v){
        mana += v.mana;
        iron += v.iron;
        wood += v.wood;
        stone += v.stone;
        workers += v.workers;
    }

    public void decrease(Values v){
        mana -= v.mana;
        iron -= v.iron;
        wood -= v.wood;
        stone -= v.stone;
        workers -= v.workers;
    }

    public Values getUpgradeCost() {
        return new Values(0,gold+gold*Constants.ObjectConstants.UPGRADEMULTIPLYER,mana+mana*Constants.ObjectConstants.UPGRADEMULTIPLYER,iron+iron* Constants.ObjectConstants.UPGRADEMULTIPLYER,wood+wood* Constants.ObjectConstants.UPGRADEMULTIPLYER,stone+stone* Constants.ObjectConstants.UPGRADEMULTIPLYER);
    }



    public boolean canAfford(Values v){
        return gold>=v.gold && workers >= v.workers && mana >= v.getMana()&& iron >= v.getIron()&&wood >=v.getWood() && stone >=v.getStone();
    }
    public double getWorkers() {
        return workers;
    }
    public double getGold() {
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

    public ArrayList<Integer> getLevelscleared() {
        return levelscleared;
    }

    public double getRewardmultiplyer() {
        return rewardmultiplyer;
    }

    public void setRewardmultiplyer(double rewardmultiplyer) {
        this.rewardmultiplyer = rewardmultiplyer;
    }
    public Values cloneByPercent(double percentage) {
        return new Values(workers,gold*percentage,mana*percentage,iron*percentage,wood*percentage,stone*percentage);
    }
}
