package gameObjects;

import controllers.EnemyController;
import enemy.EnemyAttackPattern;
import enemy.EnemyType;
import helpers.*;

import java.util.ArrayList;

import static basics.Game.fps;

/**
 * Klasse für Gegner
 */
public class Enemy extends GameObject {
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

	/**
	 * Updates für den Gegner, fürs Bewegen und Angriff
	 * @param pathAbsoluteCoordinates Pfad der Gegner
	 */
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
				currentStun--; //Stun reduzierung
			}
		}
	}

	/**
	 * Bewegungsmethode für Gegner
	 * @param pathAbsoluteCoordinates Pfad der Gegner
	 */
	public void move(ArrayList<AbsoluteCoordinate> pathAbsoluteCoordinates) {
		switch (enemyAttackPattern) { //Je nach Angriffsmuster wird eine andere Bewegungsmethode aufgerufen
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

	/**
	 * Bewegungsmethode für Nahkämpfer
	 * @param pathAbsoluteCoordinates Pfad der Gegner
	 */
	private void meleeMove(ArrayList<AbsoluteCoordinate> pathAbsoluteCoordinates) {
		if (!isMeleeAttacking) {
			double remainingPath = pathAbsoluteCoordinates.size() - 2 - getPathIndex(); // -2 um eine coord vor dem tor zu stoppen
			if (getSpeed() < remainingPath && Math.ceil(getPathIndex()) < pathAbsoluteCoordinates.size() - 2) { //Kondition, dass der Gegner noch nicht am tor ist
				double index = getPathIndex() + getSpeed();
				index = Math.min(index, pathAbsoluteCoordinates.size() - 2);
				setPathIndex(index);
				setPos(pathAbsoluteCoordinates.get((int) Math.round(index)));
				range.setPos(pos);
			} else {
				isMeleeAttacking = true; //In der nähe des Tors, Angriff starten
			}
		} else {
			bounceAttack(pathAbsoluteCoordinates);
		}
	}

	/**
	 * Eine simple Angriffs-Methode für Nahkämpfer, die den Gegner vor und zurück bewegt
	 * @param pathAbsoluteCoordinates Pfad der Gegner
	 */
	private void bounceAttack(ArrayList<AbsoluteCoordinate> pathAbsoluteCoordinates) {
		final double attackSpeed = ObjectValues.Enemies.getEnemyReloadTime(type);
		double incrementPerTick = Constants.ObjectConstants.MELEEATTACKDISTANCE / (attackSpeed * fps);  //aufteilen der Gesamtstrecke in einzelne Schritte, von fps abhängig

		double newIndex = isMovingForward ? getPathIndex() + incrementPerTick : getPathIndex() - incrementPerTick; //Berechnung der neuen Position

		if (newIndex < 0) newIndex = 0;
		if (newIndex > pathAbsoluteCoordinates.size() - 1) newIndex = pathAbsoluteCoordinates.size() - 1; //Muss innerhalb des Pfades sein

		setPathIndex(newIndex);
		setPos(pathAbsoluteCoordinates.get((int) Math.round(newIndex)));
		range.setPos(pos);

		attackTickCounter++;


		if (attackTickCounter >= attackSpeed * fps || newIndex == pathAbsoluteCoordinates.size() - 1 || newIndex == 0) { //Ein Angriff wurde "beendet"
			if (isMovingForward) enemyController.enemyMeleeAttackOnGate(this);
			isMovingForward = !isMovingForward;
			attackTickCounter = 0;
		}
	}


	/**
	 * Bewegungsmethode für Gegner, die sich selbst zerstören, wenn sie am tor sind
	 * @param pathAbsoluteCoordinates Pfad der Gegner
	 */
	private void sacrificeMove(ArrayList<AbsoluteCoordinate> pathAbsoluteCoordinates) {
		double remainingPath = pathAbsoluteCoordinates.size() - 1 - getPathIndex();
		if (getSpeed() < remainingPath && Math.ceil(getPathIndex()) < pathAbsoluteCoordinates.size() - 1) {
			double index = getPathIndex() + getSpeed();

			index = Math.min(index, pathAbsoluteCoordinates.size() - 1);

			setPathIndex(index);
			setPos(pathAbsoluteCoordinates.get((int) Math.round(index)));
		} else {
			enemyController.enemySacrificeOnGate(this);
		}
	}

	/**
	 * Bewegungsmethode für Fernkämpfer, sie bleiben stehen sobald das Tor in ihrer Range ist, andere Türme werden im "vorbeigehen" angegriffen
	 * @param pathAbsoluteCoordinates Pfad der Gegner
	 */
	private void rangedMove(ArrayList<AbsoluteCoordinate> pathAbsoluteCoordinates) {
		if (!getRange().contains(enemyController.getPlaying().getTowerController().getGate().getHitBox())) {

			double remainingPath = pathAbsoluteCoordinates.size() - 1 - getPathIndex();
			if (getSpeed() < remainingPath && Math.ceil(getPathIndex()) < pathAbsoluteCoordinates.size() - 1) {
				double index = getPathIndex() + getSpeed();

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
	@Override
	public void setStun(double stun) {
		if (enemyType == EnemyType.BOSS) {
			stun = 0;
		}
		currentStun = stun;
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