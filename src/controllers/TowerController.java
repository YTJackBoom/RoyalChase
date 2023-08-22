package controllers;

import gameObjects.Enemy;
import gameObjects.GameObject;
import gameObjects.Tile;
import helpers.*;
import scenes.Playing;
import gameObjects.Tower;
import towers.TowerFoundation;
import uiElements.Explosion;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static basics.Game.ups;

public class TowerController extends ObjectsController implements ControllerMethods{
    private ArrayList<Tower> towerEntityList;
    private Playing playing;
    private ArrayList<Enemy> enemyList;
    private ArrayList<Explosion> explosionsList;
    private Tower selectedTower;
    private Values playerValues;

    private int towerSoldCounter; //Wenn turm kürzlich verkauft wurde, um nicht sofort einen weiteren verkaufen zu könnnen
    public TowerController(Playing playing) {
        towerEntityList = new ArrayList<Tower>();
        explosionsList = new ArrayList<Explosion>();
        this.playing = playing;
        playerValues = playing.getGame().getPlayerValues();
        initGate();
    }




    public void update() {
        for (Tower tower : towerEntityList) {
            if(!playing.isPaused()) {
                checkTowerRange(tower);
            }
            tower.update();
        }
        updateExplosions();
    }
    public void initGate() {
        towerEntityList.add(new TowerFoundation(this, variables.Maps.getGatePos(playing.getGame().getGameState().getPlayerValues().getLevel())));
    }
    public void checkTowerRange(Tower tower) {
        enemyList = playing.getEnemyController().getEnemyList();
        for (Enemy enemy : enemyList) {
                if (tower.getRange().contains(enemy.getHitBox()) && tower.getTarget() == null) {
                    tower.setStatus(true);
                    tower.setTarget(enemy);
                    break;
//                    if (tower.getType() == 1) System.out.println("tower target set");
                }
            }

             if (!enemyList.contains(tower.getTarget())||!tower.getRange().contains(tower.getTarget().getHitBox())) {
                tower.setStatus(false);
                tower.setTarget(null);
//              if (tower.getType() == 1) System.out.println("towerTarget reset");

            }
    }



    public synchronized void updateExplosions() {
        for(Explosion e : explosionsList) {
            e.update(this);
        }
    }
    public void render(Graphics g) {
        if (towerEntityList != null) {
            renderTowers(g);
            renderTowersHealthBars(g);
            renderTowerLevels(g);
            renderExplosions(g);
        }
    }


    public void renderTowers(Graphics g){
        for(Tower tower : towerEntityList) {
            int width = tower.getWidth();
            int height = tower.getHeight();
            int towerX = tower.getPos().getX() - width / 2;
            int towerY = tower.getPos().getY() - height / 2;

            if (tower.isActive()) {
                Image turretImage = tower.getActiveAnimator().getCurrentFrame();
                g.drawImage(turretImage, towerX, towerY, width, height, null);
                tower.getActiveAnimator().incrementFrame();
            } else {
                Image turretImage = tower.getPassiveAnimator().getCurrentFrame();
                g.drawImage(turretImage, towerX, towerY, width, height, null);
                tower.getPassiveAnimator().incrementFrame();
            }
        }
    }
    public void renderTowerLevels(Graphics g) {
        for(Tower tower : towerEntityList) {
            if(tower.getLevel()>1) {
                int width = tower.getWidth();
                int height = tower.getHeight();
                int levelX = tower.getPos().getX() + width / 2;
                int levelY = tower.getPos().getY() - height / 2;
                g.setFont(Constants.UIConstants.TOWERLEVELFONT);
                if(tower.isMaxedLevel()){
                    g.setColor(Constants.UIConstants.TOWERMAXEDLEVELCOLOR);
                }else {
                    g.setColor(Constants.UIConstants.TOWERLEVELCOLOR);
                }
                g.drawString(String.valueOf(tower.getLevel()), levelX, levelY);
            }

        }
    }
    public synchronized void renderExplosions(Graphics g){
        Iterator<Explosion> iterator = explosionsList.iterator();
        while (iterator.hasNext()) {
            Explosion e = iterator.next();
            e.render(g);
            if (e.isFinished()) {
                iterator.remove();
            }
        }
    }
    public void renderTowersHealthBars(Graphics g) {
        for (Tower tower : towerEntityList) {
            tower.renderHealthBar(g);
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
        if(!playing.getRecentlySold()) {
            playerValues.increase(selectedTower.getWorth());

            towerEntityList.remove(selectedTower);
            selectedTower = null;
            playing.setSelectedTower(null);
            playing.setRecentlySold(true);
        }else {
            playing.setRecentlySoldRender(true);
        }
    }

    public void damageTower(Tower target, double damage, double stun) {
        target.damage(damage);
        target.setStun(stun*ups);
    }
    public void damageTowersInRadius(Circle explosion, double maxDamage, double maxStun, ArrayList<GameObject> damagedObjects) {
        for (Tower tower : towerEntityList) {
            if (explosion.contains(tower.getPos()) && (damagedObjects.isEmpty() || !damagedObjects.contains(tower))) {
                double distanceToCenter = explosion.getCenter().distanceTo(tower.getPos());
                double radius = explosion.getRadius();

                double damageFactor = Constants.ObjectConstants.EXPLOSIONBORDERDAMAGEPERCET + (1 - Constants.ObjectConstants.EXPLOSIONBORDERDAMAGEPERCET) * (1 - Math.pow(distanceToCenter / radius, 2));
                double finalDamage = maxDamage * damageFactor;
                double finalStun = maxStun * damageFactor;

                tower.damage(finalDamage);
                tower.setStun(finalStun * ups);
                damagedObjects.add(tower);
            }
        }
    }

    public synchronized void addExplosion(Explosion explosion) {
        explosionsList.add(explosion);
    }
    public void mouseReleased(int x, int y) {
        Tower t;
        if((t = towerOn(x,y))!=null) {
            replaceTower(t);
        }else {
            Tile tile;
            if ((tile = playing.getTileController().getTile(x, y))!=null&&tile.isBuildable()) {
                Values cost = variables.Towers.getCost(playing.getDraggedTower());
                if (playerValues.canAfford(cost)) {
                    playerValues.decrease(cost);
                    towerEntityList.add(new Tower(this, tile.getPos(), playing.getDraggedTower()));
                    playing.setSelectedTower(null);
                    System.out.println("Tower placed");
                } else {
                    playing.setCantAfford(true);
                }

            }
        }
    }
    public void replaceTower(Tower t) {
        if(t.getType() != variables.Towers.Foundation_T) {
                playerValues.increase(t.getWorth());
                towerEntityList.set(towerEntityList.indexOf(t), new Tower(this, t.getPos(), playing.getDraggedTower()));
                playing.setSelectedTower(null);
       }
    }
    public Tower towerOn(int x, int y) {
        for(Tower tower : towerEntityList) {
            if(tower.getBounds().contains(x, y)) {
                return tower;
            }
        }
        return null;
    }
    public void sellAllTowers() {
        for(Tower tower : towerEntityList) {
            if(tower.getType() != variables.Towers.Foundation_T) {
                playerValues.increase(tower.getWorth());
            }
        }
        towerEntityList.clear();
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
    public GameObject getGate() {
        return towerEntityList.get(0);
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


    public ArrayList<Tower> getTowerList() {
        return towerEntityList;
    }

    public boolean contains(GameObject target) {
        if(target instanceof Tower) {
            return towerEntityList.contains((Tower) target);
        }
        return false;
    }



}
