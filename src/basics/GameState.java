package basics;

import gameObjects.Building;
import helpers.BuildingSaveState;
import helpers.UserSettings;
import helpers.Values;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable { //diese klasse ist zum speichern des spielstandes nötig, ier werden die spielerwerte soiwe stadt-gebäude(in extra Klssen) abgespeichert
    private Game game;
    public static Values playerValues;
    public static UserSettings userSettings;
    private ArrayList<BuildingSaveState> townBuildingsSave;




    public GameState(Game game) {
        this.game = game;

        playerValues = new Values();
        userSettings = new UserSettings();
        townBuildingsSave = new ArrayList<BuildingSaveState>();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException { //speichert spieler werte und schreib die Buildings in BuildingSaveState um
        oos.writeObject(playerValues);
        oos.writeObject(userSettings);
        for(Building b : game.getTown().getBuildingsController().getBuildingsList()) {
            townBuildingsSave.add(new BuildingSaveState(b.getPos(),b.getType()));
        }
        oos.writeObject(townBuildingsSave);
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException { //liest die spielerwerte und buildings aus der datei
        playerValues = (Values) ois.readObject();
        userSettings = (UserSettings) ois.readObject();
        townBuildingsSave = (ArrayList<BuildingSaveState>) ois.readObject();

    }


    public void setPlayerValues(Values playerValues) {
        this.playerValues = playerValues;
    }

    public Values getPlayerValues() {
        return playerValues;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public ArrayList<BuildingSaveState> getTownBuildingsSave() {
        return townBuildingsSave;
    }


}
