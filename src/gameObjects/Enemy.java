package gameObjects;

import controllers.EnemyController;
import helpers.*;

import java.awt.*;
import java.util.ArrayList;

public class Enemy extends GameObject{
//	private Coordinate pos;
	private double pathIndex;
	private double speed;
	protected boolean isActive;
	protected EnemyController enemyController;
	protected GameObject target;
	protected Circle range;

	
	public Enemy(EnemyController enemyController, Coordinate pos, int type) {
		super(pos,enemyController.getPlaying().getGame().getPreLoader(),ObjectType.ENEMY,type);
		this.enemyController = enemyController;
		pathIndex = 1;
		isActive = true;
		initVariables();
	}

	public void update(ArrayList<Coordinate> pathCoordinates) {
		if(!enemyController.getPlaying().isPaused()) {
			if (getStun() <= 0) {
				move(pathCoordinates);
				fire();

			} else {
				currentStun--;
			}
		}
	}

	public void move(ArrayList<Coordinate> pathCoordinates) {
		if (!getRange().contains(enemyController.getPlaying().getTowerController().getGate().getHitBox())) {

			double remainingPath = pathCoordinates.size() - 1 - getPathIndex();
			if (getSpeed() < remainingPath&&Math.ceil(getPathIndex())<pathCoordinates.size()-1) {
				double index =  getPathIndex() + getSpeed();

				// Ensure index stays within the bounds of pathCoordinates.
				index = Math.min(index, pathCoordinates.size() - 1);

				setPathIndex(index);
				setPos(pathCoordinates.get((int)Math.round(index)));
				range.setPos(pos);
			} else { // Enemy has reached the gate, implement different behaviors.
				enemyController.enemyReachedGate(this);
			}

		} else {
			target = enemyController.getPlaying().getTowerController().getGate();
			setStatus(true);
		}
	}

	public void fire() {

	}



	public void initVariables() {
		speed = variables.Enemies.getEnemySpeed(type);
		range = new Circle(pos,variables.Enemies.getEnemyRange(type));
	}

	public double getPathIndex() {
		return pathIndex;
	}
	public double getSpeed() {
		return speed;
	}
	public double getReloadTime() {
		return variables.Enemies.getEnemyReloadTime(type);
	}
	public void setPathIndex(double i) {
		pathIndex =i;
	}
	public double getHealth() {
		return health;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setStatus(boolean b) {
	isActive = b;
	}
	public void setTarget(GameObject obj) {
		target = obj;
	}
	public GameObject getTarget() {
		return target;
	}

	public int getReward() {
		return variables.Enemies.getEnemyReward(type);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Enemy) {
			Enemy enemy = (Enemy) obj;
			return enemy.getPos().equals(pos);
		}
		return false;
	}
	public Circle getRange() {
		return range;
	}
}