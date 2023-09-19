package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

import static helpers.variables.Enemies.SLIME;

public class Slime extends Enemy {
    public Slime(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, SLIME, EnemyAttackPattern.SACRIFICE, EnemyType.NORMAL);
    }

}
