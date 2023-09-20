package uiElements;

import enemy.EnemyType;
import gameObjects.Enemy;
import scenes.Playing;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BossBar {
    private Rectangle bounds;
    private BufferedImage backgroundImage;
    private Enemy bossPointer;
    private Color lostHealthColor;
    private Playing playing;

    public BossBar(Playing playing, Rectangle bounds) {
        this.bounds = bounds;
        try {
            this.backgroundImage = ImageIO.read(new File("res/images/icons/infooverlay/boss_bar.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.lostHealthColor = Color.RED;
        this.playing = playing;
    }

    public void render(Graphics g) {
        if (playing.getEnemyController().getEnemyList().size() == 0) return;
        bossPointer = null;

        for (Enemy enemy : playing.getEnemyController().getEnemyList()) { //zum updaten des pointers
            if (enemy.getEnemyType() == EnemyType.BOSS) {
                bossPointer = enemy;
                break;
            }
        }

        if (bossPointer != null) {
            int currentHealth = (int) bossPointer.getHealth();
            int maxHealth = (int) bossPointer.getMaxHealth();
            // Draw the background image
            g.drawImage(backgroundImage, bounds.x, bounds.y, bounds.width, bounds.height, null);

            // Calculate the width of the lost health rectangle
            float healthPercentage = (float) currentHealth / maxHealth;
            int lostHealthWidth = (int) ((1.0f - healthPercentage) * bounds.width);

            // Determine the starting x-coordinate for the lost health rectangle
            int startX = bounds.x + bounds.width - lostHealthWidth;

            // Draw the lost health rectangle
            g.setColor(lostHealthColor);
            g.fillRect(startX, bounds.y, lostHealthWidth, bounds.height);
        }
    }


    public void setBossPointer(Enemy e) {
        bossPointer = e;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
