package controllers;

import basics.Wave;
import enemy.Enemy;
import helpers.WaveFileAnalyser;
import scenes.Playing;

import java.util.ArrayList;

public class WaveController implements ControllerMethods {
    private Playing playing;
    private EnemyController enemyController;
    private WaveFileAnalyser waveFileAnalyser;
    private int currentWave, counter, counter2;
    private int cooldown = 100;
    private int timeBetweenSpawns = 100;
    private ArrayList<Wave> waves;


    public WaveController(Playing playing) { //TODO: prob needs changes
        this.playing = playing;
        enemyController = playing.getEnemyController();
        currentWave = 0;
        initWaves();
    }


    public void initWaves() {
        waveFileAnalyser = new WaveFileAnalyser(playing.getCurrentLevel());
        int wavesNum = waveFileAnalyser.getWavesNum();
        waves = new ArrayList<Wave>();
        for(int i = 0; i < wavesNum; i++) {
            waves.add(new Wave(waveFileAnalyser.getArrayList(i)));
        }

    }
    public void update() {
        if (enemyController.getEnemyList().size() == 0&&counter2/timeBetweenSpawns == 1) {
            if (counter / cooldown == 1) {
                newWave();
                counter = 0;
            }else {
                counter++;
            }
        }
        if(counter2/timeBetweenSpawns == 1) {
            spawnEnemy();
            counter2 = 0;
        }else {
            counter2++;
        }
    }
    public void newWave() {
        currentWave++;

    }
    public void spawnEnemy() {
        enemyController.spawnEnemy(waves.get(currentWave).getCurrentEnemyType());
        waves.get(currentWave).setCurrentIndex(waves.get(currentWave).getCurrentIndex()+1);

    }

    @Override
    public Playing getPlaying() {
        return playing;
    }
}
