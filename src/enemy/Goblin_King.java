package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

import static helpers.variables.Enemies.GOBLIN_KING;

public class Goblin_King extends Enemy {
    public Goblin_King(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, GOBLIN_KING, EnemyType.MELEE);
    }

}
