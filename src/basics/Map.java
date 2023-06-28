package basics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import helpers.Coordinate;
import enemy.Enemy;
import controllers.EnemyController;
import helpers.ImageAnalyser;
import projectiles.Projectile;
import gameObjects.Tower;


public class Map  {
	private BufferedImage mapImage;
	private int mapNum;
	private ImageIcon clickableImg;
	
	private JPanel gS;
	private ImageAnalyser iA;
	
	private EnemyController mMan;
	
	private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	private ArrayList<Projectile> projectileList = new ArrayList<Projectile>();
	private ArrayList<Tower> towerEntityList = new ArrayList<Tower>();
	private ArrayList<Coordinate> pathCoordinates;
	private ArrayList<Coordinate> towerFoundationsList;
	
	public Map(int mapNum,JPanel gS) { //number für nummer der map
		this.mapNum=mapNum;
		this.gS = gS;
		iA = new ImageAnalyser(getPMapFile());
		pathCoordinates = iA.imgToPath();
		towerFoundationsList = iA.imgToFoundList();
	////	setLayout(null);
		//setPreferredSize(gS.getPreferredSize());
		//setVisible(true);
		
		//initTowerFoundations();
		
		
		//enemyList.add(new Enemy(gS, new Coordinate(500,500), 480,350));

		}
	
	
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;	
		try {
			mapImage = ImageIO.read(getMapFile());
		} catch (IOException e) {
			System.out.println(e);
		}
		g2d.drawImage(mapImage,0,0, null);
		//repaintTowerEntities(g);		
	}
	
	/*public void initTowerFoundations() {
			for (int i= 0;i<towerFoundationsList.size();i++) {
				towerEntityList.add(new TowerFoundation());
			}
	}
	*/
	public File getPMapFile() { 
		return new File("res/Map/PathMaps/Path"+mapNum+".bmp");
		}
	
	public File getMapFile() {
		return new File("res/Map/BackgroundMaps/Map"+mapNum+".jpg");
	}

	public ArrayList<Tower> getTowerEntityList() {
		return towerEntityList;
	}

}
