package gameObjects;

import helpers.Animator;
import controllers.TowerController;
import helpers.Coordinate;
import enemy.Enemy;
import helpers.PreLoader;
import helpers.variables;

import java.awt.*;

public class Tower  {
	protected Animator passiveAnimator, activeAnimator;
	private  boolean menuOpen;
	private Coordinate pos;
	protected int range;
	private Enemy target;
	private boolean isActive;
	private int type;
	private int width, height;
	private Rectangle bounds;
	private TowerController towerController;

			
	public Tower(TowerController towerController, Coordinate pos, int type) {
		this.type = type;
		this.pos = pos;
		this.towerController = towerController;
		initAnimators();
		initVariables();
		initBounds();


	}


	public void update() {
		if (!towerController.getPlaying().isPaused()) {
			System.out.println("3");
		}
	}


	public void initAnimators() {
		PreLoader preLoader = towerController.getPlaying().getGame().getPreLoader();
		passiveAnimator = preLoader.getTowerPassiveAnimator(type);
		activeAnimator = preLoader.getTowerActiveAnimator(type);
	}
	public void initVariables() {
		range = variables.Towers.getRange(type);
		this.height = activeAnimator.getHeight();
		this.width = activeAnimator.getWidth();
	}
	public void initBounds() {
			bounds = new Rectangle(pos.getX(),pos.getY(),width,height);
	}
	public void setStatus(boolean status) {
		isActive = status;
	}

	public void setTarget(Enemy target) {
		this.target = target;
	}

	public Coordinate getPos() {
		return pos;
	}

	public boolean isActive() {
		return isActive;
	}

	public Animator getActiveAnimator() {
		return activeAnimator;
	}

	public Animator getPassiveAnimator() {
		return passiveAnimator;
	}

	public int getType() {
		return type;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public Enemy getTarget() {
		return target;
	}

}
