package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.AbsoluteCoordinate;

import static helpers.ObjectValues.Enemies.WIZARD;
import static helpers.ObjectValues.Projectiles.LIGHTNINGBALL;

public class Wizard extends Enemy {
    private int counter;
    private boolean isLoaded;

    public Wizard(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, WIZARD, EnemyAttackPattern.RANGED, EnemyType.BOSS);
    }

    @Override
    public void fire() {
        if (counter - getReloadTime() >= 0) {
            isLoaded = true;
            counter = 0;
        } else counter++;
        spawnProjectile();
    }

    public void spawnProjectile() {
        if (isLoaded && isActive && target != null) {
            enemyController.getPlaying().getProjectileController().spawnProjectile(this, target, LIGHTNINGBALL); // target mitgabe, um target zu damagen

            isLoaded = false;
        }
    }

}
