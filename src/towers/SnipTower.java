package towers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import controllers.TowerController;
import gameObjects.Coordinate;
import enemy.Enemy;

public class SnipTower extends Tower {
	private int range = 100;


	public SnipTower(TowerController towerController, Coordinate pos, int type) {
		super(towerController,pos, type);
	}




}
