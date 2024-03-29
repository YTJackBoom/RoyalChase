package scenes;

import basics.Game;
import helpers.AbsoluteCoordinate;
import helpers.RelativeCoordinate;
import helpers.UiCoordinate;
import helpers.Values;
import uiElements.MyButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;

/**
 * Scene Klasse für das LevelSelect (= Levelauswahl durch den Spieler)
 */
public class LevelSelect extends GameScenes implements SceneMethods{
    private Game game;
    private ArrayList<MyButton> buttons = new ArrayList<MyButton>();
    private GameStates backScene;

    public LevelSelect(Game game) {
        super(game);
        this.game = game;

        initButtons();

    }

    /**
     * Initialisieren der Buttons
     */
    public void initButtons() {
        int frameWidth = Game.initGameWidth;
        int frameHeight = Game.initGameHeight;

        int buttonsAmount = 9;
        int buttonsPerLine = 3;
        int buttonWidth = 150;
        int buttonHeight = 150;
        float buttonXOffsetPct = 200.0f / frameWidth;
        float buttonYOffsetPct = 200.0f / frameHeight;

        // Adjusted starting points based on the middle button's position
        float buttonXStartPct = 0.5f - (buttonWidth/2) / (float) frameWidth - buttonXOffsetPct;
        float buttonYStartPct = 0.5f - (buttonHeight/2) / (float) frameHeight - buttonYOffsetPct;


        AbsoluteCoordinate referencePoint = new AbsoluteCoordinate(0, 0); // Top-left corner as reference

        for (int i = 0; i < buttonsAmount; i++) { // Platziert die buttons zur Level-Auswahl in einem 3x3 Raster
            float x = buttonXStartPct + (i % buttonsPerLine) * buttonXOffsetPct;
            float y = buttonYStartPct + (i / buttonsPerLine) * buttonYOffsetPct;

            UiCoordinate buttonCoordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, x, y, frameWidth, frameHeight));

            buttons.add(new MyButton((i + 1), buttonCoordinate, buttonWidth, buttonHeight, true));
        }

        float tutorialX = buttonXStartPct - buttonXOffsetPct;
        UiCoordinate tutorialCoordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, tutorialX, buttonYStartPct, frameWidth, frameHeight));
        buttons.add(new MyButton(0, tutorialCoordinate, buttonWidth, buttonHeight, true)); // Tutorial

        float backButtonX = buttonXStartPct - (buttonXOffsetPct + buttonXOffsetPct / 2);
        float backButtonY = buttonYStartPct - buttonYOffsetPct / 2;
        UiCoordinate backButtonCoordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, backButtonX, backButtonY, frameWidth, frameHeight));
        buttons.add(new MyButton("Zurück", backButtonCoordinate, buttonWidth / 2, buttonHeight / 2, true));
    }


    @Override
    public void render(Graphics g) {
        renderBackground(g);
        renderButtons(g);
        renderTicks(g);

    }


    public void renderBackground(Graphics g) {
        BufferedImage image = new BufferedImage(game.getWidth(), game.getHeight(), BufferedImage.TYPE_INT_ARGB); //der hintergrund (= das Spielfeld) wird als buffered image aufgenommen
        Graphics2D g2d = image.createGraphics();
        game.getTown().softRender(g2d);
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

    /**
     * Zeichnet die Haken auf die Level, die der Spieler bereits geschafft hat
     * @param g Graphics Objekt
     */
    public void renderTicks(Graphics g) {
        for (MyButton button : buttons) {
            if (button.isChecked()) {

                int x = (int) (button.getUiCoordinate().getX() + button.getWidth() / 2 - 10);
                int y = (int) (button.getUiCoordinate().getY() + button.getHeight() / 2 - 10);

                g.setColor(Color.GREEN);

                g.drawLine(x, y, x + 10, y + 10);
                g.drawLine(x + 10, y + 10, x + 20, y - 10);
            }
        }
    }
    @Override
    public void update() {
        for (MyButton button : buttons) {
            button.setChecked(game.getPlayerValues().getLevelscleared().contains(button.getLevel()));
        }
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

        for(MyButton button: buttons){
            if (button.contains(x, y)) {
                button.setPressed(true);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for (MyButton button: buttons){
            if (button.contains(x, y)) {
                if (button.getLevel() >= 0) {
                    if (button.isChecked()) {
                        game.getPlayerValues().setRewardmultiplyer(0); // Wenn der Spieler ein Level wiederholt, bekommt er keine Belohnung
                    } else {
                        game.getPlayerValues().setRewardmultiplyer(1);
                    }
                    game.getPlayerValues().setLevel(button.getLevel());

//                    System.out.println("Level " + button.getText());
                    game.getPlaying().reset();
                    GameStates.gameState = GameStates.PLAYING;
                    if (button.getLevel() == 0){
                        game.setPaused(true); // Beim Tutorial wird das Spiel (zunächst) pausiert+
                        game.getPlaying().initDialogController();
                    }
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
            case "Zurück" -> {
                GameStates.gameState = backScene;
            }
            default -> System.out.println("THis Button should not exist: " + b.getText());
        }
    }

    public void resetButtons() {
        for (MyButton button : buttons) {
            button.resetBools();
        }
    }



    public void setBackScene(GameStates gameStates) {
        this.backScene = gameStates;
    }
}
