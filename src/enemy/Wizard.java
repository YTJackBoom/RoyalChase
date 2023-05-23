package enemy;

import controllers.EnemyController;
import helpers.Coordinate;

public class Wizard extends Enemy{
    public Wizard(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, 4);
    }

}
