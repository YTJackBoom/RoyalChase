package controllers;

import gameObjects.Enemy;
import helpers.*;
import scenes.Playing;
import gameObjects.Tower;
import towers.TowerFoundation;

import java.awt.*;
import java.util.ArrayList;

import static helpers.variables.Towers.*;

public class TowerController implements ControllerMethods{
    private ArrayList<Tower> towerEntityList;
    private Playing playing;
    private ArrayList<Enemy> enemyList;
    private Tower selectedTower;
    private Values playerValues;
    public TowerController(Playing playing) {
        towerEntityList = new ArrayList<Tower>();
        this.playing = playing;
        playerValues = playing.getGame().getPlayerValues();

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
        enemyList = playing.getEnemyController().getEnemyList();
        for (Enemy enemy : enemyList) {
                if (tower.getRange().contains(enemy.getHitBox()) && tower.getTarget() == null) {
                    tower.setStatus(true);
                    tower.setTarget(enemy);
//                    if (tower.getType() == 1) System.out.println("tower target set");
                }
            }

             if (!enemyList.contains(tower.getTarget())||!tower.getRange().contains(tower.getTarget().getHitBox())) {
                tower.setStatus(false);
                tower.setTarget(null);
//              if (tower.getType() == 1) System.out.println("towerTarget reset");

            }
    }



    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        if (towerEntityList != null) {
            for (Tower tower : towerEntityList) {
                int width = tower.getWidth() * Constants.UIConstants.TOWERSCALEFACTOR;
                int height = tower.getHeight() * Constants.UIConstants.TOWERSCALEFACTOR;
                int towerX = tower.getPos().getX() - width / 2;
                int towerY = tower.getPos().getY() - height/2;

                if (tower.isActive()) {
                    Image turretImage = tower.getActiveAnimator().getCurrentFrame();
                    g2d.drawImage(turretImage, towerX, towerY, width, height, null);
                    tower.getActiveAnimator().incrementFrame();
                } else {
                    Image turretImage = tower.getPassiveAnimator().getCurrentFrame();
                    g2d.drawImage(turretImage, towerX, towerY, width, height, null);
                    tower.getPassiveAnimator().incrementFrame();
                }
            }
        }
    }


     public void upgradeTower() {
        Values upgradeCost = selectedTower.getWorth().getUpgradeCost();
        if(playerValues.canAfford(upgradeCost)) {
            playerValues.decrease(upgradeCost);
            selectedTower.getWorth().increase(upgradeCost);

            selectedTower.upgrade();
//            System.out.println("d");
        } else {
            playing.setCantAfford(true);
//            System.out.println("$");
        }
    }

    public void sellTower() {
        playerValues.increase(selectedTower.getWorth());

        int towerIndex = towerEntityList.indexOf(selectedTower);
        towerEntityList.set(towerIndex,new TowerFoundation(this, selectedTower.getPos()));
        selectedTower = null;
        playing.setSelectedTower(null);

    }

    public void mouseReleased(int x, int y) {
        for (int i = 0; i < towerEntityList.size(); i++) {
            Tower tower = towerEntityList.get(i);
            if (tower.getBounds().contains(x, y)) {
                Values cost = variables.Towers.getCost(playing.getDraggedTower());
                if(playerValues.canAfford(cost)){
                    playerValues.decrease(cost);
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
            if(tower.getType() != variables.Towers.Foundation_T) {
                if (tower.getBounds().contains(x, y)) {
                    selectedTower = tower;
                    playing.setSelectedTower(tower);
                }
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
