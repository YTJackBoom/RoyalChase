package controllers;

import enemy.Enemy;
import helpers.Coordinate;
import helpers.math;
import scenes.Playing;
import towers.ArrowTower;
import towers.Tower;
import towers.TowerFoundation;

import java.awt.*;
import java.util.ArrayList;

public class TowerController {
    private ArrayList<Tower> towerEntityList;
    private Playing playing;
    private ArrayList<Enemy> enemyList;
    private int counter = 0;
    private int delay = 10;
    public TowerController(Playing playing) {
        towerEntityList = new ArrayList<Tower>();
        enemyList = playing.getEnemyController().getEnemyList();
        this.playing = playing;

        initTowers(playing.getImageAnalyser().imgToFoundList());
    }



    public void update() {
        checkTELStatus();
        for (Tower tower : towerEntityList) {
            tower.update();
        }
    }
    public void initTowers(ArrayList<Coordinate> foundList) {
        for (Coordinate coordinate : foundList) {
            towerEntityList.add(new TowerFoundation(this, coordinate));
        }
    }
    public void checkTELStatus() {
        for (Tower tower : towerEntityList) {
            for (Enemy enemy : enemyList) {
                if (math.TowerMath.checkRange(tower, enemy) && tower.getTarget() == null) {
                    if(tower.getType()==1) System.out.println(99);
                    tower.setStatus(true);
                    tower.setTarget(enemy);
                    //System.out.println("			"+tower+"inrange"+enemy);
                }
            }
                    //	System.out.println("			"+i+"n range"+o);
            if (!math.TowerMath.checkRange(tower, tower.getTarget())) {
                    tower.setStatus(false);
                    tower.setTarget(null);
                    if(tower.getType()==1) System.out.println(100);
                }
            }
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

    public void mouseReleased(int x, int y) {
        for (int i = 0; i < towerEntityList.size(); i++) {
            Tower tower = towerEntityList.get(i);
            if (tower.getBounds().contains(x, y)) {
                towerEntityList.set(i, new ArrowTower(this, tower.getPos(), playing.getSelectedTower()));
                System.out.println("Tower placed");
            }
        }
    }

    public Playing getPlaying() {
        return playing;
    }
}
