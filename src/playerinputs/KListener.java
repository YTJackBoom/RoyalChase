package playerinputs;

import basics.Game;
import static basics.GameStates.*;
import basics.GameStates;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KListener implements KeyListener {
    public KListener(Game game) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
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
        System.out.println("djaw");
    }
}
//sa