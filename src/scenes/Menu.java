package scenes;

import basics.Game;
import helpers.*;
import uiElements.MyButton;
import uiElements.Slider;

import java.awt.*;
import java.awt.event.MouseEvent;
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
        game.getPlaying().render(g);
        renderButtons(g);
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

        AbsoluteCoordinate referencePoint = new AbsoluteCoordinate(0, 0); // Top-left corner as reference

        // First Slider
        UiCoordinate slider1Coordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, relativeX, relativeY1));
        sliders.add(new Slider(slider1Coordinate, sliderWidth, sliderHeight, 0, 5, userSettings.getDifficulty()));

        // Second Slider
        UiCoordinate slider2Coordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, relativeX, relativeY2));
        sliders.add(new Slider(slider2Coordinate, sliderWidth, sliderHeight, 0, 100, userSettings.getVolume()));
    }


    public void initButtons() {
        int frameWidth = Game.initGameWidth;
        int frameHeight = Game.initGameHeight;

        int buttonWidth = 200;
        int buttonHeight = 100;

        // First Button: Positioned at 1/3 of the frame's width
        float relativeX1 = (1.0f / 3.0f) - (float) buttonWidth / (2 * frameWidth);
        float relativeY = 0.5f - (float) buttonHeight / (2 * frameHeight);

        // Second Button: Positioned at 2/3 of the frame's width
        float relativeX2 = (2.0f / 3.0f) - (float) buttonWidth / (2 * frameWidth);
        // (relativeY remains the same)

        // Third Button: Positioned at the end of the frame's width
        float relativeX3 = 1.0f - (float) buttonWidth / frameWidth;

        AbsoluteCoordinate referencePoint = new AbsoluteCoordinate(0, 0); // Top-left corner as reference

        UiCoordinate button1Coordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, relativeX1, relativeY));
        buttons.add(new MyButton("Play", button1Coordinate, buttonWidth, buttonHeight, true));

        UiCoordinate button2Coordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, relativeX2, relativeY));
        buttons.add(new MyButton("Load Game", button2Coordinate, buttonWidth, buttonHeight, true));

        UiCoordinate button3Coordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, relativeX3, relativeY));
        buttons.add(new MyButton("Exit", button3Coordinate, buttonWidth, buttonHeight, true));
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
        game.getGameState().getUserSettings().setVolume(sliders.get(1).getValue());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (MyButton button : buttons) {
            if (button.contains(x, y)) {
                if (button.getText().equals("Play")) {
                    GameStates.gameState = GameStates.LEVELSELECT;
                } else if (button.getText().equals("Load Game")) {
                    game.loadGame(Constants.OtherConstants.SAVEGAMELOCATION);
                    GameStates.gameState = GameStates.LEVELSELECT;
                } else if (button.getText().equals("Exit")) {
                    System.exit(0);
                }
            }
        }
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
                    game.loadGame(Constants.OtherConstants.SAVEGAMELOCATION);
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
