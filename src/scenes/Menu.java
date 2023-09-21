package scenes;

import basics.Game;
import helpers.Constants;
import helpers.UserSettings;
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
        int sliderWidth = 200;
        int sliderHeight = 50;
        sliders.clear();
        sliders.add(new Slider(new Rectangle(100, 100, sliderWidth, sliderHeight), 0, 5, userSettings.getDifficulty()));
        sliders.add(new Slider(new Rectangle(100, 200, sliderWidth, sliderHeight), 0, 100, userSettings.getVolume()));
    }

    public void initButtons() {
        int buttonWidth = 200;
        int buttonHeight = 100;
        int buttonX = 300;
        int buttonY = 500;
        int offsetX = 200;
        buttons.add(new MyButton("Play", new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight), true));
        buttons.add(new MyButton("Load Game", new Rectangle(buttonX + offsetX, buttonY, buttonWidth, buttonHeight), true));
        buttons.add(new MyButton("Exit", new Rectangle(buttonX + 2 * offsetX, buttonY, buttonWidth, buttonHeight), true));
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
            if (button.getBounds().contains(x, y)) {
                if (button.getText().equals("Play")) {
                    GameStates.gameState = GameStates.LEVELSELECT;
                }else if(button.getText().equals("Load Game")){
                    game.loadGame(Constants.OtherConstants.SAVEGAMELOCATION);
                    GameStates.gameState = GameStates.LEVELSELECT;
                }else if(button.getText().equals("Exit")){
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

        for (MyButton button : buttons) {
            if (button.getBounds().contains(x, y)) {
                button.setPressed(true);
            }
        }
        for (Slider slider : sliders) {
            if (slider.getBounds().contains(x, y)) {
                slider.mousePressed(x, y);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (MyButton button: buttons){
            if(button.getBounds().contains(x,y)){
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
