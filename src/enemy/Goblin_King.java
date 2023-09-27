package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.AbsoluteCoordinate;

import static helpers.ObjectValues.Enemies.GOBLIN_KING;

public class Goblin_King extends Enemy {
    public Goblin_King(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, GOBLIN_KING, EnemyAttackPattern.MELEE, EnemyType.BOSS);
    }

}
