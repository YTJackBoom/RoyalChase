package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.AbsoluteCoordinate;

import static helpers.variables.Enemies.SLIME;

public class Slime extends Enemy {
    public Slime(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, SLIME, EnemyAttackPattern.SACRIFICE, EnemyType.NORMAL);
    }

}
