package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

public class Zombie extends Enemy {
    public Zombie(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, 2);
    }

}
