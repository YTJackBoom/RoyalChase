package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.AbsoluteCoordinate;

import static helpers.variables.Enemies.WIZARD_KING;

public class Wizard_King extends Enemy {
    public Wizard_King(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, WIZARD_KING, EnemyAttackPattern.RANGED, EnemyType.BOSS);
    }

}
