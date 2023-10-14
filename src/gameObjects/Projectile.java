package gameObjects;


import controllers.ProjectileController;
import controllers.SoundController;
import helpers.*;

import static helpers.ObjectValues.Projectiles.*;

/**
 * Klasse für alle Projektile
 */
public class Projectile extends GameObject{
	private int type;
	private GameObject target;
	private GameObject origin;
	private ProjectileController projectileController;
	private int counter;
	private double numberForTrajectory;
	private double towerAngleToTarget;

	public Projectile(ProjectileController projectileController,GameObject origin, GameObject target, int type) {
		super(new AbsoluteCoordinate(origin.getPos().getX(), origin.getPos().getY()), GameObjectType.PROJECTILE, type, true);
		SoundController.getInstance().playSoundEffect("projectileFired_"+type); //Soundeffekt für Projektil
		this.type = type;
		this.target = target;
		this.projectileController = projectileController;
		this.origin = origin;
		towerAngleToTarget = math.GeneralMath.calculateAngle(target.getPos(),origin.getPos()); //Winkel zwischen Tower und Ziel
	}

	/**
	 * Updates für die Projektile, unter berücksichtigung des SpeedOffsets
	 */
	public void update() {
		if(target != null) {
			if(counter>= Constants.ObjectConstants.SPEEDOFFSET) {
				move();
				counter=0;
			}else counter++;

		}
	}

	/**
	 * Methode zum Bewegen der Projektile
	 */
	public void move() {
		switch (type) { //Berechnung der neuen Position je nach Projektiltyp
			case ARROW -> {
				pos = math.ProjectileMath.calculateArrowPos(pos,target.getPos(),getSpeed());
			}
			case ROCKET -> {
				numberForTrajectory += ObjectValues.Projectiles.getProjectileSpeed(ObjectValues.Projectiles.ROCKET); //Berechnung des Punktes auf der Flugbahn, mit dem Speed
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


	/**
	 * Methode zum berechnen des Schadens, unter berücksichtigung des Towerlevels
	 * @return Schaden
	 */
	public double getDamage() {
		double dmg = 0;
		if (origin instanceof Tower) {
			dmg = origin.damageDealt;

			Tower temp = (Tower) origin;
			int tLevel = temp.getLevel();

			for (int i = 1; i < tLevel; i++) {
				dmg += dmg * Constants.ObjectConstants.DMGUPGRADE;
			}
		} else if (origin instanceof Enemy) {
			dmg = origin.damageDealt;

		}
		return dmg;
	}
	public double getStun() {
		return ObjectValues.Projectiles.getStun(type);
	}

	public double getSpeed() {
		return ObjectValues.Projectiles.getProjectileSpeed(type);
	}

}
