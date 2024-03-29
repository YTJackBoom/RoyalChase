package basics;

import collectors.UiElementCollector;
import controllers.AssetController;
import controllers.BuildingsController;
import controllers.SoundController;
import gameObjects.Building;
import helpers.AbsoluteCoordinate;
import helpers.BuildingSaveState;
import helpers.Values;
import scenes.Menu;
import scenes.*;
import uiElements.InfoOverlay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URISyntaxException;

public class Game extends JFrame implements Serializable {


    public final static int fps = 60; // frames pro sekunde, zum rendern
    public static final int ups = 120; //updates pro sekunde, für game logic
    public static final int initGameWidth = 256 * 7;
    public static final int initGameHeight = 256 * 4;
    public static volatile int fWIDTH = initGameWidth;
    public static volatile int fHEIGHT = initGameHeight;
    public static volatile int prevfWIDTH = initGameWidth;
    public static volatile int prevfHEIGHT = initGameHeight;
    protected boolean isFullScreen = false;
    private volatile int currentUPS = 0;
    private int currentFPS = 0;
    private boolean isPaused = false;
    public static boolean ISDEVMODE = false;
    // Classes
    private GameScreen gameScreen;
    private Menu menu;
    private Playing playing;
    private GameOver gameOver;
    private LevelCleared levelCleared;
    private LevelSelect levelSelect;
    private Town town;
    private InfoOverlay infoOverlay;
    private GameState gameState;
    private GameRenderUpdater renderUpdater;
    private Timer resizeEndTimer;
    private static Logger logger;


    /**
     * Konstruktor der Klasse Game, start des Spiels
     */
    public Game() { //Initialisieren des Fensters
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (logger != null) {
                    logger.close(); // Close the logger when JFrame is closing
                }
                System.exit(0);
            }
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        initClasses();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        setLayout(new BorderLayout());
        add(gameScreen, BorderLayout.CENTER);


        setTitle("Royal Chase");
        setMinimumSize(new Dimension(initGameWidth, initGameHeight));

        setContentPane(gameScreen);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            logger = new Logger("RoyalChase-Log.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();

    }
        /**
         * Initialisieren der Klassen
         */
   private void initClasses() {
            System.out.println("Gerade werden die Assets des Spiels geladen, bitte warten...");
            AssetController.getInstance(); //Initialisieren der AssetController Klasse, welche die Bilder und Sounds des Spiels in den Arbeitsspeicher lädt
            SoundController.getInstance(); //Initialisieren der SoundController Klasse, welche die Sounds des Spiels in den Arbeitsspeicher lädt
            System.out.println("Assets geladen");

            gameState = new GameState(this);

            infoOverlay = new InfoOverlay(this);


            gameScreen = new GameScreen(this);
            menu = new Menu(this);
            playing = new Playing(this);
            gameOver = new GameOver(this);
            levelCleared = new LevelCleared(this);
            levelSelect = new LevelSelect(this);
            town = new Town(this);

            initClassesForResizes();

                    }

