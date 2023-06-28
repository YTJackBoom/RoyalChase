package playerinputs;

import basics.Game;
import static scenes.GameStates.*;
import scenes.GameStates;


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
            case GAMEOVER -> game.getGameOver().keyPressed(e);
            case LEVELCLEARED -> game.getLevelCleared().keyPressed(e);
            case LEVELSELECT -> game.getLevelSelect().keyPressed(e);
            case TUTORIAL -> game.getTutorial().keyPressed(e);
            case TOWN -> game.getTown().keyPressed(e);

        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            GameStates.gameState = MENU;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            GameStates.gameState = PLAYING;
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            GameStates.gameState = SETTINGS;
            game.resetAll();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("djaw");
    }
}
//sa