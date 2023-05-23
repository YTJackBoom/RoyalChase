package towers;

import controllers.TowerController;
import helpers.Coordinate;

public class RocketTower extends Tower {
	private int range = 100;

	public RocketTower(TowerController towerController, Coordinate pos, int type) {
		super(towerController,pos, type);
	}

}
