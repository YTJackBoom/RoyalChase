package controllers;

import gameObjects.Enemy;
import gameObjects.GameObject;
import gameObjects.Projectile;
import gameObjects.Tower;
import helpers.Constants;
import scenes.Playing;
import uiElements.Explosion;

import java.awt.*;
import java.util.ArrayList;

import static basics.Game.fHEIGHT;
import static basics.Game.fWIDTH;
import static helpers.ObjectValues.Projectiles.BULLET;
import static helpers.ObjectValues.Projectiles.ROCKET;
import static helpers.math.GeneralMath.calculateAngle;

/**
 * Controller Klasse für die Projektile
 */
public class ProjectileController implements ControllerMethods {
    private Playing playing;
    private ArrayList<Projectile> projectilesList, removeQueue , addQueue;



    public ProjectileController(Playing playing) {
        projectilesList = new ArrayList<Projectile>();
        removeQueue  = new ArrayList<Projectile>();
        addQueue = new ArrayList<Projectile>();

        this.playing = playing;
    }




    public synchronized void render(Graphics g) {
        if (projectilesList != null) {
            for (Projectile projectile : projectilesList) {
                if (projectile.isVisible()) {
                    renderProjectile(projectile, g);
                }
            }
        }
    }

    /**
     * Rendern eines projectiles, nihct standart, da Projectile zum Gegner gedreht werden müssen
     * @param projectile
     * @param g
     */
    public synchronized void renderProjectile (Projectile projectile, Graphics g) {
        int width = projectile.getActiveAnimator().getWidth();
        int height = projectile.getActiveAnimator().getHeight();
        int projectileX = projectile.getPos().getX()-width/2;
        int projectileY = projectile.getPos().getY()-height/2;

        Image pImage = projectile.getActiveAnimator().getCurrentFrame();

        double angle = calculateAngle(projectile.getPos(), projectile.getTarget().getPos());

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.translate(projectileX + width / 2, projectileY + height / 2); //Mitellppunkt der Rotation als mitte des Prjectiles

        g2d.rotate(angle);

        g2d.drawImage(pImage, -width / 2, -height / 2, null);
        projectile.getActiveAnimator().incrementFrame();

        g2d.dispose();
    }

/**
 * Methode zum updaten der Projektile, hauptsächlich Kollisions-Checks
 */
    public void update() {
        if (projectilesList != null&&!playing.isPaused()) {
            for (Projectile projectile : projectilesList) {
                GameObject target = projectile.getTarget();
                if(playing.getEnemyController().contains(target)||playing.getTowerController().contains(target)||projectile.getType()==BULLET) { //BULLET is der einzige type, welcher nicht entfernt werden soll, wenn sein ziel nicht mehr existier
                    projectile.update();
                    switch (projectile.getType()) {
                        case ROCKET -> checkRocketCollision(projectile,projectile.getTarget());
                        case BULLET -> checkBulletCollision(projectile,projectile.getTarget());
                        default -> checkCollision(projectile,projectile.getTarget());
                    }
                } else {
                    if(projectile.getType()==ROCKET) {
                        rocketNoTarget(projectile);
                    }
                    removeQueue.add(projectile);
                    System.out.println("removed");
                }
            }
        }
        workRemoveQueue();
        workAddQueue();
    }

    /**
     * Methode zum überprüfen, ob ein Standart-Projectile(bsp. Arrow, LightningBall) mit einem GameObject kollidiert
     * @param projectile Projectile, welches überprüft werden soll
     * @param target Das Ziel auf welches Das Projectil zufliegt
     */
   public void checkCollision(Projectile projectile, GameObject target) { // Collisionscheck kugel u. gegner + löschen u damage
       if (projectile.getHitBox().collidesWith(target.getHitBox())) { //ers prüfen ob es hauptziel getroffen hat und dann erst auf alle anderen
           removeQueue.add(projectile);

           if (target instanceof Enemy) {
               playing.getEnemyController().damageEnemy((Enemy) target, projectile.getDamage(), projectile.getStun());
           } else if (target instanceof Tower) {
               playing.getTowerController().damageTower((Tower) target, projectile.getDamage(), projectile.getStun());
           }
       } else {
           if (target instanceof Enemy) {
               for (Enemy enemy : playing.getEnemyController().getEnemyList()) {
                   if (projectile.getHitBox().collidesWith(enemy.getHitBox())) {
                       removeQueue.add(projectile);
                       playing.getEnemyController().damageEnemy(enemy, projectile.getDamage(), projectile.getStun());
                       return;
                   }
               }
           } else if (target instanceof Tower) {
               for (Tower tower : playing.getTowerController().getTowerList()) {
                   if (projectile.getHitBox().collidesWith(tower.getHitBox())) {
                       removeQueue.add(projectile);
                       playing.getTowerController().damageTower(tower, projectile.getDamage(), projectile.getStun());
                       return;
                   }
               }
           }
       }

   }

