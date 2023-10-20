package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.AbsoluteCoordinate;

import static helpers.ObjectValues.Enemies.GOLEM;

public class Golem extends Enemy {
    public Golem(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, GOLEM, EnemyAttackPattern.MELEE, EnemyType.NORMAL);
    }

}
