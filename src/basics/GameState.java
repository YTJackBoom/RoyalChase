package basics;

import controllers.BuildingsController;
import gameObjects.Building;
import helpers.BuildingSaveState;
import helpers.Coordinate;
import helpers.Values;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    private Game game;
    private Values playerValues;
    private ArrayList<BuildingSaveState> townBuildingsSave;




    public GameState(Game game) {
        this.game = game;

        playerValues = new Values();
        townBuildingsSave = new ArrayList<BuildingSaveState>();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(playerValues);
        for(Building b : game.getTown().getBuildingsController().getBuildingsList()) {
            townBuildingsSave.add(new BuildingSaveState(b.getPos(),b.getType()));
        }
        oos.writeObject(townBuildingsSave);
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        playerValues =(Values) ois.readObject();
        townBuildingsSave = (ArrayList<BuildingSaveState>) ois.readObject();

//        ois.defaultReadObject(); // This will deserialize the fields as usual
        // Handle the `nonSerializableData` field manually
//        BuildingsController buildingsController = this.game.getTown().getBuildingsController();
//        int i = 0;
//        for (BuildingSaveState b : townBuildingsSave) {
//            Town.buildingsList.setBuilding(i,new Building(buildingsController,b.getPos().getX(), b.getPos().getY(),b.getType()));
//            i++;
//        }
    }






    public void setPlayerValues(Values playerValues) {
        this.playerValues = playerValues;
    }

    public Values getPlayerValues() {
        return playerValues;
    }

    public ArrayList<BuildingSaveState> getTownBuildingsSave() {
        return townBuildingsSave;
    }



}
