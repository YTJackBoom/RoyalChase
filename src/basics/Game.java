package basics;

import javax.swing.*;


import controllers.BuildingsController;
import gameObjects.Building;
import helpers.BuildingSaveState;
import helpers.Coordinate;
import helpers.PreLoader;
import helpers.Values;
import scenes.*;
import uiElements.InfoOverlay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Game extends JFrame implements Serializable {

        public final static int fps = 60; // frames pro sekunde, zum rendern
        public static final int ups = 120; //updates pro sekunde, für game logic
        private volatile int currentUPS = 0;
        private int currentFPS = 0;
        private boolean isPaused = false;
        protected boolean isFullScreen =false;

        // Classes
        private GameScreen gameScreen;
        private Menu menu;
        private Playing playing;
        private Settings settings;
        private GameOver gameOver;
        private LevelCleared levelCleared;
        private LevelSelect levelSelect;
        private Tutorial tutorial;
        private Town town;
        private InfoOverlay infoOverlay;
        private GameState gameState;
        private GameRenderUpdater renderUpdater;
        private PreLoader preLoader;


        public Game() { //Initialisieren des Fensters
            initClasses();

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);

            setTitle("Bang Bang");
            add(gameScreen);
            pack();
            setVisible(true);
        }


        private void initClasses() { //Initialisieren der Klassen
            gameState = new GameState(this);
            preLoader = new PreLoader();

            infoOverlay = new InfoOverlay(this);


            gameScreen = new GameScreen(this);
            menu = new Menu(this);
            playing = new Playing(this);
            settings = new Settings(this);
            gameOver = new GameOver(this);
            levelCleared = new LevelCleared(this);
            levelSelect = new LevelSelect(this);
            tutorial = new Tutorial(this);
            town = new Town(this);


        }
        public static void main(String[] args) {
            Game game = new Game();
            game.gameScreen.initInputs();
            game.start();

        }
        private void start() {//Initialisiert zwei threaths, einen für spiel-logic und einen für spiel-grafik(sowie einen timer zur ausgabe der fps und ups)



            renderUpdater = new GameRenderUpdater(this);
            Thread gameRederThread = new Thread(renderUpdater);
            gameRederThread.start();

            GameLogicUpdater logicUpdater = new GameLogicUpdater(this);
            Thread gameLogicThread = new Thread(logicUpdater);
            gameLogicThread.start();


            Timer statsTimer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Current UPS: " + currentUPS + ", Current FPS: " + currentFPS);
                    currentUPS = 0; // Reset UPS count
                    currentFPS = 0; // Reset FPS count
                }
            });
            statsTimer.start();
        }

        protected void updateGame() { //zum updaten der game logic, town erhält soft-updates zum aktualisieren der produzierten resourcen
            switch (GameStates.gameState) {
                case MENU ->menu.update();
                case PLAYING -> {playing.update();
                                 town.softUpdate();}
                case SETTINGS -> settings.update();
                case GAMEOVER -> gameOver.update();
                case LEVELCLEARED -> levelCleared.update();
                case LEVELSELECT -> levelSelect.update();
                case TUTORIAL -> {tutorial.update();
                                  town.softUpdate();}
                case TOWN -> town.update();

            }
        }

        public void togglePause() {
            if (!isPaused) {
                isPaused = true;
                playing.pause();
                town.pause();
                tutorial.pause();
            } else {
                isPaused = false;
                playing.resume();
                town.resume();
                tutorial.resume();
            }
        }
        public void saveGame( String filePath) { //speicher den spielstand der ressourcen des spielers sowie der gebäude in der stadt,hierfür werden die klassen von java.io  benutzt
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gameState);
            out.close();
            fileOut.close();
            System.out.println("Game saved");
         } catch (IOException i) {
            i.printStackTrace();
         }
        }
    public void loadGame(String filePath) { //lädt den spielstand aus dem pfad und setzt die speielrwerte in den benötigten klassen neu
        GameState gameState = null;
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gameState = (GameState) in.readObject();
            in.close();
            fileIn.close();
            this.gameState = gameState;
            loadTownBuildings();
            levelSelect.setPlayerValues(getPlayerValues());
            infoOverlay.setPlayerValues(getPlayerValues());
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("GameState class not found");
            c.printStackTrace();
        }

    }
    private void loadTownBuildings() { //schreibt die geladenen stadt gebäude in die town klasse
           int i=0;
            BuildingsController buildingsController = town.getBuildingsController();
            for(BuildingSaveState b: gameState.getTownBuildingsSave()) {
                Coordinate pos = b.getPos();
                buildingsController.getBuildingsList().set(i,new Building(buildingsController,pos.getX(),pos.getY(),b.getType()));
                i++;
            }
    }

    public void resetAll() { //zum reseten beim "tod"
        playing.reset();
        levelSelect.reset();
        tutorial.reset();
        town.reset();
        getPlayerValues().reset();
    }
        // Getters and setters
        protected void incrementUPS() {
            currentUPS++;
        }
        protected void incrementFPS() {currentFPS++;};
        public Menu getMenu() {
            return menu;
        }

        public Playing getPlaying() {
            return playing;
        }

        public Settings getSettings() {
            return settings;
        }
        public GameOver getGameOver() {
            return gameOver;
        }
        public LevelSelect getLevelSelect() {return levelSelect;}
        public Tutorial getTutorial() {return tutorial;}
        public Town getTown() {return town;}
        public InfoOverlay getInfoOverlay() {return infoOverlay;}


        public PreLoader getPreLoader() {
            return preLoader;
        }
        public int getUps() {return ups;}


         public LevelCleared getLevelCleared() {
            return levelCleared;
        }
        public Values getPlayerValues() {return gameState.getPlayerValues();}
        @Override
        public int getHeight() {
            return gameScreen.getHeight();
        }
        @Override
        public int getWidth() {
            return gameScreen.getWidth();
        }
        public boolean isPaused() {
            return isPaused;
        }
        public GameState getGameState(){return gameState;}
        public void setGameState(GameState g){gameState = g;}

        public GameScreen getGameScreen() {
            return gameScreen;
        }
        public boolean isFullScreen() {
            return isFullScreen;
        }
        public void toggleFullscreen(){
            isFullScreen = !isFullScreen;
        }
        public GameRenderUpdater getRenderUpdater() {
            return renderUpdater;
    }
}

