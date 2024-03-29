package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.AbsoluteCoordinate;

import static helpers.ObjectValues.Enemies.SKELETON;
import static helpers.ObjectValues.Projectiles.ARROW;

public class Skeleton extends Enemy {

    public Skeleton(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, SKELETON, EnemyAttackPattern.RANGED, EnemyType.NORMAL);
    }

    /**
     * Überschriebene AngriffMethode, bei Fernkämpfern nötig
     */
    @Override
    public void fire() {
        if (isLoaded && isActive&&target != null) {
            enemyController.getPlaying().getProjectileController().spawnProjectile(this, target,ARROW); // target mitgabe, um target zu damagen

            isLoaded = false;
        }
    }

}
