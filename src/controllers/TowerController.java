package controllers;

import gameObjects.Enemy;
import gameObjects.ObjectType;
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
                if(tower.isActive()) {
                    g.drawImage(tower.getActiveAnimator().getCurrentImage(), tower.getPos().getX()-width/2, tower.getPos().getY()-height/2, null);
                } else {
                    g.drawImage(tower.getPassiveAnimator().getCurrentImage(), tower.getPos().getX()-width/2, tower.getPos().getY()-height/2, null);
                }
            }
        }
    }


     public void upgradeTower() {
        if(Values.MANA > math.TowerMath.calculateTowerUpgradeCost(selectedTower, Values.VALUES.MANA) &&
           Values.WOOD > math.TowerMath.calculateTowerUpgradeCost(selectedTower, Values.VALUES.WOOD) &&
           Values.IRON > math.TowerMath.calculateTowerUpgradeCost(selectedTower, Values.VALUES.IRON) &&
           Values.STONE > math.TowerMath.calculateTowerUpgradeCost(selectedTower, Values.VALUES.STONE)) {

            Values.MANA -= math.TowerMath.calculateTowerUpgradeCost(selectedTower, Values.VALUES.MANA);
            Values.WOOD -= math.TowerMath.calculateTowerUpgradeCost(selectedTower, Values.VALUES.WOOD);
            Values.IRON -= math.TowerMath.calculateTowerUpgradeCost(selectedTower, Values.VALUES.IRON);
            Values.STONE -= math.TowerMath.calculateTowerUpgradeCost(selectedTower, Values.VALUES.STONE);

            selectedTower.upgrade();
            System.out.println("d");
        } else {
            playing.setCantAfford(true);
            System.out.println("$");
        }
    }

    public void sellTower() {
        Values.MANA += math.TowerMath.calculateTowerWorth(selectedTower, Values.VALUES.MANA);
        Values.WOOD += math.TowerMath.calculateTowerWorth(selectedTower, Values.VALUES.WOOD);
        Values.IRON += math.TowerMath.calculateTowerWorth(selectedTower, Values.VALUES.IRON);
        Values.STONE += math.TowerMath.calculateTowerWorth(selectedTower, Values.VALUES.STONE);

        int towerIndex = towerEntityList.indexOf(selectedTower);
        towerEntityList.set(towerIndex,new TowerFoundation(this, selectedTower.getPos()));
        selectedTower = null;
        playing.setSelectedTower(null);

    }

    public void mouseReleased(int x, int y) {
        for (int i = 0; i < towerEntityList.size(); i++) {
            Tower tower = towerEntityList.get(i);
            if (tower.getBounds().contains(x, y)) {
                if(math.PlayerMath.canAfford(playing.getDraggedTower(), ObjectType.TOWER)) {
                    math.PlayerMath.deduct(playing.getDraggedTower(),ObjectType.TOWER);
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
