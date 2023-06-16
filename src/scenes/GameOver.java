package scenes;

import basics.Game;
import uiElements.MyButton;

import java.awt.*;
import java.util.ArrayList;

public class GameOver extends GameScenes implements SceneMethods{
    private Game game;
    private ArrayList<MyButton> buttons = new ArrayList<MyButton>();

    public GameOver(Game game){
        super(game);
        this.game = game;
        initButtons();


    }

    public void initButtons() {
        int startX = game.getWidth()/4;
        int startY = game.getHeight()/2;
        int xOffset = game.getWidth()/2;
        int yOffset = 0;
        int width = 100;
        int height = 100;
        System.out.println(startX);
        System.out.println(xOffset);
        System.out.println(startY);
    	buttons.add(new MyButton( "Try Again", startX, startY, width, height));
    	buttons.add(new MyButton("Main Menu", startX+xOffset, startY, width, height));
    }
    @Override
    public void render(Graphics g) {
        renderBackground(g);
        renderButtons(g);

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
    @Override
    public void update(){

    }

    @Override
    public void mouseClicked(int x, int y) {
        for (MyButton button: buttons){
            if(button.getBounds().contains(x,y)){
                if(button.getText().equals("Try Again")){
                    GameStates.gameState = GameStates.LEVELSELECT;
//                    System.out.println("Continue");
                }else if(button.getText().equals("Main Menu")){
                    GameStates.gameState = GameStates.MENU;
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
    public void resetButtons(){
        for(MyButton button: buttons){
            button.resetBools();
        }
    }


}
