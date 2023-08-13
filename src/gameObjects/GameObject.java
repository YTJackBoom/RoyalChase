package gameObjects;

import basics.Direction;
import helpers.*;

import java.io.File;

import static gameObjects.ObjectType.*;

public abstract class GameObject {
	protected Animator activeAnimator,passiveAnimator;
	protected ObjectType objectType;
	protected int type;
	protected Coordinate pos;

	protected HitBox hitBox;

	protected double currentStun =0;

	public GameObject(Coordinate pos,PreLoader preLoader, ObjectType oType, int type) {
		objectType = oType;
		this.type = type;
		this.pos = pos;
		initAnimators(preLoader);
		hitBox = new HitBox(this);
	}



	private void initAnimators(PreLoader preLoader){
		switch (objectType) {
			case ENEMY -> {
				activeAnimator = preLoader.getEnemyActiveAnimator(type).clone();
				passiveAnimator = preLoader.getEnemyPassiveAnimator(type).clone();
			}
			case TOWER -> {
				activeAnimator = preLoader.getTowerActiveAnimator(type).clone();
				activeAnimator.scaleImages(Constants.UIConstants.TOWERSCALEFACTOR);
//				activeAnimator = new Animator(new File("res/images/towers/active/mage_t_active/normal.gif"));
				passiveAnimator = preLoader.getTowerPassiveAnimator(type).clone();
				passiveAnimator.scaleImages(Constants.UIConstants.TOWERSCALEFACTOR);
			}
			case BUILDING -> {
				activeAnimator = preLoader.getBuildingAnimator(type).clone();
			}
			case PROJECTILE -> {
				activeAnimator = preLoader.getProjectileAnimator(type).clone();
			}
		}
	}

	public Animator getActiveAnimator(){
		return activeAnimator;
	}
	public Animator getPassiveAnimator() {
		return passiveAnimator;
	}
	public void removeAnimators() {
		activeAnimator = null;
		passiveAnimator = null;
	}
	public ObjectType getObjectType() {
		return objectType;
	}
	public int getType() {
		return type;
	}
	public Coordinate getPos() {
		return pos;
	}
	public void setPos(Coordinate pos) {
		Coordinate prePos = this.pos;
		int deltaX = pos.getX() - prePos.getX();
		int deltaY = pos.getY() - prePos.getY();

		Direction dir = activeAnimator.getDirection();

		if (Math.abs(deltaX) > Math.abs(deltaY)) {
			if (deltaX > 0) {
				dir = Direction.RIGHT;
			} else if (deltaX < 0) {
				dir = Direction.LEFT;
			} // If deltaX is 0, it means the object hasn't moved horizontally.
		} else {
			if (deltaY > 0) {
				dir = Direction.DOWN;
			} else if (deltaY < 0) {
				dir = Direction.UP;
			} // If deltaY is 0, it means the object hasn't moved vertically.
		}

		activeAnimator.setDirection(dir);
		this.pos = pos;
	}

	public HitBox getHitBox() {
		return hitBox;
	}

    public void setStun(double stun) {
		currentStun = stun;
    }

	public double getStun() {
		return currentStun;
	}
}
