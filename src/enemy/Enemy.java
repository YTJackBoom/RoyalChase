package enemy;

import controllers.EnemyController;
import gameObjects.Coordinate;
import basics.Animator;
import helpers.variables;

import java.io.File;

public class Enemy {
	private Coordinate pos;
	private int pathIndex;
	private int speed;
	private int health;
	private int type;
	private Animator Animator;
	
	
	public Enemy(EnemyController enemyController, Coordinate pos, int type) {
		this.pos = pos;
		pathIndex = 1;
		this.type = type;
		setSpeed();
		createAnimator();
	}


	public void createAnimator() {
		Animator = new Animator(getGifFile());
	}
	public void die() {

	}

	public void move() {
	}
	public int getPathIndex() {
		return pathIndex;
	}
	public int getSpeed() {
		return speed;
	}
	public void setPathIndex(int i) {
		pathIndex =i;
	}
	public void setPos(Coordinate pos) {
		System.out.println(pos.getX()+" "+pos.getY());
		this.pos = pos;
	}

	public Coordinate getPos() {
		return pos;
	}

	public int getType() {
		return type;
	}

	public Animator getAnimator() {
		return Animator;
	}

	public File getGifFile() {
		return variables.Enemies.getEnemyGifFile(type);
	}
	public void setSpeed() {
		speed = variables.Enemies.getEnemySpeed(type);
	}
}