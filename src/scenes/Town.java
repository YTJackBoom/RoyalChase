package scenes;

import basics.Game;
import controllers.BuildingsController;
import controllers.DialogController;
import helpers.Constants;
import helpers.variables;
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
        townImage = variables.Town.getBackgroundImage();
    }

    public void initButtonBar() {
        int width = 120;
        int height = 1000;

        int x = game.getWidth() - width - 20;
        int y = game.getHeight() - height - 20;

        buttonBar = new MyButtonBar(this, new helpers.Coordinate(x, y), width, height, UIPos.TOWNRIGHT);

        buttonBarControls.add(new MyButton(10, 500, 500, 50, 50, true));
        buttonBarControls.add(new MyButton(11, 600, 500, 50, 50, false));

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
        BufferedImage draggedImage;
        try {
            draggedImage = ImageIO.read(helpers.variables.Buttons.getButtonImageFile(draggedObjectType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g.drawImage(draggedImage, mouseX-draggedImage.getWidth()/2 , mouseY-draggedImage.getHeight()/2, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
//        if(buttonBar.getBounds().contains(mouseX,mouseY)){
//            buttonBar.mouseClicked(mouseX,mouseY);
//        }
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
            if (buttonBar.getBounds().contains(mouseX, mouseY)) {
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
            if (buttonBar.getBounds().contains(mouseX, mouseY)) buttonBar.mouseReleased(mouseX, mouseY);
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
        if (extendButtonBarRight.getBounds().contains(x, y) && extendButtonBarRight.isVisible()) {
            buttonBar.setVisible(true);
            extendButtonBarRight.setVisible(false);
            collapseButtonBarRight.setVisible(true);
        }

        if (collapseButtonBarRight.getBounds().contains(x, y) && collapseButtonBarRight.isVisible()) {
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
