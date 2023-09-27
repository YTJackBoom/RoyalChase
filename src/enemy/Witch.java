package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.AbsoluteCoordinate;

import static helpers.ObjectValues.Enemies.WITCH;

public class Witch extends Enemy {
    public Witch(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, WITCH, EnemyAttackPattern.RANGED, EnemyType.NORMAL);
    }

}
