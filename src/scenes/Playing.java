package scenes;

import basics.Direction;
import basics.Game;
import controllers.*;
import gameObjects.Tower;
import helpers.Constants;
import helpers.ImageAnalyser;
import helpers.Values;
import helpers.variables;
import uiElements.InfoOverlay;
import uiElements.MyButton;
import uiElements.MyButtonBar;
import uiElements.UIPos;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static basics.Game.fps;
import static basics.GameScreen.fHEIGHT;
import static basics.GameScreen.fWIDTH;
import static helpers.Values.HEALTH;
import static helpers.Values.LEVEL;
import static scenes.GameStates.GAMEOVER;
import static scenes.GameStates.gameState;

public class Playing extends GameScenes implements SceneMethods{
    private EnemyController enemyController;
    private TowerController towerController;
    private WaveController waveController;
    private ProjectileController projectileController;
    private ImageAnalyser imageAnalyser;
    private MyButtonBar buttonBarRight,buttonBarDown;
    private InfoOverlay infoOverlay;
    private int draggedTower;
    private int mouseX, mouseY;
    private boolean dragingTower;
    private boolean cantAfford;
    private int cantAffordCounter;
    private boolean isPaused = false;
    private Tower selectedTower;
    private Game game;

    public Playing(Game game) {
        super(game);
        this.game = game;
        this.infoOverlay = game.getInfoOverlay();
        imageAnalyser = new ImageAnalyser(getCurrentPMapFile());

        enemyController = new EnemyController(this, imageAnalyser.imgToPath());
        towerController = new TowerController(this);
        waveController = new WaveController(this);
        projectileController = new ProjectileController(this);

        initButtonBars();


    }

    @Override
    public void render(Graphics g) {
        g.drawImage(variables.Maps.getMapBufferedImage(LEVEL), 0, 0, null);
        enemyController.render(g);
        towerController.render(g);
        projectileController.render(g);
        buttonBarDown.render(g);
        buttonBarRight.render(g);

        renderCantAfford(g);
        waveController.render(g);
        if (dragingTower) {
            renderDraggedButton(g);
        }
//        infoOverlay.render(g);
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

    public void initButtonBars() {
        //the right bar
        int widthr = 120;
        int heightr = 1000;
        int xr = fWIDTH - widthr - 20;
        int yr = fHEIGHT - heightr - 20;
        //the buttom bar
        int widthd = 260;
        int heightd = 100;
        int xd = fWIDTH/2-widthd;
        int yd = fHEIGHT-10-heightd;

        buttonBarRight = new MyButtonBar(this, new helpers.Coordinate(xr, yr), widthr, heightr, UIPos.PLAYINGRIGHT);
        buttonBarDown = new MyButtonBar(this,new helpers.Coordinate(xd, yd),widthd,heightd,UIPos.PLAYINGDOWN);

    }
    public void reset() {
        imageAnalyser = new ImageAnalyser(getCurrentPMapFile());
        enemyController = new EnemyController(this, imageAnalyser.imgToPath());
        towerController = new TowerController(this);
        waveController = new WaveController(this);
        projectileController = new ProjectileController(this);
    }
    public void renderDraggedButton(Graphics g) {

        BufferedImage draggedImage;
        try {
            draggedImage = ImageIO.read(helpers.variables.Buttons.getButtonImageFile(draggedTower));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g.drawImage(draggedImage, mouseX-draggedImage.getWidth()/2 , mouseY-draggedImage.getHeight()/2, null);
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
    public void checkHealth() {
        if (HEALTH <= 0) {
            gameState = GAMEOVER;
            game.resetAll();
            Values.reset();
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
        infoOverlay.setDraggedTowerType(draggedTower);
        infoOverlay.setHoveredButton(buttonBarRight.getHoveredButton());
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if(e.getButton() ==1) {
            infoOverlay.mouseClicked(x,y);

            if (buttonBarRight.getBounds().contains(x,y)) {
                buttonBarRight.mouseClicked(x, y);
            }
            if (buttonBarDown.getBounds().contains(x,y)) {
                buttonBarDown.mouseClicked(x,y);
            }
            towerController.mouseClicked(x, y);
        } else if (e.getButton() ==3) {
            selectedTower =null;
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(buttonBarRight.getBounds().contains(x,y)){
            buttonBarRight.setVisible(true);
            buttonBarRight.mouseMoved(x,y);
        } else{
            buttonBarRight.setVisible(false);
            buttonBarRight.setHoveredButton(null);
        }
        buttonBarDown.mouseMoved(x,y);


    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if(e.getButton()==1) {
            infoOverlay.mousePressed(mouseX,mouseY);

            if (buttonBarRight.getBounds().contains(mouseX, mouseY)) {
                buttonBarRight.mousePressed(mouseX, mouseY);
            } else if (buttonBarDown.getBounds().contains(mouseX, mouseY)) {
                buttonBarDown.mousePressed(mouseX, mouseY);
            }
        } else if (e.getButton()==3) {
            selectedTower =null;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if(e.getButton()==1) {
            buttonBarRight.mouseReleased(x, y);
            buttonBarDown.mouseReleased(x,y);
            if (dragingTower) {
                towerController.mouseReleased(x, y);
                dragingTower = false;
            }

        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        mouseX = x;
        mouseY = y;
        infoOverlay.mouseDragged(x,y);
    }

    public void resetBools() {
        dragingTower = false;
        selectedTower = null;
        update();
    }



    //Getters and Setters
    public MyButtonBar getButtonBar(Direction dir) {
        return switch (dir) {
            case RIGHT -> buttonBarRight;
            case DOWN -> buttonBarDown;
            default -> null;
        };
    }
    public TowerController getTowerController() {return towerController;}
    public File getCurrentPMapFile(){
        return helpers.variables.Maps.getPMapFile(LEVEL);
    }
    public BufferedImage getCurrentMapFile(){
        return helpers.variables.Maps.getMapBufferedImage(LEVEL);
    }
    public EnemyController getEnemyController(){
        return enemyController;
    }

    public WaveController getWaveController() {
        return waveController;
    }
    public ImageAnalyser getImageAnalyser() {return imageAnalyser;}


    public void setDraggedTower(int draggedTower) {
        this.draggedTower = draggedTower;
    }
    public boolean getDragingTower() {
        return dragingTower;
    }

    public int getDraggedTower() {
        return draggedTower;
    }
    public void setDragingTower(boolean b) {
        dragingTower = b;
    }
    public ProjectileController getProjectileController() {
        return projectileController;
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
    public void setCantAfford(boolean b) {cantAfford=b;}
    public void setSelectedTower(Tower t) {selectedTower = t;}
}
