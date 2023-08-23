package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

import static helpers.variables.Enemies.WITCH_QUEEN;

public class Witch_Queen extends Enemy {
    public Witch_Queen(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, WITCH_QUEEN,EnemyType.RANGED);
    }

}
