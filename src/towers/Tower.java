package towers;

import basics.Animator;
import controllers.TowerController;
import helpers.Coordinate;
import enemy.Enemy;
import helpers.variables;
import uiElements.PlacementMenu;

import java.awt.*;

public class Tower  {
	protected Animator passiveAnimator, activeAnimator;
	private  boolean menuOpen;
	private Coordinate pos;
	private PlacementMenu[] pMenu;
	protected int range;
	private Enemy target;
	private boolean isActive;
	private int type;
	private int width, height;
	private Rectangle bounds;
	

			
	public Tower(TowerController towerController, Coordinate pos, int type) {
		this.type = type;
		this.pos = pos;
		initAnimators();
		initVariables();
		initBounds();


	//	initPlacementMenu();
	}


	public void update() {
		System.out.println("3");
	}


	public void initAnimators() {
		passiveAnimator = new Animator(variables.Towers.getTowerPassiveGifFile(type));
		activeAnimator = new Animator(variables.Towers.getTowerActiveGifFile(type));
	}
	public void initVariables() {
		range = variables.Towers.getRange(type);
		this.height = activeAnimator.getHeight();
		this.width = activeAnimator.getWidth();
	}
	public void initBounds() {
			bounds = new Rectangle(pos.getX(),pos.getY(),width,height);
	}
	/*public void initPlacementMenu() {
		pMenu = new PlacementMenu[4];
		pMenu[0] = new PlacementMenu(gS,1,new File("res/images/black square small.png"), new Coordinate(pos.getX()-40, pos.getY()),tEL,aPos,this);
		pMenu[1] = new PlacementMenu(gS,2,new File("res/images/black square small.png"), new Coordinate(pos.getX()-85, pos.getY()),tEL,aPos,this);
		pMenu[2] = new PlacementMenu(gS,3,new File("res/images/black square small.png"), new Coordinate(pos.getX()-130, pos.getY()),tEL,aPos,this);
		pMenu[3] = new PlacementMenu(gS,4,new File("res/images/black square small.png"), new Coordinate(pos.getX()-175, pos.getY()),tEL,aPos,this);
	}
	public void openPlacementMenu() {
		pMenu[0].setVisible(true);
		pMenu[1].setVisible(true);
		pMenu[2].setVisible(true);
		pMenu[3].setVisible(true);
		
	}
	public void closePlacementMenu() {
		pMenu[0].setVisible(false);
		pMenu[1].setVisible(false);
		pMenu[2].setVisible(false);
		pMenu[3].setVisible(false);
	}
	
	
	public void vanish() {
		for(int i =0; i<=pMenu.length-1;i++) {
			pMenu[i].vanish();
		}
		this.gS.remove(tLabel);
		tLabel = null;
	}
	public int getRange() {
		return range;
	}
	*/
	public void setStatus(boolean status) {
		isActive = status;
	}

	public void setTarget(Enemy target) {
		this.target = target;
	}

	public Coordinate getPos() {
		return pos;
	}

	public boolean isActive() {
		return isActive;
	}

	public Animator getActiveAnimator() {
		return activeAnimator;
	}

	public Animator getPassiveAnimator() {
		return passiveAnimator;
	}

	public int getType() {
		return type;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public Enemy getTarget() {
		return target;
	}

}
