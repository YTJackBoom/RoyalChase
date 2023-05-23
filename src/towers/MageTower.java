package towers;

import controllers.TowerController;
import helpers.Coordinate;

public class MageTower extends Tower {	
	private int range = 100;


	public MageTower(TowerController towerController, Coordinate pos, int type) {
		super(towerController,pos, type);
	}




}
