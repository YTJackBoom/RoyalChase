package scenes;

import basics.Game;
import helpers.*;
import uiElements.MyButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static basics.Game.fHEIGHT;
import static basics.Game.fps;

/**
 * Scene Klasse nach dem Erfolgreichen Beenden eines Levels
 */
public class LevelCleared  extends GameScenes implements SceneMethods {
    private Game game;
    private ArrayList<MyButton> buttons = new ArrayList<MyButton>();
    private Values playerValues;
    private int succesfullSaveCounter;
    private boolean succesfullSave;
    public LevelCleared(Game game) {
        super(game);
        this.game = game;
        playerValues = game.getPlayerValues();
        initButtons();
    }
    public void initButtons() {
        int width = 200;
        int height = 100;

        float yOffset = 0.5f - (float) height / (fHEIGHT * 2);
        buttons.add(new MyButton("Continue", new UiCoordinate(new RelativeCoordinate(new AbsoluteCoordinate(0, 0), 1 / 4f, yOffset)), width, height, true)); //continueButton
        buttons.add(new MyButton("Save", new UiCoordinate(new RelativeCoordinate(new AbsoluteCoordinate(0, 0), 2 / 4f, yOffset)), width, height, true)); //saveButton
        buttons.add(new MyButton("Main Menu", new UiCoordinate(new RelativeCoordinate(new AbsoluteCoordinate(0, 0), 3 / 4f, yOffset)), width, height, true)); //mainMenuButton
    }

    @Override
    public void render(Graphics g) {
        game.getPlaying().softRender(g);
        renderLevelClearedText(g);
        renderLevelClearedButtons(g);
        renderSuccesfullSave(g);
    }

    @Override
    public void update() {

    }

    public void renderLevelClearedText(Graphics g){
        if(!succesfullSave) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            String text = "Level Cleared";

            int x = (game.getWidth() / 2) - 40;
            int y = game.getHeight() / 3 + 50 / 2;

            g.drawString(text, x, y);
        }
    }
    public void renderLevelClearedButtons(Graphics g) {
        for (MyButton button : buttons) {
            button.render(g);
        }
    }

    /**
     * Zeigt an, dass das Spiel erfolgreich gespeichert wurde, wenn dies der Fall ist
     * @param g
     */
    public void renderSuccesfullSave(Graphics g) {
            if (succesfullSave) {
                g.setFont(Constants.UIConstants.SUCCESFULLSAVEFONT);
                g.setColor(Color.BLACK);
                String text = "Erfolgreich Gespeichert!";

                FontMetrics fontMetrics = g.getFontMetrics();
                int textWidth = fontMetrics.stringWidth(text);

                int x = (game.getWidth() - textWidth) / 2;
                int y = game.getHeight() / 2 + 50 / 2;

                g.drawString(text, x, y);
                succesfullSaveCounter++;
                if (succesfullSaveCounter >= fps * Constants.UIConstants.CANTAFFORDTIMEONSCREEN) {
                    succesfullSave = false;
                    succesfullSaveCounter = 0;
                }
            }
    }
    public void clearedCall() {
        game.getLevelSelect().setBackScene(GameStates.LEVELCLEARED);
        game.getPlaying().getTowerController().sellAllTowers();
    }

    private void resetButtons(){
        for(MyButton button: buttons){
            button.resetBools();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for (MyButton button: buttons){
            if (button.contains(x, y)) {
                button.setHovered(true);
            } else {
                button.setHovered(false);
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton()==1) {
            int x = e.getX();
            int y = e.getY();
            for (MyButton button : buttons) {
                if (button.contains(x, y)) {
                    button.setPressed(true);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == 1) {
            int x = e.getX();
            int y = e.getY();
            for (MyButton button : buttons) {
                if (button.contains(x, y)) {
                    playerValues.getLevelscleared().add(playerValues.getLevel());

                    if (button.getText().equals("Continue")) {
                        GameStates.gameState = GameStates.LEVELSELECT;
                        System.out.println("Continue");
                        succesfullSave = false;
                    } else if (button.getText().equals("Save")) {
                        game.saveGame("RoyalChaseSaveFile");
                        succesfullSave = true;
                    } else if (button.getText().equals("Main Menu")) {
                        GameStates.gameState = GameStates.MENU;
                        succesfullSave = false;

                    }
                }
            }
        }
        resetButtons();

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void reset() {

    }
}
