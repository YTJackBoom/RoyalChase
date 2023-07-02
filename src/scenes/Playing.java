package scenes;

import basics.Direction;
import basics.Game;
import controllers.*;
import gameObjects.Tower;
import helpers.Constants;
import helpers.ImageAnalyser;
import helpers.Values;
import helpers.variables;
import uiElements.MyPlayingButtonBar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    private MyPlayingButtonBar buttonBarRight,buttonBarDown;
    private RangeController rangeController;
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
        imageAnalyser = new ImageAnalyser(getCurrentPMapFile());

        enemyController = new EnemyController(this, imageAnalyser.imgToPath());
        towerController = new TowerController(this);
        waveController = new WaveController(this);
        projectileController = new ProjectileController(this);
        rangeController = new RangeController(this);

        initButtonBars();


    }

    @Override
    public void render(Graphics g) {
        g.drawImage(variables.Maps.getMapBufferedImage(LEVEL), 0, 0, null);
        enemyController.render(g);
        towerController.render(g);
        projectileController.render(g);
        buttonBarRight.render(g);
        buttonBarDown.render(g);
        renderCantAfford(g);
        waveController.render(g);
        if (dragingTower) {
            renderDraggedButton(g);
        }
        rangeController.render(g);
    }
    @Override
    public void update(){
        projectileController.update();
        towerController.update();
        enemyController.update();
        waveController.update();
        updateRangeController();
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
        int widthd = 100;
        int heightd = 100;
        int xd = fWIDTH/2-widthd;
        int yd = fHEIGHT-10-heightd;

        buttonBarRight = new MyPlayingButtonBar(this, new helpers.Coordinate(xr, yr), widthr, heightr, Direction.RIGHT);
        buttonBarDown = new MyPlayingButtonBar(this,new helpers.Coordinate(xd, yd),widthd,heightd,Direction.DOWN);

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
    public void updateRangeController() {
        rangeController.setPointer(selectedTower);
        rangeController.setDraggedPointer(draggedTower);
    }
    @Override
    public void mouseClicked(int x, int y) {
        if(y>=buttonBarRight.getPos().getY() && y<=buttonBarRight.getPos().getY()+buttonBarRight.getHeight()){
            buttonBarRight.mouseClicked(x,y);
        }
        towerController.mouseClicked(x,y);
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(buttonBarRight.getBounds().contains(x,y)){
            buttonBarRight.setVisible(true);
            buttonBarRight.mouseMoved(x,y);
        } else{
            buttonBarRight.setVisible(false);
        }
        buttonBarDown.mouseMoved(x,y);


    }

    @Override
    public void mousePressed(int x, int y) {
        mouseX = x;
        mouseY = y;
        if(y>=buttonBarRight.getPos().getY() && y<=buttonBarRight.getPos().getY()+buttonBarRight.getHeight()){
            buttonBarRight.mousePressed(x,y);
        }

    }

    @Override
    public void mouseReleased(int x, int y) {
        buttonBarRight.mouseReleased(x,y);
        if (dragingTower) {
            towerController.mouseReleased(x, y);
            dragingTower = false;
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        mouseX = x;
        mouseY = y;
        rangeController.mouseDragged(x,y);
    }



    //Getters and Setters
    public MyPlayingButtonBar getButtonBar(Direction dir) {
        return switch (dir) {
            case RIGHT -> buttonBarRight;
            case DOWN -> buttonBarDown;
            default -> null;
        };
    }
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
