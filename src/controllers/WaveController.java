package controllers;

import basics.Game;
import helpers.Direction;
import helpers.TextReader;
import helpers.Values;
import helpers.Wave;
import scenes.GameStates;
import scenes.Playing;

import java.awt.*;
import java.util.ArrayList;

import static scenes.GameStates.LEVELCLEARED;

/**
 * Controller Klasse für die Waves
 */
public class WaveController implements ControllerMethods {
    private Playing playing;
    private EnemyController enemyController;
    private TextReader textReader;
    private int currentWave, counter, counter2; //currentWAve == derzeitige Welle, counter == Counter zwichen dem Spawnen von Wellen, counter2 == Counter zwichen dem spawnen von Gegnern
    private int cooldown = 400; //Colldown nach jeder Welle
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


    /**
     * Methode um Wellen aus einer Textdatei zu initialisieren
     */
    public void initWaves() {
        textReader = new TextReader(playerValues);
        int wavesNum = textReader.getWavesNum();
        waves = new ArrayList<Wave>();
        for(int i = 0; i < wavesNum; i++) {
            textReader.initArrayLists(i);
            waves.add(new Wave(textReader.getSpawnList(),textReader.getDelayList()));
        }

    }

    /**
     * Methode zum updaten der WEllen und spawnen von Gegenern
     */
    public void update() {
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

    /**
     * Methode zum updaten der neuen Welle und falls keine mehr verfügbar sind, beenden des Levels
     */
    public void updateNewWave() {
        if (generateNewWave) {
            if (counter / cooldown == 1) {
                currentWave++;
                generateNewWave = false;
                counter = 0;
            } else {
                counter++;
            }
            if (currentWave + 1 == waves.size() && waves.get(currentWave).getCurrentIndex() == waves.get(currentWave).getSpawnList().size()) {
                playing.getProjectileController().clearProjectiles();
                playing.setDragingTower(false);
                playing.setSelectedTower(null);
                playing.getButtonBar(Direction.RIGHT).setVisible(false);
                playing.getButtonBar(Direction.DOWN).setVisible(false);

                generateNewWave = false;
                GameStates.gameState = LEVELCLEARED;
                playing.getGame().getLevelCleared().clearedCall();

            }
        }
    }

    public void spawnEnemy() {
        enemyController.spawnEnemy(waves.get(currentWave).getCurrentEnemyType());
        waves.get(currentWave).setCurrentIndex(waves.get(currentWave).getCurrentIndex()+1);

    }

    /**
     * Methode zum rendern des Cooldowns zur neuen Welle
     * @param g Graphics Objekt
     */
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
