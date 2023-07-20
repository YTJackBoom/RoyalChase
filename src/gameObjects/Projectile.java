package gameObjects;


import helpers.*;
import controllers.ProjectileController;

import static helpers.variables.Projectiles.*;


public class Projectile extends GameObject{
//	private Coordinate pos;
	private int type;
//	private Animator animator;
	private Enemy target;
	private Tower origin;
	private ProjectileController projectileController;
	private int counter;
	private double numberForTrajectory;
	private int height,width;


	public Projectile(ProjectileController projectileController,Tower tower, Enemy target, int type) {  // TODO: eine "animaion" pro richtung, prob in animation selber. wie enemys und towers verbessern
		super(new Coordinate(tower.getPos().getX(),tower.getPos().getY()),projectileController.getPlaying().getGame().getPreLoader(),ObjectType.PROJECTILE,type);
		this.pos = new Coordinate(tower.getPos().getX(),tower.getPos().getY());
		this.type = type;
		this.target = target;
		this.projectileController = projectileController;
		origin = tower;
//		initAnimator();
		height = activeAnimator.getHeight();
		width = activeAnimator.getWidth();
	}

//	private void initAnimator() {
//		animator = projectileController.getPlaying().getGame().getPreLoader().getProjectileAnimator(type);
//
//	}
	public Coordinate getPos() {
		return pos;
	}

	public void update() {
		if(target != null) {
			if(counter>= Constants.ObjectConstants.SPEEDOFFSET) {
				move();
				counter=0;
			}else counter++;

		}
	}
	public void move() { //berechnung der neuen position durch winkel  zwichen den punkten. vgl Quelle 1
		switch (type) {
			case ARROW -> {
				pos = math.ProjectileMath.calculateArrowPos(pos,target.getPos(),getSpeed());
			}
			case ROCKET -> {
				numberForTrajectory += variables.Projectiles.getProjectileSpeed(variables.Projectiles.ROCKET);
				pos = math.ProjectileMath.calculateRocketPos(pos, target.getPos(), numberForTrajectory, getSpeed());
			}
			case LIGHTNINGBALL -> {
				pos = math.ProjectileMath.calculateLightningBallPos(pos,target.getPos(),getSpeed(),17);
			}
		}

//		int[] tempArray = math.ProjectileMath.calculateArrowChange(pos, target.getPos());
//		pos.setX(pos.getX()+tempArray[0]);
//		pos.setY(pos.getY()+tempArray[1]);
//		numberForTrajectory+=variables.Projectiles.getProjectileSpeed(variables.Projectiles.ROCKET);
//		pos = math.ProjectileMath.calculateRocketPos(pos,target.getPos(),numberForTrajectory,getSpeed());
	}

//	public Animator getAnimator() {
//		return animator;
//	}
	public Enemy getTarget() {return target;}
	public int getType() {return type;}


	public double getDamage() {
		double dmg = variables.Projectiles.getProjectileDamage(type);
		int tLevel = origin.getLevel();

		for(int i =1 ; i<tLevel;i++) {
			dmg += dmg*Constants.ObjectConstants.DMGUPGRADE;
		}
		return dmg;
	}

	public double getSpeed() {
		//		int tLevel = origin.getLevel();
//
//		for(int i =1 ; i<tLevel;i++) {
//			speed += speed*Constants.ObjectConstants.SPEEDUPGRADE;
//		}
		return variables.Projectiles.getProjectileSpeed(type);
	}
//	public void removeAnimators() {
//		animator = null;
//	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
//	public Hitbox getHitBox() {return new Hitbox(pos.getX()-width/2,pos.getY()-height/2,width,height);}
}
