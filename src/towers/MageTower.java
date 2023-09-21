package towers;

import controllers.TowerController;
import gameObjects.Tower;
import helpers.AbsoluteCoordinate;

public class MageTower extends Tower {
	private int range = 100;


	public MageTower(TowerController towerController, AbsoluteCoordinate pos, int type) {
		super(towerController, pos, type, true);
	}

	@Override
	public void update() {

	}


}
