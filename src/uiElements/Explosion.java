package uiElements;

import gameObjects.Enemy;
import helpers.Circle;
import helpers.Coordinate;
import controllers.EnemyController;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static basics.Game.fps;

public class Explosion {

    private Coordinate pos;
    private double lifespan,radius,maxDamage,maxStun;
    private ArrayList<Enemy> damagedEnemies;

    private double elapsedTime; // time since explosion started (in seconds)

    public Explosion(Coordinate pos, double lifespan, double radius, double maxDamage, double maxStun) {
        this.pos = pos;
        this.lifespan = lifespan;
        this.radius = radius;
        this.maxDamage = maxDamage;
        this.maxStun = maxStun;
        this.elapsedTime = 0.0;
        damagedEnemies = new ArrayList<Enemy>();
    }

    public void render(Graphics g) {
        if(elapsedTime !=0) {
            Graphics2D g2d = (Graphics2D) g;

            float fraction = (float) (elapsedTime / lifespan);

            // Calculate current explosion radius based on elapsed time
            double currentRadius = fraction * radius;

            // We'll create a radial gradient that goes from red at the center to yellow at the edge
            Point2D center = new Point2D.Double(pos.getX(), pos.getY());
            float[] dist = {0.0f, 1.0f};
            Color[] colors = {Color.RED, Color.YELLOW};

            RadialGradientPaint paint = new RadialGradientPaint(center, (float) currentRadius, dist, colors);
            g2d.setPaint(paint);
            g2d.fillOval((int) (pos.getX() - currentRadius), (int) (pos.getY() - currentRadius),
                    (int) (2 * currentRadius), (int) (2 * currentRadius));

            // update elapsed time
        }
    }

    public boolean isFinished() {
        return elapsedTime >= lifespan;
    }

    // Call this method on each frame update
    public void updateAndDamage(EnemyController enemyController) {
        elapsedTime += 1.0 / fps;

        double currentRadius = (elapsedTime / lifespan) * radius;
        Circle currentExplosionArea = new Circle(pos, currentRadius);
        enemyController.damageEnemiesInRadius(currentExplosionArea, maxDamage, maxStun, damagedEnemies);
    }
}
