package controllers;

import gameObjects.Building;
import helpers.AbsoluteCoordinate;
import helpers.AssetLocation;
import helpers.ObjectValues;
import helpers.Values;
import scenes.Town;
import specialBuildings.House;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static basics.Game.*;
import static helpers.ObjectValues.Buildings.*;

/**
 * Controller Klasse für die Gebäude in der Stadt
 */
public class BuildingsController implements ControllerMethods{
	private Town town;
	private ArrayList<Building> buildingsList;
	private Values playerValues;
	private int counterForBackGroundProduction;
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
		initPlayeHolders();
		initMines();
		initWood();
	}

	private void initPlayeHolders() {
		int startX = 200; // Spots for Buildings
		int startY = 250;
		int buildingsPerLine = 3;
		int numberOfLines = 5;
		int buildingSize = 50;
		int buildingSpacing = 75;
		for (int i = 0; i < numberOfLines; i++) {
			for (int j = 0; j < buildingsPerLine; j++) {
				buildingsList.add(new Building(this, startX + (j * (buildingSize + buildingSpacing)), startY + (i * (buildingSize + buildingSpacing)), PLACEHOLDER, true));
			}
		}
	}
	private void initMines() {
		int startX = 1390;
		int startY = 225;
		int buildingsPerLine;
		int numberOfLines = 5; // for the cone pattern
		int buildingSize = 50;
		int buildingSpacing = 100;

		int totalBuildings = 0; //Bestimmen der gesamtzahl an gebäuden
		for (int i = 0; i < numberOfLines; i++) {
			totalBuildings += (i < numberOfLines / 2) ? (i + 1) : (numberOfLines - i);
		}

		ArrayList<Integer> typesList = new ArrayList<>();
		for (int i = 0; i < totalBuildings; i++) { //Vorgefertigte liste, für gleiche verteilung der 3 Minen
			typesList.add((i % 3) + 1);
		}
		Collections.shuffle(typesList);


		int currentTypeIndex = 0;
		for (int i = 0; i < numberOfLines; i++) {
			if (i < numberOfLines / 2) {
				buildingsPerLine = i + 1;
			} else {
				buildingsPerLine = numberOfLines - i;
			}

			for (int j = 0; j < buildingsPerLine; j++) {
				int type = typesList.get(currentTypeIndex);
				currentTypeIndex++;

				int xPosition = startX + (j * (buildingSize + buildingSpacing)) - (buildingsPerLine - 1) * (buildingSize + buildingSpacing) / 2;
				int yPosition = startY + (i * (buildingSize + buildingSpacing));
				buildingsList.add(new Building(this, xPosition, yPosition, type, true));
			}
		}
	}

	private void initWood() {
		int startX = 775;
		int startY = 380;
		int buildingsPerLine = 3;
		int numberOfLines = 3;
		int buildingSize = 50;
		int buildingSpacing = 75;
		for (int i = 0; i < numberOfLines; i++) {
			for (int j = 0; j < buildingsPerLine; j++) {
				buildingsList.add(new Building(this, startX + (j * (buildingSize + buildingSpacing)), startY + (i * (buildingSize + buildingSpacing)), WOOD_ORE, true));
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
			if (counterForBackGroundProduction >= ups) {
				playerValues.increase(new Values(0, 0.5, 0.5, 0.5, 0.5, 0.5));
				counterForBackGroundProduction = 0;
			} else counterForBackGroundProduction++;
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

			SoundController.getInstance().playSoundEffect("otherSounds_2");
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
				int newX =  prevBuilding.getPos().getX();
				int newY = prevBuilding.getPos().getY();
				AbsoluteCoordinate pos = new AbsoluteCoordinate(newX, newY);
				newBuilding.setPos(pos);
				if(!(fWIDTH == initGameWidth && fHEIGHT == initGameHeight))	newBuilding.getActiveAnimator().notifyScreenResize(fWIDTH, fHEIGHT);
				buildingsList.set(index, newBuilding);
				SoundController.getInstance().playSoundEffect("otherSounds_2");


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
		notifyBuildingPosUpdate(width,height);
	}

	public void notifyBuildingPosUpdate(int width, int height) {
		double xChange = (double) width / prevfWIDTH;
		double yChange = (double) height / prevfHEIGHT;
		for (Building building : buildingsList) {
			AbsoluteCoordinate pos = building.getPos();
			pos.setX((int) Math.round(pos.getX()*xChange));
			pos.setY((int) Math.round(pos.getY()*yChange));
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
