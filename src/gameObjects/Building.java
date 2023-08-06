package gameObjects;

import controllers.BuildingsController;
import helpers.*;

import java.awt.*;
import java.io.Serializable;

import static basics.Game.fps;
import static basics.Game.ups;
import static helpers.Values.*;

public class Building extends GameObject {
	private int x,y;
	private int counter;
	private int type;
	private BuildingsController buildingsController;
//	private Animator animator;
	private Rectangle bounds;
	private Values playerValues;
	public Building(BuildingsController buildingsController, int x, int y, int type ) {
		super(new Coordinate(x,y),buildingsController.getTown().getGame().getPreLoader(), ObjectType.BUILDING,type);
		this.x = x;
		this.y = y;
		this.type = type;
		this.buildingsController = buildingsController;
		initVariables();
	}

	public void initVariables() {
		PreLoader preLoader = buildingsController.getTown().getGame().getPreLoader();
//		animator = preLoader.getBuildingAnimator(type);
		bounds = new Rectangle(x,y,activeAnimator.getWidth(),activeAnimator.getHeight());

		playerValues = buildingsController.getTown().getGame().getPlayerValues();
	}
	public void update() { //coal iro wood stone
		if (counter >=ups) {
			counter = 0;
			playerValues.increase(variables.Buildings.getProduction(type));
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
//	public Animator getAnimator() {
//		return animator;
//	}
	public Rectangle getBounds() {
		return bounds;
	}
}
