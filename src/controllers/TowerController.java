package controllers;

import enemy.Enemy;
import helpers.Coordinate;
import helpers.variables;
import scenes.Playing;
import towers.Tower;
import towers.TowerFoundation;

import java.awt.*;
import java.util.ArrayList;

public class TowerController {
    private ArrayList<Tower> towerEntityList;
    private Playing playing;
    private ArrayList<Enemy> eL;
    public TowerController(Playing playing) {
        towerEntityList = new ArrayList<Tower>();
        eL = playing.getEnemyController().getEnemyList();
        this.playing = playing;

        initTowers(playing.getImageAnalyser().imgToFoundList());
    }



    public void initTowers(ArrayList<Coordinate> foundList) {
        for (Coordinate coordinate : foundList) {
            towerEntityList.add(new TowerFoundation(this, coordinate));
        }
    }
    public void checkTELStatus() {
        for (Tower tower : towerEntityList) {
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
        if (towerEntityList != null) {
            for (Tower tower : towerEntityList) {
                if(tower.isActive()) {
                    g.drawImage(tower.getActiveAnimator().getCurrentImage(), tower.getPos().getX(), tower.getPos().getY(), null);
                } else {
                    g.drawImage(tower.getPassiveAnimator().getCurrentImage(), tower.getPos().getX(), tower.getPos().getY(), null);
                }
            }
        }
    }
}
