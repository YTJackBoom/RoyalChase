package towers;

import controllers.TowerController;
import helpers.Coordinate;

public class SnipTower extends Tower {
	private int range = 100;


	public SnipTower(TowerController towerController, Coordinate pos, int type) {
		super(towerController,pos, type);
	}




}
