package playerinputs;

import basics.Game;
import static basics.GameStates.*;
import basics.GameStates;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KListener implements KeyListener {
    private Game game;
    public KListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameStates.gameState) {
            case MENU -> game.getMenu().keyPressed(e);
            case PLAYING -> game.getPlaying().keyPressed(e);
            case SETTINGS -> game.getSettings().keyPressed(e);
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            GameStates.gameState = MENU;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            GameStates.gameState = PLAYING;
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            GameStates.gameState = SETTINGS;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("djaw");
    }
}
//sa