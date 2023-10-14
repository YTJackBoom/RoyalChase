package gameObjects;

import controllers.BuildingsController;
import helpers.*;

import java.awt.*;

import static basics.Game.ups;

/**
 * Klasse für Gebäude in Town
 */
public class Building extends GameObject {
	private int counter;
	private int type;
	private BuildingsController buildingsController;
	private Values playerValues;
	public Building(BuildingsController buildingsController, int x, int y, int type, boolean visibility ) {
		super(new AbsoluteCoordinate(x, y),GameObjectType.BUILDING, type, visibility);
		this.type = type;
		this.buildingsController = buildingsController;
		initVariables();
	}

	public void initVariables() {
		playerValues = buildingsController.getTown().getGame().getPlayerValues();
	}

	/**
	 * Updates für die Gebäude-Produktion
	 */
	public void update() {
		if (counter >=ups) {
			counter = 0;
			playerValues.increase(ObjectValues.Buildings.getProduction(type));
		} else {
			counter++;
		}
	}






	//Getters and Setters
	public int getType() {
		return type;
	}
}