    /**
     * Methode zum überprüfen, ob ein Rocket-Projectile mit einem GameObject kollidiert
     * @param projectile Projectile, welches überprüft werden soll
     * @param target Das Ziel auf welches Das Projectil zufliegt
     */
    public void checkRocketCollision(Projectile projectile, GameObject target) {
        if (projectile.getHitBox().collidesWith(target.getHitBox())) {
            if(target instanceof Enemy) {
                playing.getEnemyController().addExplosion(new Explosion(projectile.getTarget().getPos(), Constants.ObjectConstants.EXPLOSIONLIFETIME, Constants.ObjectConstants.EXPLOSIONRADIUS, projectile.getDamage(), projectile.getStun()));
            } else if (target instanceof Tower) {
                playing.getTowerController().addExplosion(new Explosion(projectile.getTarget().getPos(), Constants.ObjectConstants.EXPLOSIONLIFETIME, Constants.ObjectConstants.EXPLOSIONRADIUS, projectile.getDamage(), projectile.getStun()));
            }
            removeQueue.add(projectile);
        }else {
            if(target instanceof Enemy) {
                 for(Enemy enemy : playing.getEnemyController().getEnemyList()) {
                     if (projectile.getHitBox().collidesWith(enemy.getHitBox())) {
                        playing.getEnemyController().addExplosion(new Explosion(enemy.getPos(), Constants.ObjectConstants.EXPLOSIONLIFETIME, Constants.ObjectConstants.EXPLOSIONRADIUS, projectile.getDamage(), projectile.getStun()));
                         removeQueue.add(projectile);
                        break;
                    }
                 }
            } else if (target instanceof Tower) {
                for (Tower tower : playing.getTowerController().getTowerList()) {
                    if(projectile.getHitBox().collidesWith(tower.getHitBox())){
                        playing.getTowerController().addExplosion(new Explosion(tower.getPos(), Constants.ObjectConstants.EXPLOSIONLIFETIME, Constants.ObjectConstants.EXPLOSIONRADIUS, projectile.getDamage(), projectile.getStun()));
                        removeQueue.add(projectile);
                        break;
                    }
                }
            }

        }
    }

    /**
     * Methode zum überprüfen, ob ein Bullet-Projectile mit einem GameObject kollidiert und ob dieses den Bildschirm-Rand verlässt
     * @param projectile
     * @param target
     */
    public void checkBulletCollision(Projectile projectile, GameObject target) {
        if (projectile.getPos().getX()>fWIDTH||projectile.getPos().getY()>fHEIGHT) { //check ob projectile im spiel ist, nur für bullet nötig da diese sich unnabhängig vom ziel bewegt
            removeQueue.add(projectile);
//            System.out.println("out of bounds");
        } else {
            if(target instanceof Enemy) {
                for (Enemy enemy : playing.getEnemyController().getEnemyList()) {
                    if (projectile.getHitBox().collidesWith(enemy.getHitBox())) {
                        playing.getEnemyController().damageEnemy(enemy, projectile.getDamage(), projectile.getStun());
                        break;
                    }
                }
            } else if (target instanceof Tower) {
                for (Tower tower : playing.getTowerController().getTowerList()) {
                    if(projectile.getHitBox().collidesWith(tower.getHitBox())) {
                        playing.getTowerController().damageTower(tower,projectile.getDamage(),projectile.getStun());
                        break;
                    }
                }
            }
        }
    }

    /**
     * Methode um Rocket explodieren zu lassen, falls diese kein Ziel mehr hat
     * @param projectile Rocket-Projectile, welches explodieren soll
     */
    public void rocketNoTarget(Projectile projectile) {
        GameObject target = projectile.getTarget();
        if(target instanceof Enemy) {
            playing.getEnemyController().addExplosion(new Explosion(projectile.getPos(), Constants.ObjectConstants.EXPLOSIONLIFETIME, Constants.ObjectConstants.EXPLOSIONRADIUS, projectile.getDamage(), projectile.getStun()));
            removeQueue.add(projectile);
        } else if (target instanceof Tower) {
            playing.getTowerController().addExplosion(new Explosion(projectile.getPos(), Constants.ObjectConstants.EXPLOSIONLIFETIME, Constants.ObjectConstants.EXPLOSIONRADIUS, projectile.getDamage(), projectile.getStun()));
            removeQueue.add(projectile);
        }
//        System.out.println("removed");

    }

    @Override
    public synchronized void workRemoveQueue() {
        for (Projectile projectile : removeQueue) {
            projectile.removeAnimators();
            projectilesList.remove(projectile);
        }
        removeQueue.clear();
    }
    @Override
    public synchronized void workAddQueue() {
        projectilesList.addAll(addQueue);
        addQueue.clear();
    }

    public void spawnProjectile(GameObject origin, GameObject target, int type) {
        Projectile projectile = new Projectile(this, origin, target, type);

        addQueue.add(projectile);
    }


    public Playing getPlaying() {
        return playing;
    }

    public synchronized void clearProjectiles() {
        for (Projectile projectile : projectilesList) {
            projectile.removeAnimators();
        }
        projectilesList.clear();
    }
}
