package scenes;

import basics.Game;
import helpers.Values;
import uiElements.MyButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class LevelCleared  extends GameScenes implements SceneMethods {
    private Game game;
    private ArrayList<MyButton> buttons = new ArrayList<MyButton>();
    private Values playerValues;
    public LevelCleared(Game game) {
        super(game);
        this.game = game;
        playerValues = game.getPlayerValues();
        initButtons();
    }
    public void initButtons() {
        buttons.add(new MyButton( "Continue", 100, 100, 200, 100)); //continueButton
        buttons.add(new MyButton( "Save", 100, 300, 200, 100)); //saveButton
        buttons.add(new MyButton( "Main Menu", 100, 500, 200, 100)); //mainMenuButton
    }

    @Override
    public void render(Graphics g) {
        game.getPlaying().render(g);
        renderLevelCleared(g);
    }

    @Override
    public void update() {

    }
    public void renderLevelCleared(Graphics g){
        renderLevelClearedText(g);
        renderLevelClearedButtons(g);
    }

    public void renderLevelClearedText(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        String text = "Level Cleared";

        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(text);

        int x = (game.getWidth() - textWidth) / 2;
        int y = game.getHeight() / 2 + 50 / 2;

        g.drawString(text, x, y);
    }
    public void renderLevelClearedButtons(Graphics g) {
        for (MyButton button : buttons) {
            button.render(g);
        }
    }

    private void resetButtons(){
        for(MyButton button: buttons){
            button.resetBools();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == 1) {
            int x = e.getX();
            int y = e.getY();
            for (MyButton button : buttons) {
                if (button.getBounds().contains(x, y)) {
                    if (playerValues.getRewardmultiplyer() == 1) {
                        playerValues.setLevelscleared(playerValues.getLevelscleared()+1);
                    }
                    if (button.getText().equals("Continue")) {
                        GameStates.gameState = GameStates.LEVELSELECT;
                        System.out.println("Continue");
                    } else if (button.getText().equals("Save")) {
//                    GameStates.gameState = GameStates.SAVE;
                    } else if (button.getText().equals("Main Menu")) {
                        GameStates.gameState = GameStates.MENU;
                    }
                }
            }
        }

    }

    @Override
    public void mouseMoved(int x, int y) {
        for (MyButton button: buttons){
            if(button.getBounds().contains(x,y)){
                button.setHovered(true);
            }else{
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
                if (button.getBounds().contains(x, y)) {
                    button.setPressed(true);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        resetButtons();

    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    @Override
    public void reset() {

    }
}