    /**
     * Initialisieren der Klassen, welche Aktionen beim resizen des Fensters ausführen
     */
    private void initClassesForResizes() {
            resizeEndTimer = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { //timer um sehr viele resizes zu verhindern
                    SwingUtilities.invokeLater(() -> {

                        prevfHEIGHT = fHEIGHT; //speichern der alten werte
                        prevfWIDTH = fWIDTH;

                        fWIDTH = getContentPane().getWidth(); //setzen der neuen werte
                        fHEIGHT = getContentPane().getHeight();


                        gameScreen.setSize(new Dimension(fWIDTH, fHEIGHT)); //explicites setzen der größe des gamescreens
                        gameScreen.revalidate();
                        gameScreen.repaint();

                        UiElementCollector.getInstance().notifyScreenResize(fWIDTH,fHEIGHT); //benachrichtigen der UiElemente über die neue größe
                        getPlaying().getTileController().extendTiles(fWIDTH, fHEIGHT);  //erweitern des Spielfeldes auf die neue größe
                        getTown().notifyScreenResize(fWIDTH,fHEIGHT); //benachrichtigen der Town über die neue größe


                    });

                }
            });

            resizeEndTimer.setRepeats(false); //nur einmal ausführen , .restart() startet timer auch

            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) { //wenn das fenster resized wird, wird ein timer restartet, der nach 50ms die größe des fensters an die anderen klassen weitergibt
                    resizeEndTimer.restart();
                }
            });
    }

    /**
     * Initialisiert zwei threaths, einen für spiel-logic und einen für spiel-grafik(sowie einen timer zur ausgabe der fps und ups)
     */
        private void start() {



            renderUpdater = new GameRenderUpdater(this);    //initialisieren der Klasse
            Thread gameRederThread = new Thread(renderUpdater); //adden des Threats auf die Klasse
            gameRederThread.start(); //starten des Threats

            GameLogicUpdater logicUpdater = new GameLogicUpdater(this);
            Thread gameLogicThread = new Thread(logicUpdater);
            gameLogicThread.start();


            Timer statsTimer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(ISDEVMODE) System.out.println("Current UPS: " + currentUPS + ", Current FPS: " + currentFPS);
                    currentUPS = 0; // Reset UPS count
                    currentFPS = 0; // Reset FPS count
                }
            });
            statsTimer.start();
        }

    /**
     * Methode zum Updaten der game logic, town erhält soft-updates zum aktualisieren der produzierten resourcen
     */
    protected void updateGame() {
            switch (GameStates.gameState) {
                case MENU ->menu.update();
                case PLAYING -> {playing.update();
                                 town.softUpdate();}
                case GAMEOVER -> gameOver.update();
                case LEVELCLEARED -> levelCleared.update();
                case LEVELSELECT -> levelSelect.update();
                case TOWN -> town.updateInfoOverlay();

            }
        }

    /**
     * Methode zum Pausieren der game logic
     */
    public void togglePause() {
            isPaused = !isPaused;
            playing.togglePause();
            town.togglePause();
    }

    /**
     * Methode zum Speichern des Spielstandes, speichert Resourcen des Spielers sowie die gebäude in der Stadt
     *
     */
    public void saveGame(String fileName) {
        try {
            String jarPath = new File(Game.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            String savePath = jarPath + File.separator + fileName;

            FileOutputStream fileOut = new FileOutputStream(savePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gameState);
            System.out.println(gameState.getUserSettings().getDifficulty());
            out.close();
            fileOut.close();
            System.out.println("Game saved to " + savePath);
        } catch (IOException i) {
            i.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    /**
     * Methode zum Laden des Spielstandes, lädt Resourcen des Spielers sowie die gebäude in der Stadt und setzt die Werte der geladenen GameState-class in diversen Klassen
     */
    public void loadGame(String fileName) {
        GameState gameState = null;
        try {
            String jarPath = new File(Game.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            String loadPath = jarPath + File.separator + fileName;

            FileInputStream fileIn = new FileInputStream(loadPath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gameState = (GameState) in.readObject();
            in.close();
            fileIn.close();
            this.gameState = gameState;
            loadTownBuildings();
            menu.initSliders();
            System.out.println(gameState.getUserSettings().getDifficulty());
            System.err.println("Loaded Save");
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("GameState class not found");
            c.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    /**
     * Methode, um die geladenen BuildingSaveState Klassen in die Building Klassen umzuwandeln und in der BuildingController-Klasse zu speichern
     */
    private void loadTownBuildings() {
           int i=0;
            BuildingsController buildingsController = town.getBuildingsController();
            for(BuildingSaveState b: gameState.getTownBuildingsSave()) {
                AbsoluteCoordinate pos = b.getPos();
                buildingsController.getBuildingsList().set(i,new Building(buildingsController,pos.getX(),pos.getY(),b.getType(),true));
                i++;
            }
    }

    /**
     * Methode zum Resetten des Spielstandes
     */
    public void resetAll() {
        playing.reset();
        levelSelect.reset();
        town.reset();
        getPlayerValues().reset();
    }

    /**
     * Methode, welche dürch das drücken von F11 ausgeführt wird, diese ermöglicht das Wechseln aus und in den Fullscreen-Modus. Diese Methode ruft auch indirekt die Methoden zum Resize des Fensters auf
     */
    public void toggleFullscreen(){
        GameScreen gameScreen = getGameScreen();
        if (isFullScreen()) { //Wechseln in Fenster-Modus
            dispose();             // temporäres schließen des Fensters
            setUndecorated(false); //Hinzufügen der Fensterleiste
            setVisible(true);
            gameScreen.setSize(new Dimension(initGameWidth, initGameHeight));

            Insets insets = getInsets();
            setSize(initGameWidth + insets.left + insets.right, initGameHeight + insets.top + insets.bottom); //Zurücksetzten der Größe auf die start-Werte
            setLocationRelativeTo(null); //Zentrieren des Fensters
            gameScreen.revalidate();
        } else { //WEchesln in Vollbild-Modus
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
            DisplayMode displayMode = defaultScreen.getDisplayMode();
            int screenWidth = displayMode.getWidth();
            int screenHeight = displayMode.getHeight();

            dispose();
            setUndecorated(true);
            setVisible(true);
            setSize(new Dimension(screenWidth, screenHeight));
            Insets insets = getInsets();
            setLocation(0, 0);

            gameScreen.setSize(new Dimension(screenWidth-insets.left-insets.right, screenHeight-insets.top-insets.bottom));
            gameScreen.revalidate();
        }
        isFullScreen = !isFullScreen;
    }


    // Getters and setters
    protected void incrementUPS() {
        currentUPS++;
    }

    protected void incrementFPS() {
        currentFPS++;
    }

    ;

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
        }

        public GameOver getGameOver() {
            return gameOver;
        }
        public LevelSelect getLevelSelect() {return levelSelect;}
        public Town getTown() {return town;}
        public InfoOverlay getInfoOverlay() {return infoOverlay;}

        public int getUps() {return ups;}


         public LevelCleared getLevelCleared() {
            return levelCleared;
        }
        public Values getPlayerValues() {return gameState.getPlayerValues();}
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
    public void toggleDevMode() {
        ISDEVMODE = !ISDEVMODE;
        playing.getTowerController().getGate().setVisible(ISDEVMODE);
    }

}

