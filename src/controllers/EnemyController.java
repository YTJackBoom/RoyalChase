package controllers;

import java.util.ArrayList;

import basics.Game;
import enemy.Skull;
import gameObjects.Coordinate;
import enemy.Enemy;
import enemy.EnemyType;

import static enemy.EnemyType.*;

public class EnemyController {
	private ArrayList<Enemy> eL;
	private ArrayList<Coordinate> pC;//pathCoordinates
	private Game game;

	public EnemyController(Game game, ArrayList<Coordinate> pathCoordinates) {
		this.game = game;
		pC = pathCoordinates;

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
			if (!enemy.getPos().equals(pC.get(pC.size() - 1))) {
				enemy.setPathIndex(enemy.getPathIndex() + enemy.getSpeed());
				enemy.setPos(pC.get(enemy.getPathIndex()));
				//System.out.println(eL.get(a).getPathIndex()+"  "+ pC.get(eL.get(a).getPathIndex()).getX()+" "+pC.get(eL.get(a).getPathIndex()).getY());
				//System.out.println(eL.get(a).getPos().getX());
			} else {

			}
		}
	}
	public void spawnEnemy(int enemyType) {
		switch (enemyType) { //switch case for different enemy types from enum
			case SKULL -> eL.add(new Skull());
			case SKELETON -> eL.add(new Enemy());
			case ZOMBIE -> eL.add(new Enemy());
			case WITCH -> eL.add(new Enemy());
			case SKELETON_KING -> eL.add(new Enemy());
			case ZOMBIE_KING -> eL.add(new Enemy());
			case WITCH_KING -> eL.add(new Enemy());
			case WIZARD_KING -> eL.add(new Enemy());

		}
   }
 }


