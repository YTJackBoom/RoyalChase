package enemy;

import controllers.EnemyController;
import helpers.Coordinate;
import basics.Animator;
import helpers.variables;

public class Enemy {
	private Coordinate pos;
	private int pathIndex;
	private int speed;
	private int health;
	private int type;
	private boolean isActive;
	private Animator activeAnimator,passiveAnimator;
	
	
	public Enemy(EnemyController enemyController, Coordinate pos, int type) {
		this.pos = pos;
		pathIndex = 1;
		this.type = type;
		isActive = true;
		initAnimators();
		initVariables();
	}


	public void initAnimators() {
		activeAnimator = new Animator(variables.Enemies.getEnemyActiveGifPath(type));
		passiveAnimator = new Animator(variables.Enemies.getEnemyPassiveGifPath(type));
	}
	public void initVariables() {
		speed = variables.Enemies.getEnemySpeed(type);
	}

	public void die() {

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
	//	System.out.println(pos.getX()+" "+pos.getY());
		this.pos = pos;
	}

	public Coordinate getPos() {
		return pos;
	}

	public int getType() {
		return type;
	}

	public Animator getActiveAnimator() {
		return activeAnimator;
	}
	public Animator getPassiveAnimator() {
		return passiveAnimator;
	}
	public boolean isActive() {
		return isActive;
	}
}