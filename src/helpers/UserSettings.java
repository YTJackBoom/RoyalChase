package helpers;

import java.io.Serializable;
/**
 * Klasse um die Einstellungen des Spielers zu speichern
 */
public class UserSettings implements Serializable {
    protected int DIFFICULTY = 1;
    protected int VOLUME = 100;

    public void setDifficulty(int difficulty) {
        DIFFICULTY = difficulty;
    }

    public void setVolume(int volume) {
        VOLUME = volume;
    }

    public int getDifficulty() {
        return DIFFICULTY;
    }

    public int getVolume() {
        return VOLUME;
    }


}
