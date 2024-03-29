package scenes;

import basics.Game;
import controllers.*;
import gameObjects.Tower;
import helpers.*;
import uiElements.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static basics.Game.*;
import static helpers.AssetLocation.Buttons.COLLAPSE_U_B;
import static helpers.AssetLocation.Buttons.EXTEND_U_B;
import static playerinputs.MListener.MOUSEX;
import static playerinputs.MListener.MOUSEY;
import static scenes.GameStates.GAMEOVER;
import static scenes.GameStates.gameState;

public class Playing extends GameScenes implements SceneMethods {
    private EnemyController enemyController;
    private TowerController towerController;
    private WaveController waveController;
    private TileController tileController;
    private ProjectileController projectileController;


    private MyButtonBar buttonBarRight, buttonBarDown;
    private ArrayList<MyButton> buttonBarRightControls = new ArrayList<MyButton>();

    private BossBar bossBar;

    private InfoOverlay infoOverlay;
    private boolean cantAfford, recentlySold, recentlySoldRender;
    private int cantAffordCounter, recentlySoldCounter;
    private boolean isPaused = false;
    private Tower selectedTower;
    private Game game;

    private DialogController dialogController; //for the tutorial

    public Playing(Game game) {
        super(game);
        this.game = game;
        this.infoOverlay = game.getInfoOverlay();
        tileController = new TileController(this);

        enemyController = new EnemyController(this);
        towerController = new TowerController(this);
        waveController = new WaveController(this);
        projectileController = new ProjectileController(this);
        initBossBar();
        initDialogController();
        initButtonBars();


    }

    @Override
    public void render(Graphics g) {

        tileController.render(g);
//        g.drawImage(variables.Maps.getMapBufferedImage(playerValues.getLevel()), 0, 0, null);
        enemyController.render(g);
        towerController.render(g);
        projectileController.render(g);
        buttonBarDown.render(g);
        buttonBarRight.render(g);


        bossBar.render(g);
        renderCantAfford(g);
        renderRecentlySold(g);
        waveController.render(g);
        for (MyButton button : buttonBarRightControls) {
            button.render(g);
        }
        if (dragingObject) renderDraggedButton(g);
        if (game.getPlayerValues().getLevel() == 0) dialogController.render(g);

//        infoOverlay.render(g);
    }
    /**
     * Methode welche aufgerufen wird, wenn der hintergrund aus playing in anderen Fenstern aufgerufen werden soll
     */
    public void softRender(Graphics g) {
        tileController.render(g);
        towerController.render(g);
    }
    @Override
    public void update(){
        projectileController.update();
        towerController.update();
        enemyController.update();
        waveController.update();
        updateInfoOverlay();
        checkHealth();
        updateButtonBarDown();


    }


    public void initBossBar() {
        int width = 100;
        int height = 20;

        float relativeX = 0.5f - (float) width / (2 * fWIDTH); // Calculate relative X to account for BossBar's width.
        float relativeY = 0.0f; // At the top

        AbsoluteCoordinate referencePoint = new AbsoluteCoordinate(0, 0); // Top-left corner of the frame.
        RelativeCoordinate relativeCoordinate = new RelativeCoordinate(referencePoint, relativeX, relativeY);
        UiCoordinate uiCoordinate = new UiCoordinate(relativeCoordinate);

        bossBar = new BossBar(this, uiCoordinate, width, height);

    }

    public void initButtonBars() {
        // The right bar
        int widthr = 117;
        int heightr = 798;
        float relativeXr = 1.0f - (float) widthr / (fWIDTH);  // All the way to the right minus the width
        float relativeYr = 0.05f;

        // The bottom bar
        int widthd = 260;
        int heightd = 100;
        float relativeXd = 0.5f - (float) widthd / (fWIDTH);  // Centered horizontally
        float relativeYd = 1.0f - (float) heightd / fHEIGHT;  // At the bottom minus the height

        AbsoluteCoordinate referencePoint = new AbsoluteCoordinate(0, 0);  // Top-left corner as reference

        UiCoordinate uiCoordinateRight = new UiCoordinate(new RelativeCoordinate(referencePoint, relativeXr, relativeYr));

        UiCoordinate uiCoordinateDown = new UiCoordinate(new RelativeCoordinate(referencePoint, relativeXd, relativeYd));

        buttonBarRight = new MyButtonBar(this, uiCoordinateRight, widthr, heightr, UIPos.PLAYINGRIGHT);
        buttonBarDown = new MyButtonBar(this, uiCoordinateDown, widthd, heightd, UIPos.PLAYINGDOWN);
        buttonBarRight.setVisible(false);
        buttonBarDown.setVisible(false);

        initButtonBarControls();
    }


