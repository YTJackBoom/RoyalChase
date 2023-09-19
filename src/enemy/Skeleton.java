package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

import static helpers.variables.Enemies.SKELETON;
import static helpers.variables.Projectiles.ARROW;

public class Skeleton extends Enemy {
    private int counter;
    private boolean isLoaded;
    public Skeleton(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, SKELETON, EnemyAttackPattern.RANGED, EnemyType.NORMAL);
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
        if (isLoaded && isActive&&target != null) {
            enemyController.getPlaying().getProjectileController().spawnProjectile(this, target,ARROW); // target mitgabe, um target zu damagen

            isLoaded = false;
        }
    }

}
