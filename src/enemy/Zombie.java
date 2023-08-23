package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

import static helpers.variables.Enemies.ZOMBIE;

public class Zombie extends Enemy {
    public Zombie(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, ZOMBIE,EnemyType.MELEE );
    }

}
