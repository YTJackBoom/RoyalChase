package scenes;

import basics.Game;
import basics.ImageAnalyser;
import controllers.EnemyController;
import controllers.ProjectileController;
import controllers.TowerController;
import controllers.WaveController;
import helpers.variables;
import uiElements.MyButtonBar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Playing extends GameScenes implements SceneMethods{
    private EnemyController enemyController;
    private TowerController towerController;
    private WaveController waveController;
    private ProjectileController projectileController;
    private ImageAnalyser imageAnalyser;
    private MyButtonBar buttonBar;
    private int currentLevel,selectedTower;
    private int mouseX, mouseY;
    private boolean towerSelected, dragingTower;

    private int gold;

    public Playing(Game game) {
        super(game);
        imageAnalyser = new ImageAnalyser(getCurrentPMapFile());

        enemyController = new EnemyController(this, imageAnalyser.imgToPath());
        towerController = new TowerController(this);
        waveController = new WaveController(this);
        projectileController = new ProjectileController(this);

        buttonBar = new MyButtonBar(this, new helpers.Coordinate(0, 700), 500, 100);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(variables.Maps.getMapBufferedImage(currentLevel), 0, 0, null);
        enemyController.render(g);
        towerController.render(g);
        projectileController.render(g);
        buttonBar.render(g);
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
    @Override
    public void mouseClicked(int x, int y) {
        if(y>=buttonBar.getPos().getY() && y<=buttonBar.getPos().getY()+buttonBar.getHeight()){
            buttonBar.mouseClicked(x,y);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y>=buttonBar.getPos().getY() && y<=buttonBar.getPos().getY()+buttonBar.getHeight()){
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
        if(y>=buttonBar.getPos().getY() && y<=buttonBar.getPos().getY()+buttonBar.getHeight()){

        }

    }



    //Getters and Setters
    public File getCurrentPMapFile(){
        return helpers.variables.Maps.getPMapFile(currentLevel);
    }
    public BufferedImage getCurrentMapFile(){
        return helpers.variables.Maps.getMapBufferedImage(currentLevel);
    }
    public int getCurrentLevel(){
        return currentLevel;
    }
    public void setCurrentLevel(int currentLevel){
        this.currentLevel = currentLevel;
    }
    public EnemyController getEnemyController(){
        return enemyController;
    }
    public ImageAnalyser getImageAnalyser() {return imageAnalyser;}

    public void setTowerSelected(boolean towerSelected) {
        this.towerSelected = towerSelected;
    }
    public void setSelectedTower(int selectedTower) {
        this.selectedTower = selectedTower;
    }
    public boolean isTowerSelected() {
        return towerSelected;
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
    public void setGold(int i){
        gold=+i;
    }

    public int getGold() {
        return gold;
    }
}
