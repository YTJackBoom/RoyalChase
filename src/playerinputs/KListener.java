package playerinputs;

import basics.Game;

import static basics.GameScreen.fHEIGHT;
import static basics.GameScreen.fWIDTH;
import static scenes.GameStates.*;

import helpers.Values;
import scenes.GameStates;


import javax.swing.*;
import java.awt.*;
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
        if (e.getKeyCode() == KeyEvent.VK_A) {
            GameStates.gameState = MENU;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            GameStates.gameState = PLAYING;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            GameStates.gameState = SETTINGS;
            game.resetAll();
        }
        if(e.getKeyCode() == KeyEvent.VK_M) {
            game.getPlayerValues().increase(new Values(10000,10000,10000,10000));
        }
        if (e.getKeyCode() == KeyEvent.VK_F11) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();

            // Get the screen's resolution
            DisplayMode displayMode = defaultScreen.getDisplayMode();
            int screenWidth = displayMode.getWidth();
            int screenHeight = displayMode.getHeight();

            if (game.isFullScreen()) {
                // Switch back to decorated mode
                game.dispose();
                game.setUndecorated(false);
                game.setVisible(true);
                game.setSize(new Dimension(fWIDTH, fHEIGHT));
                // Center the frame if desired
                game.setLocationRelativeTo(null);
            } else {
                // Switch to fullscreen mode
                game.dispose();
                game.setUndecorated(true);
                game.setVisible(true);
                game.setSize(new Dimension(screenWidth,screenHeight));
                game.setLocation(0,0 );

            }
            game.toggleFullscreen();
        }
        game.getGameScreen().requestFocusInWindow();
    }



    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("djaw");
    }
}
//sa