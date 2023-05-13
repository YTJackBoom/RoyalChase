package towers;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import basics.GameScreen;
import gameObjects.Coordinate;
import gameObjects.Enemy;

public class SnipTower extends Tower {
	private int range = 100;


	public SnipTower(GameScreen gS, Coordinate pos, int type, ArrayList towerEntityList,
			int ArrayPos) {
		super(gS, pos,35,35, type, towerEntityList, ArrayPos, new ImageIcon(imgP4));
		super.range = 100;

		// TODO Auto-generated constructor stub
	}

	@Override
	public void fire() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeStatus(boolean status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTarget(Enemy target) {
		// TODO Auto-generated method stub
		
	}

}
