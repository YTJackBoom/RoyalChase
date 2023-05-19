package enemy;

import controllers.EnemyController;
import gameObjects.Coordinate;

public class Enemy {
	private Coordinate pos;
	private int pathIndex;
	private int speed;
	private int health;
	
	
	public Enemy(EnemyController enemyController, Coordinate pos) {
		this.pos = pos;
		pathIndex = 1;

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
		this.pos = pos;
	}

	public Coordinate getPos() {
		return pos;
	}
	
}