package gameObjects;

import controllers.EnemyController;
import helpers.*;

import java.awt.*;

public class Enemy extends GameObject{
//	private Coordinate pos;
	private double pathIndex;
	private double speed;
	private double health;
	private int type;
	private int width, height;
	private boolean isActive;
	private EnemyController enemyController;

	
	public Enemy(EnemyController enemyController, Coordinate pos, int type) {
		super(pos,enemyController.getPlaying().getGame().getPreLoader(),ObjectType.ENEMY,type);
		this.enemyController = enemyController;
		pathIndex = 1;
		this.type = type;
		isActive = true;
		initVariables();
	}

	public void initVariables() {
		speed = variables.Enemies.getEnemySpeed(type);
		health = variables.Enemies.getEnemyHealth(type);
		width = activeAnimator.getWidth();
		height = activeAnimator.getHeight();
	}
	public void renderHealthBar(Graphics g) {
		int t = variables.Enemies.getEnemyHealth(type);
		int hpbarx = pos.getX()-variables.Enemies.getEnemyHealth(type)/2;
		g.setColor(Color.RED);
		g.fillRect(hpbarx, pos.getY()-10-height/2, t, 5);

		g.setColor(Color.GREEN);
		g.fillRect(hpbarx, pos.getY()-10-height/2, (int)(t*((double)health/variables.Enemies.getEnemyHealth(type))), 5);
	}


	public void die() {

	}
	public void damage(double i) {
		health -= i;
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
	public int getType() {
		return type;
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

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}