package gameObjects;

import controllers.ProjectileController;
import helpers.*;
import controllers.TowerController;

import java.awt.*;

import static helpers.variables.Projectiles.LIGHTNINGBALL;

public class Tower extends GameObject {
//	private Animator passiveAnimator, activeAnimatorTurret;
//	private Animator animatorBase;

//	private final Coordinate pos;
	protected int counter;
	private Enemy target;
	private boolean isFiring,isLoaded;
	private int type;
	private int width, height;
	private int level;
	private Rectangle bounds;
	private TowerController towerController;
	private ProjectileController projectileController;
	private Circle[] circles;
	private Values worth;
			
	public Tower(TowerController towerController, Coordinate pos, int type) {
		super(pos,towerController.getPlaying().getGame().getPreLoader(),ObjectType.TOWER,type);
		this.type = type;
		this.pos = pos;
		this.towerController = towerController;

		initVariables();
		initBounds();
		initRange();


	}


	public void update() {
		if (!towerController.getPlaying().isPaused()) {
			if (counter - getReloadTime() >= 0) {
				isLoaded = true;
				counter = 0;
			} else counter++;
			fire();
			}
	}
	public void fire() {
		//System.out.println("isLoaded "+isLoaded+"   "+"isfiring  "+isFiring);
		if (isLoaded && isFiring&&target != null) {
			projectileController.spawnProjectile(this, target,type); // target mitgabe, um target zu damagen
//			System.out.println("ürpjectile sdpawnes");

			isLoaded = false;
		}
	}
	public void renderRange(Graphics g) {
		for(Circle circle: circles) {
			circle.render(g);
		}
	}

	public void initVariables() {
		this.height = activeAnimator.getHeight();
		this.width = activeAnimator.getWidth();
		projectileController = towerController.getPlaying().getProjectileController();
		isLoaded = true;
		worth = variables.Towers.getCost(type);
		level = 1;


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
		circles[0] = new Circle(pos,range);
	}

	public double getReloadTime() {
		int r = variables.Towers.getTowerReloadTime(type);

		for(int i =1 ; i<level;i++) {
			if ((r-r*Constants.ObjectConstants.SPEEDUPGRADE)>Constants.ObjectConstants.LOWESTRELOADSPEED){
				r -= r * Constants.ObjectConstants.SPEEDUPGRADE;
			}else break;
		}
		return r;
	}


	//Getters n setter
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
		return activeAnimator.getHeight();
	}
	public int getWidth() {
		return activeAnimator.getWidth();
	}
	public void upgrade() {level ++;}
	public int getLevel() {return level;}

	public Circle getRange() {
		return circles[0];
	}
	public Values getWorth() {
		return worth;
	}
}
