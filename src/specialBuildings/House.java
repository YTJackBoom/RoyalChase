package specialBuildings;

import controllers.BuildingsController;
import gameObjects.Building;
import helpers.variables;

import static helpers.variables.Buildings.HOUSE;

public class House extends Building {
    public House(BuildingsController buildingsController, int x, int y) {
        super(buildingsController, x, y, HOUSE);
        buildingsController.getTown().getGame().getPlayerValues().increase(variables.Buildings.getProduction(HOUSE));
    }


    @Override
    public void update() {}
}
