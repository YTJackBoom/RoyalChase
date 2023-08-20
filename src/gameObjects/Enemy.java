package gameObjects;

import controllers.EnemyController;
import helpers.*;

import java.awt.*;

public class Enemy extends GameObject{
//	private Coordinate pos;
	private double pathIndex;
	private double speed;
	private boolean isActive;
	private EnemyController enemyController;

	
	public Enemy(EnemyController enemyController, Coordinate pos, int type) {
		super(pos,enemyController.getPlaying().getGame().getPreLoader(),ObjectType.ENEMY,type);
		this.enemyController = enemyController;
		pathIndex = 1;
		isActive = true;
		initVariables();
	}

	public void initVariables() {
		speed = variables.Enemies.getEnemySpeed(type);
	}

	public double getPathIndex() {
		return pathIndex;
	}
	public double getSpeed() {
		return speed;
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


}