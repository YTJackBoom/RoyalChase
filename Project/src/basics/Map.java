package basics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gameObjects.Coordinate;
import gameObjects.Enemy;
import gameObjects.GameObject;
import projectiles.Projectile;
import towers.Tower;
import towers.TowerFoundation;


public class Map {
	private BufferedImage map_image;
	private Status mapIoStatus;
	private int mapNum;
	private ImageIcon clickableImg;
	
	private GameScreen gS;
	private int towerFoundationsNum;
	private Coordinate[] towerFoundationsPos1;
	private Coordinate[] towerFoundationsPos2;
	private Coordinate[] towerFoundationsPos3;
	
	private ActionManager mMan;
	
	private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	private ArrayList<Projectile> projectileList = new ArrayList<Projectile>();
	private ArrayList<Tower> towerEntityList = new ArrayList<Tower>();
	
	public Map(BufferedImage mapImage, Status status,int mapNum,GameScreen gS) { //number für nummer der map
		map_image=mapImage;
		mapIoStatus = status;
		this.mapNum=mapNum;
		this.gS = gS;
		initTowerFoundations();
		
		
		enemyList.add(new Enemy(gS, new Coordinate(500,500), 480,350));
		
		mMan = new ActionManager(gS,towerEntityList, enemyList, projectileList,null);
		}
	
	
	protected void paintMe(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		
		switch (mapNum) {
		
		case 1:
			switch (mapIoStatus) {
			case OK:
				g2d.drawImage(map_image,0,0, null);
				break;
			case ERR:
				g2d.setColor(Color.RED);
				Ellipse2D.Double ball = new Ellipse2D.Double(1000,1000,10,10);	
				g2d.draw(ball);
				g2d.fill(ball);
				break;
			}
		case 2:
		
		
	}
	
	}
	
	public void initTowerFoundations() {
		
		
		towerFoundationsPos2 = new Coordinate[5];
		towerFoundationsPos3 = new Coordinate[5];
		switch (mapNum) {
		
		case 1:
			towerFoundationsPos1 = new Coordinate[5];
			towerFoundationsPos1[0] = new Coordinate(1000,400);
			towerFoundationsPos1[1] = new Coordinate(1499,108);
			towerFoundationsPos1[2] = new Coordinate(1486,461);
			towerFoundationsPos1[3] = new Coordinate(1508,728);
			towerFoundationsPos1[4] = new Coordinate(1550,126);
			
			for (int i= 0;i<towerFoundationsPos1.length;i++) {
				towerEntityList.add(new TowerFoundation(gS,towerFoundationsPos1[i],51,50,towerEntityList,i));
			}
			break;
		case 2:
		System.out.println(mapNum);
		

		
		
		
		
		
	}
	}
}
