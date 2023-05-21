package controllers;

import java.awt.*;
import java.util.ArrayList;

import enemy.*;
import gameObjects.Coordinate;
import scenes.Playing;

public class EnemyController {
	private ArrayList<Enemy> eL;
	private ArrayList<Coordinate> pathCoordinates;//pathCoordinates
	private Playing playing;

	public EnemyController(Playing playing, ArrayList<Coordinate> pathCoordinates) {
		this.playing = playing;
		this.pathCoordinates = pathCoordinates;
		eL = new ArrayList<Enemy>();
		eL.add(new Skull(this, pathCoordinates.get(0)));

	}
	public void update() {
		updateEnemyMovement();
		moveEnemy();

	}
	public void moveEnemy(){
		for (Enemy enemy : eL) {
			enemy.move();
		}
	}
	public void updateEnemyMovement() {
		for (Enemy enemy : eL) {
			if (!((pathCoordinates.size() - enemy.getPathIndex()) <= enemy.getSpeed()) ) {
				enemy.setPathIndex(enemy.getPathIndex() + enemy.getSpeed());
				enemy.setPos(pathCoordinates.get(enemy.getPathIndex()));
				System.out.println(enemy.getPathIndex()+"  "+ pathCoordinates.get(enemy.getPathIndex()).getX()+" "+pathCoordinates.get(enemy.getPathIndex()).getY());
				//System.out.println(eL.get(a).getPos().getX());
			} else { //Enemy hat das tor erreicht --> verschiedene verhalten

			}
		}
	}
	public void spawnEnemy(int enemyType) {
		switch (enemyType) { //switch case for different enemy types from enum
			case 0 -> eL.add(new Skull(this, pathCoordinates.get(0)));
			case 1 -> eL.add(new Skeleton(this, pathCoordinates.get(0)));
			case 2 -> eL.add(new Zombie(this, pathCoordinates.get(0)));
			case 3 -> eL.add(new Witch(this, pathCoordinates.get(0)));
			case 4 -> eL.add(new Wizard(this, pathCoordinates.get(0)));
			case 5 -> eL.add(new Skeleton_King(this, pathCoordinates.get(0)));
			case 6 -> eL.add(new Zombie_King(this, pathCoordinates.get(0)));
			case 7 -> eL.add(new Witch_Queen(this, pathCoordinates.get(0)));
			case 8 -> eL.add(new Wizard_King(this, pathCoordinates.get(0)));

		}
   }
   public void render(Graphics g) {
	   if (eL != null) {
		   for (Enemy enemy : eL) {
			   g.drawImage(enemy.getAnimator().getCurrentImage(), enemy.getPos().getX(), enemy.getPos().getY(), null);
		   }
	   }
   }
   //Getters and Setters
   public void setPathCoordinates(ArrayList<Coordinate> newPathCoordinates) {
		pathCoordinates = newPathCoordinates;
   }

	public ArrayList<Enemy> getEnemyList() {
		return eL;
	}
}


