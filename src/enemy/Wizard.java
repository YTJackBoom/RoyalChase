package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import helpers.Coordinate;

import static helpers.variables.Enemies.WIZARD;

public class Wizard extends Enemy {
    public Wizard(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, WIZARD,EnemyType.RANGED);
    }

}
