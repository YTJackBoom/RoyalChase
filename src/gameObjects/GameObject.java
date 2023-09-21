package gameObjects;

import helpers.*;

import java.awt.*;

public abstract class GameObject {
	protected Animator activeAnimator,passiveAnimator;
	protected GameObjectType gameObjectType;
	protected int type;
	protected Coordinate pos;
	protected double health;
	protected boolean isVisible = true;

	protected HitBox hitBox;
	protected int height, width;

	protected double currentStun = 0;

	public GameObject(Coordinate pos, PreLoader preLoader, GameObjectType oType, int type, boolean visibility) {
		isVisible = visibility;
		gameObjectType = oType;
		this.type = type;
		this.pos = pos;
		initAnimators(preLoader);
		hitBox = new HitBox(this);

		width = activeAnimator.getWidth();
		height = activeAnimator.getHeight();

		health = getMaxHealth();
	}



	private void initAnimators(PreLoader preLoader){
		switch (gameObjectType) {
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
				activeAnimator = preLoader.getProjectileAnimator(type-1).clone();
			}
		}
	}

	public void renderHealthBar(Graphics g) {
		double t = getMaxHealth();
		if (health < t) {
			int hpbarx = (int)Math.round(pos.getX() - t / 2);
			g.setColor(Color.RED);
			g.fillRect(hpbarx, pos.getY() - 10 - height / 2, (int)t, 5);

			g.setColor(Color.GREEN);
			g.fillRect(hpbarx, pos.getY() - 10 - height / 2, (int) (t * ((double) health / t)), 5);
		}
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

	public Coordinate getPos() {
		return pos;
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
			case TOWER -> variables.Towers.getTowerHealth(type);
			case ENEMY -> variables.Enemies.getEnemyHealth(type);
			default -> 0.0;
		};

	}
}
