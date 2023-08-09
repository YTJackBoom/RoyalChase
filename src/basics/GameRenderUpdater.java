package basics;

import scenes.GameStates;

import java.awt.*;

import static basics.Game.fps;

public class GameRenderUpdater implements Runnable{
    private Game game;
    public GameRenderUpdater(Game game){this.game = game;};
    @Override
    public void run() {
        while (true) {
            game.repaint();
            game.incrementFPS();
            // Adjust the delay to control the update rate
            try {
                Thread.sleep(1000 / fps);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

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
            case TUTORIAL -> {
                game.getTutorial().render(g);
                game.getInfoOverlay().render(g);
            }
            case TOWN -> {
                game.getTown().render(g);
                game.getInfoOverlay().render(g);
            }
        }


    }

}
