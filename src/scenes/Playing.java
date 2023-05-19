package scenes;

import basics.Game;
import controllers.EnemyController;
import controllers.TowerController;

import java.awt.*;

public class Playing extends GameScenes implements SceneMethods{
    private EnemyController enemyController;
    private TowerController towerController;
    public Playing(Game game) {
        super(game);

        enemyController = new EnemyController(this);
        towerController = new TowerController(this;
    }

    @Override
    public void render(Graphics g) {

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
    public void update(){

    }
}
