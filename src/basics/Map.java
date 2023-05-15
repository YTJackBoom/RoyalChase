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
import java.nio.file.Path;
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


public class Map extends JPanel {
	private BufferedImage mapImage;
	private Status mapIoStatus;
	private int mapNum;
	private ImageIcon clickableImg;
	
	private JPanel gS;
	private ImageAnalyser iA;
	
	private ActionManager mMan;
	
	private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	private ArrayList<Projectile> projectileList = new ArrayList<Projectile>();
	private ArrayList<Tower> towerEntityList = new ArrayList<Tower>();
	private ArrayList<Coordinate> pathCoordinates;
	private ArrayList<Coordinate> towerFoundationsList;
	
	public Map(Status status,int mapNum,GameScreen gS) { //number für nummer der map
		mapIoStatus = status;
		this.mapNum=mapNum;
		this.gS = this;
		iA = new ImageAnalyser(getPMapFile());
		pathCoordinates = iA.imgToPath();
		towerFoundationsList = iA.imgToFoundList();
		initTowerFoundations();
		
		
		enemyList.add(new Enemy(gS, new Coordinate(500,500), 480,350));
		
		mMan = new ActionManager(gS,towerEntityList, enemyList, projectileList,pathCoordinates);
		}
	

	protected void paintMe(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;	
		try {
			mapImage = ImageIO.read(getMapFile());
		} catch (IOException e) {
			System.out.println(e);
		}
		g2d.drawImage(mapImage,0,0, null);
		
		
		
	}
	
	public void initTowerFoundations() {
			for (int i= 0;i<towerFoundationsList.size();i++) {
				towerEntityList.add(new TowerFoundation(gS,towerFoundationsList.get(i),51,50,towerEntityList,i));
			}
	}
	
	public File getPMapFile() { 
		return new File("res/Map/PathMaps/Path"+mapNum+".bmp");
		}
	
	public File getMapFile() {
		return new File("res/Map/BackgroundMaps/Map"+mapNum+".jpg");
	}
		
}
