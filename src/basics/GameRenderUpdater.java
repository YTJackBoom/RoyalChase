package basics;

import scenes.GameStates;

import java.awt.*;

import static basics.Game.fps;

/**
 * Die Klasse des threats zum rendern der grafik
 */
public class GameRenderUpdater implements Runnable{
    private Game game;
    public GameRenderUpdater(Game game){this.game = game;};
    @Override
    public void run() {
        while (true) {
            game.repaint(); //hier wird repaint() auf dass fenster gecalled, welches repaint() auf den gamescreen called, welcher die render() klasse called
            game.incrementFPS();
            try {
                Thread.sleep(1000 / fps);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Die render methode, welche die render() methode der derzeitgen GameState(s) aufruft, wird von GameScreen gecalled
     * @param g Das Graphics Object aus Gamescreen
     */
    public void render (Graphics g) {
        switch (GameStates.gameState) {
            case MENU -> game.getMenu().render(g);
            case PLAYING -> {
                game.getPlaying().render(g);
                game.getInfoOverlay().render(g);
            }
            case SETTINGS -> game.getSettings().render(g);
            case GAMEOVER -> {
                game.getGameOver().render(g);
                game.getInfoOverlay().render(g);
            }
            case LEVELCLEARED -> game.getLevelCleared().render(g);
            case LEVELSELECT -> game.getLevelSelect().render(g);
            case TOWN -> {
                game.getTown().render(g);
                game.getInfoOverlay().render(g);
            }
        }


    }

}
