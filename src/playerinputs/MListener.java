package playerinputs;

import basics.Game;
import scenes.GameStates;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

/**
 * Klasse um die Mausinputs zu verarbeiten
 */
public class MListener implements MouseInputListener {
    private Game game;
    private long lastReleaseTime = 0;
    private final long releaseCooldown = 500;
    public MListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
            switch (GameStates.gameState) {
                case MENU -> game.getMenu().mouseClicked(e);
                case PLAYING -> game.getPlaying().mouseClicked(e);
                case SETTINGS -> game.getSettings().mouseClicked(e);
                case GAMEOVER -> game.getGameOver().mouseClicked(e);
                case LEVELCLEARED -> game.getLevelCleared().mouseClicked(e);
                case LEVELSELECT -> game.getLevelSelect().mouseClicked(e);
                case TOWN -> game.getTown().mouseClicked(e);
            }


    }

    @Override
    public void mousePressed(MouseEvent e) {
            switch (GameStates.gameState) {
                case MENU -> game.getMenu().mousePressed(e);
                case PLAYING -> game.getPlaying().mousePressed(e);
                case SETTINGS -> game.getSettings().mousePressed(e);
                case GAMEOVER -> game.getGameOver().mousePressed(e);
                case LEVELCLEARED -> game.getLevelCleared().mousePressed(e);
                case LEVELSELECT -> game.getLevelSelect().mousePressed(e);
                case TOWN -> game.getTown().mousePressed(e);


            }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        long currentTime = System.currentTimeMillis(); //Cooldown nach Releases der Maus um versehentliches mehrfaches Klicken zu verhindern
        if (currentTime - lastReleaseTime < releaseCooldown) {
            return;
        }
        lastReleaseTime = currentTime;

        switch (GameStates.gameState) {
            case MENU -> game.getMenu().mouseReleased(e);
            case PLAYING -> game.getPlaying().mouseReleased(e);
            case SETTINGS -> game.getSettings().mouseReleased(e);
            case GAMEOVER -> game.getGameOver().mouseReleased(e);
            case LEVELCLEARED -> game.getLevelCleared().mouseReleased(e);
            case LEVELSELECT -> game.getLevelSelect().mouseReleased(e);
            case TOWN -> game.getTown().mouseReleased(e);

        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU -> game.getMenu().mouseDragged(e);
            case PLAYING -> game.getPlaying().mouseDragged(e);
            case SETTINGS -> game.getSettings().mouseDragged(e);
            case GAMEOVER -> game.getGameOver().mouseDragged(e);
            case LEVELCLEARED -> game.getLevelCleared().mouseDragged(e);
            case LEVELSELECT -> game.getLevelSelect().mouseDragged(e);
            case TOWN -> game.getTown().mouseDragged(e);


        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU -> game.getMenu().mouseMoved(e);
            case PLAYING -> game.getPlaying().mouseMoved(e);
            case SETTINGS -> game.getSettings().mouseMoved(e);
            case GAMEOVER -> game.getGameOver().mouseMoved(e);
            case LEVELCLEARED -> game.getLevelCleared().mouseMoved(e);
            case LEVELSELECT -> game.getLevelSelect().mouseMoved(e);
            case TOWN -> game.getTown().mouseMoved(e);

        }
    }
}