    public void initButtonBarControls() {
        int buttonHeight = 50; // Example height of the button; adjust as needed.
        int buttonWidth = 50; // Example width of the button; adjust as needed.

        // For the button's relative positioning
        float relativeX = -((float) buttonWidth / buttonBarRight.getWidth()); // to the left of buttonBarRight by the width of the button
        float relativeY = 0.5f - (0.5f * buttonHeight / buttonBarRight.getHeight()); // Vertically centered as a percentage

        AbsoluteCoordinate referencePoint = buttonBarRight.getUiCoordinate().getAbsolutePosition();

        UiCoordinate extendUiCoordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, 0.5f, relativeY, buttonBarRight.getWidth(), buttonBarRight.getHeight()));
        UiCoordinate collapseUiCoordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, relativeX, relativeY, buttonBarRight.getWidth(), buttonBarRight.getHeight()));

        buttonBarRightControls.add(new MyButton(EXTEND_U_B, extendUiCoordinate, buttonWidth, buttonHeight, true, false, buttonBarRight));
        buttonBarRightControls.add(new MyButton(COLLAPSE_U_B, collapseUiCoordinate, buttonWidth, buttonHeight, false, false, buttonBarRight));
        buttonBarRight.addChild(buttonBarRightControls.get(0));
        buttonBarRight.addChild(buttonBarRightControls.get(1));
    }


    public void initDialogController() {
        dialogController = new DialogController(this);
    }

    public void reset() {
        tileController = new TileController(this);

        enemyController = new EnemyController(this);
        towerController = new TowerController(this);
        waveController = new WaveController(this);
        projectileController = new ProjectileController(this);
    }

    public void renderDraggedButton(Graphics g) {

        BufferedImage draggedImage = AssetController.getInstance().getIcon("button_" + draggedObjectType);

        g.drawImage(draggedImage, MOUSEX-draggedImage.getWidth()/2 , MOUSEY-draggedImage.getHeight()/2, null);
    }
    public void renderCantAfford(Graphics g) {
        if (cantAfford) {
            g.setFont(Constants.UIConstants.CANTAFFORDFONT);
            g.setColor(Color.BLACK);
            g.drawString("Zu Teuer!", Game.initGameWidth / 2, initGameHeight / 2);
            cantAffordCounter++;

            if (cantAffordCounter >= fps * Constants.UIConstants.CANTAFFORDTIMEONSCREEN) {
                cantAfford = false;
                cantAffordCounter = 0;
            }
        }
    }
    public void renderRecentlySold(Graphics g) {
        if (recentlySold) {
            if(recentlySoldRender) {
                g.setFont(Constants.UIConstants.RECENTLYSOLDFONT);
                g.setColor(Color.RED);
                g.drawString("Du hast kürzlich einen Turm verkauft!", initGameWidth / 2, initGameHeight / 2);
                recentlySoldCounter++;
            }

            if (recentlySoldCounter >= fps * Constants.UIConstants.RECENTLYSOLDTIMEONSCREEN) {
                recentlySold = false;
                recentlySoldRender = false;
                recentlySoldCounter = 0;
            }
        }
    }
    public void checkHealth() {
        if (game.getPlayerValues().getHealth() <= 0) {
            gameState = GAMEOVER;
//            game.resetAll();
//            playerValues.reset();
            selectedTower = null;
            updateInfoOverlay();
        }
    }
    public void updateButtonBarDown() {
        if(selectedTower != null) {
            buttonBarDown.setPointer(selectedTower);
            buttonBarDown.setVisible(true);
        } else {
            buttonBarDown.setVisible(false);
        }
    }
    public void updateInfoOverlay() {
        infoOverlay.setTowerPointer(selectedTower);
        infoOverlay.setDraggedTowerType(draggedObjectType);
        infoOverlay.setHoveredButton(buttonBarRight.getHoveredButton() != null ? buttonBarRight.getHoveredButton() : buttonBarDown.getHoveredButton());
        if (buttonBarDown.getHoveredButton() == null && buttonBarRight.getHoveredButton() == null)
            infoOverlay.setHoveredButton(null);

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() ==1) {
            if (buttonBarRight.contains(MOUSEX, MOUSEY)) {
                buttonBarRight.mouseClicked(MOUSEX, MOUSEY);
            }
            if (buttonBarDown.contains(MOUSEX, MOUSEY)) {
                buttonBarDown.mouseClicked(MOUSEX, MOUSEY);
            }
            towerController.mouseClicked(MOUSEX, MOUSEY);
        } else if (e.getButton() ==3) {
            selectedTower =null;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        buttonBarRight.mouseMoved(MOUSEX, MOUSEY);
        buttonBarDown.mouseMoved(MOUSEX, MOUSEY);


    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton()==1) {
            if (buttonBarRight.contains(MOUSEX, MOUSEY)) {
                buttonBarRight.mousePressed(MOUSEX, MOUSEY);
            } else if (buttonBarDown.contains(MOUSEX, MOUSEY)) {
                buttonBarDown.mousePressed(MOUSEX, MOUSEY);
            }
        } else if (e.getButton()==3) {
            selectedTower =null;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1) {
            if (game.getPlayerValues().getLevel() == 0) dialogController.mouseReleased(MOUSEX, MOUSEY);
            tileController.mouseReleased(MOUSEX, MOUSEY);
            if (buttonBarDown.contains(MOUSEX, MOUSEY)) buttonBarDown.mouseReleased(MOUSEX, MOUSEY);
            if (buttonBarRight.contains(MOUSEX, MOUSEY)) buttonBarRight.mouseReleased(MOUSEX, MOUSEY);
            if (dragingObject) {
                towerController.mouseReleased(MOUSEX, MOUSEY);
                dragingObject = false;
            }
            checkButtonBarControls(MOUSEX, MOUSEY);
        }
    }

    public void checkButtonBarControls(int x, int y) {
        MyButton extendButtonBarRight = buttonBarRightControls.get(0);
        MyButton collapseButtonBarRight = buttonBarRightControls.get(1);
        if (extendButtonBarRight.contains(x, y) && extendButtonBarRight.isVisible()) {
            buttonBarRight.setVisible(true);
            extendButtonBarRight.setVisible(false);
            collapseButtonBarRight.setVisible(true);
        }

        if (collapseButtonBarRight.contains(x, y) && collapseButtonBarRight.isVisible()) {
            buttonBarRight.setVisible(false);
            collapseButtonBarRight.setVisible(false);
            extendButtonBarRight.setVisible(true);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        tileController.mouseDragged(MOUSEX, MOUSEY);
    }

    public void resetBools() {
        dragingObject = false;
        selectedTower = null;
        update();
    }



    //Getters and Setters
    public TileController getTileController() {
        return tileController;
    }
    public MyButtonBar getButtonBar(Direction dir) {
        return switch (dir) {
            case RIGHT -> buttonBarRight;
            case DOWN -> buttonBarDown;
            default -> null;
        };
    }
    public TowerController getTowerController() {return towerController;}
    public EnemyController getEnemyController(){
        return enemyController;
    }

    public WaveController getWaveController() {
        return waveController;
    }


    public void setDraggedTower(int draggedTower) {
        draggedObjectType = draggedTower;
    }
    public boolean getDragingTower() {
        return dragingObject;
    }

    public int getDraggedTower() {
        return draggedObjectType;
    }

    public void setDragingTower(boolean b) {
        dragingObject = b;
    }

    public ProjectileController getProjectileController() {
        return projectileController;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean b) {
        isPaused = b;
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    public void setCantAfford(boolean b) {
        cantAfford = b;
    }

    public void setSelectedTower(Tower t) {
        selectedTower = t;
    }

    public boolean getRecentlySold() {
        return recentlySold;
    }

    public void setRecentlySold(boolean b) {
        recentlySold = b;
    }

    public void setRecentlySoldRender(boolean b) {
        recentlySoldRender = b;
    }


}
