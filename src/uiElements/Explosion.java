package uiElements;

import controllers.EnemyController;
import controllers.ObjectsController;
import controllers.TowerController;
import gameObjects.GameObject;
import helpers.AbsoluteCoordinate;
import helpers.Circle;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static basics.Game.fps;

public class Explosion {

    private AbsoluteCoordinate pos;
    private double lifespan,radius,maxDamage,maxStun;
    private ArrayList<GameObject> damagedObjects;

    private double elapsedTime; // time since explosion started (in seconds)

    public Explosion(AbsoluteCoordinate pos, double lifespan, double radius, double maxDamage, double maxStun) {
        this.pos = pos;
        this.lifespan = lifespan;
        this.radius = radius;
        this.maxDamage = maxDamage;
        this.maxStun = maxStun;
        this.elapsedTime = 0.0;
        damagedObjects = new ArrayList<GameObject>();
    }

    public void render(Graphics g) {
        if (elapsedTime != 0) {
            Graphics2D g2d = (Graphics2D) g;

            float fraction = (float) (elapsedTime / lifespan);
            double currentRadius = fraction * radius;

            int pixelSize = 3; // Change this value to adjust the granularity of the pixelation.

            // Calculate dimensions of pixelated explosion
            int width = (int) (2 * currentRadius);
            int height = (int) (2 * currentRadius);

            // Create an offscreen image for pixelation
            BufferedImage offscreen = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D offscreenG = offscreen.createGraphics();

            Point2D center = new Point2D.Double(width / 2.0, height / 2.0);
            float[] dist = {0.0f, 1.0f};
            Color[] colors = {Color.RED, Color.YELLOW};
            RadialGradientPaint paint = new RadialGradientPaint(center, (float) currentRadius, dist, colors);

            offscreenG.setPaint(paint);
            offscreenG.fillOval(0, 0, width, height);

            // Pixelate the offscreen image
            for (int y = 0; y < height; y += pixelSize) {
                for (int x = 0; x < width; x += pixelSize) {
                    int avgColor = offscreen.getRGB(x, y);
                    for (int yy = y; yy < y + pixelSize && yy < height; yy++) {
                        for (int xx = x; xx < x + pixelSize && xx < width; xx++) {
                            offscreen.setRGB(xx, yy, avgColor);
                        }
                    }
                }
            }

            // Draw the pixelated explosion on screen
            g2d.drawImage(offscreen, (int) (pos.getX() - currentRadius), (int) (pos.getY() - currentRadius), null);
            offscreenG.dispose();
        }
    }


    public boolean isFinished() {
        return elapsedTime >= lifespan;
    }

    // Call this method on each frame update
    public void update(ObjectsController objectsController) {
        elapsedTime += 1.0 / fps;

        double currentRadius = (elapsedTime / lifespan) * radius;
        Circle currentExplosionArea = new Circle(pos, currentRadius);
        if (objectsController instanceof EnemyController) {
            EnemyController enemyController = (EnemyController) objectsController;
            enemyController.damageEnemiesInRadius(currentExplosionArea, maxDamage, maxStun, damagedObjects);
        } else if (objectsController instanceof TowerController) {
            TowerController towerController = (TowerController) objectsController;
            towerController.damageTowersInRadius(currentExplosionArea, maxDamage, maxStun, damagedObjects);
        }
    }
}
