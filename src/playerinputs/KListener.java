package playerinputs;

import basics.Game;
import helpers.Values;
import scenes.GameStates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static basics.Game.ISDEVMODE;

/**
 * Klasse um die Tastatureingaben zu verarbeiten
 */
public class KListener {
    private Game game;

    public KListener(Game game) {
        this.game = game;

        setupKeyBindings();
    }

    /**
     * Methode um die Tastaturbindings zu setzen. InputMaps werden auf keystrokes gebunden, diese schließlich auf die ActionMap ges rootpanes des Spiels
     */
    private void setupKeyBindings() {
        InputMap inputMap = game.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = game.getRootPane().getActionMap();

        // VK_SPACE binding
        inputMap.put(KeyStroke.getKeyStroke("released SPACE"), "pauseAction");
        actionMap.put("pauseAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.togglePause();
            }
        });
        // VK_D binding
        inputMap.put(KeyStroke.getKeyStroke("released D"), "devModeToggle");
        actionMap.put("devModeToggle", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.toggleDevMode();
                System.err.println("DevMode is "+ISDEVMODE);
            }
        });

        // VK_M binding
        inputMap.put(KeyStroke.getKeyStroke("M"), "increaseValuesAction");
        actionMap.put("increaseValuesAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getPlayerValues().increase(new Values(1000000, 10000, 10000, 10000, 10000, 10000));
                game.getPlayerValues().setHealth(50000);
            }
        });

        // VK_F11 binding
        inputMap.put(KeyStroke.getKeyStroke("released F11"), "toggleFullscreenAction");
        actionMap.put("toggleFullscreenAction", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game.toggleFullscreen();
                    }
                });
    }
}
