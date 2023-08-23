package gameObjects;


import helpers.*;
import controllers.ProjectileController;

import static helpers.variables.Projectiles.*;


public class Projectile extends GameObject{
//	private Coordinate pos;
	private int type;
//	private Animator animator;
	private GameObject target;
	private GameObject origin;
	private ProjectileController projectileController;
	private int counter;
	private double numberForTrajectory;
	private double towerAngleToTarget;
	private int height,width;


	public Projectile(ProjectileController projectileController,GameObject origin, GameObject target, int type) {
		super(new Coordinate(origin.getPos().getX(),origin.getPos().getY()),projectileController.getPlaying().getGame().getPreLoader(),ObjectType.PROJECTILE,type);
		this.type = type;
		this.target = target;
		this.projectileController = projectileController;
		this.origin = origin;
		towerAngleToTarget = math.GeneralMath.calculateAngle(target.getPos(),origin.getPos());
		height = activeAnimator.getHeight();
		width = activeAnimator.getWidth();
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
			case BULLET -> {
				pos = math.ProjectileMath.calculateBulletPos(pos,towerAngleToTarget,getSpeed());
			}
		}


	}
	public GameObject getTarget() {return target;}
	public int getType() {return type;}


	public double getDamage() {
		double dmg = 0;
		if (origin instanceof Tower) {
			dmg = variables.Towers.getTowerDamage(origin.type);

			Tower temp = (Tower) origin;
			int tLevel = temp.getLevel();

			for (int i = 1; i < tLevel; i++) {
				dmg += dmg * Constants.ObjectConstants.DMGUPGRADE;
			}
		} else if (origin instanceof Enemy) {
			dmg = variables.Enemies.getEnemyDamage(origin.type);

		}
		return dmg;
	}
	public double getStun() {
		return variables.Projectiles.getStun(type);
	}

	public double getSpeed() {
		//		int tLevel = origin.getLevel();
//
//		for(int i =1 ; i<tLevel;i++) {
//			speed += speed*Constants.ObjectConstants.SPEEDUPGRADE;
//		}
		return variables.Projectiles.getProjectileSpeed(type);
	}

	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
