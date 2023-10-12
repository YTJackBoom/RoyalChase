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
	protected double damageDealt;
	protected boolean isVisible = true;
	protected boolean isActive = true;

	protected HitBox hitBox;

	protected double currentStun = 0;

	public GameObject(AbsoluteCoordinate pos, GameObjectType oType, int type, boolean visibility) {
		isVisible = visibility;
		gameObjectType = oType;
		this.type = type;
		this.pos = pos;
		initAnimators();
		hitBox = new HitBox(this);


		health = getMaxHealth();
	}



	public void initAnimators(){
		switch (gameObjectType) {
			case ENEMY -> {
				activeAnimator = AssetController.getInstance().getAnimator("enemyActive_"+type);
			}
			case TOWER -> {
				activeAnimator = AssetController.getInstance().getAnimator("towerActive_"+type);
				activeAnimator.scaleImages(Constants.UIConstants.TOWERSCALEFACTOR,Constants.UIConstants.TOWERSCALEFACTOR);
//				activeAnimator = new Animator(new File("res/images/towers/active/mage_t_active/normal.gif"));
				passiveAnimator = AssetController.getInstance().getAnimator("towerPassive_"+type);
				passiveAnimator.scaleImages(Constants.UIConstants.TOWERSCALEFACTOR,Constants.UIConstants.TOWERSCALEFACTOR);
			}
			case BUILDING -> {
				activeAnimator = AssetController.getInstance().getAnimator("building_"+type);
			}
			case PROJECTILE -> {
				activeAnimator = AssetController.getInstance().getAnimator("projectile_"+type);
			}
		}
	}

	public void renderHealthBar(Graphics g) {
		double maxWidth = 1.5 * getWidth();
		double t = getMaxHealth();

		if (health < t) {
			double healthRatio = health / t;
			int displayedHealthWidth = (int) (maxWidth * healthRatio);

			int hpbarx = (int) Math.round(pos.getX() - maxWidth / 2);
			int yOffset = gameObjectType == GameObjectType.ENEMY ? 10+getHeight() : 10+getHeight() / 2;

			g.setColor(Color.RED);
			g.fillRect(hpbarx, pos.getY()-yOffset, (int) maxWidth, 5);

			g.setColor(Color.GREEN);
			g.fillRect(hpbarx, pos.getY()-yOffset , displayedHealthWidth, 5);
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
		if (gameObjectType == GameObjectType.BUILDING) {
			this.pos = pos;
			return;
		}
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

		if (passiveAnimator != null) passiveAnimator.setDirection(dir);
		activeAnimator.setDirection(dir);
		this.pos = pos;
	}
	public void render(Graphics g) {
		if (isVisible) {
			if(isActive){
				int x = pos.getX()- activeAnimator.getWidth()/2;
				int y = gameObjectType == GameObjectType.ENEMY ? pos.getY()-activeAnimator.getHeight() : pos.getY()-activeAnimator.getHeight()/2;
				g.drawImage(activeAnimator.getCurrentFrame(), x, y, null);
				activeAnimator.incrementFrame();
			}else {
				int x = pos.getX()- passiveAnimator.getWidth()/2;
				int y = gameObjectType == GameObjectType.ENEMY ? pos.getY()-activeAnimator.getHeight() : pos.getY()-activeAnimator.getHeight()/2;
				g.drawImage(passiveAnimator.getCurrentFrame(), x, y, null);
				passiveAnimator.incrementFrame();
			}
		}
	}

	public HitBox getHitBox() {
		return hitBox;
	}

	public double getHealth() {
		return health;
	}

	public void setStun(double stun) {
		currentStun = stun;
    }

	public double getStun() {
		return currentStun;
	}

	public double getDamageDealt() {
		return damageDealt;
	}

	public void damage(double i) {
		health -= i;
	}

	public int getWidth() {
		return activeAnimator.getWidth();
	}

	public int getHeight() {
		return activeAnimator.getHeight();
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean visible) {
		isVisible = visible;
	}

	public double getMaxHealth() {
		return switch (gameObjectType) {
			case TOWER -> ObjectValues.Towers.getTowerHealth(type)*math.DifficultyMath.calculateTowerHealthPercentChange();
			case ENEMY -> ObjectValues.Enemies.getEnemyHealth(type)*math.DifficultyMath.calculateEnemyHealthPercentChange();
			default -> 0.0;
		};

	}
}
