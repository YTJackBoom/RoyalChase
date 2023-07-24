package gameObjects;

import basics.Direction;
import helpers.*;

import static gameObjects.ObjectType.*;

public abstract class GameObject {
	protected Animator activeAnimator,passiveAnimator,animatorTowerBase;
	protected ObjectType objectType;
	protected int type;
	protected Coordinate pos;

	protected HitBox hitBox;

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
				passiveAnimator = preLoader.getTowerPassiveAnimator(type).clone();
				animatorTowerBase = preLoader.getTowerBaseAnimator(type).clone();
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
	public Animator getAnimatorTowerBase(){
		return animatorTowerBase;
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
		Direction dir = Direction.NORMAL;
		if(prePos.getX() < pos.getX()&&prePos.getY() > pos.getY()) {
			dir = Direction.NORMAL;
		}
		if(prePos.getX() > pos.getX()&&prePos.getY() < pos.getY()) {
			dir = Direction.DOWN;
		}
		if(prePos.getX() < pos.getX()&&Math.abs(prePos.getY() - pos.getY()) < Constants.ObjectConstants.MAXYCHANGEFORRIGHTORLEFTMOVEMENT) {
			dir = Direction.RIGHT;
		}
		if(prePos.getX() > pos.getX()&&Math.abs(prePos.getY() - pos.getY()) < Constants.ObjectConstants.MAXYCHANGEFORRIGHTORLEFTMOVEMENT) {
			dir = Direction.LEFT;
		}
		activeAnimator.setDirection(dir);
		this.pos = pos;
	}

	public HitBox getHitBox() {
		return hitBox;
	}
}
