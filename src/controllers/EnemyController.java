package controllers;

import java.awt.*;
import java.util.ArrayList;

import enemy.*;
import helpers.Coordinate;
import scenes.Playing;

public class EnemyController {
	private ArrayList<Enemy> enemyList;
	private ArrayList<Enemy> removeQue;
	private ArrayList<Coordinate> pathCoordinates;//pathCoordinates

	private Playing playing;

	public EnemyController(Playing playing, ArrayList<Coordinate> pathCoordinates) {
		this.playing = playing;
		this.pathCoordinates = pathCoordinates;
		enemyList = new ArrayList<Enemy>();
		removeQue = new ArrayList<Enemy>();

	}
	public void update() {
		updateEnemyMovement();

		workRemoveQueue();
	}

	public void updateEnemyMovement() {
		for (Enemy enemy : enemyList) {
			if (!((pathCoordinates.size() - enemy.getPathIndex()) <= enemy.getSpeed()) ) {
				enemy.setPathIndex(enemy.getPathIndex() + enemy.getSpeed());
				enemy.setPos(pathCoordinates.get(enemy.getPathIndex()));
			//	System.out.println(enemy.getPathIndex()+"  "+ pathCoordinates.get(enemy.getPathIndex()).getX()+" "+pathCoordinates.get(enemy.getPathIndex()).getY());
				//System.out.println(enemyList.get(a).getPos().getX());
			} else { //Enemy hat das tor erreicht --> verschiedene verhalten //TODO: damage player
				removeQue.add(enemy);
			}
		}

	}
	public void workRemoveQueue() {
		for (Enemy enemy : removeQue) {
			enemyList.remove(enemy);
		}
	}
	public void spawnEnemy(int enemyType) {
		switch (enemyType) { //switch case for different enemy types from enum
			case 0 -> enemyList.add(new Skull(this, pathCoordinates.get(0)));
			case 1 -> enemyList.add(new Skeleton(this, pathCoordinates.get(0)));
			case 2 -> enemyList.add(new Zombie(this, pathCoordinates.get(0)));
			case 3 -> enemyList.add(new Witch(this, pathCoordinates.get(0)));
			case 4 -> enemyList.add(new Wizard(this, pathCoordinates.get(0)));
			case 5 -> enemyList.add(new Skeleton_King(this, pathCoordinates.get(0)));
			case 6 -> enemyList.add(new Zombie_King(this, pathCoordinates.get(0)));
			case 7 -> enemyList.add(new Witch_Queen(this, pathCoordinates.get(0)));
			case 8 -> enemyList.add(new Wizard_King(this, pathCoordinates.get(0)));

		}
   }
   public void render(Graphics g) {
	   if (enemyList != null) {
		   for (Enemy enemy : enemyList) {
			   if (enemy != null) {
			   		if(enemy.isActive()) {
				  		 g.drawImage(enemy.getActiveAnimator().getCurrentImage(), enemy.getPos().getX(), enemy.getPos().getY(), null);
			   		} else {
				   		g.drawImage(enemy.getPassiveAnimator().getCurrentImage(), enemy.getPos().getX(), enemy.getPos().getY(), null);
			    	}
			   }
		   }
	   }
   }
   public void damageEnemy(Enemy enemy, int damage) {
		//TODO: damage enemy
   }

   //Getters and Setters
   public void setPathCoordinates(ArrayList<Coordinate> newPathCoordinates) {
		pathCoordinates = newPathCoordinates;
   }

	public ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}

	public boolean contains(Enemy enemy) {
		return enemyList.contains(enemy);
	}
}


