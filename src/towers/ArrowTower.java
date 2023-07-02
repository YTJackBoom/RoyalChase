package towers;

import controllers.ProjectileController;
import controllers.TowerController;
import gameObjects.Tower;
import helpers.Coordinate;
import gameObjects.Enemy;

import static helpers.variables.Projectiles.ARROW;

public class ArrowTower extends Tower {
	double counter;
	private boolean isLoaded;
	private boolean isFiring;
	private Enemy target;
	private ProjectileController projectileController;
	private TowerController towerController;

	public ArrowTower(TowerController towerController, Coordinate pos, int type) {
		super(towerController,pos, type);
		projectileController = towerController.getPlaying().getProjectileController();
		this.towerController = towerController;
		isLoaded = true;
	}
	@Override
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
			projectileController.spawnProjectile(new Coordinate(getPos().getX(), getPos().getY()), target,ARROW); // target mitgabe, um target zu damagen
//			System.out.println("ürpjectile sdpawnes");

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
