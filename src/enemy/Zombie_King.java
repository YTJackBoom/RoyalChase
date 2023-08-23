package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

import static helpers.variables.Enemies.ZOMBIE_KING;

public class Zombie_King extends Enemy {
    public Zombie_King(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, ZOMBIE_KING,EnemyType.MELEE);
    }

}
