package controllers;

import enemy.Enemy;
import helpers.Coordinate;
import projectiles.Arrow;
import projectiles.Projectile;
import scenes.Playing;

import java.awt.*;
import java.util.ArrayList;

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
                    g.drawImage(projectile.getAnimator().getCurrentImage(), projectile.getPos().getX(), projectile.getPos().getY(), null);
                }
            //System.out.println("daw");
            }
        }


    public void update() {
        if (projectileList != null) {
            for (Projectile projectile : projectileList) {
                if(playing.getEnemyController().contains(projectile.getTarget())) {
                    projectile.update();
                    checkCollision(projectile,projectile.getTarget());
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
        if (Math.abs(distanceX)<=0&&Math.abs(distanceY)<=0) {
            removeQueue.add(projectile);
            playing.getEnemyController().damageEnemy(target,projectile.getDamage());
           // System.out.println("hit");
        }
    }
    public void workRemoveQueue() {
        for (Projectile projectile : removeQueue) {
            projectile.removeAnimators();
            projectileList.remove(projectile);
        }
        removeQueue.clear();
    }
    public void workAddQueue() {
        projectileList.addAll(addQueue);
        addQueue.clear();
    }
        public void removeProjectile (Projectile projectile) {
        removeQueue.add(projectile);
    }


    public void spawnProjectile(Coordinate start, Enemy ziel, int type) {
        switch (type) {
            case 0 -> addQueue.add(new Arrow(this,start,ziel,type));
            case 1 -> addQueue.add(new Arrow(this,start,ziel,type));
            case 2 -> addQueue.add(new Arrow(this,start,ziel,type));
            case 3 -> addQueue.add(new Arrow(this,start,ziel,type));
            case 4 -> addQueue.add(new Arrow(this,start,ziel,type));
            case 5 -> addQueue.add(new Arrow(this,start,ziel,type));
            case 6 -> addQueue.add(new Arrow(this,start,ziel,type));
            case 7 -> addQueue.add(new Arrow(this,start,ziel,type));
            case 8 -> addQueue.add(new Arrow(this,start,ziel,type));
        }
    }


    @Override
    public Playing getPlaying() {
        return playing;
    }
}
