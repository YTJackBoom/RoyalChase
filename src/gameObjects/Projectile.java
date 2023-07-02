package gameObjects;


import helpers.*;
import controllers.ProjectileController;

import static helpers.variables.Projectiles.ARROW;
import static helpers.variables.Projectiles.ROCKET;


public class Projectile extends GameObject{
	private Coordinate pos;
	private int type;
	private Animator animator;
	private Enemy target;
	private ProjectileController projectileController;
	private int counter;
	private double numberForTrajectory;
	int height,width;


	public Projectile(ProjectileController projectileController, Coordinate start, Enemy target, int type) {  // TODO: eine "animaion" pro richtung, prob in animation selber. wie enemys und towers verbessern
		this.pos = start;
		this.type = type;
		this.target = target;
		this.projectileController = projectileController;
		initAnimator();
		height = animator.getHeight();
		width = animator.getWidth();
	}

	private void initAnimator() {
		animator = projectileController.getPlaying().getGame().getPreLoader().getProjectileAnimator(type);

	}
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
				int[] tempArray = math.ProjectileMath.calculateArrowChange(pos, target.getPos());
				pos.setX(pos.getX() + tempArray[0]);
				pos.setY(pos.getY() + tempArray[1]);
			}
			case ROCKET -> {
				numberForTrajectory += variables.Projectiles.getProjectileSpeed(variables.Projectiles.ROCKET);
				pos = math.ProjectileMath.calculateRocketPos(pos, target.getPos(), numberForTrajectory);
			}
		}

//		int[] tempArray = math.ProjectileMath.calculateArrowChange(pos, target.getPos());
//		pos.setX(pos.getX()+tempArray[0]);
//		pos.setY(pos.getY()+tempArray[1]);
		numberForTrajectory+=variables.Projectiles.getProjectileSpeed(variables.Projectiles.ROCKET);
		pos = math.ProjectileMath.calculateRocketPos(pos,target.getPos(),numberForTrajectory);
	}

	public Animator getAnimator() {
		return animator;
	}
	public Enemy getTarget() {return target;}
	public int getType() {return type;}


	public int getDamage() {
		return variables.Projectiles.getProjectileDamage(type);
	}
	public void removeAnimators() {
		animator = null;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
