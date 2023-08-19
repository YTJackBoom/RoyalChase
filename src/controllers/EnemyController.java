package controllers;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import enemy.*;
import gameObjects.Enemy;
import helpers.*;
import scenes.Playing;
import uiElements.Explosion;

import static basics.Game.ups;


public class EnemyController implements ControllerMethods{
	private ArrayList<Enemy> enemyList,removeQue,addQue;
	private ArrayList<Explosion> explosionsList;

	private ArrayList<Coordinate> pathCoordinates;//pathCoordinates

	private Playing playing;
	private Values playerValues;

	public EnemyController(Playing playing, ArrayList<Coordinate> pathCoordinates) {
		this.playing = playing;
		this.pathCoordinates = pathCoordinates;
		enemyList = new ArrayList<Enemy>();
		explosionsList = new ArrayList<Explosion>();
		removeQue = new ArrayList<Enemy>();
		addQue = new ArrayList<Enemy>();
		playerValues = playing.getGame().getPlayerValues();
	}
	public void update() {
		workAddQueue();
		if (!playing.isPaused()) {
			updateEnemyMovement();
			updateExplosions();

		}
		checkEnemyHealth();
		workRemoveQueue();
	}

	public void updateEnemyMovement() {
		for (Enemy enemy : enemyList) {
			if(enemy != null) {
				if(enemy.getStun()<=0) {
						if (!((pathCoordinates.size() - enemy.getPathIndex()) <= enemy.getSpeed())) {
							enemy.setPathIndex(enemy.getPathIndex() + enemy.getSpeed());
							enemy.setPos(pathCoordinates.get((int)Math.round(enemy.getPathIndex())));
							//	System.out.println(enemy.getPathIndex()+"  "+ pathCoordinates.get(enemy.getPathIndex()).getX()+" "+pathCoordinates.get(enemy.getPathIndex()).getY());
							//System.out.println(enemyList.get(a).getPos().getX());
						} else {//Enemy hat das tor erreicht --> verschiedene verhalten
							playerValues.setHealth(playerValues.getHealth() - variables.Enemies.getEnemyDamage(enemy.getType()));
							removeQue.add(enemy);
							System.out.println("Enemy reached the end");
						}
				}else { enemy.setStun(enemy.getStun()-1);}
			}
		}
	}
	public void checkEnemyHealth() {
		for (Enemy enemy : enemyList) {
			if (enemy != null) {
				if (enemy.getHealth() <= 0) {
					removeQue.add(enemy);
					playerValues.setGold((int) (playerValues.getGold() +  (enemy.getReward()* playerValues.getRewardmultiplyer())));
					System.out.println("Nemy killed for " + enemy.getReward() + " gold");
				}
			}
		}
	}
	public void updateExplosions() {
		for(Explosion e : explosionsList) {
			e.update(this);
		}
	}
	@Override
	public synchronized void workRemoveQueue() {
			for (Enemy enemy : removeQue) {
				enemy.removeAnimators();
				enemyList.remove(enemy);
			}
			removeQue.clear();
	}
	@Override
	public synchronized void workAddQueue() {
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
	   if (enemyList != null && !enemyList.isEmpty()) {
		   renderEnemies(g);
		   renderHealthBars(g);
	   }
	   renderExplosions(g);
   }


   public synchronized void renderEnemies(Graphics g) {
	   for (Enemy enemy : enemyList) {
		   if (enemy != null) {
			   int width = enemy.getActiveAnimator().getWidth();
			   int height = enemy.getActiveAnimator().getHeight();
			   if (enemy.isActive()) {
				   g.drawImage(enemy.getActiveAnimator().getCurrentFrame(), enemy.getPos().getX() - width / 2, enemy.getPos().getY() - height / 2, null);
				   enemy.getActiveAnimator().incrementFrame();
			   } else {
				   g.drawImage(enemy.getPassiveAnimator().getCurrentFrame(), enemy.getPos().getX() - width / 2, enemy.getPos().getY() - height / 2, null);
				   enemy.getPassiveAnimator().incrementFrame();
			   }

		   }
	   }
   }
   public synchronized void renderHealthBars(Graphics g) {
		for (Enemy enemy : enemyList) {
			enemy.renderHealthBar(g);
		}
   }
   public void renderExplosions(Graphics g){
	   Iterator<Explosion> iterator = explosionsList.iterator();
	   while (iterator.hasNext()) {
		   Explosion e = iterator.next();
		   e.render(g);
		   if (e.isFinished()) {
			   iterator.remove();
		   }
	   }
   }
   public void damageEnemy(Enemy enemy, double damage,double stun) {
		enemy.damage(damage);
		enemy.setStun(stun*ups);
   }
	public void damageEnemiesInRadius(Circle explosion, double maxDamage, double maxStun, ArrayList<Enemy> damagedEnemies) {
		for (Enemy enemy : enemyList) {
			if (explosion.contains(enemy.getPos()) && (damagedEnemies.isEmpty() || !damagedEnemies.contains(enemy))) {
				double distanceToCenter = explosion.getCenter().distanceTo(enemy.getPos());
				double radius = explosion.getRadius();

				double damageFactor = Constants.ObjectConstants.EXPLOSIONBORDERDAMAGEPERCET + (1 - Constants.ObjectConstants.EXPLOSIONBORDERDAMAGEPERCET) * (1 - Math.pow(distanceToCenter / radius, 2));
				double finalDamage = maxDamage * damageFactor;
				double finalStun = maxStun * damageFactor;

				enemy.damage(finalDamage);
				enemy.setStun(finalStun * ups);
				damagedEnemies.add(enemy);
			}
		}
	}
	//Getters and Setters
   public void setPathCoordinates(ArrayList<Coordinate> newPathCoordinates) {
		pathCoordinates = newPathCoordinates;
   }

	public ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}

	public synchronized boolean contains(Enemy enemy) {
		for (Enemy enemy1 : enemyList) {
			if (enemy1 != null) {
				if (enemy1.equals(enemy)) {
					return true;
				}
			}
		}
		return false;
	}

	public Playing getPlaying() {
		return playing;
	}

	public void addExplosion(Explosion e) {
		explosionsList.add(e);
	}



}


