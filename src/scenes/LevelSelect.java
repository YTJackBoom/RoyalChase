package scenes;

import basics.Game;
import helpers.Values;
import uiElements.MyButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class LevelSelect extends GameScenes implements SceneMethods{
    private Game game;
    private ArrayList<MyButton> buttons = new ArrayList<MyButton>();
    public LevelSelect(Game game){
        super(game);
        this.game = game;

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
            buttons.add(new MyButton("Level "+(i+1),x,y,buttonWidth,buttonHeight));
        }
        buttons.add(new MyButton("Tutorial",buttonXStart-buttonXOffset,buttonYStart,buttonWidth,buttonHeight));
        buttons.add(new MyButton("Back",buttonXStart-(buttonXOffset+buttonXOffset/2),buttonYStart-buttonYOffset/2,buttonWidth/2,buttonHeight/2));
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
        for (int i = 0; i < Values.LEVELSCLEARED; i++) {
            buttons.get(i).setChecked(true);
        }
        for(int i = Values.LEVELSCLEARED; i < buttons.size(); i++){
            buttons.get(i).setChecked(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {  //TODO: Make this wor
        int x = e.getX();
        int y = e.getY();

        for (MyButton button: buttons){
            if(button.getBounds().contains(x,y)) {
                if (button.isChecked()) {
//                helpers.Values.LEVEL = button.getType();
                    Values.REWARDMULTIPLYER = 0;
                } else {
                    Values.REWARDMULTIPLYER = 1;
                }
                System.out.println("Level " + button.getText());
                game.getPlaying().reset();
                GameStates.gameState = GameStates.PLAYING;

            }
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
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
        resetButtons();

    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    @Override
    public void reset() {
        for (MyButton b  : buttons) {
            b.setChecked(false);
        }
    }

    public void resetButtons(){
        for(MyButton button: buttons){
            button.resetBools();
        }
    }
}
