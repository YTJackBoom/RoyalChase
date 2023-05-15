package towers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import basics.GameScreen;
import gameObjects.Coordinate;
import gameObjects.Enemy;
import gameObjects.GameObject;
import uiElements.PlacementMenu;

public abstract class Tower extends GameObject  {
	protected ImageIcon towerPassive;
	private JLabel tLabel;
	private  boolean menuOpen;
	private JPanel gS;
	private Coordinate pos;
	private PlacementMenu[] pMenu;
	private ArrayList tEL;
	private int aPos;
	protected int range;
	private boolean isFiring;
	
	protected final static String imgP1 = "res/images/black square.png";
	protected final static String imgP2 = "res/images/red square.png";
	protected final static String imgP3 = "res/images/blue square.png";
	protected final static String imgP4 = "res/images/yellow square.png";
	protected final static String imgP5 = "res/images/green square.png";
			
	public Tower(JPanel  gS, Coordinate pos, int width, int height, int type, ArrayList towerEntityList, int ArrayPos,ImageIcon towerPassive) {
		super(gS, pos, width, height, type,0);
		this.tEL = towerEntityList;
		this.gS = gS;
		this.pos = pos;
		this.aPos = ArrayPos;
		range = 100;
		//switch (type) {
		//case 1: 
		//	towerPassive = new ImageIcon(imgP1);
			tLabel = new JLabel(towerPassive);
			tLabel.setBounds(pos.getX(),pos.getY(),60,60);

			gS.add(tLabel);
			gS.setComponentZOrder(tLabel,0);

			tLabel.setVisible(true);
		
		tLabel.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(menuOpen) {
	        	closePlacementMenu();
	        	menuOpen = false;
	        	}else{
	            System.out.println("Image clicked!");
	            openPlacementMenu();
	            menuOpen = true;
	        	}
	        }
	    });
		initPlacementMenu();
	}
	
	
	public void initPlacementMenu() {
		pMenu = new PlacementMenu[4];
		pMenu[0] = new PlacementMenu(gS,1,new File("res/images/black square small.png"), new Coordinate(pos.getX()-40, pos.getY()),tEL,aPos,this);
		pMenu[1] = new PlacementMenu(gS,2,new File("res/images/black square small.png"), new Coordinate(pos.getX()-85, pos.getY()),tEL,aPos,this);
		pMenu[2] = new PlacementMenu(gS,3,new File("res/images/black square small.png"), new Coordinate(pos.getX()-130, pos.getY()),tEL,aPos,this);
		pMenu[3] = new PlacementMenu(gS,4,new File("res/images/black square small.png"), new Coordinate(pos.getX()-175, pos.getY()),tEL,aPos,this);
	}
	public void openPlacementMenu() {
		pMenu[0].setVisible(true);
		pMenu[1].setVisible(true);
		pMenu[2].setVisible(true);
		pMenu[3].setVisible(true);
		
	}
	public void closePlacementMenu() {
		pMenu[0].setVisible(false);
		pMenu[1].setVisible(false);
		pMenu[2].setVisible(false);
		pMenu[3].setVisible(false);
	}
	
	
	@Override
	public void vanish() {
		for(int i =0; i<=pMenu.length-1;i++) {
			pMenu[i].vanish();
		}
		this.gS.remove(tLabel);
		tLabel = null;
	}
	public int getRange() {
		return range;
	}
	
	
	public abstract void fire();
	public abstract void refresh();
	
	public abstract void changeStatus(boolean status);
	public abstract void setTarget(Enemy target);
	
	public boolean getFiring() {
		return isFiring;
	}
	public boolean getStatus() {
		return isFiring;
	}
	public void printThings() {
		
	}
}
