package towers;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import basics.GameScreen;
import basics.GameWindow;
import gameObjects.Coordinate;
import gameObjects.Enemy;
import gameObjects.GameObject;
import uiElements.PlacementMenu;

public class TowerFoundation extends Tower {
	private File towerFoundImgFile;
	private ImageIcon towerFoundImg;
	private JLabel towerFoundLabel;
	private Coordinate pos;
	private JPanel gS;
	private int arrayPos;
	private  int range=900;
	
	private JPanel menuPanel;
	private PlacementMenu[] pMenu;
	private ArrayList towerEntityList;
	
	private boolean menuOpen;
	
	public TowerFoundation(JPanel gS,Coordinate pos, int width, int height, ArrayList towerEntityList,int arrayPos) {
		super(gS ,pos, width, height, 0, towerEntityList, arrayPos, getBufferedImage());
		this.pos = pos;
		this.gS = gS;
		this.towerEntityList = towerEntityList;
		this.arrayPos = arrayPos;
		super.range = 100;

		
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
	public void setPos(Coordinate pos) {
		// TODO Auto-generated method stub
		
	}
	public void refresh(Graphics g) {
	//	super.revalidate();
		super.paintComponent(g);
	}
	public static BufferedImage getBufferedImage() {
		try {
			return ImageIO.read(new File("res/images/black square.png"));
		} catch (IOException e) {
			System.out.println(e);
			System.out.print(" Error in PlacementMenu");
		}
		return null;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

		
	
	
}


