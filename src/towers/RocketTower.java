package towers;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import basics.GameScreen;
import gameObjects.Coordinate;
import gameObjects.Enemy;

public class RocketTower extends Tower {
	private int range = 100;

	public RocketTower(JPanel sPanel, Coordinate pos, int type, ArrayList towerEntityList,
			int ArrayPos) {
		super(sPanel, pos,35,35, type, towerEntityList, ArrayPos, new ImageIcon(imgP1));
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
