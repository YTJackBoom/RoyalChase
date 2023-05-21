package controllers;

import enemy.Enemy;
import gameObjects.GameObject;
import scenes.Playing;
import towers.Tower;

import java.awt.*;
import java.util.ArrayList;

public class TowerController {
    private ArrayList<Tower> tEL;
    private Playing playing;
    private ArrayList<Enemy> eL;
    public TowerController(Playing playing) {
        tEL = new ArrayList<Tower>();
        eL = playing.getEnemyController().getEnemyList();
        this.playing = playing;

    }



    public void checkTELStatus() {
        for (Tower tower : tEL) {
            for (Enemy enemy : eL) {
                if (checkRange(tower, enemy)) {
                    tower.changeStatus(true);
                    tower.setTarget(enemy);
                    //		System.out.println("			"+i+"inrange"+o);
                } else {
                    tower.changeStatus(false);
                    //	System.out.println("			"+i+"n range"+o);
                }
            }
        }
    }


    public boolean checkRange(Tower tower, Enemy object2) {
        //	System.out.println(Math.abs(object2.getPos().getY()-object1.getPos().getY()));
        if(Math.abs(object2.getPos().getY()-tower.getPos().getY())<=tower.getRange()&&Math.abs(object2.getPos().getX()-tower.getPos().getX())<=tower.getRange()) {
            return true;
        }else return false;
    }
    public void render(Graphics g) {
        for (Tower tower : tEL) {
        }
    }
}
