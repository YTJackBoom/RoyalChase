package gameObjects;

import controllers.ProjectileController;
import helpers.*;
import controllers.TowerController;

import java.awt.*;

public class Tower extends GameObject {
	protected Animator passiveAnimator, activeAnimator;
	private Coordinate pos;
	protected int counter;
	private Enemy target;
	private boolean isFiring,isLoaded;
	private int type;
	private int width, height;
	private Rectangle bounds;
	private TowerController towerController;
	private ProjectileController projectileController;
	private Circle[] circles;
			
	public Tower(TowerController towerController, Coordinate pos, int type) {
		this.type = type;
		this.pos = pos;
		this.towerController = towerController;
		projectileController = towerController.getPlaying().getProjectileController();
		isLoaded = true;

		initAnimators();
		initVariables();
		initBounds();
		initRange();


	}


	public void update() {
		if (!towerController.getPlaying().isPaused()) {
			if (counter - 3 == 0) {
				isLoaded = true;
				counter = 0;
			} else counter++;
			fire();
			;
			;
			//System.out.println("sdfe");
		}
	}
	public void fire() {
		//System.out.println("isLoaded "+isLoaded+"   "+"isfiring  "+isFiring);
		if (isLoaded && isFiring&&target != null) {
			projectileController.spawnProjectile(new Coordinate(getPos().getX(), getPos().getY()), target,type); // target mitgabe, um target zu damagen
//			System.out.println("ürpjectile sdpawnes");

			isLoaded = false;
		}
	}
	public void renderRange(Graphics g) {
		for(Circle circle: circles) {
			circle.render(g);
		}
	}


	public void initAnimators() {
		PreLoader preLoader = towerController.getPlaying().getGame().getPreLoader();
		passiveAnimator = preLoader.getTowerPassiveAnimator(type);
		activeAnimator = preLoader.getTowerActiveAnimator(type);
	}
	public void initVariables() {
		this.height = activeAnimator.getHeight();
		this.width = activeAnimator.getWidth();
	}
	public void initBounds() {
			bounds = new Rectangle(pos.getX()-width/2,pos.getY()-height/2,width,height);
	}
	public void initRange() {
		int range = variables.Towers.getTowerRange(type);
		circles = new Circle[Constants.UIConstants.NUMBEROFRANGECIRCLES];

		for(int i = 0; i<circles.length; i++) {
			circles[i] = new Circle(pos,range-circles.length+i);
		}
	}
	public void setStatus(boolean status) {
		isFiring = status;
	}

	public void setTarget(Enemy target) {
		this.target = target;
	}

	public Coordinate getPos() {
		return pos;
	}

	public boolean isActive() {
		return isFiring;
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
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}

}