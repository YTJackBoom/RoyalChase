package controllers;

import gameObjects.Enemy;
import helpers.Coordinate;
import helpers.Values;
import helpers.math;
import helpers.variables;
import scenes.Playing;
import gameObjects.Tower;
import towers.TowerFoundation;

import java.awt.*;
import java.util.ArrayList;

import static helpers.variables.Towers.ARROW_T;

public class TowerController implements ControllerMethods{
    private ArrayList<Tower> towerEntityList;
    private Playing playing;
    private ArrayList<Enemy> enemyList;
    private Tower selectedTower;
    private Values playerValues;
    public TowerController(Playing playing) {
        towerEntityList = new ArrayList<Tower>();
        enemyList = playing.getEnemyController().getEnemyList();
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
            if(tower.getType() == ARROW_T) {
                System.out.println(tower.getPos().getX()+ "  "+tower.getPos().getY());
            }
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
                int towerX = tower.getPos().getX()-width/2;
                int towerY = tower.getPos().getY()-height/2;
                int baseX  = towerX + tower.getAnimatorBase().getWidth()/2;
                int baseY = towerY + tower.getAnimatorBase().getHeight()/2;
                if(tower.isActive()) {
                    // Retrieve the current image of the active animator
                    Image turretImage = tower.getActiveAnimatorTurret().getCurrentImage();

                    // Calculate the angle between the tower and its target
                    double angle = math.GeneralMath.calculateAngle(tower.getPos(), tower.getTarget().getPos());

                    // Create a new Graphics2D object
                    Graphics2D g2d = (Graphics2D) g.create();

                    // Translate the graphics origin to the center of the tower
                    g2d.translate(towerX + width / 2, towerY + height / 2);

                    // Rotate the graphics by the calculated angle
                    g2d.rotate(angle);

                    // Draw the rotated turret image
                    g2d.drawImage(turretImage, -width / 2, -height / 2, null);

                    // Dispose of the Graphics2D object
                    g2d.dispose();
                } else {
                    g.drawImage(tower.getPassiveAnimator().getCurrentImage(), towerX,towerY, null);
                }
                g.drawImage(tower.getAnimatorBase().getCurrentImage(),baseX,baseY,null);
            }
        }
    }


     public void upgradeTower() {
        Values upgradeCost = selectedTower.getWorth().getUpgradeCost();
        if(playerValues.canAfford(upgradeCost)) {
            playerValues.decrease(upgradeCost);
            selectedTower.getWorth().increase(upgradeCost);

            selectedTower.upgrade();
            System.out.println("d");
        } else {
            playing.setCantAfford(true);
            System.out.println("$");
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
