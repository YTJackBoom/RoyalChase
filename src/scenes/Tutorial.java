package scenes;

import basics.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

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
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseMoved(int x, int y) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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
