package controllers;

import gameObjects.Building;
import helpers.math;
import scenes.Town;

import java.awt.*;
import java.util.ArrayList;

import static basics.Game.fps;
import static basics.GameScreen.fHEIGHT;
import static basics.GameScreen.fWIDTH;

public class BuildingsController implements ControllerMethods{
	private int cantAffordCounter;
	private Town town;
	private boolean cantAfford;
	private ArrayList<Building> buildingsList;
	public BuildingsController(Town town) {
		this.town = town;
		initVariables();
		initBuildings();
	}

	public void initVariables() {
		buildingsList = new ArrayList<Building>();
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
			g.drawImage(building.getAnimator().getCurrentImage(), building.getX(), building.getY(), null);
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
			if (building.getBounds().contains(x, y)) {
				if (math.PlayerMath.canAfford(town.getSelectedBuilding(), 1)) {
					math.PlayerMath.deduct(town.getSelectedBuilding(), 1);
					buildingsList.set(i, new Building(this, building.getX(), building.getY(), town.getSelectedBuilding()));
					System.out.println("Building placed");
				}else {
					town.setCantAfford(true);
				}
			}
		}
	}
}
