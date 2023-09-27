package gameObjects;

import controllers.BuildingsController;
import helpers.AbsoluteCoordinate;
import helpers.ObjectValues;
import helpers.Values;
import helpers.AssetLocation;

import java.awt.*;

import static basics.Game.ups;

public class Building extends GameObject {
	private int x,y;
	private int counter;
	private int type;
	private BuildingsController buildingsController;
	private Rectangle bounds;
	private Values playerValues;
	public Building(BuildingsController buildingsController, int x, int y, int type ) {
		super(new AbsoluteCoordinate(x, y),GameObjectType.BUILDING, type, true);
		this.x = x;
		this.y = y;
		this.type = type;
		this.buildingsController = buildingsController;
		initVariables();
	}

	public void initVariables() {
		bounds = new Rectangle(x,y,activeAnimator.getWidth(),activeAnimator.getHeight());

		playerValues = buildingsController.getTown().getGame().getPlayerValues();
	}
	public void update() { //coal iro wood stone
		if (counter >=ups) {
			counter = 0;
			playerValues.increase(ObjectValues.Buildings.getProduction(type));
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
	public Rectangle getBounds() {
		return bounds;
	}
}
