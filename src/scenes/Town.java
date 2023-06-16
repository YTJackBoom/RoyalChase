package scenes;

import basics.Game;
import helpers.variables;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Town extends GameScenes implements SceneMethods{
    private Game game;
    private boolean isPaused = false;
    private BufferedImage townImage;
    public Town(Game game){
        super(game);
        this.game = game;
        initClasses();
        initVariables();
    }


    private void initClasses() {

    }

    private void initVariables() {
        townImage = variables.Town.getBackgroundImage();
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(townImage, 0, 0, null);

    }

    @Override
    public void update() {

    }

    @Override
    public void mouseClicked(int x, int y) {

    }

    @Override
    public void mouseMoved(int x, int y) {

    }

    @Override
    public void mousePressed(int x, int y) {

    }

    @Override
    public void mouseReleased(int x, int y) {

    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    @Override
    public void reset() {
        // TODO: reset towen
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }
}
