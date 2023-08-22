package playerinputs;

import basics.Game;
import helpers.Values;
import scenes.GameStates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class KListener {
    private Game game;

    public KListener(Game game) {
        this.game = game;

        setupKeyBindings();
    }

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
        // VK_A binding
        inputMap.put(KeyStroke.getKeyStroke("released A"), "menuAction");
        actionMap.put("menuAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameStates.gameState = GameStates.MENU;
            }
        });

        // VK_S binding
        inputMap.put(KeyStroke.getKeyStroke("released S"), "playingAction");
        actionMap.put("playingAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameStates.gameState = GameStates.PLAYING;
            }
        });

        // VK_D binding
        inputMap.put(KeyStroke.getKeyStroke("released D"), "settingsAction");
        actionMap.put("settingsAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameStates.gameState = GameStates.SETTINGS;
                game.resetAll();
            }
        });

        // VK_M binding
        inputMap.put(KeyStroke.getKeyStroke("released M"), "increaseValuesAction");
        actionMap.put("increaseValuesAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getPlayerValues().increase(new Values(1000000, 10000, 10000, 10000, 10000, 10000));
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
