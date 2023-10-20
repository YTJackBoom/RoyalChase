package scenes;

import basics.Game;
import controllers.SoundController;
import helpers.*;
import uiElements.MyButton;
import uiElements.Slider;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;

public class Menu extends GameScenes implements SceneMethods {
    private ArrayList<MyButton> buttons = new ArrayList<MyButton>();
    private ArrayList<Slider> sliders = new ArrayList<Slider>();

    public Menu(Game game) {
        super(game);
        initButtons();
        initSliders();
    }

    @Override
    public void render(Graphics g) {
        renderBackground(g);
        renderButtons(g);                                             //buttons und sliders werden gezeichnet
        renderSliders(g);
    }

    @Override
    public void update() {
        grabSlidersInfos();
    }

    public void initSliders() {
        UserSettings userSettings = game.getGameState().getUserSettings();
        int frameWidth = Game.initGameWidth;
        int frameHeight = Game.initGameHeight;
        int sliderWidth = 200;
        int sliderHeight = 50;

        float relativeX = 100.0f / frameWidth;
        float relativeY1 = 100.0f / frameHeight;
        float relativeY2 = 200.0f / frameHeight;
        float relativeY3 = 300.0f / frameHeight;

        AbsoluteCoordinate referencePoint = new AbsoluteCoordinate(0, 0); // Top-left corner as reference

        // First Slider
        UiCoordinate slider1Coordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, relativeX, relativeY1));
        sliders.add(new Slider(slider1Coordinate, sliderWidth, sliderHeight, 0, 5, userSettings.getDifficulty(),"Difficulty"));

        // Second Slider
        UiCoordinate slider2Coordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, relativeX, relativeY2));
        sliders.add(new Slider(slider2Coordinate, sliderWidth, sliderHeight, 0, 100, userSettings.getEffectVolume(),"SoundEffects"));

        // Third Slider
        UiCoordinate slider3Coordinate = new UiCoordinate(new RelativeCoordinate(referencePoint,relativeX,relativeY3));
        sliders.add(new Slider(slider3Coordinate,sliderWidth,sliderHeight,0,100,userSettings.getBackGroundVolume(),"BackGroundMusic"));
    }


    public void initButtons() {
        int frameWidth = Game.initGameWidth;
        int frameHeight = Game.initGameHeight;

        int buttonWidth = 200;
        int buttonHeight = 100;


        float yOffset = 0.5f - (float) buttonHeight / (2 * frameHeight);

        AbsoluteCoordinate referencePoint = new AbsoluteCoordinate(0, 0); // Top-left corner as reference

        UiCoordinate button1Coordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, 1 / 4f, yOffset));
        buttons.add(new MyButton("Play", button1Coordinate, buttonWidth, buttonHeight, true));

        UiCoordinate button2Coordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, 2 / 4f, yOffset));
        buttons.add(new MyButton("Load Game", button2Coordinate, buttonWidth, buttonHeight, true));

        UiCoordinate button3Coordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, 3 / 4f, yOffset));
        buttons.add(new MyButton("Exit", button3Coordinate, buttonWidth, buttonHeight, true));
    }


    public void renderBackground(Graphics g) {
        BufferedImage image = new BufferedImage(game.getWidth(), game.getHeight(), BufferedImage.TYPE_INT_ARGB); //der hintergrund (= das Spielfeld) wird als buffered image aufgenommen
        Graphics2D g2d = image.createGraphics();
        game.getPlaying().render(g2d);
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
    public void renderButtons(Graphics g) {
        for (MyButton button : buttons) {
            button.render(g);
        }
    }

    public void renderSliders(Graphics g) {
        for (Slider slider : sliders) {
            slider.render(g);
        }
    }

    private void resetButtons() {
        for (MyButton button : buttons) {
            button.resetBools();
        }
    }

    private void grabSlidersInfos() {
        game.getGameState().getUserSettings().setDifficulty(sliders.get(0).getValue());
//        System.out.println(sliders.get(0).getValue());
        game.getGameState().getUserSettings().setEffectVolume(sliders.get(1).getValue());
        SoundController.getInstance().setSoundEffectVolume(sliders.get(1).getValue()/100f);

        game.getGameState().getUserSettings().setBackGroundVolume(sliders.get(2).getValue());
        SoundController.getInstance().setBackgroundVolume(sliders.get(2).getValue()/100f);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for(MyButton button: buttons){
            if (button.contains(x, y)) {
                button.setHovered(true);
            } else {
                button.setHovered(false);
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (MyButton button : buttons) {
            if (button.contains(x, y)) {
                button.setPressed(true);
            }
        }
        for (Slider slider : sliders) {
            if (slider.contains(x, y)) {
                slider.mousePressed(x, y);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (MyButton button: buttons){
            if (button.contains(x, y)) {
                if (button.getText().equals("Play")) {
                    GameStates.gameState = GameStates.LEVELSELECT;
                    game.getLevelSelect().setBackScene(GameStates.MENU);
                } else if (button.getText().equals("Load Game")) {
                    game.loadGame("RoyalChaseSaveFile");
                } else if (button.getText().equals("Exit")) {
                    System.exit(0);
                }
            }
        }
        resetButtons();

        for (Slider slider : sliders) {
                slider.mouseReleased(x, y);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        for (Slider slider : sliders) {
                slider.mouseDragged(mouseX, mouseY);
        }
    }

    @Override
    public void reset() {

    }


}
