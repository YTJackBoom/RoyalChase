package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.AbsoluteCoordinate;

import static helpers.variables.Enemies.SKELETON_KING;

public class Skeleton_King extends Enemy {
    public Skeleton_King(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, SKELETON_KING, EnemyAttackPattern.RANGED, EnemyType.BOSS);
    }

}
