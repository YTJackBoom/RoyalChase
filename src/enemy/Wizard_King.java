package enemy;

import controllers.EnemyController;
import gameObjects.Coordinate;

public class Wizard_King extends Enemy{
    public Wizard_King(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, 8);
    }

}
