package basics;

import controllers.BuildingsController;
import gameObjects.Building;
import helpers.BuildingSaveState;
import helpers.Coordinate;
import helpers.PreLoader;
import helpers.Values;
import scenes.Menu;
import scenes.*;
import uiElements.InfoOverlay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Game extends JFrame implements Serializable {


        public final static int fps = 60; // frames pro sekunde, zum rendern
        public static final int ups = 120; //updates pro sekunde, für game logic
        public static final int initGameWidth = 265*7;
        public static final int initGameHeight = 256*4;
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
        private Town town;
        private InfoOverlay infoOverlay;
        private GameState gameState;
        private GameRenderUpdater renderUpdater;
        private PreLoader preLoader;


        public Game() { //Initialisieren des Fensters
            initClasses();

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            setLayout(null);

            setTitle("Bang Bang");
            add(gameScreen);
            setSize(new Dimension(initGameWidth, initGameHeight));
            setLocationRelativeTo(null);

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
            town = new Town(this);


        }
        public static void main(String[] args) {
//            System.setOut(null);
//            System.setErr(null);


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
                case TOWN -> town.update();

            }
        }

        public void togglePause() {
            isPaused = !isPaused;
            playing.togglePause();
            town.togglePause();
        }
        public void saveGame( String filePath) { //speicher den spielstand der ressourcen des spielers sowie der gebäude in der stadt,hierfür werden die klassen von java.io  benutzt
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gameState);
            System.out.println(gameState.getUserSettings().getDifficulty());
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
            menu.initSliders();
            System.out.println(gameState.getUserSettings().getDifficulty());
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
        town.reset();
        getPlayerValues().reset();
    }

    public void toggleFullscreen(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();

        // Get the screen's resolution
        DisplayMode displayMode = defaultScreen.getDisplayMode();
        int screenWidth = displayMode.getWidth();
        int screenHeight = displayMode.getHeight();

        GameScreen gameScreen = getGameScreen();

        if (isFullScreen()) {
            // Switch back to decorated mode
            dispose();
            setUndecorated(false);
            setVisible(true);
            setSize(new Dimension(initGameWidth, initGameHeight));
            // Center the frame if desired
            setLocationRelativeTo(null);

            gameScreen.setSize(new Dimension(initGameWidth, initGameHeight));

            gameScreen.revalidate();
        } else {
            // Switch to fullscreen mode
            dispose();
            setUndecorated(true);
            setVisible(true);
            getContentPane().setSize(screenWidth,screenHeight);
            setSize(new Dimension(screenWidth,screenHeight));
            setLocation(0,0 );

            gameScreen.setSize(new Dimension(screenWidth,screenHeight));
            gameScreen.revalidate();

            getPlaying().getTileController().extendTiles(screenWidth,screenHeight);

        }
        isFullScreen = !isFullScreen;
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

    public void setPaused(boolean paused) {
        isPaused = paused;
        playing.setPaused(paused);
        town.setPaused(paused);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState g) {
        gameState = g;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

        public GameRenderUpdater getRenderUpdater() {
            return renderUpdater;
    }
}

