package gameObjects;

import controllers.ProjectileController;
import controllers.TowerController;
import helpers.*;

import java.awt.*;

/**
 * Klasse für Türme
 */
public class Tower extends GameObject {
protected int counter;
	private Enemy target;
	private boolean isFiring, isLoaded;
	private int level;
	private Rectangle bounds;
	protected TowerController towerController;
	private ProjectileController projectileController;
	private Circle[] circles; //Kreise für die Range
	private Values worth;

	public Tower(TowerController towerController, AbsoluteCoordinate pos, int type, boolean visibility) {
		super(pos,GameObjectType.TOWER, type, visibility);
		this.type = type;
		this.pos = pos;
		this.towerController = towerController;

		initVariables();
		initBounds();
		initRange();


	}

	/**
	 * Updates für die Türme, fürs Angriffen und Richtungs-Aktualisieren
	 */
	public void update() {
		if (!towerController.getPlaying().isPaused()) {
			updateDirection();
			if (counter - getReloadTime() >= 0) {
				isLoaded = true;
				counter = 0;
			} else counter++;
			fire();
			}
	}

	/**
	 * Methode zum Feuern der Türme
	 */
	public void fire() {
		if (isLoaded && isFiring&&target != null) {
			projectileController.spawnProjectile(this, target,type); // target mitgabe, um target zu damagen

			isLoaded = false;
		}
	}

	/**
	 * Methode zum Aktualisieren der Schuss-Richtung des Turms
	 */
	public void updateDirection() {
		if(!(target==null&&!towerController.getPlaying().getEnemyController().contains(target))) {
			AbsoluteCoordinate towerPos = this.getPos();
			AbsoluteCoordinate targetPos = this.getTarget().getPos();

			int deltaX = targetPos.getX() - towerPos.getX();
			int deltaY = targetPos.getY() - towerPos.getY();

			Direction newDir;

			double angle = Math.atan2(deltaY, deltaX); // Winkel zwischen Tower und Ziel in Radians

			double angleDeg = Math.toDegrees(angle);

			if (angleDeg >= -45 && angleDeg < 45) {
				newDir = Direction.RIGHT;
			} else if (angleDeg >= 45 && angleDeg < 135) {
				newDir = Direction.UP;
			} else if (angleDeg >= -135 && angleDeg < -45) {
				newDir = Direction.DOWN;
			} else {
				newDir = Direction.LEFT;
			}

			this.activeAnimator.setDirection(newDir);
		}
	}

	public void renderRange(Graphics g) {
		for(Circle circle: circles) {
			circle.render(g);
		}
	}

	public void initVariables() {
		damageDealt = ObjectValues.Towers.getTowerDamage(type)*math.DifficultyMath.calculateTowerDamagePercentChange();
		projectileController = towerController.getPlaying().getProjectileController();
		isLoaded = true;
		worth = ObjectValues.Towers.getCost(type);
		level = 1;


	}
	public void initBounds() {
			bounds = new Rectangle(pos.getX()-128,pos.getY()-128,256, 256); //Die größen der Tiles -> einfachere Auswahl
	}
	public void initRange() {
		int range = ObjectValues.Towers.getTowerRange(type);
		circles = new Circle[Constants.UIConstants.NUMBEROFRANGECIRCLES];


		for(int i = 0; i<circles.length; i++) {
			circles[i] = new Circle(pos,range-circles.length+i);
		}
		circles[0] = new Circle(pos,range);
	}

	public double getReloadTime() {
		int r = ObjectValues.Towers.getTowerReloadTime(type);

		for(int i =1 ; i<level;i++) {
			if ((r-r*Constants.ObjectConstants.SPEEDUPGRADE)>Constants.ObjectConstants.LOWESTRELOADSPEED){
				r -= r * Constants.ObjectConstants.SPEEDUPGRADE;
			}else break;
		}
		return r;
	}

	public boolean isMaxedLevel() {
		return level >= Constants.ObjectConstants.TOWERMAXLEVEL;
	}

	//Getters n setter
	public void setStatus(boolean status) {
		isFiring = status;
	}

	public void setTarget(Enemy target) {
		this.target = target;
	}

	public AbsoluteCoordinate getPos() {
		return pos;
	}

	public boolean isActive() {
		return isFiring;
	}
	public Rectangle getBounds() {
		return bounds;
	}

	public Enemy getTarget() {
		return target;
	}

	public void upgrade() {
		level++;
		health = health*Constants.ObjectConstants.HEALTHUPGRADE;
	}

	public int getLevel() {
		return level;
	}

	public Circle getRange() {
		return circles[0];
	}

	public Values getWorth() {
		return worth;
	}

	public Values getWorthByPercentage(double percentage) {
		return worth.cloneByPercent(percentage);
	}

}
