package towers;

import java.util.ArrayList;

import javax.swing.Timer;

import controllers.ProjectileController;
import controllers.TowerController;
import helpers.Coordinate;
import enemy.Enemy;
import projectiles.Arrow;

public class ArrowTower extends Tower {
	private int cooldown = 20;
	private int range = 100;
	double counter;
	private Timer t;
	private boolean isLoaded;
	private boolean isFiring;
	private Enemy target;
	private ProjectileController projectileController;

	public ArrowTower(TowerController towerController, Coordinate pos, int type) {
		super(towerController,pos, type);
		projectileController = towerController.getPlaying().getProjectileController();
	}
	@Override
	public void update() {
		if (counter - cooldown == 0) {
			isLoaded = true;
			counter = 0;
		} else counter++;
		fire();;;
		//System.out.println("sdfe");
	}


	public void fire() {
		//System.out.println("isLoaded "+isLoaded+"   "+"isfiring  "+isFiring);
		if (isLoaded && isFiring&&target != null) {
			projectileController.spawnProjectile(new Coordinate(getPos().getX(), getPos().getY()), target,0); // target mitgabe, um target zu damagen
			System.out.println("ürpjectile sdpawnes");

			isLoaded = false;
		}
	}

	public boolean getStatus() {
		return isFiring;
	}

	@Override
	public void setStatus(boolean status) {
		isFiring = status;

	}

	@Override
	public void setTarget(Enemy target) {
		this.target = target;
		super.setTarget(target);
	}

	@Override
	public Enemy getTarget() {
		return target;
	}
}
