package enemy;

import controllers.EnemyController;
import gameObjects.Coordinate;

public class Witch_Queen extends Enemy{
    public Witch_Queen(EnemyController enemyController, Coordinate pos) {
        super(enemyController, pos, 7);
    }

}