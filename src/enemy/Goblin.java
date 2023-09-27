package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.AbsoluteCoordinate;

import static helpers.ObjectValues.Enemies.GOBLIN;

public class Goblin extends Enemy {
    public Goblin(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, GOBLIN, EnemyAttackPattern.MELEE, EnemyType.NORMAL);
    }

}
