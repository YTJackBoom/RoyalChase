package towers;

import controllers.TowerController;
import gameObjects.Tower;
import helpers.AbsoluteCoordinate;

public class RocketTower extends Tower {
	private int range = 100;

	public RocketTower(TowerController towerController, AbsoluteCoordinate pos, int type) {
		super(towerController, pos, type, true);
	}

	@Override
	public void update() {

	}

}
