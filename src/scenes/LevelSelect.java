package scenes;

import basics.Game;
import helpers.Coordinate;
import helpers.Values;
import uiElements.MyButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class LevelSelect extends GameScenes implements SceneMethods{
    private Game game;
    private ArrayList<MyButton> buttons = new ArrayList<MyButton>();
    private Values playerValues;
    public LevelSelect(Game game){
        super(game);
        this.game = game;
        playerValues = game.getPlayerValues();

        initButtons();

    }


    public void initButtons(){
        int buttonsAmount = 9;
        int buttonsPerLine = 3;
        int buttonWidth = 150;
        int buttonHeight = 150;
        int buttonXStart = 300;
        int buttonYStart = 200;
        int buttonXOffset = 200;
        int buttonYOffset = 200;

        for(int i = 0; i < buttonsAmount; i++){
            int x = buttonXStart + (i%buttonsPerLine)*buttonXOffset;
            int y = buttonYStart + (i/buttonsPerLine)*buttonYOffset;
            buttons.add(new MyButton((i+1),new Coordinate(x,y),buttonWidth,buttonHeight));
        }
        buttons.add(new MyButton(0,new Coordinate(buttonXStart-buttonXOffset,buttonYStart),buttonWidth,buttonHeight));
        buttons.add(new MyButton("Zurück",buttonXStart-(buttonXOffset+buttonXOffset/2),buttonYStart-buttonYOffset/2,buttonWidth/2,buttonHeight/2));
    }

    @Override
    public void render(Graphics g) {
        renderBackground(g);
        renderButtons(g);
        renderTicks(g);

    }


    public void renderBackground(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,game.getWidth(),game.getHeight());
    }

    public void renderButtons(Graphics g){
        for(MyButton button: buttons){
            button.render(g);
        }
    }
    public void renderTicks(Graphics g) {
        for (MyButton button : buttons) {
            if (button.isChecked()) {

                int x = button.getX() + button.getWidth() / 2 - 10; // Adjust the tick position as needed
                int y = button.getY() + button.getHeight() / 2 - 10; // Adjust the tick position as needed

                g.setColor(Color.GREEN); // Set the tick color

                // Draw a tick symbol (checkmark)
                g.drawLine(x, y, x + 10, y + 10);
                g.drawLine(x + 10, y + 10, x + 20, y - 10);
            }
        }
    }
    @Override
    public void update() {
        for (int i = 0; i < playerValues.getLevelscleared(); i++) {
            buttons.get(i).setChecked(true);
        }
        for(int i = playerValues.getLevelscleared(); i < buttons.size(); i++){
            buttons.get(i).setChecked(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {  //TODO: Make this wor
        int x = e.getX();
        int y = e.getY();

//        for (MyButton button: buttons){
//            if(button.getBounds().contains(x,y)) {
//                if (button.isChecked()) {
////                helpers.Values.LEVEL = button.getType();
//                    playerValues.setRewardmultiplyer(0);
//                } else {
//                    playerValues.setRewardmultiplyer(1);
//                }
//                playerValues.setLevel(button.getLevel());
//
//                System.out.println("Level " + button.getText());
//                game.getPlaying().reset();
//                GameStates.gameState = GameStates.PLAYING;
//
//            }
//        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for(MyButton button: buttons){
            if(button.getBounds().contains(x,y)){
                button.setHovered(true);
            }else{
                button.setHovered(false);
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for(MyButton button: buttons){
            if(button.getBounds().contains(x,y)){
                button.setPressed(true);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for (MyButton button: buttons){
            if(button.getBounds().contains(x,y)) {
                if(button.getLevel()>=0) {
                    if (button.isChecked()) {
//                helpers.Values.LEVEL = button.getType();
                        playerValues.setRewardmultiplyer(0);
                    } else {
                        playerValues.setRewardmultiplyer(1);
                    }
                    playerValues.setLevel(button.getLevel());

                    System.out.println("Level " + button.getText());
                    game.getPlaying().reset();
                    GameStates.gameState = GameStates.PLAYING;
                }else {
                    releasedOnTextButton(button);
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
        for (MyButton b  : buttons) {
            b.setChecked(false);
        }
    }

    public void releasedOnTextButton(MyButton b){
        switch (b.getText()) {
            case "Zurück" -> GameStates.gameState = GameStates.MENU;
            default -> System.out.println("THis Button should not exist: "+b.getText());
        }
    }

    public void resetButtons(){
        for(MyButton button: buttons){
            button.resetBools();
        }
    }

    public void setPlayerValues(Values playerValues) {
        this.playerValues = playerValues;
    }
}
