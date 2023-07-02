package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

public class Skeleton extends Enemy {
    public Skeleton(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, 1);
    }

}
