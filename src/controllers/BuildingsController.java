package controllers;

import gameObjects.Building;
import gameObjects.ObjectType;
import helpers.Values;
import helpers.math;
import helpers.variables;
import scenes.Town;

import java.awt.*;
import java.util.ArrayList;

import static basics.Game.fps;
import static basics.GameScreen.fHEIGHT;
import static basics.GameScreen.fWIDTH;
import static helpers.variables.Buildings.*;

public class BuildingsController implements ControllerMethods{
	private int cantAffordCounter;
	private Town town;
	private boolean cantAfford;
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
			buildingsList.add(new Building(this, 800,800,MANAORE));
		}



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
			g.drawImage(building.getActiveAnimator().getCurrentImage(), building.getX(), building.getY(), null);
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

//		System.out.println("s");
		for (int i = 0; i < buildingsList.size(); i++) {
			Building building = buildingsList.get(i);
			if (building.getBounds().contains(x, y) && (building.getType() >= MANAORE && building.getType() <= WOODORE)) {
				if (town.getSelectedBuilding() == (building.getType()+4) && building.getType()!=0) {
					Values cost = variables.Buildings.getCost(town.getSelectedBuilding());
					if (playerValues.canAfford(cost)) {
						playerValues.decrease(cost);
						buildingsList.set(i, new Building(this, building.getX(), building.getY(), town.getSelectedBuilding()));
						System.out.println("Building placed");
					} else {
						town.setCantAfford(true);
					}
				}
			}
		}
		}
	}
