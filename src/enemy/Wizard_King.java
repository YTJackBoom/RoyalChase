package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

import static helpers.variables.Enemies.WIZARD_KING;

public class Wizard_King extends Enemy {
    public Wizard_King(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, WIZARD_KING, EnemyAttackPattern.RANGED, EnemyType.BOSS);
    }

}
