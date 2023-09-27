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

import static basics.GameScreen.fBOUNDS;
import static helpers.ObjectValues.Projectiles.BULLET;
import static helpers.ObjectValues.Projectiles.ROCKET;
import static helpers.math.GeneralMath.calculateAngle;


public class ProjectileController implements ControllerMethods {
    private Playing playing;
    private ArrayList<Projectile> projectilesList, removeQueue , addQueue, enemyQueue;



    public ProjectileController(Playing playing) {
        projectilesList = new ArrayList<Projectile>();
        removeQueue  = new ArrayList<Projectile>();
        addQueue = new ArrayList<Projectile>();
        enemyQueue = new ArrayList<Projectile>();

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
    public synchronized void renderProjectile (Projectile projectile, Graphics g) {
        int width = projectile.getActiveAnimator().getWidth();
        int height = projectile.getActiveAnimator().getHeight();
        int projectileX = projectile.getPos().getX()-width/2;
        int projectileY = projectile.getPos().getY()-height/2;

        // Retrieve the current image of the active animator
        Image pImage = projectile.getActiveAnimator().getCurrentFrame();

        // Calculate the angle between the tower and its target
        double angle = calculateAngle(projectile.getPos(), projectile.getTarget().getPos());

        // Create a new Graphics2D object
        Graphics2D g2d = (Graphics2D) g.create();

        // Translate the graphics origin to the center of the tower
        g2d.translate(projectileX + width / 2, projectileY + height / 2);

        // Rotate the graphics by the calculated angle
        g2d.rotate(angle);

        // Draw the rotated turret image
        g2d.drawImage(pImage, -width / 2, -height / 2, null);

        // Dispose of the Graphics2D object
        g2d.dispose();

        projectile.getActiveAnimator().incrementFrame();
    }


    public void update() {
        if (projectilesList != null&&!playing.isPaused()) {  //checksCollsions, all targets only iterated in the methods themselfs, first check with original target,
            for (Projectile projectile : projectilesList) {
                GameObject target = projectile.getTarget();
                if(playing.getEnemyController().contains(target)||playing.getTowerController().contains(target)||projectile.getType()==BULLET) { //BULLET is der einzige type, welcher sich unabhängig von seinem ziel beweegt
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


   public void checkCollision(Projectile projectile, GameObject target) { // Collisionscheck kugel u. gegner + löschen u damage
       if (projectile.getHitBox().collidesWith(target.getHitBox())) {
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
                       break;
                   }
               }
           } else if (target instanceof Tower) {
               for (Tower tower : playing.getTowerController().getTowerList()) {
                   if (projectile.getHitBox().collidesWith(tower.getHitBox())) {
                       removeQueue.add(projectile);
                       playing.getTowerController().damageTower(tower, projectile.getDamage(), projectile.getStun());
                       break;
                   }
               }
           }
       }

   }
    public void checkRocketCollision(Projectile projectile, GameObject target) {
        if (projectile.getHitBox().collidesWith(target.getHitBox())) {
            if(target instanceof Enemy) {
                playing.getEnemyController().addExplosion(new Explosion(projectile.getTarget().getPos(), Constants.ObjectConstants.EXPLOSIONLIFETIME, Constants.ObjectConstants.EXPLOSIONRADIUS, projectile.getDamage(), projectile.getStun()));
            } else if (target instanceof Tower) {
                playing.getTowerController().addExplosion(new Explosion(projectile.getTarget().getPos(), Constants.ObjectConstants.EXPLOSIONLIFETIME, Constants.ObjectConstants.EXPLOSIONRADIUS, projectile.getDamage(), projectile.getStun()));
            }
            removeQueue.add(projectile);

            System.out.println("hit");
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

    public void checkBulletCollision(Projectile projectile, GameObject target) {
        if (!fBOUNDS.contains(projectile.getPos().getX(), projectile.getPos().getY())) { //chekc ob projectile im spiel ist, nur für bullet da diese sich unnabhängig vom ziel bewegt
            removeQueue.add(projectile);
            System.out.println("out of bounds");
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
    public void rocketNoTarget(Projectile projectile) {
        GameObject target = projectile.getTarget();
        if(target instanceof Enemy) {
            playing.getEnemyController().addExplosion(new Explosion(projectile.getPos(), Constants.ObjectConstants.EXPLOSIONLIFETIME, Constants.ObjectConstants.EXPLOSIONRADIUS, projectile.getDamage(), projectile.getStun()));
            removeQueue.add(projectile);
        } else if (target instanceof Tower) {
            playing.getTowerController().addExplosion(new Explosion(projectile.getPos(), Constants.ObjectConstants.EXPLOSIONLIFETIME, Constants.ObjectConstants.EXPLOSIONRADIUS, projectile.getDamage(), projectile.getStun()));
            removeQueue.add(projectile);
        }
        System.out.println("removed");

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
//        if (origin instanceof Tower) {
//            towerQueue.add(projectile);
//        } else if (origin instanceof Enemy) {
//            enemyQueue.add(projectile);
//        } else {
//            // Handle error or other case, perhaps with a log message or exception
//            System.out.println("Unknown origin type");
//        }
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
