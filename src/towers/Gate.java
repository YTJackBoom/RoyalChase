package towers;

import controllers.TowerController;
import gameObjects.Tower;
import helpers.AbsoluteCoordinate;

public class Gate extends Tower {

	public Gate(TowerController towerController, AbsoluteCoordinate pos) {
		super(towerController, pos, 0, false);
	}


	@Override
	public void damage(double i) {
		towerController.getPlaying().getGame().getPlayerValues().setHealth(towerController.getPlaying().getGame().getPlayerValues().getHealth() - i);
	}
	@Override
	public void update() {
		
	}
}


