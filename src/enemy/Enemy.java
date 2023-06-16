package enemy;

import controllers.EnemyController;
import helpers.Coordinate;
import helpers.Animator;
import helpers.PreLoader;
import helpers.variables;

import java.awt.*;

public class Enemy {
	private Coordinate pos;
	private int pathIndex;
	private int speed;
	private int health;
	private int type;
	private int width, height;
	private boolean isActive;
	private Animator activeAnimator,passiveAnimator;
	private EnemyController enemyController;
	
	
	public Enemy(EnemyController enemyController, Coordinate pos, int type) {
		this.pos = pos;
		this.enemyController = enemyController;
		pathIndex = 1;
		this.type = type;
		isActive = true;
		initAnimators();
		initVariables();
	}


	public void initAnimators() {
		PreLoader preLoader = enemyController.getPlaying().getGame().getPreLoader();
		activeAnimator  =  preLoader.getEnemyActiveAnimator(type);
		passiveAnimator = preLoader.getEnemyPassiveAnimator(type);
	}
	public void initVariables() {
		speed = variables.Enemies.getEnemySpeed(type);
		health = variables.Enemies.getEnemyHealth(type);
		width = activeAnimator.getWidth();
		height = activeAnimator.getHeight();

	}
	public void renderHealthBar(Graphics g) {
		int t = variables.Enemies.getEnemyHealth(type);
		int hpbarx = pos.getX()+width/2-variables.Enemies.getEnemyHealth(type)/2;
		g.setColor(Color.RED);
		g.fillRect(hpbarx, pos.getY()-10, t, 5);

		g.setColor(Color.GREEN);
		g.fillRect(hpbarx, pos.getY()-10, (int)(t*((double)health/variables.Enemies.getEnemyHealth(type))), 5);
	}


	public void die() {

	}
	public void damage(int i) {
		health -= i;
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
	public int getHealth() {
		return health;
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

	public int getReward() {
		return variables.Enemies.getEnemyReward(type);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Enemy) {
			Enemy enemy = (Enemy) obj;
			if (enemy.getPos().equals(pos)) {
				return true;
			}
		}
		return false;
	}


	public void removeAnimators() {
		activeAnimator = null;
		passiveAnimator = null;
	}
}