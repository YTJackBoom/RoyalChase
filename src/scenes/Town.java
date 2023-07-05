package scenes;

import basics.Game;
import controllers.BuildingsController;
import controllers.TowerController;
import helpers.Constants;
import helpers.variables;
import uiElements.MyPlayingButtonBar;
import uiElements.MyTownButtonBar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static basics.Game.fps;
import static basics.GameScreen.fHEIGHT;
import static basics.GameScreen.fWIDTH;

public class Town extends GameScenes implements SceneMethods{
    private Game game;
    private boolean isPaused = false;
    private boolean dragingBuilding;
    private int mouseX,mouseY;

    private BuildingsController buildingsController;
    private MyTownButtonBar buttonBar;
    private BufferedImage townImage;
    private int selectedBuilding;
    private boolean cantAfford;
    private int cantAffordCounter;
    public Town(Game game){
        super(game);
        this.game = game;
        initClasses();
        initVariables();
        initButtonBar();
    }


    private void initClasses() {
        buildingsController = new BuildingsController(this);
    }

    private void initVariables() {
        townImage = variables.Town.getBackgroundImage();
    }

    public void initButtonBar() {
            int width = 120;
            int height = 1000;

            int x = game.getWidth() - width - 20;
            int y = game.getHeight() - height - 20;

            buttonBar = new MyTownButtonBar(this, new helpers.Coordinate(x, y), width, height);

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(townImage, 0, 0, null);
        buildingsController.render(g);
        buttonBar.render(g);
        if (dragingBuilding) {
            renderDraggedButton(g);
        }
        renderCantAfford(g);
    }

    public void renderCantAfford(Graphics g) {
        if (cantAfford) {
            g.setFont(Constants.UIConstants.CANTAFFORDFONT);
            g.setColor(Color.BLACK);
            g.drawString("Cant Afford!", fWIDTH / 2, fHEIGHT / 2);
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
    }
    public void softUpdate() {
        buildingsController.update();
    }

    public void renderDraggedButton(Graphics g) {
        BufferedImage draggedImage;
        try {
            draggedImage = ImageIO.read(helpers.variables.Buttons.getButtonImageFile(selectedBuilding));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g.drawImage(draggedImage, mouseX-draggedImage.getWidth()/2 , mouseY-draggedImage.getHeight()/2, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX=e.getX();
        mouseY=e.getY();
        if(buttonBar.getBounds().contains(mouseX,mouseY)){
            buttonBar.mouseClicked(mouseX,mouseY);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(buttonBar.getBounds().contains(x,y)){
            buttonBar.setVisible(true);
            buttonBar.mouseMoved(x,y);
        } else{
            buttonBar.setVisible(false);
        }

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

//  System.out.println("ad");
        if(e.getButton()==1) {
            buttonBar.mouseReleased(mouseX,mouseY);
            if (dragingBuilding) {
                buildingsController.mouseReleased(mouseX,mouseY);
                dragingBuilding = false;
            }
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        mouseX = x;
        mouseY = y;

    }

    @Override
    public void reset() {
        buildingsController = new BuildingsController(this);
            // TODO: reset towen
    }

    public boolean isPaused() {
        return isPaused;
    }
    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    public void setSelectedBuilding(int type) {
        selectedBuilding = type;
    }

    public void setDragingBuilding(boolean b) {
        dragingBuilding = b;
    }

    public int getSelectedBuilding() {
        return selectedBuilding;
    }
    public void setCantAfford(boolean b) {
        cantAfford = b;
    }
}
