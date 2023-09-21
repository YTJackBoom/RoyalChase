package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.AbsoluteCoordinate;

import static helpers.variables.Enemies.WITCH_QUEEN;

public class Witch_Queen extends Enemy {
    public Witch_Queen(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, WITCH_QUEEN, EnemyAttackPattern.RANGED, EnemyType.BOSS);
    }

}
