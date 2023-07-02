package scenes;

import basics.Game;

import java.awt.*;

public class Tutorial extends GameScenes implements SceneMethods{
    private Game game;
    protected boolean isPaused = false;
    public Tutorial(Game game){
        super(game);
        this.game = game;
    }
    @Override
    public void render(Graphics g) {

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

    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }
}