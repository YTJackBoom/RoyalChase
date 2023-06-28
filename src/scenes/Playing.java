package scenes;

import basics.Game;
import helpers.Constants;
import helpers.ImageAnalyser;
import controllers.EnemyController;
import controllers.ProjectileController;
import controllers.TowerController;
import controllers.WaveController;
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
import static java.awt.image.ImageObserver.WIDTH;
import static scenes.GameStates.GAMEOVER;
import static scenes.GameStates.gameState;

public class Playing extends GameScenes implements SceneMethods{
    private EnemyController enemyController;
    private TowerController towerController;
    private WaveController waveController;
    private ProjectileController projectileController;
    private ImageAnalyser imageAnalyser;
    private MyPlayingButtonBar buttonBar;
    private int selectedTower;
    private int mouseX, mouseY;
    private boolean dragingTower;
    private boolean cantAfford;
    private int cantAffordCounter;
    private boolean isPaused = false;
    private Game game;

    public Playing(Game game) {
        super(game);
        this.game = game;
        imageAnalyser = new ImageAnalyser(getCurrentPMapFile());

        enemyController = new EnemyController(this, imageAnalyser.imgToPath());
        towerController = new TowerController(this);
        waveController = new WaveController(this);
        projectileController = new ProjectileController(this);

        initButtonBar();


    }

    @Override
    public void render(Graphics g) {
        g.drawImage(variables.Maps.getMapBufferedImage(LEVEL), 0, 0, null);
        enemyController.render(g);
        towerController.render(g);
        projectileController.render(g);
        buttonBar.render(g);
        renderCantAfford(g);
        waveController.render(g);
        if (dragingTower) {
            renderDraggedButton(g);
        }
    }
    @Override
    public void update(){
        projectileController.update();
        towerController.update();
        enemyController.update();
        waveController.update();
        checkHealth();
    }

    public void initButtonBar() {
        int width = 120;
        int height = 1000;

        int x = fWIDTH - width - 20;
        int y = fHEIGHT - height - 20;

        buttonBar = new MyPlayingButtonBar(this, new helpers.Coordinate(x, y), width, height);

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
            draggedImage = ImageIO.read(helpers.variables.Buttons.getButtonImageFile(selectedTower));
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
    @Override
    public void mouseClicked(int x, int y) {
        if(y>=buttonBar.getPos().getY() && y<=buttonBar.getPos().getY()+buttonBar.getHeight()){
            buttonBar.mouseClicked(x,y);
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
    public void mousePressed(int x, int y) {
        mouseX = x;
        mouseY = y;
        if(y>=buttonBar.getPos().getY() && y<=buttonBar.getPos().getY()+buttonBar.getHeight()){
            buttonBar.mousePressed(x,y);
        }

    }

    @Override
    public void mouseReleased(int x, int y) {
        buttonBar.mouseReleased(x,y);
        if (dragingTower) {
            towerController.mouseReleased(x, y);
            dragingTower = false;
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        mouseX = x;
        mouseY = y;

    }



    //Getters and Setters
    public MyPlayingButtonBar getButtonBar() {
        return buttonBar;
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


    public void setSelectedTower(int selectedTower) {
        this.selectedTower = selectedTower;
    }

    public int getSelectedTower() {
        return selectedTower;
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
}
