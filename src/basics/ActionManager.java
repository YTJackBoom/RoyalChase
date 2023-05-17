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
	private ArrayList<Coordinate> pC;//pathCoordinates
	private GameScreen gS;
	
	public ActionManager(GameScreen gS, ArrayList<Tower> towerEntityList, ArrayList<Enemy> enemyList, ArrayList<Projectile> projectileList, ArrayList<Coordinate> pathCoordinates) {
		tEL = towerEntityList;
		eL = enemyList;
		pC = pathCoordinates;
		this.gS  = gS;
		
		t = new Timer(10, new ActionListener() {
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
	public boolean checkRange(Tower tower, GameObject object2) {
	//	System.out.println(Math.abs(object2.getPos().getY()-object1.getPos().getY()));
		if(Math.abs(object2.getPos().getY()-tower.getPos().getY())<=tower.getRange()&&Math.abs(object2.getPos().getX()-tower.getPos().getX())<=tower.getRange()) {
			return true;
	}else return false;
}
	public void updateEnemyMovement() {
		for(int a = 0; a < eL.size(); a++) {
			if(!eL.get(a).getPos().equals(pC.get(pC.size()-1))) {
				eL.get(a).setPathIndex(eL.get(a).getPathIndex()+eL.get(a).getSpeed());
				eL.get(a).setPos(pC.get(eL.get(a).getPathIndex()));	
				//System.out.println(eL.get(a).getPathIndex()+"  "+ pC.get(eL.get(a).getPathIndex()).getX()+" "+pC.get(eL.get(a).getPathIndex()).getY());
				//System.out.println(eL.get(a).getPos().getX());
			}else { 
				
			}
		}
	}
	public void refreshTEL() {
		for(int a=0;a < tEL.size(); a++) {
			tEL.get(a).refresh();
		}
	}
}
