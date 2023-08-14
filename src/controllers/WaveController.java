package controllers;

import helpers.Direction;
import basics.Game;
import helpers.Values;
import scenes.GameStates;
import helpers.Wave;
import helpers.WaveFileAnalyser;
import scenes.Playing;

import java.awt.*;
import java.util.ArrayList;

import static scenes.GameStates.LEVELCLEARED;
public class WaveController implements ControllerMethods {
    private Playing playing;
    private EnemyController enemyController;
    private WaveFileAnalyser waveFileAnalyser;
    private int currentWave, counter, counter2;
    private int cooldown = 400;
    private ArrayList<Wave> waves;
    private boolean generateNewWave = false;
    private Values playerValues;



    public WaveController(Playing playing) {
        this.playing = playing;
        playerValues = playing.getGame().getPlayerValues();
        enemyController = playing.getEnemyController();
        currentWave = 0;
        initWaves();
    }


    public void initWaves() {
        waveFileAnalyser = new WaveFileAnalyser(playerValues);
        int wavesNum = waveFileAnalyser.getWavesNum();
        waves = new ArrayList<Wave>();
        for(int i = 0; i < wavesNum; i++) {
            waveFileAnalyser.initArrayLists(i);
//            System.out.println(waveFileAnalyser.getSpawnList().size() + " " + waveFileAnalyser.getDelayList().size());
            waves.add(new Wave(waveFileAnalyser.getSpawnList(),waveFileAnalyser.getDelayList()));
        }

    }
    public void update() {
//        System.out.println(waves.get(currentWave).getCurrentIndex() + " " + waves.get(currentWave).getSpawnList().size()+" "+waves.get(currentWave).getCurrentDelay()+" "+counter2 );
        if (!playing.isPaused()) {

            if (waves.get(currentWave).getCurrentIndex() == waves.get(currentWave).getSpawnList().size()) {
                if (enemyController.getEnemyList().size() == 0) {
                    generateNewWave = true;
                }
            } else if (waves.get(currentWave).getCurrentDelay() != 0) {
                if (counter2 / waves.get(currentWave).getCurrentDelay() == 1) {
                    spawnEnemy();
                    counter2 = 0;
                } else counter2++;
            }
            updateNewWave();
        }
    }
    public void updateNewWave() {
        if(generateNewWave) {
            if (counter/cooldown == 1) {
                currentWave++;
                generateNewWave = false;
                counter = 0;
            }else {
                counter++;
            }
            if(currentWave+1 == waves.size()&&waves.get(currentWave).getCurrentIndex() == waves.get(currentWave).getSpawnList().size()&&enemyController.getEnemyList().size() == 0) {
                playing.getProjectileController().clearProjectiles();
                playing.setDragingTower(false);
                playing.setSelectedTower(null);
                playing.getButtonBar(Direction.RIGHT).setVisible(false);
                playing.getButtonBar(Direction.DOWN).setVisible(false);

                generateNewWave = false;
                GameStates.gameState = LEVELCLEARED;
            }
        }

    }
    public void spawnEnemy() {
        enemyController.spawnEnemy(waves.get(currentWave).getCurrentEnemyType());
        waves.get(currentWave).setCurrentIndex(waves.get(currentWave).getCurrentIndex()+1);

    }

    public void render(Graphics g) {
        if(generateNewWave) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("New Wave in: "+(cooldown-counter)/ Game.ups, 100, 100);
        }
    }
    public Playing getPlaying() {
        return playing;
    }
    public int getRemainingWaves() {
        return waves.size()-currentWave;
    }
    public int getCurrentWaveNotSpawnedEnemies() {
        int notSpawnedEnemies = waves.get(currentWave).getSize()-waves.get(currentWave).getCurrentIndex();
        return notSpawnedEnemies;
    }

    @Override
    public void workAddQueue() {

    }

    @Override
    public void workRemoveQueue() {

    }

    public int getCurrentWave() {
        return currentWave;
    }
}
