package specialBuildings;

import controllers.BuildingsController;
import gameObjects.Building;
import helpers.AssetLocation;
import helpers.ObjectValues;

import static helpers.ObjectValues.Buildings.HOUSE;

public class House extends Building {
    public House(BuildingsController buildingsController, int x, int y) {
        super(buildingsController, x, y, HOUSE);
        buildingsController.getTown().getGame().getPlayerValues().increase(ObjectValues.Buildings.getProduction(HOUSE));
    }


    @Override
    public void update() {}
}
