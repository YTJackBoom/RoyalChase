package basics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import gameObjects.Coordinate;
import gameObjects.Enemy;
import gameObjects.GameObject;
import projectiles.Arrow;
import projectiles.Projectile;
import towers.ArrowTower;
import towers.Tower;

public class ActionManager {
	private Timer t;
	private ArrayList<Tower> tEL;
	private ArrayList<Enemy> eL;
	private ArrayList<Projectile> pL;
	private ArrayList<Coordinate> pC;//pathCoordinates
	private ImageToPath iTP;
	private GameScreen gS;
	private  boolean[][] pMap; //pathMap , map  geteilt in array für gegenr pfad 
	
	public ActionManager(GameScreen gS, ArrayList<Tower> towerEntityList, ArrayList<Enemy> enemyList, ArrayList<Projectile> projectileList, boolean[][] pMap) {
		tEL = towerEntityList;
		eL = enemyList;
		pL = projectileList;
		this.gS  = gS;
		this.pMap = new boolean[5][5];
		iTP = new ImageToPath();
		pC = iTP.imgToPath("res/images/Path.jpeg");
		
		t = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doOnTick();
			}
		});
		
		t.start();
	}
	
	public void doOnTick() {
		updateEnemyMovement();
		moveEnemy();
		checkTELStatus();
		refreshTEL();
	//	initFiring();
		
	}
	
	public void checkTELStatus() {
		for (int i = 0;i<tEL.size();i++) {
			for(int o = 0; o<eL.size();o++) { 
			if(checkRange(tEL.get(i),eL.get(o))) {
				tEL.get(i).changeStatus(true);
				tEL.get(i).setTarget(eL.get(o));
				System.out.println("			"+i+"inrange"+o);
			}else {
				tEL.get(i).changeStatus(false);
				System.out.println("			"+i+"n range"+o);
			}
		}
		}
	}
	
	public void moveEnemy(){
		for(int a = 0; a < eL.size(); a++) {
			eL.get(a).move();
		}
	}
	public boolean checkRange(GameObject object1, GameObject object2) {
	//	System.out.println(Math.abs(object2.getPos().getY()-object1.getPos().getY()));
		if(Math.abs(object2.getPos().getY()-object1.getPos().getY())<=object1.getRange()&&Math.abs(object2.getPos().getX()-object1.getPos().getX())<=object1.getRange()) {
			return true;
	}else return false;
}
	public void updateEnemyMovement() {
		for(int a = 0; a < eL.size(); a++) {
				eL.get(a).setPathIndex(eL.get(a).getPathIndex()+eL.get(a).getSpeed());
				eL.get(a).setPos(pC.get(eL.get(a).getPathIndex()));	
				//System.out.println(eL.get(a).getPathIndex()+"  "+ pC.get(eL.get(a).getPathIndex()).getX()+" "+pC.get(eL.get(a).getPathIndex()).getY());
				System.out.println(eL.get(a).getPos().getX());
			}
		}
	
	public void refreshTEL() {
		for(int a=0;a < tEL.size(); a++) {
			tEL.get(a).refresh();
		}
	}
}
