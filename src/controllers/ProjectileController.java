package controllers;

import gameObjects.Enemy;
import helpers.Constants;
import helpers.Coordinate;
import gameObjects.Projectile;
import helpers.Circle;
import scenes.Playing;

import java.awt.*;
import java.util.ArrayList;

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




    public void render(Graphics g) {
        if (projectileList != null) {
            for (Projectile projectile : projectileList) {
                int width = projectile.getAnimator().getWidth();
                int height = projectile.getAnimator().getHeight();
                g.drawImage(projectile.getAnimator().getCurrentImage(), projectile.getPos().getX()-width/2, projectile.getPos().getY()-height/2, null);
                }
            //System.out.println("daw");
            }
        }


    public void update() {
        if (projectileList != null&&!playing.isPaused()) {
            for (Projectile projectile : projectileList) {
                if(playing.getEnemyController().contains(projectile.getTarget())) {
                    projectile.update();
                    if(projectile.getType()!=ROCKET) {
                        checkCollision(projectile, projectile.getTarget());
                    }else {
                        checkRocketCollision(projectile,projectile.getTarget());
                    }
                } else {
                    removeQueue.add(projectile);
                    System.out.println("removed");

                }
            }
        }
        workRemoveQueue();
        workAddQueue();
    }


   public void checkCollision(Projectile projectile, Enemy target) { // Collisionscheck kugel u. gegner + löschen u damage
       int distanceY = target.getPos().getY() - projectile.getPos().getY();
       int distanceX = target.getPos().getX() - projectile.getPos().getX();
       if (Math.abs(distanceX) <= target.getWidth()/2+projectile.getWidth()/2&& Math.abs(distanceY) <= target.getHeight()/2+projectile.getHeight()/2) {
           removeQueue.add(projectile);
           playing.getEnemyController().damageEnemy(target, projectile.getDamage());
//            System.out.println("hit");
       }
   }
    public void checkRocketCollision(Projectile projectile, Enemy target) {
        int distanceY = target.getPos().getY() - projectile.getPos().getY();
        int distanceX = target.getPos().getX() - projectile.getPos().getX();
        if (Math.abs(distanceX) <= target.getWidth()/2+projectile.getWidth()/2 && Math.abs(distanceY) <= target.getHeight()/2+projectile.getHeight()/2) {
            Circle explosion = new Circle(projectile.getPos(), Constants.ObjectConstants.EXPLOSIONRADIUS);
            playing.getEnemyController().damageEnemiesInRadius(explosion, projectile.getDamage());
            removeQueue.add(projectile);

            System.out.println("hit");
        }
    }

    @Override
    public void workRemoveQueue() {
        for (Projectile projectile : removeQueue) {
            projectile.removeAnimators();
            projectileList.remove(projectile);
        }
        removeQueue.clear();
    }
    @Override
    public void workAddQueue() {
        projectileList.addAll(addQueue);
        addQueue.clear();
    }
        public void removeProjectile (Projectile projectile) {
        removeQueue.add(projectile);
    }


    public void spawnProjectile(Coordinate start, Enemy ziel, int type) {
//        switch (type) {
//            case 0 -> addQueue.add(new Arrow(this,start,ziel,type));
//            case 1 -> addQueue.add(new Arrow(this,start,ziel,type));
//            case 2 -> addQueue.add(new Arrow(this,start,ziel,type));
//            case 3 -> addQueue.add(new Arrow(this,start,ziel,type));
//            case 4 -> addQueue.add(new Arrow(this,start,ziel,type));
//            case 5 -> addQueue.add(new Arrow(this,start,ziel,type));
//            case 6 -> addQueue.add(new Arrow(this,start,ziel,type));
//            case 7 -> addQueue.add(new Arrow(this,start,ziel,type));
//            case 8 -> addQueue.add(new Arrow(this,start,ziel,type));
//        }
        addQueue.add(new Projectile(this,start,ziel,type));
    }


    public Playing getPlaying() {
        return playing;
    }

    public void clearProjectiles() {
        for (Projectile projectile : projectileList) {
            projectile.removeAnimators();
        }
        projectileList.clear();
    }
}
