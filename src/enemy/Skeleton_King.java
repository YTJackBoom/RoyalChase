package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

import static helpers.variables.Enemies.SKELETON_KING;

public class Skeleton_King extends Enemy {
    public Skeleton_King(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, SKELETON_KING, EnemyType.RANGED);
    }

}
