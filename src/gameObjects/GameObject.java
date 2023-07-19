package gameObjects;

import helpers.Animator;
import helpers.Coordinate;
import helpers.HitBox;
import helpers.PreLoader;

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
				activeAnimator = preLoader.getEnemyActiveAnimator(type);
				passiveAnimator = preLoader.getEnemyPassiveAnimator(type);
			}
			case TOWER -> {
				activeAnimator = preLoader.getTowerActiveAnimator(type);
				passiveAnimator = preLoader.getTowerPassiveAnimator(type);
				animatorTowerBase = preLoader.getTowerBaseAnimator(type);
			}
			case BUILDING -> {
				activeAnimator = preLoader.getBuildingAnimator(type);
			}
			case PROJECTILE -> {
				activeAnimator = preLoader.getProjectileAnimator(type);
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
		this.pos = pos;
	}

	public HitBox getHitBox() {
		return hitBox;
	}
}
