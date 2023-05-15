package towers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import basics.GameScreen;
import basics.GameWindow;
import basics.Status;
import gameObjects.Coordinate;
import gameObjects.Enemy;
import gameObjects.GameObject;
import uiElements.PlacementMenu;

public class TowerFoundation extends Tower {
	private File towerFoundImgFile;
	private ImageIcon towerFoundImg;
	private JLabel towerFoundLabel;
	private Status status;
	private Coordinate pos;
	private JPanel gS;
	private int arrayPos;
	private  int range=900;
	
	private JPanel menuPanel;
	private PlacementMenu[] pMenu;
	private ArrayList towerEntityList;
	
	private boolean menuOpen;
	
	public TowerFoundation(JPanel gS,Coordinate pos, int width, int height, ArrayList towerEntityList,int arrayPos) {
		super(gS ,pos, width, height, 0, towerEntityList, arrayPos, new ImageIcon("res/images/black square.png"));
		status = Status.OK;
		this.pos = pos;
		this.gS = gS;
		this.towerEntityList = towerEntityList;
		this.arrayPos = arrayPos;
		super.range = 100;

		
	}
		/*initPlacementMenu();
		towerFoundLabel.setBounds(this.pos.getX(),this.pos.getY(),width,height);
		this.gS.add(towerFoundLabel);
		towerFoundLabel.setVisible(true);
		towerFoundLabel.addMouseListener(new MouseAdapter() {
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
		}
	
/*	public void initPlacementMenu() {
		pMenu = new PlacementMenu[4];
		pMenu[0] = new PlacementMenu(gS,1,new File("res/images/black square small.png"), new Coordinate(pos.getX()-40, pos.getY()),towerEntityList,arrayPos,this);
		pMenu[1] = new PlacementMenu(gS,2,new File("res/images/black square small.png"), new Coordinate(pos.getX()-85, pos.getY()),towerEntityList,arrayPos,this);
		pMenu[2] = new PlacementMenu(gS,3,new File("res/images/black square small.png"), new Coordinate(pos.getX()-130, pos.getY()),towerEntityList,arrayPos,this);
		pMenu[3] = new PlacementMenu(gS,4,new File("res/images/black square small.png"), new Coordinate(pos.getX()-175, pos.getY()),towerEntityList,arrayPos,this);
	}
	public void openPlacementMenu() {
		pMenu[0].setVisible(true);
		pMenu[1].setVisible(true);
		pMenu[2].setVisible(true);
		pMenu[3].setVisible(true);
		
		
		//menuPanel = new JPanel();
		//menuPanel.setLayout(null);
		
		//gS.add(menuPanel);
		//menuPanel.setVisible(true);
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
		this.gS.remove(towerFoundLabel);
		towerFoundLabel = null;
	}
*/
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
	public void setPos(Coordinate pos) {
		// TODO Auto-generated method stub
		
	}
	public void refresh() {
		
	}

		
	
	
}


