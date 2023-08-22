package scenes;

import basics.Game;
import helpers.Constants;
import helpers.Values;
import uiElements.MyButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static basics.Game.fps;


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
        buttons.add(new MyButton( "Continue", 100, 100, 200, 100,true)); //continueButton
        buttons.add(new MyButton( "Save", 100, 300, 200, 100,true)); //saveButton
        buttons.add(new MyButton( "Main Menu", 100, 500, 200, 100,true)); //mainMenuButton
    }

    @Override
    public void render(Graphics g) {
        game.getPlaying().render(g);
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

            FontMetrics fontMetrics = g.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(text);

            int x = (game.getWidth() - textWidth) / 2;
            int y = game.getHeight() / 2 + 50 / 2;

            g.drawString(text, x, y);
        }
    }
    public void renderLevelClearedButtons(Graphics g) {
        for (MyButton button : buttons) {
            button.render(g);
        }
    }
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
//			System.out.println("s ");
//			System.out.print(fWIDTH+" "+fHEIGHT);
                if (succesfullSaveCounter >= fps * Constants.UIConstants.CANTAFFORDTIMEONSCREEN) {
                    succesfullSave = false;
                    succesfullSaveCounter = 0;
                }
            }
    }

    private void resetButtons(){
        for(MyButton button: buttons){
            button.resetBools();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
//        if(e.getButton() == 1) {
//            int x = e.getX();
//            int y = e.getY();
//            for (MyButton button : buttons) {
//                if (button.getBounds().contains(x, y)) {
//                    if (playerValues.getRewardmultiplyer() == 1) {
//                        playerValues.setLevelscleared(playerValues.getLevelscleared()+1);
//                    }
//                    if (button.getText().equals("Continue")) {
//                        GameStates.gameState = GameStates.LEVELSELECT;
//                        System.out.println("Continue");
//                    } else if (button.getText().equals("Save")) {
//                        game.saveGame(Constants.OtherConstants.SAVEGAMELOCATION);
//                    } else if (button.getText().equals("Main Menu")) {
//                        GameStates.gameState = GameStates.MENU;
//                    }
//                }
//            }
//        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
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
                        game.getPlaying().getTowerController().sellAllTowers();
                        System.out.println("Continue");
                    } else if (button.getText().equals("Save")) {
                        game.saveGame(Constants.OtherConstants.SAVEGAMELOCATION);
                        succesfullSave = true;
                    } else if (button.getText().equals("Main Menu")) {
                        GameStates.gameState = GameStates.MENU;
                        game.getPlaying().getTowerController().sellAllTowers();

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
