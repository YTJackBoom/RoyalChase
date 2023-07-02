package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

public class Skull extends Enemy {
    public Skull(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, 0);
    }

}
