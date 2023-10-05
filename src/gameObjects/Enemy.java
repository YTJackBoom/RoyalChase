package gameObjects;

import controllers.EnemyController;
import enemy.EnemyAttackPattern;
import enemy.EnemyType;
import helpers.*;

import java.util.ArrayList;

public class Enemy extends GameObject {
	//	private Coordinate pos;
	private double pathIndex;
	private double speed;
	private double reloadTime;
	private double goldReward;
	protected boolean isActive;
	protected boolean isMeleeAttacking,isMovingForward; //Für melee angriffe, die  derzeitige Richtung der Bewegung im Angriff und ob der Angriff gerade ausgeführt wird
	protected EnemyController enemyController;
	protected GameObject target;
	protected Circle range;
	protected EnemyAttackPattern enemyAttackPattern;
	protected EnemyType enemyType;
	private int attackTickCounter = 0;
	protected boolean isLoaded = true;
	private int counter = 0;

	public Enemy(EnemyController enemyController, AbsoluteCoordinate pos, int type, EnemyAttackPattern enemyAttackPattern, EnemyType enemyType) {
		super(pos, GameObjectType.ENEMY, type, true);
		this.enemyAttackPattern = enemyAttackPattern;
		this.enemyController = enemyController;
		this.enemyType = enemyType;
		pathIndex = 1;
		isActive = true;
		initVariables();
	}

	public void update(ArrayList<AbsoluteCoordinate> pathAbsoluteCoordinates) {
		if (!enemyController.getPlaying().isPaused()) {
			if (getStun() <= 0) {
				move(pathAbsoluteCoordinates);

				if (counter - getReloadTime() >= 0) {
					isLoaded = true;
					counter = 0;
				} else counter++;

				fire();

			} else {
				currentStun--;
			}
		}
	}

	public void move(ArrayList<AbsoluteCoordinate> pathAbsoluteCoordinates) { //um verschieden Arten von verhaltenn zu ermöglichen
		switch (enemyAttackPattern) {
			case MELEE:
				meleeMove(pathAbsoluteCoordinates);
				break;
			case SACRIFICE:
				sacrificeMove(pathAbsoluteCoordinates);
				break;
			case RANGED:
				rangedMove(pathAbsoluteCoordinates);
				break;
		}
	}

	private void meleeMove(ArrayList<AbsoluteCoordinate> pathAbsoluteCoordinates) {
		if (!isMeleeAttacking) {
			double remainingPath = pathAbsoluteCoordinates.size() - 2 - getPathIndex(); // -2 to stop one step before the gate
			if (getSpeed() < remainingPath && Math.ceil(getPathIndex()) < pathAbsoluteCoordinates.size() - 2) { // adjust to -2
				double index = getPathIndex() + getSpeed();
				index = Math.min(index, pathAbsoluteCoordinates.size() - 2); // adjust to -2
				setPathIndex(index);
				setPos(pathAbsoluteCoordinates.get((int) Math.round(index)));
				range.setPos(pos);
			} else {
				// We are near the gate, start "attacking"
				isMeleeAttacking = true;
			}
		} else {
			// Simulate a simple "bounce" attack animation
			bounceAttack(pathAbsoluteCoordinates);
		}
	}

	private void bounceAttack(ArrayList<AbsoluteCoordinate> pathAbsoluteCoordinates) {
		final double attackSpeed = ObjectValues.Enemies.getEnemyReloadTime(type);
		double incrementPerTick = Constants.ObjectConstants.MELEEATTACKDISTANCE / (attackSpeed * 60);  // since we want to bounce back in the entire duration, divide by 2, i.e., half for forward and half for backward

		double newIndex = isMovingForward ? getPathIndex() + incrementPerTick : getPathIndex() - incrementPerTick;

		// Ensure bounds with pathCoordinates
		if (newIndex < 0) newIndex = 0;
		if (newIndex > pathAbsoluteCoordinates.size() - 1) newIndex = pathAbsoluteCoordinates.size() - 1;

		setPathIndex(newIndex);
		setPos(pathAbsoluteCoordinates.get((int) Math.round(newIndex)));
		range.setPos(pos);

		attackTickCounter++;

		// If we've completed half the attack duration (i.e., moved forward completely),
		// or exceeded pathCoordinates or are less than 0, change direction.
		if (attackTickCounter >= attackSpeed * 60 || newIndex == pathAbsoluteCoordinates.size() - 1 || newIndex == 0) {
			if (isMovingForward) enemyController.enemyMeleeAttackOnGate(this);
			isMovingForward = !isMovingForward;
			attackTickCounter = 0;  // reset the counter for the backward movement
		}
	}


