package enemy;

import controllers.EnemyController;
import helpers.Coordinate;

public class Skeleton_King extends Enemy{
    public Skeleton_King(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, 5);
    }

}
