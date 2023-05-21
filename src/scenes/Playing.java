package scenes;

import basics.Game;
import basics.ImageAnalyser;
import controllers.EnemyController;
import controllers.TowerController;
import helpers.variables;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Playing extends GameScenes implements SceneMethods{
    private EnemyController enemyController;
    private TowerController towerController;
    private ImageAnalyser imageAnalyser;
    private int currentLevel;
    public Playing(Game game) {
        super(game);
        imageAnalyser = new ImageAnalyser(getCurrentPMapFile());

        enemyController = new EnemyController(this, imageAnalyser.imgToPath());
        towerController = new TowerController(this);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(variables.Maps.getMapBufferedImage(currentLevel), 0, 0, null);
        enemyController.render(g);
        towerController.render(g);
    }
    @Override
    public void update(){
        enemyController.update();
        //towerController.update();
    }

    @Override
    public void mouseClicked(int x, int y) {

    }

    @Override
    public void mouseMoved(int x, int y) {

    }

    @Override
    public void mousePressed(int x, int y) {

    }

    @Override
    public void mouseReleased(int x, int y) {

    }

    @Override
    public void mouseDragged(int x, int y) {

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

}
