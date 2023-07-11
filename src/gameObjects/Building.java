package gameObjects;

import controllers.BuildingsController;
import helpers.Animator;
import helpers.PreLoader;
import helpers.variables;

import java.awt.*;

import static basics.Game.fps;
import static basics.Game.ups;
import static helpers.Values.*;

public class Building extends GameObject{
	private int x,y;
	private int counter;
	private int type;
	private BuildingsController buildingsController;
	private Animator animator;
	private Rectangle bounds;
	public Building(BuildingsController buildingsController, int x, int y, int type ) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.buildingsController = buildingsController;
		initVariables();
	}

	public void initVariables() {
		PreLoader preLoader = buildingsController.getTown().getGame().getPreLoader();
		animator = preLoader.getBuildingAnimator(type*-1);
		bounds = new Rectangle(x,y,animator.getWidth(),animator.getHeight());
	}
	public void update() { //coal iro wood stone
		if (counter >=ups) {
			counter = 0;
			MANA += variables.Buildings.getManaProduction(type) * REWARDMULTIPLYER;
			WOOD += variables.Buildings.getWoodProduction(type) * REWARDMULTIPLYER;
			IRON += variables.Buildings.getIronProduction(type) * REWARDMULTIPLYER;
			STONE += variables.Buildings.getStoneProduction(type) * REWARDMULTIPLYER;
		} else {
			counter++;
		}
	}






	//Getters and Setters
	public int getX(){
		return x;
	}
	public int getY() {
		return y;
	}
	public int getType() {
		return type;
	}
	public Animator getAnimator() {
		return animator;
	}
	public Rectangle getBounds() {
		return bounds;
	}
}
