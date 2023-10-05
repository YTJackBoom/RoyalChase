package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.AbsoluteCoordinate;

import static helpers.ObjectValues.Enemies.SKELETON;
import static helpers.ObjectValues.Projectiles.ARROW;

public class Skeleton extends Enemy {
    private int counter;
    private boolean isLoaded;

    public Skeleton(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, SKELETON, EnemyAttackPattern.RANGED, EnemyType.NORMAL);
    }

    @Override
    public void fire() {
        if (isLoaded && isActive&&target != null) {
            enemyController.getPlaying().getProjectileController().spawnProjectile(this, target,ARROW); // target mitgabe, um target zu damagen

            isLoaded = false;
        }
    }

}
