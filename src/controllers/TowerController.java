package controllers;

import gameObjects.Enemy;
import helpers.Coordinate;
import helpers.math;
import scenes.Playing;
import gameObjects.Tower;
import towers.TowerFoundation;

import java.awt.*;
import java.util.ArrayList;

public class TowerController implements ControllerMethods{
    private ArrayList<Tower> towerEntityList;
    private Playing playing;
    private ArrayList<Enemy> enemyList;
    public TowerController(Playing playing) {
        towerEntityList = new ArrayList<Tower>();
        enemyList = playing.getEnemyController().getEnemyList();
        this.playing = playing;

        initTowers(playing.getImageAnalyser().imgToFoundList());
    }




    public void update() {
        for (Tower tower : towerEntityList) {
            if(!playing.isPaused()) {
                checkTELStatus(tower);
            }
            tower.update();
        }
    }
    public void initTowers(ArrayList<Coordinate> foundList) {
        for (Coordinate coordinate : foundList) {
            towerEntityList.add(new TowerFoundation(this, coordinate));
        }
    }
    public void checkTELStatus(Tower tower) {
            for (Enemy enemy : enemyList) {
                if (math.TowerMath.checkRange(tower, enemy) && tower.getTarget() == null) {
                    tower.setStatus(true);
                    tower.setTarget(enemy);
//                    if (tower.getType() == 1) System.out.println("tower target set");
                }
            }

             if (!enemyList.contains(tower.getTarget())) {
                tower.setStatus(false);
                tower.setTarget(null);
//              if (tower.getType() == 1) System.out.println("towerTarget reset");

            }
    }



    public void render(Graphics g) {
        if (towerEntityList != null) {
            for (Tower tower : towerEntityList) {
                int width = tower.getWidth();
                int height = tower.getHeight();
                if(tower.isActive()) {
                    g.drawImage(tower.getActiveAnimator().getCurrentImage(), tower.getPos().getX()-width/2, tower.getPos().getY()-height/2, null);
                } else {
                    g.drawImage(tower.getPassiveAnimator().getCurrentImage(), tower.getPos().getX()-width/2, tower.getPos().getY()-height/2, null);
                }
            }
            renderTowerInfos(g);
        }
    }
    public void renderTowerInfos(Graphics g) {

    }

    public void mouseReleased(int x, int y) {
        for (int i = 0; i < towerEntityList.size(); i++) {
            Tower tower = towerEntityList.get(i);
            if (tower.getBounds().contains(x, y)) {
                if(math.PlayerMath.canAfford(playing.getDraggedTower(), 0)) {
                    math.PlayerMath.deduct(playing.getDraggedTower(),0);
                    towerEntityList.set(i, new Tower(this, tower.getPos(), playing.getDraggedTower()));
                    System.out.println("Tower placed");
                }else {
                    playing.setCantAfford(true);
                }
            }
        }
    }
    public void mouseClicked(int x, int y) {
        for(Tower tower : towerEntityList) {
            if(tower.getBounds().contains(x,y)) {
                playing.setSelectedTower(tower);
            }
        }
    }

    public Playing getPlaying() {
        return playing;
    }

    @Override
    public void workAddQueue() {

    }

    @Override
    public void workRemoveQueue() {

    }


}
