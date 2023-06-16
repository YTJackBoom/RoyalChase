package playerinputs;

import basics.Game;
import scenes.GameStates;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MListener implements MouseListener, MouseMotionListener {
    private Game game;
    public MListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (GameStates.gameState) {
                case MENU -> game.getMenu().mouseClicked(e.getX(), e.getY());
                case PLAYING -> game.getPlaying().mouseClicked(e.getX(), e.getY());
                case SETTINGS -> game.getSettings().mouseClicked(e.getX(), e.getY());
                case GAMEOVER -> game.getGameOver().mouseClicked(e.getX(), e.getY());
                case LEVELCLEARED -> game.getLevelCleared().mouseClicked(e.getX(), e.getY());
                case LEVELSELECT -> game.getLevelSelect().mouseClicked(e.getX(), e.getY());
                case TUTORIAL -> game.getTutorial().mouseClicked(e.getX(), e.getY());
                case TOWN -> game.getTown().mouseClicked(e.getX(), e.getY());
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (GameStates.gameState) {
                case MENU -> game.getMenu().mousePressed(e.getX(), e.getY());
                case PLAYING -> game.getPlaying().mousePressed(e.getX(), e.getY());
                case SETTINGS -> game.getSettings().mousePressed(e.getX(), e.getY());
                case GAMEOVER -> game.getGameOver().mousePressed(e.getX(), e.getY());
                case LEVELCLEARED -> game.getLevelCleared().mousePressed(e.getX(), e.getY());
                case LEVELSELECT -> game.getLevelSelect().mousePressed(e.getX(), e.getY());
                case TUTORIAL -> game.getTutorial().mousePressed(e.getX(), e.getY());
                case TOWN -> game.getTown().mousePressed(e.getX(), e.getY());


            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU -> game.getMenu().mouseReleased(e.getX(), e.getY());
            case PLAYING -> game.getPlaying().mouseReleased(e.getX(), e.getY());
            case SETTINGS -> game.getSettings().mouseReleased(e.getX(), e.getY());
            case GAMEOVER -> game.getGameOver().mouseReleased(e.getX(), e.getY());
            case LEVELCLEARED -> game.getLevelCleared().mouseReleased(e.getX(), e.getY());
            case LEVELSELECT -> game.getLevelSelect().mouseReleased(e.getX(), e.getY());
            case TUTORIAL -> game.getTutorial().mouseReleased(e.getX(), e.getY());
            case TOWN -> game.getTown().mouseReleased(e.getX(), e.getY());

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
            case MENU -> game.getMenu().mouseDragged(e.getX(), e.getY());
            case PLAYING -> game.getPlaying().mouseDragged(e.getX(), e.getY());
            case SETTINGS -> game.getSettings().mouseDragged(e.getX(), e.getY());
            case GAMEOVER -> game.getGameOver().mouseDragged(e.getX(), e.getY());
            case LEVELCLEARED -> game.getLevelCleared().mouseDragged(e.getX(), e.getY());
            case LEVELSELECT -> game.getLevelSelect().mouseDragged(e.getX(), e.getY());
            case TUTORIAL -> game.getTutorial().mouseDragged(e.getX(), e.getY());
            case TOWN -> game.getTown().mouseDragged(e.getX(), e.getY());


        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU -> game.getMenu().mouseMoved(e.getX(), e.getY());
            case PLAYING -> game.getPlaying().mouseMoved(e.getX(), e.getY());
            case SETTINGS -> game.getSettings().mouseMoved(e.getX(), e.getY());
            case GAMEOVER -> game.getGameOver().mouseMoved(e.getX(), e.getY());
            case LEVELCLEARED -> game.getLevelCleared().mouseMoved(e.getX(), e.getY());
            case LEVELSELECT -> game.getLevelSelect().mouseMoved(e.getX(), e.getY());
            case TUTORIAL -> game.getTutorial().mouseMoved(e.getX(), e.getY());
            case TOWN -> game.getTown().mouseMoved(e.getX(), e.getY());

        }
    }
}
