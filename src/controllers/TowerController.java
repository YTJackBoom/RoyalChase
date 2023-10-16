package controllers;

import enemy.EnemyType;
import gameObjects.Enemy;
import gameObjects.GameObject;
import gameObjects.Tile;
import gameObjects.Tower;
import helpers.*;
import scenes.Playing;
import towers.Gate;
import uiElements.Explosion;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static basics.Game.ups;

/**
 * Controller Klasse für die Türme
 */
public class TowerController extends ObjectsController implements ControllerMethods{
    private ArrayList<Tower> towerEntityList;
    private Playing playing;
    private ArrayList<Enemy> enemyList;
    private ArrayList<Explosion> explosionsList;
    private Tower selectedTower;
    private Values playerValues;
    private ArrayList<Tower> addQueue, removeQueue, changeQueue;

//    private int towerSoldCounter; //Wenn turm kürzlich verkauft wurde, um nicht sofort einen weiteren verkaufen zu könnnen
    public TowerController(Playing playing) {
        addQueue = new ArrayList<Tower>();
        removeQueue = new ArrayList<Tower>();
        towerEntityList = new ArrayList<Tower>();
        explosionsList = new ArrayList<Explosion>();
        this.playing = playing;
        playerValues = playing.getGame().getPlayerValues();
        initGate();
    }




    public void update() {
        workAddQueue();
        workRemoveQueue();
        for (Tower tower : towerEntityList) {
            if(!playing.isPaused()) {
                checkTowerRange(tower);
                checkTowerHealth(tower);
            }

            tower.update();
        }
        updateExplosions();
    }

    /**
     * Methode zum initialisieren des Tores auf der letzten Position des Gegner-Pfades
     */
    public void initGate() {
        ArrayList<AbsoluteCoordinate> pathAbsoluteCoordinates = playing.getEnemyController().getPathFinder().getPath();
        towerEntityList.add(new Gate(this, pathAbsoluteCoordinates.get(pathAbsoluteCoordinates.size() - 1)));
    }

    /**
     * Methode zum überprüfen, ob ein Gegner im Range des Turmes ist, Bosse werden priorisiert
     * @param tower
     */
    public void checkTowerRange(Tower tower) {
        enemyList = playing.getEnemyController().getEnemyList();
        Enemy targetEnemy = null;
        double highestPathIndex = -1;

        // First priority: Active enemies
        for (Enemy enemy : enemyList) {
            if (tower.getRange().contains(enemy.getHitBox()) && enemy.isActive()) {
                targetEnemy = enemy;
                break;
            }
        }

        // If no active enemy is found, look for the enemy with the highest getPathIndex()
        if (targetEnemy == null) {
            for (Enemy enemy : enemyList) {
                if (tower.getRange().contains(enemy.getHitBox()) && enemy.getPathIndex() > highestPathIndex) {
                    highestPathIndex = enemy.getPathIndex();
                    targetEnemy = enemy;
                }
            }
        }

        // If a target is found or the current target is out of range/not in the enemy list, refresh the target
        if (targetEnemy != null || (!enemyList.contains(tower.getTarget()) || !tower.getRange().contains(tower.getTarget().getHitBox()))) {
            if (targetEnemy != null) {
                tower.setStatus(true);
                tower.setTarget(targetEnemy);
            } else {
                tower.setStatus(false);
                tower.setTarget(null);
            }
        }
    }

