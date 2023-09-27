package controllers;

import gameObjects.Building;
import helpers.ObjectValues;
import helpers.Values;
import helpers.AssetLocation;
import scenes.Town;
import specialBuildings.House;

import java.awt.*;
import java.util.ArrayList;

import static helpers.ObjectValues.Buildings.*;

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
	public void initBuildings() { //places buildings in a grid like pattern
		int startX = 50;
		int startY = 50;
		int type = 0;
		int buildingsPerLine =  3;
		int buildingSize = 50;
		int buildingSpacing = 200;
		for (int i = 0; i < buildingsPerLine; i++) {
			for (int j = 0; j < buildingsPerLine; j++) {
				buildingsList.add(new Building(this,startX + (i * (buildingSize + buildingSpacing)), startY + (j * (buildingSize + buildingSpacing)), type));
			}
		}
		buildingsList.add(new Building(this, 900,900,MANAORE));




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

	@Override
	public void render(Graphics g) {
		for (Building building : buildingsList) {
			g.drawImage(building.getActiveAnimator().getCurrentFrame(), building.getX(), building.getY(), null);
		}
	}
	@Override
	public void update() {
		if(!town.isPaused()) {
			for (Building building : buildingsList) {
				building.update();
			}
		}

	}


	public void mouseReleased(int x, int y) {
		for (int i = 0; i < buildingsList.size(); i++) {
			Building building = buildingsList.get(i);

			// Checks if the mouse was released within a building's bounds.
			if (building.getBounds().contains(x, y)) {

				// Check if the building is of type MANAORE to WOODORE
				if (isValidOreType(building.getType())) {

					// Check if the selected building matches the criteria for placement.
					if (town.getSelectedBuilding() == (building.getType() + 4) && building.getType() != 0) {
						handleBuildingPlacement(i);
					}
				}
				// For any other building type, it should be placed on PLACEHOLDER
				else if (building.getType() == PLACEHOLDER && !isValidMineType(town.getSelectedBuilding())) {
					handleBuildingPlacement(i);
				}
			}
		}
	}

	private boolean isValidMineType(int type) {
		return type >= MANA && type <= WOOD;
	}
	private boolean isValidOreType(int type) {
		return type >= MANAORE && type <= WOODORE;
	}

	private void handleBuildingPlacement(int index) {
		Values cost = ObjectValues.Buildings.getCost(town.getSelectedBuilding());

		if (playerValues.canAfford(cost)) {
			playerValues.decrease(cost);
			if (town.getSelectedBuilding() == HOUSE) {  //Seperater check für house da es keine konstante sondern einmalige produktion hat
				buildingsList.set(index, new House(this, buildingsList.get(index).getX(), buildingsList.get(index).getY()));
			}else {
				buildingsList.set(index, new Building(this, buildingsList.get(index).getX(), buildingsList.get(index).getY(), town.getSelectedBuilding()));
			}
			System.out.println("Building placed");
		} else {
			town.setCantAfford(true);
		}
	}
		public ArrayList<Building> getBuildingsList() {
		return buildingsList;
		}
	}
