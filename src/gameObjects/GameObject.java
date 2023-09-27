package gameObjects;

import controllers.AssetController;
import helpers.*;

import java.awt.*;

public abstract class GameObject {
	protected Animator activeAnimator,passiveAnimator;
	protected GameObjectType gameObjectType;
	protected int type;
	protected AbsoluteCoordinate pos;
	protected double health;
	protected boolean isVisible = true;

	protected HitBox hitBox;
	protected int height, width;

	protected double currentStun = 0;

	public GameObject(AbsoluteCoordinate pos, GameObjectType oType, int type, boolean visibility) {
		isVisible = visibility;
		gameObjectType = oType;
		this.type = type;
		this.pos = pos;
		initAnimators();
		hitBox = new HitBox(this);

		width = activeAnimator.getWidth();
		height = activeAnimator.getHeight();

		health = getMaxHealth();
	}



	private void initAnimators(){
		switch (gameObjectType) {
			case ENEMY -> {
				activeAnimator = AssetController.getInstance().getAnimator("enemyActive_"+type);
				passiveAnimator = AssetController.getInstance().getAnimator("enemyPassive_"+type);
			}
			case TOWER -> {
				activeAnimator = AssetController.getInstance().getAnimator("towerActive_"+type);
				activeAnimator.scaleImages(Constants.UIConstants.TOWERSCALEFACTOR);
//				activeAnimator = new Animator(new File("res/images/towers/active/mage_t_active/normal.gif"));
				passiveAnimator = AssetController.getInstance().getAnimator("towerPassive_"+type);
				passiveAnimator.scaleImages(Constants.UIConstants.TOWERSCALEFACTOR);
			}
			case BUILDING -> {
				activeAnimator = AssetController.getInstance().getAnimator("building_"+type);
			}
			case PROJECTILE -> {
				activeAnimator = AssetController.getInstance().getAnimator("projectile_"+(type-1));
			}
		}
	}

	public void renderHealthBar(Graphics g) {
		double t = getMaxHealth();
		if (health < t) {
			int hpbarx = (int) Math.round(pos.getX() - t / 2);
			g.setColor(Color.RED);
			g.fillRect(hpbarx, pos.getY() - 10 - height / 2, (int) t, 5);

			g.setColor(Color.GREEN);
			g.fillRect(hpbarx, pos.getY() - 10 - height / 2, (int) (t * ((double) health / t)), 5);
		}
	}

	public AbsoluteCoordinate getPos() {
		return pos;
	}


	public Animator getActiveAnimator() {
		return activeAnimator;
	}

	public Animator getPassiveAnimator() {
		return passiveAnimator;
	}

	public void removeAnimators() {
		activeAnimator = null;
		passiveAnimator = null;
	}

	public GameObjectType getObjectType() {
		return gameObjectType;
	}

	public int getType() {
		return type;
	}

	public void setPos(AbsoluteCoordinate pos) {
		AbsoluteCoordinate prePos = this.pos;
		int deltaX = pos.getX() - prePos.getX();
		int deltaY = pos.getY() - prePos.getY();

		Direction dir = activeAnimator.getDirection();

		if (Math.abs(deltaX) > Math.abs(deltaY)) {
			if (deltaX > 0) {
				dir = Direction.RIGHT;
			} else if (deltaX < 0) {
				dir = Direction.LEFT;
			}
		} else {
			if (deltaY > 0) {
				dir = Direction.DOWN;
			} else if (deltaY < 0) {
				dir = Direction.UP;
			}
		}

		passiveAnimator.setDirection(dir);
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

	public void damage(double i) {
		health -= i;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public double getMaxHealth() {
		return switch (gameObjectType) {
			case TOWER -> ObjectValues.Towers.getTowerHealth(type);
			case ENEMY -> ObjectValues.Enemies.getEnemyHealth(type);
			default -> 0.0;
		};

	}
}
