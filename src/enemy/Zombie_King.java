package enemy;

import controllers.EnemyController;
import helpers.Coordinate;

public class Zombie_King extends Enemy{
    public Zombie_King(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, 6);
    }

}
