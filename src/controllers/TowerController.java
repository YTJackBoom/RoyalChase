package controllers;

import enemy.Enemy;
import gameObjects.Coordinate;
import gameObjects.GameObject;
import helpers.variables;
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
        int towerRange = variables.Towers.getRange(tower.getType());
        Coordinate towerPos = tower.getPos();
        Coordinate enemyPos = object2.getPos();
        //	System.out.println(Math.abs(object2.getPos().getY()-object1.getPos().getY()));
        if(Math.abs(enemyPos.getY()-towerPos.getY())<= towerRange&&Math.abs(enemyPos.getX()-towerPos.getX())<=towerRange) {
            return true;
        }else return false;
    }
    public void render(Graphics g) {
        if (tEL != null) {
            for (Tower tower : tEL) {
                if(tower.isActive()) {
                    g.drawImage(tower.getActiveAnimator().getCurrentImage(), tower.getPos().getX(), tower.getPos().getY(), null);
                } else {
                    g.drawImage(tower.getPassiveAnimator().getCurrentImage(), tower.getPos().getX(), tower.getPos().getY(), null);
                }
            }
        }
    }
}
