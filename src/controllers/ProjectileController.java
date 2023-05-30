package controllers;

import enemy.Enemy;
import helpers.Coordinate;
import projectiles.Arrow;
import projectiles.Projectile;
import scenes.Playing;
import towers.Tower;

import java.awt.*;
import java.util.ArrayList;

public class ProjectileController {
    private Playing playing;
    private ArrayList<Projectile> projectileList;
    ArrayList<Projectile> removeQueue;

    public ProjectileController(Playing playing) {
        projectileList = new ArrayList<Projectile>();
        removeQueue = new ArrayList<Projectile>();
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
                }
            }
        }
        workRemoveQueue();
    }


   public void checkCollision(Projectile projectile, Enemy target) { // Collisionscheck kugel u. gegner + löschen u damage
        int distanceY = target.getPos().getY() - projectile.getPos().getY();
        int distanceX = target.getPos().getX() - projectile.getPos().getX();
        if (Math.abs(distanceX)<=3&&Math.abs(distanceY)<=3) {
            removeQueue.add(projectile);
            playing.getEnemyController().damageEnemy(target,projectile.getType());
        }
    }
    public void workRemoveQueue() {
        for (Projectile projectile : removeQueue) {
            removeProjectile(projectile);
        }
        removeQueue.clear();
    }

    public void spawnProjectile(Coordinate start, Enemy ziel, int type) {
        switch (type) {
            case 0 -> projectileList.add(new Arrow(this,start,ziel,type));
            case 1 -> projectileList.add(new Arrow(this,start,ziel,type));
            case 2 -> projectileList.add(new Arrow(this,start,ziel,type));
            case 3 -> projectileList.add(new Arrow(this,start,ziel,type));
            case 4 -> projectileList.add(new Arrow(this,start,ziel,type));
            case 5 -> projectileList.add(new Arrow(this,start,ziel,type));
            case 6 -> projectileList.add(new Arrow(this,start,ziel,type));
            case 7 -> projectileList.add(new Arrow(this,start,ziel,type));
            case 8 -> projectileList.add(new Arrow(this,start,ziel,type));
        }
    }
    public void removeProjectile (Projectile projectile) {
        projectileList.remove(projectile);
    }


}
