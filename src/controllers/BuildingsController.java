package controllers;

import gameObjects.Building;
import helpers.AbsoluteCoordinate;
import helpers.ObjectValues;
import helpers.Values;
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

	@Override
	public void render(Graphics g) {
		for (Building building : buildingsList) {
			g.drawImage(building.getActiveAnimator().getCurrentFrame(), building.getPos().getX(), building.getPos().getY(), null);
			building.getActiveAnimator().incrementFrame();
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
			if (building.getBounds().contains(x,y)) {
				if (isValidOreType(building.getType())&&building.getType() != PLACEHOLDER) {
					handleMineBuildingPlacement(i);
				} else if (building.getType() == PLACEHOLDER) {
					handleHosueBuildingPlacement(i);
				}
			}
			}
		}

	private boolean isValidOreType(int type) {
		return type >= MANA_ORE && type <= WOOD_ORE;
	}

	private void handleHosueBuildingPlacement(int index) {
		Values cost = ObjectValues.Buildings.getCost(town.getSelectedBuilding());

		if (playerValues.canAfford(cost)) {
			playerValues.decrease(cost);
			Building prevBuilding = buildingsList.get(index);

			buildingsList.set(index, new House(this, prevBuilding.getPos().getX(), prevBuilding.getPos().getY(),true));

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
				buildingsList.set(index, newBuilding);


			System.out.println("Building placed");
		} else {
			town.setCantAfford(true);
		}
	}
		public ArrayList<Building> getBuildingsList() {
		return buildingsList;
		}
	}
