package controllers;

import basics.Game;
import scenes.Playing;

import java.awt.*;

/**
 * Interface für die Controller-Klassen
 */
public interface ControllerMethods {
//    public Playing getPlaying();
    public void workAddQueue();
    public void workRemoveQueue();
    public void render(Graphics g);
    public void update();

}
