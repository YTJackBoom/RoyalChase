package scenes;

import basics.Game;

import java.awt.event.KeyEvent;

public class GameScenes {
    protected Game game;
    public GameScenes(Game game){
        this.game = game;
    }
    public Game getGame(){
        return game;
    }
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            getGame().togglePause();
        }
    }

}
