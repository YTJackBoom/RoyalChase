package controllers;

import gameObjects.Building;
import helpers.AbsoluteCoordinate;
import helpers.ObjectValues;
import helpers.Values;
import scenes.Town;
import specialBuildings.House;

import java.awt.*;
import java.util.ArrayList;

import static basics.Game.*;
import static helpers.ObjectValues.Buildings.*;

/**
 * Controller Klasse für die Gebäude in der Stadt
 */
public class BuildingsController implements ControllerMethods{
	private Town town;
	private ArrayList<Building> buildingsList;
	private Values playerValues;
	public BuildingsController(Town town) {
		this.town = town;
		initVariables();
		initBuildings();
	}



	public void initVariables() {
		buildingsList = new ArrayList<Building>();
		playerValues = town.getGame().getPlayerValues();
	}

	/**
	 * Methode, um Erze und Haus-Plätze an vordefinierten Orten zu initialisieren
	 */
	public void initBuildings() {
		int startX = 50;
		int startY = 50;
		int type = 0;
		int buildingsPerLine =  3;
		int buildingSize = 50;
		int buildingSpacing = 200;
		for (int i = 0; i < buildingsPerLine; i++) { //Haus-Plätze in einem Grid-Pattern
			for (int j = 0; j < buildingsPerLine; j++) {
				buildingsList.add(new Building(this,startX + (i * (buildingSize + buildingSpacing)), startY + (j * (buildingSize + buildingSpacing)), type,true));
			}
		}
		buildingsList.add(new Building(this, 900,900, MANA_ORE,true));
		buildingsList.add(new Building(this, 900,800, IRON_ORE,true));
		buildingsList.add(new Building(this, 900,700, STONE_ORE,true));
		buildingsList.add(new Building(this, 900,600, WOOD_ORE,true));



	}
	public Town getTown() {
		return town;
	}

	@Override
	public void workAddQueue() {

	}

	@Override
	public void workRemoveQueue() {

	}

	/**
	 * Methode zum rendern der Gebäude
	 * @param g Graphics Objekt
	 */
	@Override
	public void render(Graphics g) {
		for (Building building : buildingsList) {
			building.render(g);
		}
	}
	/**
	 * Methode zum updaten der Gebäude
	 */
	@Override
	public void update() {
		if(!town.isPaused()) {
			for (Building building : buildingsList) {
				building.update();
			}
		}

	}

	/**
	 * Mouse-Released Methode, um Gebäude zu platzieren
	 * @param x-Coordinate der Maus
	 * @param y-Coordinate der Maus
	 */
	public void mouseReleased(int x, int y) {
		Building tempBuilding = new Building(this, x, y, town.getSelectedBuilding(),false);

		for (int i = 0; i < buildingsList.size(); i++) {
			Building building = buildingsList.get(i);
			if (tempBuilding.getHitBox().collidesWith(building.getHitBox())) {
				if (isValidOreType(building.getType())&&town.getSelectedBuilding() == MINER) { //Differenzieruing zwichen Minen(auch Holz) und Häusern
					handleMineBuildingPlacement(i);
				} else if (building.getType() == PLACEHOLDER&&town.getSelectedBuilding() == HOUSE) {
					handleHosueBuildingPlacement(i);
				} else if (building.getType() == WOOD_ORE && town.getSelectedBuilding() == WOOD_MINER ) {
					handleMineBuildingPlacement(i);
				}
			}
			}
		}

	private boolean isValidOreType(int type) {
		return type >= MANA_ORE && type <= STONE_ORE;
	}

	private void handleHosueBuildingPlacement(int index) {
		Values cost = ObjectValues.Buildings.getCost(town.getSelectedBuilding());

		if (playerValues.canAfford(cost)) {
			playerValues.decrease(cost);
			Building prevBuilding = buildingsList.get(index);

			House newBuilding = new House(this, prevBuilding.getPos().getX(), prevBuilding.getPos().getY(),true);
			if(!(fWIDTH == initGameWidth && fHEIGHT == initGameHeight)) newBuilding.getActiveAnimator().notifyScreenResize(fWIDTH, fHEIGHT);
			buildingsList.set(index, newBuilding);

			System.out.println("Building placed");
		} else {
			town.setCantAfford(true);
		}
	}
	private void handleMineBuildingPlacement(int index) {
		Values cost = ObjectValues.Buildings.getCost(town.getSelectedBuilding());

		if (playerValues.canAfford(cost)) {
			playerValues.decrease(cost);

			Building prevBuilding = buildingsList.get(index);
				Building newBuilding = new Building(this, 500, 500, prevBuilding.getType()+4,true);
				AbsoluteCoordinate pos = new AbsoluteCoordinate(prevBuilding.getPos().getX()+(prevBuilding.getWidth()-newBuilding.getWidth()), prevBuilding.getPos().getY()+ (prevBuilding.getHeight()-newBuilding.getHeight()));
				newBuilding.setPos(pos);
				if(!(fWIDTH == initGameWidth && fHEIGHT == initGameHeight))	newBuilding.getActiveAnimator().notifyScreenResize(fWIDTH, fHEIGHT);
				buildingsList.set(index, newBuilding);


			System.out.println("Building placed");
		} else {
			town.setCantAfford(true);
		}
	}
		public ArrayList<Building> getBuildingsList() {
		return buildingsList;
		}

	/**
	 * Methode zum updaten der Gebäude-Animator-Größen
	 * @param width Bildschirmbreite
	 * @param height Bildschirmhöhe
	 */
	public void notifyScreenResize(int width, int height) {
		for (Building building : buildingsList) {
			building.getActiveAnimator().notifyScreenResize(width, height);
		}
	}

	/**
	 * Methode um die Gebäude-Animatoren zu resetten, diese wird bei einem Resize auf original-Größe gecalled, verwebndet um starke Verzerrungen zu vermeiden
	 */
	public void flushImages() {
		for (Building building : buildingsList) {
			building.initAnimators();
		}
	}
}
