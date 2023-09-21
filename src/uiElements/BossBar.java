package uiElements;

import enemy.EnemyType;
import gameObjects.Enemy;
import scenes.Playing;

import java.awt.*;

import static helpers.variables.Icons.BOSSBAR;

public class BossBar extends UiElement {
    private Enemy bossPointer;
    private Color lostHealthColor;
    private Playing playing;

    public BossBar(Playing playing, Rectangle bounds) {
        super(bounds, UIObjectType.ICON, BOSSBAR, true,
                "fWIDTH/2 - WIDTH/2", "10");
        this.lostHealthColor = Color.RED;
        this.playing = playing;
    }

    public void render(Graphics g) {
        if (playing.getEnemyController().getEnemyList().size() == 0) return;
        super.render(g);

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
}
