package controllers;

import java.awt.*;
import java.util.ArrayList;

import basics.Game;
import enemy.*;
import helpers.Constants;
import helpers.Coordinate;
import helpers.Values;
import helpers.variables;
import scenes.Playing;

import static helpers.Values.GOLD;
import static helpers.Values.HEALTH;

public class EnemyController implements ControllerMethods{
	private ArrayList<Enemy> enemyList,removeQue,addQue;

	private ArrayList<Coordinate> pathCoordinates;//pathCoordinates

	private Playing playing;
	int i ;

	public EnemyController(Playing playing, ArrayList<Coordinate> pathCoordinates) {
		this.playing = playing;
		this.pathCoordinates = pathCoordinates;
		enemyList = new ArrayList<Enemy>();
		removeQue = new ArrayList<Enemy>();
		addQue = new ArrayList<Enemy>();
	}
	public void update() {
		workAddQueue();
		workRemoveQueue();
		if (!playing.isPaused()) {
			if (i >= Constants.ObjectConstants.SPEEDOFFSET) { //TODO: potentially find a better way to do this
				updateEnemyMovement();
				i = 0;
			} else i++;
		}
		checkEnemyHealth();
	}

	public void updateEnemyMovement() {
		for (Enemy enemy : enemyList) {
			if(enemy != null) {
				if (!((pathCoordinates.size() - enemy.getPathIndex()) <= enemy.getSpeed())) {
					enemy.setPathIndex(enemy.getPathIndex() + enemy.getSpeed());
					enemy.setPos(pathCoordinates.get(enemy.getPathIndex()));
					//	System.out.println(enemy.getPathIndex()+"  "+ pathCoordinates.get(enemy.getPathIndex()).getX()+" "+pathCoordinates.get(enemy.getPathIndex()).getY());
					//System.out.println(enemyList.get(a).getPos().getX());
				} else {//Enemy hat das tor erreicht --> verschiedene verhalten //TODO: damage player
					HEALTH = HEALTH - variables.Enemies.getEnemyDamage(enemy.getType());
					removeQue.add(enemy);
					System.out.println("Enemy reached the end");
				}
			}
		}
//		System.out.println(enemyList.size());
	}
	public void checkEnemyHealth() {
		for (Enemy enemy : enemyList) {
			if (enemy != null) {
				if (enemy.getHealth() <= 0) {
					removeQue.add(enemy);
					GOLD = (int) (GOLD +  (enemy.getReward()* Values.REWARDMULTIPLYER));
					System.out.println("Nemy killed for " + enemy.getReward() + " gold");
				}
			}
		}
	}
	@Override
	public void workRemoveQueue() {
			for (Enemy enemy : removeQue) {
				enemy.removeAnimators();
				enemyList.remove(enemy);
			}
			removeQue.clear();
	}
	@Override
	public void workAddQueue() {
		enemyList.addAll(addQue);
		addQue.clear();
	}
	public void spawnEnemy(int enemyType) {
		switch (enemyType) { //switch case for different enemy types from enum
			case 0 -> addQue.add(new Skull(this, pathCoordinates.get(0)));
			case 1 -> addQue.add(new Skeleton(this, pathCoordinates.get(0)));
			case 2 -> addQue.add(new Zombie(this, pathCoordinates.get(0)));
			case 3 -> addQue.add(new Witch(this, pathCoordinates.get(0)));
			case 4 -> addQue.add(new Wizard(this, pathCoordinates.get(0)));
			case 5 -> addQue.add(new Skeleton_King(this, pathCoordinates.get(0)));
			case 6 -> addQue.add(new Zombie_King(this, pathCoordinates.get(0)));
			case 7 -> addQue.add(new Witch_Queen(this, pathCoordinates.get(0)));
			case 8 -> addQue.add(new Wizard_King(this, pathCoordinates.get(0)));

		}
   }
   public void render(Graphics g) {
	   if (enemyList != null) {
		   for (Enemy enemy : enemyList) {
			   if (enemy != null) {
				   if (enemy.isActive()) {
					   g.drawImage(enemy.getActiveAnimator().getCurrentImage(), enemy.getPos().getX(), enemy.getPos().getY(), null);
				   } else {
					   g.drawImage(enemy.getPassiveAnimator().getCurrentImage(), enemy.getPos().getX(), enemy.getPos().getY(), null);
				   }
				   enemy.renderHealthBar(g);

			   }
		   }
	   }
   }
   public void damageEnemy(Enemy enemy, int damage) {
		enemyList.get(enemyList.indexOf(enemy)).damage(damage);
   }

   //Getters and Setters
   public void setPathCoordinates(ArrayList<Coordinate> newPathCoordinates) {
		pathCoordinates = newPathCoordinates;
   }

	public ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}

	public boolean contains(Enemy enemy) {
		for (Enemy enemy1 : enemyList) {
			if (enemy1 != null) {
				if (enemy1.equals(enemy)) {
					return true;
				}
			}
		}

//		System.out.println(enemyList.indexOf(enemy)+" "+removeQue.indexOf(enemy));
		//return enemyList.contains(enemy);
		return false;
	}

	public Playing getPlaying() {
		return playing;
	}


}


