package helpers;

import java.io.Serializable;
/**
 * Klasse um die Einstellungen des Spielers zu speichern
 */
public class UserSettings implements Serializable {
    protected int DIFFICULTY = 1;
    protected int EFFECTVOLUME = 30;
    protected int BACKGROUNDVOLUME = 30;


    public void setDifficulty(int difficulty) {
        DIFFICULTY = difficulty;
    }

    public void setEffectVolume(int volume) {
        EFFECTVOLUME = volume;
    }

    public int getDifficulty() {
        return DIFFICULTY;
    }

    public int getEffectVolume() {
        return EFFECTVOLUME;
    }
    public int getBackGroundVolume() {
        return BACKGROUNDVOLUME;
    }
    public void setBackGroundVolume(int volume) {
        BACKGROUNDVOLUME = volume;
    }


}
