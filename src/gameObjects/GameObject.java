package gameObjects;

import helpers.Animator;
import helpers.PreLoader;

import static gameObjects.ObjectType.*;

public abstract class GameObject {
	protected Animator activeAnimator,passiveAnimator,animatorTowerBase;
	protected ObjectType objectType;
	protected int type;

	public GameObject(PreLoader preLoader,ObjectType oType, int type) {
		objectType = oType;
		this.type = type;
		initAnimators(preLoader);
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
}
