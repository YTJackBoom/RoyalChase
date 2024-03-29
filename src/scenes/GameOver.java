package scenes;

import basics.Game;
import helpers.AbsoluteCoordinate;
import helpers.RelativeCoordinate;
import helpers.UiCoordinate;
import uiElements.MyButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;

/**
 * Scene Klasse für das GameOver
 */
public class GameOver extends GameScenes implements SceneMethods{
    private Game game;
    private ArrayList<MyButton> buttons = new ArrayList<MyButton>();

    public GameOver(Game game){
        super(game);
        this.game = game;
        initButtons();


    }

    public void initButtons() {
        int startX = Game.initGameWidth / 4;
        int startY = Game.initGameHeight / 2;
        int xOffset = Game.initGameWidth / 2;
        int yOffset = 0;
        int width = 100;
        int height = 100;
//        System.out.println(startX);
//        System.out.println(xOffset);
//        System.out.println(startY);
        buttons.add(new MyButton("Try Again", new UiCoordinate(new RelativeCoordinate(new AbsoluteCoordinate(0, 0), 1 / 3f, 1 / 2f)), width, height, true));
        buttons.add(new MyButton("Main Menu", new UiCoordinate(new RelativeCoordinate(new AbsoluteCoordinate(0, 0), 2 / 3f, 1 / 2f)), width, height, true));
    }
    @Override
    public void render(Graphics g) {
        renderBackground(g);
        renderButtons(g);

    }


    public void renderBackground(Graphics g){
        BufferedImage image = new BufferedImage(game.getWidth(), game.getHeight(), BufferedImage.TYPE_INT_ARGB); //der hintergrund (= das Spielfeld) wird als buffered image aufgenommen
        Graphics2D g2d = image.createGraphics();
        game.getPlaying().softRender(g2d);
        g2d.dispose();

        float[] matrix = {                                                                                       //und geblurred + als hintergrund gezeichnet
                1/9f, 1/9f, 1/9f,
                1/9f, 1/9f, 1/9f,
                1/9f, 1/9f, 1/9f,
        };
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix), ConvolveOp.EDGE_NO_OP, null);
        BufferedImage blurredImage = op.filter(image, null);

        g.drawImage(blurredImage, 0, 0, null);

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
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for (MyButton button: buttons){
            if (button.contains(x, y)) {
                if (button.getText().equals("Try Again")) {
                    GameStates.gameState = GameStates.LEVELSELECT;
                    game.getLevelSelect().setBackScene(GameStates.GAMEOVER);
                    game.resetAll();
                } else if (button.getText().equals("Main Menu")) {
                    GameStates.gameState = GameStates.MENU;
                }
            }
        }
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
        if(e.getButton() == 1) {
            for (MyButton button : buttons) {
                if (button.contains(e.getX(), e.getY())) {
                    button.setPressed(true);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == 1){resetButtons();}
    }

    @Override
    public void mouseDragged(MouseEvent e) {

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
