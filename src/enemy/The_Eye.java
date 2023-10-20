package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.AbsoluteCoordinate;

import static helpers.ObjectValues.Enemies.THE_EYE;

public class The_Eye extends Enemy {
    public The_Eye(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, THE_EYE, EnemyAttackPattern.RANGED, EnemyType.BOSS);
    }

}
