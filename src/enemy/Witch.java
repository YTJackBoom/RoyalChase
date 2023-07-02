package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

public class Witch extends Enemy {
    public Witch(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, 3);
    }

}
