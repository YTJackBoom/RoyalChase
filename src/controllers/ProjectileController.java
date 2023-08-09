package controllers;

import gameObjects.Enemy;
import gameObjects.Tower;
import helpers.Constants;
import gameObjects.Projectile;
import helpers.Circle;
import scenes.Playing;

import java.awt.*;
import java.util.ArrayList;

import static basics.GameScreen.fBOUNDS;
import static helpers.math.GeneralMath.calculateAngle;
import static helpers.variables.Projectiles.BULLET;
import static helpers.variables.Projectiles.ROCKET;

public class ProjectileController implements ControllerMethods {
    private Playing playing;
    private ArrayList<Projectile> projectileList, removeQueue ,addQueue ;


    public ProjectileController(Playing playing) {
        projectileList = new ArrayList<Projectile>();
        removeQueue  = new ArrayList<Projectile>();
        addQueue  = new ArrayList<Projectile>();
        this.playing = playing;
    }




    public synchronized void render(Graphics g) {
        if (projectileList != null) {
            for (Projectile projectile : projectileList) {
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


            //System.out.println("daw");
            }
        }


    public void update() {
        if (projectileList != null&&!playing.isPaused()) {  //checksCollsions, all targets only iterated in the methods themselfs, first check with original target,
            for (Projectile projectile : projectileList) {
                if(playing.getEnemyController().contains(projectile.getTarget())||projectile.getType()==BULLET) { //BULLET is der einzige type, welcher sich unabhängig von seinem ziel beweegt
                    projectile.update();
                    switch (projectile.getType()) {
                        case ROCKET -> checkRocketCollision(projectile,projectile.getTarget());
                        case BULLET -> checkBulletCollision(projectile,projectile.getTarget());
                        default -> checkCollision(projectile,projectile.getTarget());
                    }
                } else {
                    if(projectile.getType()==ROCKET) {
                        Circle explosion = new Circle(projectile.getPos(), Constants.ObjectConstants.EXPLOSIONRADIUS);
                        playing.getEnemyController().damageEnemiesInRadius(explosion,projectile.getDamage());
                        removeQueue.add(projectile);
                        System.out.println("removed");
                    }
                    removeQueue.add(projectile);
                    System.out.println("removed");
                }
            }
        }
        workRemoveQueue();
        workAddQueue();
    }


   public void checkCollision(Projectile projectile, Enemy target) { // Collisionscheck kugel u. gegner + löschen u damage
//       int distanceY = target.getPos().getY() - projectile.getPos().getY();
//       int distanceX = target.getPos().getX() - projectile.getPos().getX();
//       if (Math.abs(distanceX) <= target.getWidth()/2+projectile.getWidth()/2&& Math.abs(distanceY) <= target.getHeight()/2+projectile.getHeight()/2) {
       if(projectile.getHitBox().collidesWith(target.getHitBox())){
           removeQueue.add(projectile);
           playing.getEnemyController().damageEnemy(target, projectile.getDamage());
//            System.out.println("hit");
       } else {
           for(Enemy enemy : playing.getEnemyController().getEnemyList()) {
               if (projectile.getHitBox().collidesWith(enemy.getHitBox())) {
                   removeQueue.add(projectile);
                   playing.getEnemyController().damageEnemy(enemy, projectile.getDamage());
               }
           }
       }
   }
    public void checkRocketCollision(Projectile projectile, Enemy target) {
//        int distanceY = target.getPos().getY() - projectile.getPos().getY();
//        int distanceX = target.getPos().getX() - projectile.getPos().getX();
        if (projectile.getHitBox().collidesWith(target.getHitBox())) {
            Circle explosion = new Circle(projectile.getPos(), Constants.ObjectConstants.EXPLOSIONRADIUS);
            playing.getEnemyController().damageEnemiesInRadius(explosion, projectile.getDamage());
            removeQueue.add(projectile);

            System.out.println("hit");
        }else {
            for(Enemy enemy : playing.getEnemyController().getEnemyList()) {
                if (projectile.getHitBox().collidesWith(enemy.getHitBox())) {
                    Circle explosion = new Circle(projectile.getPos(), Constants.ObjectConstants.EXPLOSIONRADIUS);
                    playing.getEnemyController().damageEnemiesInRadius(explosion, projectile.getDamage());
                    removeQueue.add(projectile);
                }
            }
        }
    }
    public void checkBulletCollision(Projectile projectile, Enemy target) {
        if(!fBOUNDS.contains(projectile.getPos().getX(),projectile.getPos().getY())){ //chekc ob projectile im spiel ist, nur für bullet da diese sich unnabhängig vom ziel bewegt
            removeQueue.add(projectile);
            System.out.println("out of bounds");
        }
        for (Enemy enemy : playing.getEnemyController().getEnemyList()) {
            if(projectile.getHitBox().collidesWith(enemy.getHitBox())){
                playing.getEnemyController().damageEnemy(enemy,projectile.getDamage());
            }
        }
    }

    @Override
    public synchronized void workRemoveQueue() {
        for (Projectile projectile : removeQueue) {
            projectile.removeAnimators();
            projectileList.remove(projectile);
        }
        removeQueue.clear();
    }
    @Override
    public synchronized void workAddQueue() {
        projectileList.addAll(addQueue);
        addQueue.clear();
    }

    public void spawnProjectile(Tower tower, Enemy ziel, int type) {
        addQueue.add(new Projectile(this,tower,ziel,type));
    }


    public Playing getPlaying() {
        return playing;
    }

    public synchronized void clearProjectiles() {
        for (Projectile projectile : projectileList) {
            projectile.removeAnimators();
        }
        projectileList.clear();
    }
}