	private void sacrificeMove(ArrayList<AbsoluteCoordinate> pathAbsoluteCoordinates) {
		double remainingPath = pathAbsoluteCoordinates.size() - 1 - getPathIndex();
		if (getSpeed() < remainingPath && Math.ceil(getPathIndex()) < pathAbsoluteCoordinates.size() - 1) {
			double index = getPathIndex() + getSpeed();

			// Ensure index stays within the bounds of pathCoordinates.
			index = Math.min(index, pathAbsoluteCoordinates.size() - 1);

			setPathIndex(index);
			setPos(pathAbsoluteCoordinates.get((int) Math.round(index)));
		} else { // Enemy has reached the gate, implement different behaviors.
			enemyController.enemySacrificeOnGate(this);
		}
	}

	private void rangedMove(ArrayList<AbsoluteCoordinate> pathAbsoluteCoordinates) {
		if (!getRange().contains(enemyController.getPlaying().getTowerController().getGate().getHitBox())) {

			double remainingPath = pathAbsoluteCoordinates.size() - 1 - getPathIndex();
			if (getSpeed() < remainingPath && Math.ceil(getPathIndex()) < pathAbsoluteCoordinates.size() - 1) {
				double index = getPathIndex() + getSpeed();

				// Ensure index stays within the bounds of pathCoordinates.
				index = Math.min(index, pathAbsoluteCoordinates.size() - 1);

				setPathIndex(index);
				AbsoluteCoordinate newPos = pathAbsoluteCoordinates.get((int) Math.round(index));
				setPos(newPos);
				range.setPos(newPos);
			}
			} else {
				target = enemyController.getPlaying().getTowerController().getGate();
				setStatus(true);
		}
	}


	public void fire() {}



	public void initVariables() {
		speed = ObjectValues.Enemies.getEnemySpeed(type)*math.DifficultyMath.calculateEnemySpeedPercentChange();
		range = new Circle(pos, ObjectValues.Enemies.getEnemyRange(type));
		reloadTime = ObjectValues.Enemies.getEnemyReloadTime(type)*math.DifficultyMath.calculateEnemyReloadTimePercentChange();
		damageDealt = ObjectValues.Enemies.getEnemyDamage(type)*math.DifficultyMath.calculateEnemyDamagePercentChange();
		goldReward = ObjectValues.Enemies.getEnemyReward(type)*math.DifficultyMath.calculateEnemyRewardPercentChange();
	}

	public double getPathIndex() {
		return pathIndex;
	}
	public double getSpeed() {
		return speed;
	}
	public double getReloadTime() {
		return reloadTime;
	}
	public void setPathIndex(double i) {
		pathIndex =i;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setStatus(boolean b) {
	isActive = b;
	}
	public void setTarget(GameObject obj) {
		target = obj;
	}
	public GameObject getTarget() {
		return target;
	}

	public double getReward() {
		return goldReward;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Enemy) {
			Enemy enemy = (Enemy) obj;
			return enemy.getPos().equals(pos);
		}
		return false;
	}

	public Circle getRange() {
		return range;
	}

	public EnemyAttackPattern getEnemyAttackPattern() {
		return enemyAttackPattern;
	}

	public EnemyType getEnemyType() {
		return enemyType;
	}
}