package scenes;

import basics.Game;
import controllers.AssetController;
import controllers.BuildingsController;
import controllers.DialogController;
import helpers.*;
import uiElements.InfoOverlay;
import uiElements.MyButton;
import uiElements.MyButtonBar;
import uiElements.UIPos;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static basics.Game.*;
import static helpers.AssetLocation.Buttons.COLLAPSE_U_B;
import static helpers.AssetLocation.Buttons.EXTEND_U_B;

public class Town extends GameScenes implements SceneMethods {
    private Game game;
    private boolean isPaused = false;
    private int mouseX, mouseY;

    private BuildingsController buildingsController;
    private MyButtonBar buttonBar;
    private ArrayList<MyButton> buttonBarControls = new ArrayList<MyButton>();

    private BufferedImage townImage;
    private boolean cantAfford;
    private int cantAffordCounter;
    private InfoOverlay infoOverlay;

    private DialogController dialogController; //fürs tutorial

    public Town(Game game) {
        super(game);
        this.game = game;
        infoOverlay = game.getInfoOverlay();
        initClasses();
        initVariables();
        initButtonBar();

    }


    private void initClasses() {
        buildingsController = new BuildingsController(this);
        dialogController = new DialogController(this);

    }

    private void initVariables() {
        townImage = AssetLocation.Town.getBackgroundImage();
    }

    public void initButtonBar() {
        // The right bar
        int widthr = 117;
        int heightr = 798;
        float relativeXr = 1.0f - (float) widthr / (fWIDTH);  // All the way to the right minus the width
        float relativeYr = 0.05f;

        AbsoluteCoordinate referencePoint = new AbsoluteCoordinate(0, 0);  // Top-left corner as reference

        UiCoordinate uiCoordinateRight = new UiCoordinate(new RelativeCoordinate(referencePoint, relativeXr, relativeYr));

        buttonBar = new MyButtonBar(this, uiCoordinateRight, widthr, heightr, UIPos.TOWNRIGHT);

        initButtonBarControls();
    }

    public void initButtonBarControls() {
        int buttonHeight = 50; // Example height of the button; adjust as needed.
        int buttonWidth = 50; // Example width of the button; adjust as needed.

        // For the button's relative positioning
        float relativeX = 0f; // At the left edge of the buttonBarRight
        float relativeY = 50f - (0.5f * buttonHeight / buttonBar.getHeight()); // Vertically centered as a percentage

        AbsoluteCoordinate referencePoint = buttonBar.getUiCoordinate().getAbsolutePosition();

        UiCoordinate sharedUiCoordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, relativeX, relativeY, buttonBar.getWidth(), buttonBar.getHeight()));

        buttonBarControls.add(new MyButton(EXTEND_U_B, sharedUiCoordinate, buttonWidth, buttonHeight, true, false, buttonBar));
        buttonBarControls.add(new MyButton(COLLAPSE_U_B, sharedUiCoordinate, buttonWidth, buttonHeight, false, false, buttonBar));
        buttonBar.addChild(buttonBarControls.get(0));
        buttonBar.addChild(buttonBarControls.get(1));
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(townImage, 0, 0, null);
        buildingsController.render(g);

        buttonBar.render(g);
        for (MyButton button : buttonBarControls) {
            button.render(g);
        }

        if (dragingObject) renderDraggedButton(g);
        renderCantAfford(g);

        if (game.getPlayerValues().getLevel() == 0) dialogController.render(g);


    }

    public void renderCantAfford(Graphics g) {
        if (cantAfford) {
            g.setFont(Constants.UIConstants.CANTAFFORDFONT);
            g.setColor(Color.BLACK);
            g.drawString("Cant Afford!", initGameWidth / 2, initGameHeight / 2);
            cantAffordCounter++;
//			System.out.println("s ");
//			System.out.print(fWIDTH+" "+fHEIGHT);
            if (cantAffordCounter >= fps * Constants.UIConstants.CANTAFFORDTIMEONSCREEN) {
                cantAfford = false;
                cantAffordCounter = 0;
            }
        }
    }

    @Override
    public void update() {
        buildingsController.update();
        updateInfoOverlay();
    }
    public void softUpdate() {
        buildingsController.update();
    }

    public void updateInfoOverlay() {
        infoOverlay.setHoveredButton(buttonBar.getHoveredButton());
    }
    public void renderDraggedButton(Graphics g) {
        BufferedImage draggedImage = AssetController.getInstance().getIcon("button_" + draggedObjectType);
        g.drawImage(draggedImage, mouseX-draggedImage.getWidth()/2 , mouseY-draggedImage.getHeight()/2, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x =e.getX();
        int y =e.getY();
            buttonBar.mouseMoved(x,y);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if (e.getButton()==1) {
            if (buttonBar.contains(mouseX, mouseY)) {
                buttonBar.mousePressed(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        if (e.getButton() == 1) {
            if (game.getPlayerValues().getLevel() == 0) dialogController.mouseReleased(mouseX, mouseY);
            if (buttonBar.contains(mouseX, mouseY)) buttonBar.mouseReleased(mouseX, mouseY);
            if (dragingObject) {
                buildingsController.mouseReleased(mouseX, mouseY);
                dragingObject = false;
            }
            checkButtonBarControls(mouseX, mouseY);
        }
    }

    public void checkButtonBarControls(int x, int y) {
        MyButton extendButtonBarRight = buttonBarControls.get(0);
        MyButton collapseButtonBarRight = buttonBarControls.get(1);
        if (extendButtonBarRight.contains(x, y) && extendButtonBarRight.isVisible()) {
            buttonBar.setVisible(true);
            extendButtonBarRight.setVisible(false);
            collapseButtonBarRight.setVisible(true);
        }

        if (collapseButtonBarRight.contains(x, y) && collapseButtonBarRight.isVisible()) {
            buttonBar.setVisible(false);
            collapseButtonBarRight.setVisible(false);
            extendButtonBarRight.setVisible(true);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

    }

    @Override
    public void reset() {
        buildingsController = new BuildingsController(this);
        // TODO: reset towen
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


    public void setSelectedBuilding(int type) {
        draggedObjectType = type;
    }

    public void setDragingBuilding(boolean b) {
        dragingObject = b;
    }

    public int getSelectedBuilding() {
        return draggedObjectType;
    }
    public void setCantAfford(boolean b) {
        cantAfford = b;
    }
    public BuildingsController getBuildingsController() {
        return buildingsController;
    }
}
