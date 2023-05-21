package scenes;

import basics.Game;
import basics.GameStates;
import uiElements.MyButton;

import java.awt.*;

public class Menu extends GameScenes implements SceneMethods{
    private MyButton playButton;
    public Menu(Game game) {
        super(game);
        playButton = new MyButton("Play",100,100,100,100);
    }

    @Override
    public void render(Graphics g) {
        playButton.render(g);
    }
    @Override
    public void update(){

    }

    @Override
    public void mouseClicked(int x, int y) {
        if(playButton.getBounds().contains(x,y)){
            GameStates.gameState = GameStates.PLAYING;
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(playButton.getBounds().contains(x,y)){
            playButton.setHovered(true);
        }else{
            playButton.setHovered(false);
        }

    }

    @Override
    public void mousePressed(int x, int y) {
        if (playButton.getBounds().contains(x, y)) {
            playButton.setPressed(true);

        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
    private void resetButtons(){
        playButton.resetBools();
    }
}
