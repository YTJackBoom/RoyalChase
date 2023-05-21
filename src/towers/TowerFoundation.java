package towers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controllers.TowerController;
import gameObjects.Coordinate;
import enemy.Enemy;
import uiElements.PlacementMenu;

public class TowerFoundation extends Tower {
	private File towerFoundImgFile;
	private ImageIcon towerFoundImg;
	private JLabel towerFoundLabel;
	private Coordinate pos;
	private JPanel gS;
	private int arrayPos;
	private int range = 900;

	private JPanel menuPanel;
	private PlacementMenu[] pMenu;
	private ArrayList towerEntityList;

	private boolean menuOpen;

	public TowerFoundation(TowerController towerController, Coordinate pos, int type) {
		super(towerController,pos, type);
	}


	


}


