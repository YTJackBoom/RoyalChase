package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

import static helpers.variables.Enemies.GOBLIN;

public class Goblin extends Enemy {
    public Goblin(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, GOBLIN, EnemyAttackPattern.MELEE, EnemyType.NORMAL);
    }

}
