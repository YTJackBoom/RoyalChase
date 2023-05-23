package scenes;

import basics.Game;
import basics.ImageAnalyser;
import controllers.EnemyController;
import controllers.TowerController;
import helpers.variables;
import uiElements.MyButtonBar;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Playing extends GameScenes implements SceneMethods{
    private EnemyController enemyController;
    private TowerController towerController;
    private ImageAnalyser imageAnalyser;
    private MyButtonBar buttonBar;
    private int currentLevel;

    public Playing(Game game) {
        super(game);
        imageAnalyser = new ImageAnalyser(getCurrentPMapFile());

        enemyController = new EnemyController(this, imageAnalyser.imgToPath());
        towerController = new TowerController(this);

        buttonBar = new MyButtonBar(this, new helpers.Coordinate(0, 700), 500, 100);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(variables.Maps.getMapBufferedImage(currentLevel), 0, 0, null);
        enemyController.render(g);
        towerController.render(g);
        buttonBar.render(g);
    }
    @Override
    public void update(){
        enemyController.update();
        //towerController.update();
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
            buttonBar.mouseMoved(x,y);
        }

    }

    @Override
    public void mousePressed(int x, int y) {
        if(y>=buttonBar.getPos().getY() && y<=buttonBar.getPos().getY()+buttonBar.getHeight()){
            buttonBar.mousePressed(x,y);
        }

    }

    @Override
    public void mouseReleased(int x, int y) {
        buttonBar.mouseReleased(x,y);

    }

    @Override
    public void mouseDragged(int x, int y) {
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

}
