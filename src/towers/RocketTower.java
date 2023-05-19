package towers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gameObjects.Coordinate;
import enemy.Enemy;

public class RocketTower extends Tower {
	private int range = 100;

	public RocketTower(JPanel sPanel, Coordinate pos, int type, ArrayList towerEntityList,
			int ArrayPos) {
		super(sPanel, pos,35,35, type, towerEntityList, ArrayPos, getBufferedImage());
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

	public static BufferedImage getBufferedImage() {
		try {
			return ImageIO.read(new File(imgP1));
		} catch (IOException e) {
			System.out.println(e);
			System.out.print(" Error in RocketTower");
		}
		return null;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}
