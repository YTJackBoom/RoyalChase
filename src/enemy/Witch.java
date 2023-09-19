package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

import static helpers.variables.Enemies.WITCH;

public class Witch extends Enemy {
    public Witch(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, WITCH, EnemyAttackPattern.RANGED, EnemyType.NORMAL);
    }

}
