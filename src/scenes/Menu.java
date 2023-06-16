package scenes;

import basics.Game;
import uiElements.MyButton;

import java.awt.*;
import java.util.ArrayList;

public class Menu extends GameScenes implements SceneMethods {
    private ArrayList<MyButton> buttons = new ArrayList<MyButton>();

    public Menu(Game game) {
        super(game);
        initButtons();
    }

    @Override
    public void render(Graphics g) {
        renderButtons(g);
    }

    @Override
    public void update() {
    }

    public void initButtons() {
        int buttonWidth = 200;
        int buttonHeight = 100;
        int buttonX = 300;
        int buttonY = 500;
        int offsetX = 200;
    buttons.add(new MyButton("Play",buttonX,buttonY,buttonWidth,buttonHeight));
    buttons.add(new MyButton("Settings",buttonX+offsetX,buttonY,buttonWidth,buttonHeight));
    buttons.add(new MyButton("Exit",buttonX+2*offsetX,buttonY,buttonWidth,buttonHeight));
    }

    public void renderButtons(Graphics g){
        for(MyButton button: buttons){
            button.render(g);
        }
    }

    private void resetButtons(){
        for(MyButton button: buttons){
            button.resetBools();
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        for (MyButton button: buttons){
            if(button.getBounds().contains(x,y)){
                if(button.getText().equals("Play")){
                    GameStates.gameState = GameStates.LEVELSELECT;
                }else if(button.getText().equals("Settings")){
                    GameStates.gameState = GameStates.SETTINGS;
                }else if(button.getText().equals("Exit")){
                    System.exit(0);
                }
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
    public void mousePressed(int x, int y) {
        for(MyButton button: buttons){
            if(button.getBounds().contains(x,y)){
                button.setPressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    @Override
    public void reset() {

    }


}
