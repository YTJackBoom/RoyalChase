package uiElements;

import enemy.EnemyType;
import gameObjects.Enemy;
import helpers.UiCoordinate;
import scenes.Playing;

import java.awt.*;

import static helpers.variables.Icons.BOSSBAR;

public class BossBar extends UiElement {
    private Enemy bossPointer;
    private Color lostHealthColor;
    private Playing playing;

    public BossBar(Playing playing, UiCoordinate uiCoordinate, int width, int height) {
        super(uiCoordinate, width, height, UIObjectType.ICON, BOSSBAR, true);
        this.lostHealthColor = Color.RED;
        this.playing = playing;

    }

    public void render(Graphics g) {
        if (playing.getEnemyController().getEnemyList().size() == 0) return;

        bossPointer = null;

        for (Enemy enemy : playing.getEnemyController().getEnemyList()) {
            if (enemy.getEnemyType() == EnemyType.BOSS) {
                bossPointer = enemy;
                break;
            }
        }

        if (bossPointer != null) {
            super.render(g);

            int currentHealth = (int) bossPointer.getHealth();
            int maxHealth = (int) bossPointer.getMaxHealth();

            // Calculate the width of the lost health rectangle
            float healthPercentage = (float) currentHealth / maxHealth;
            int lostHealthWidth = (int) ((1.0f - healthPercentage) * width);
            int remainingHealthWidth = width - lostHealthWidth;  // Calculate the width of the remaining health rectangle

            // Draw the remaining health rectangle (green)
            g.setColor(Color.GREEN);
            g.fillRect(uiCoordinate.getX(), uiCoordinate.getY(), remainingHealthWidth, height);

            // Determine the starting x-coordinate for the lost health rectangle
            int startX = uiCoordinate.getX() + remainingHealthWidth;

            // Draw the lost health rectangle (red)
            g.setColor(lostHealthColor);
            g.fillRect(startX, uiCoordinate.getY(), lostHealthWidth, height);
        }
    }
}