    /**
     * Methode zum überprüfen, ob ein Turm keine Lebenspunkte mehr hat und evtl sound abspielen
     * @param tower
     */
    public void checkTowerHealth(Tower tower) {
        if (tower.getHealth() <= 0) {
            SoundController.getInstance().playSoundEffect("deaths_5");
            removeQueue.add(tower);
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


    public synchronized void renderTowers(Graphics g){
        for(Tower tower : towerEntityList) {
            tower.render(g);
        }
    }

    /**
     * Methode zum rendern der Level der Türme über dem Turm
     * @param g
     */
    public synchronized void renderTowerLevels(Graphics g) {
        for(Tower tower : towerEntityList) {
            if (tower.isVisible()) {
                if (tower.getLevel() > 1) {
                    int width = tower.getWidth();
                    int height = tower.getHeight();
                    int levelX = tower.getPos().getX() + width / 2;
                    int levelY = tower.getPos().getY() - height / 2;
                    g.setFont(Constants.UIConstants.TOWERLEVELFONT);
                    if (tower.isMaxedLevel()) {
                        g.setColor(Constants.UIConstants.TOWERMAXEDLEVELCOLOR);
                    } else {
                        g.setColor(Constants.UIConstants.TOWERLEVELCOLOR);
                    }
                    g.drawString(String.valueOf(tower.getLevel()), levelX, levelY);
                }

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
    public synchronized void renderTowersHealthBars(Graphics g) {
        for (Tower tower : towerEntityList) {
            if (tower.isVisible()) {
                tower.renderHealthBar(g);
            }
        }
    }

    /**
     * Methode zum upgraden eines Turmes, wenn der spielr genug resourcen hat
     */
     public void upgradeTower() {
        Values upgradeCost = selectedTower.getWorth().getUpgradeCost(selectedTower.getLevel());
        if(playerValues.canAfford(upgradeCost)) {
            playerValues.decrease(upgradeCost);
            selectedTower.getWorth().increase(upgradeCost);

            selectedTower.upgrade();
        } else {
            playing.setCantAfford(true);
        }
    }

    /**
     * Methode zum verkaufen eines Turmes, wenn nicht kürzlich einer verkauft wurde
     */
    public void sellTower() {
        if(!playing.getRecentlySold()) {
            playerValues.increase(selectedTower.getWorth());

            removeQueue.add(selectedTower);
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

    /**
     * Methode um Türme entweder zu bauen, zu ersetzten oder nicht zu machen
     * @param x
     * @param y
     */
    public void mouseReleased(int x, int y) {
        Tower t;
        if((t = towerOn(x,y))!=null) {
            replaceTower(t);
        }else {
            Tile tile;
            if ((tile = playing.getTileController().getTile(x, y))!=null&&tile.isBuildable()) {
                placeTower(tile.getPos());

            }
        }
        mouseClicked(x,y);
    }

    public void replaceTower(Tower t) {
        if (t.getType() != ObjectValues.Towers.Foundation_T) {
            if (placeTower(t.getPos())) {
                playerValues.increase(t.getWorthByPercentage(Constants.OtherConstants.REPLACETOWERPERCENT));
                removeQueue.add(t);
                playing.setSelectedTower(null);
            }
        }
    }

    public boolean placeTower(AbsoluteCoordinate pos) {
        Values cost = ObjectValues.Towers.getCost(playing.getDraggedTower());
        if (playerValues.canAfford(cost)) {
            playerValues.decrease(cost);
            addQueue.add(new Tower(this, pos, playing.getDraggedTower(), true));
            playing.setSelectedTower(null);
            return true;
        } else {
            playing.setCantAfford(true);
            return false;
        }
    }

    /**
     * Methode zum erhalten des Turmes an einer bestimmten position
     * @param x x-Koordinate
     * @param y y-Koordinate
     * @return Turm an der Position
     */
    public Tower towerOn(int x, int y) {
        for(Tower tower : towerEntityList) {
            if(tower.getBounds().contains(x, y)) {
                return tower;
            }
        }
        return null;
    }

    /**
     * Methode zum verkaufen aller Türme (z.b. beim schaffen eines Levels) mit einem bestimmten prozentsatz
     */
    public void sellAllTowers() {
        for(Tower tower : towerEntityList) {
            if(tower.getType() != ObjectValues.Towers.Foundation_T) {
                playerValues.increase(tower.getWorthByPercentage(Constants.OtherConstants.SELLALLTOWERSPERCENT));
            }
        }
        towerEntityList.clear();
    }

    /**
     * Methode zum auswählen des Turmes, der angeklickt wurde
     * @param x
     * @param y
     */
    public void mouseClicked(int x, int y) {
        for(Tower tower : towerEntityList) {
            if(tower.getType() != ObjectValues.Towers.Foundation_T) {
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
    public synchronized void workAddQueue() {
        towerEntityList.addAll(addQueue);
        addQueue.clear();

    }

    @Override
    public synchronized void workRemoveQueue() {
        towerEntityList.removeAll(removeQueue);
        removeQueue.clear();
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
