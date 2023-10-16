package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.AbsoluteCoordinate;

import static helpers.ObjectValues.Enemies.GOLEM;

public class Witch extends Enemy {
    public Witch(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, GOLEM, EnemyAttackPattern.RANGED, EnemyType.NORMAL);
    }

}
