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

/**
 * Klasse zum speichern des spielstandes, hier werden die spielerwerte, spielereinstellungen soiwe stadt-gebäude(in extra Klssen) abgespeichert
 */
public class GameState implements Serializable {
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

    /**
     * Methode zum Umschreiben und speichern der Stadt-Gebäude in eine seperate Klasse, wird durch saveGame() in Game gecalled
     * @param oos ObjectOutputStream zum schreiben in die Datei
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(playerValues);
        oos.writeObject(userSettings);
        for(Building b : game.getTown().getBuildingsController().getBuildingsList()) {
            townBuildingsSave.add(new BuildingSaveState(b.getPos(),b.getType()));
        }
        oos.writeObject(townBuildingsSave);
    }

    /**
     * Methode zum laden der Klassen aus der Datei, wird durch loadGame() in Game gecalled
     * @param ois ObjectInputStream zum lesen aus der Datei
     */
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException { //liest die spielerwerte und buildings aus der datei
        playerValues = (Values) ois.readObject();
        userSettings = (UserSettings) ois.readObject();
        townBuildingsSave = (ArrayList<BuildingSaveState>) ois.readObject();

    }



    //getter und setter
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
