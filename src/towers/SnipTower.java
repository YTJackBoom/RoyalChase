package towers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import basics.GameScreen;
import gameObjects.Coordinate;
import gameObjects.Enemy;

public class SnipTower extends Tower {
	private int range = 100;


	public SnipTower(JPanel gS, Coordinate pos, int type, ArrayList towerEntityList,
			int ArrayPos) {
		super(gS, pos,35,35, type, towerEntityList, ArrayPos, getBufferedImage());
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

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	public static BufferedImage getBufferedImage() {
		try {
			return ImageIO.read(new File(imgP4));
		} catch (IOException e) {
			System.out.println(e);
			System.out.print(" Error in snipTower");
		}
		return null;
	}

}
