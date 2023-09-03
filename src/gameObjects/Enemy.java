package gameObjects;

import controllers.EnemyController;
import enemy.EnemyType;
import helpers.Circle;
import helpers.Constants;
import helpers.Coordinate;
import helpers.variables;

import java.util.ArrayList;

public class Enemy extends GameObject {
	//	private Coordinate pos;
	private double pathIndex;
	private double speed;
	protected boolean isActive;
	protected boolean isMeleeAttacking,isMovingForward; //Für melee angriffe, die  derzeitige Richtung der Bewegung im Angriff und ob der Angriff gerade ausgeführt wird
	protected EnemyController enemyController;
	protected GameObject target;
	protected Circle range;
	protected EnemyType enemyType;
	private int attackTickCounter = 0;



	public Enemy(EnemyController enemyController, Coordinate pos, int type, EnemyType enemyType) {
		super(pos, enemyController.getPlaying().getGame().getPreLoader(), ObjectType.ENEMY, type, true);
		this.enemyType = enemyType;
		this.enemyController = enemyController;
		pathIndex = 1;
		isActive = true;
		initVariables();
	}

	public void update(ArrayList<Coordinate> pathCoordinates) {
		if (!enemyController.getPlaying().isPaused()) {
			if (getStun() <= 0) {
				move(pathCoordinates);
				fire();

			} else {
				currentStun--;
			}
		}
	}

	public void move(ArrayList<Coordinate> pathCoordinates) { //um verschieden Arten von verhaltenn zu ermöglichen
		switch (enemyType) {
			case MELEE:
				meleeMove(pathCoordinates);
				break;
			case SACRIFICE:
				sacrificeMove(pathCoordinates);
				break;
			case RANGED:
				rangedMove(pathCoordinates);
				break;
		}
	}

	private void meleeMove(ArrayList<Coordinate> pathCoordinates) {
		if (!isMeleeAttacking) {
			double remainingPath = pathCoordinates.size() - 2 - getPathIndex(); // -2 to stop one step before the gate
			if (getSpeed() < remainingPath && Math.ceil(getPathIndex()) < pathCoordinates.size() - 2) { // adjust to -2
				double index = getPathIndex() + getSpeed();
				index = Math.min(index, pathCoordinates.size() - 2); // adjust to -2
				setPathIndex(index);
				setPos(pathCoordinates.get((int) Math.round(index)));
				range.setPos(pos);
			} else {
				// We are near the gate, start "attacking"
				isMeleeAttacking = true;
			}
		} else {
			// Simulate a simple "bounce" attack animation
			bounceAttack(pathCoordinates);
		}
	}

	private void bounceAttack(ArrayList<Coordinate> pathCoordinates) {
		final double attackSpeed = variables.Enemies.getEnemyReloadTime(type);
		double incrementPerTick = Constants.ObjectConstants.MELEEATTACKDISTANCE / (attackSpeed * 60);  // since we want to bounce back in the entire duration, divide by 2, i.e., half for forward and half for backward

		double newIndex = isMovingForward ? getPathIndex() + incrementPerTick : getPathIndex() - incrementPerTick;

		// Ensure bounds with pathCoordinates
		if (newIndex < 0) newIndex = 0;
		if (newIndex > pathCoordinates.size() - 1) newIndex = pathCoordinates.size() - 1;

		setPathIndex(newIndex);
		setPos(pathCoordinates.get((int) Math.round(newIndex)));
		range.setPos(pos);

		attackTickCounter++;

		// If we've completed half the attack duration (i.e., moved forward completely),
		// or exceeded pathCoordinates or are less than 0, change direction.
		if (attackTickCounter >= attackSpeed * 60 || newIndex == pathCoordinates.size() - 1 || newIndex == 0) {
			if(isMovingForward) enemyController.enemyMeleeAttackOnGate(this);
			isMovingForward = !isMovingForward;
			attackTickCounter = 0;  // reset the counter for the backward movement
		}
	}



	private void sacrificeMove(ArrayList<Coordinate> pathCoordinates) {
			double remainingPath = pathCoordinates.size() - 1 - getPathIndex();
			if (getSpeed() < remainingPath && Math.ceil(getPathIndex()) < pathCoordinates.size() - 1) {
				double index = getPathIndex() + getSpeed();

				// Ensure index stays within the bounds of pathCoordinates.
				index = Math.min(index, pathCoordinates.size() - 1);

				setPathIndex(index);
				setPos(pathCoordinates.get((int) Math.round(index)));
			} else { // Enemy has reached the gate, implement different behaviors.
				enemyController.enemySacrificeOnGate(this);
			}
	}
	private void rangedMove(ArrayList<Coordinate> pathCoordinates) {
		if (!getRange().contains(enemyController.getPlaying().getTowerController().getGate().getHitBox())) {

			double remainingPath = pathCoordinates.size() - 1 - getPathIndex();
			if (getSpeed() < remainingPath && Math.ceil(getPathIndex()) < pathCoordinates.size() - 1) {
				double index = getPathIndex() + getSpeed();

				// Ensure index stays within the bounds of pathCoordinates.
				index = Math.min(index, pathCoordinates.size() - 1);

				setPathIndex(index);
				Coordinate newPos = pathCoordinates.get((int) Math.round(index));
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
		speed = variables.Enemies.getEnemySpeed(type);
		range = new Circle(pos,variables.Enemies.getEnemyRange(type));
	}

	public double getPathIndex() {
		return pathIndex;
	}
	public double getSpeed() {
		return speed;
	}
	public double getReloadTime() {
		return variables.Enemies.getEnemyReloadTime(type);
	}
	public void setPathIndex(double i) {
		pathIndex =i;
	}
	public double getHealth() {
		return health;
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

	public int getReward() {
		return variables.Enemies.getEnemyReward(type);
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

	public EnemyType getEnemyType() {
		return enemyType;
	}
}