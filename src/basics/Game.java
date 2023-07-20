package basics;

import javax.swing.*;


import helpers.PreLoader;
import helpers.Values;
import scenes.*;
import uiElements.InfoOverlay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame  {

        public final static int fps = 15;
        public static final int ups = 120; //updates per second, for the game logic
        private volatile int currentUPS = 0;
        private int currentFPS = 0;
        private boolean isPaused = false;
        protected boolean isFullScreen =false;
        private GameScreen gameScreen;

        // Classes
        private Render render;
        private Menu menu;
        private Playing playing;
        private Settings settings;
        private GameOver gameOver;
        private LevelCleared levelCleared;
        private LevelSelect levelSelect;
        private Tutorial tutorial;
        private Town town;
        private InfoOverlay infoOverlay;
        private Values playerValues;


        private Timer RenderTimer, GameTimer;
        private PreLoader preLoader;


        public Game() {
            initClasses();
            initVariables();

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);

            setTitle("Bang Bang");
            add(gameScreen);
            pack();
            setVisible(true);

            // GameStates.gameState = PLAYING;
//            System.out.println(" ");
        }


        private void initClasses() {
            playerValues = new Values();

            preLoader = new PreLoader();

            infoOverlay = new InfoOverlay(this);


            gameScreen = new GameScreen(this);
            render = new Render(this);
            menu = new Menu(this);
            playing = new Playing(this);
            settings = new Settings(this);
            gameOver = new GameOver(this);
            levelCleared = new LevelCleared(this);
            levelSelect = new LevelSelect(this);
            tutorial = new Tutorial(this);
            town = new Town(this);


        }
        private void initVariables(){

        }

        public static void main(String[] args) {
            Game game = new Game();
            game.gameScreen.initInputs();
            game.start();

        }
        private void start() {//initialises and starts two timers: one for the game and one for the render(in 60fps)


            RenderTimer = new Timer((int)(1000/fps), new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    repaint();
                    currentFPS++;

                }
            });
            RenderTimer.start();

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

        protected void updateGame() {
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

        public void fpsCounter() { //calculates refreshes per second  and  prints them

        }
        // Getters and setters
        protected synchronized void incrementUPS() {
            currentUPS++;
        }
        public Render getRender() {
            return render;
        }

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
        public Values getPlayerValues() {return playerValues;}
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

        public GameScreen getGameScreen() {
            return gameScreen;
        }

        public void resetAll() {
            playing.reset();
            levelSelect.reset();
            tutorial.reset();
              town.reset();
            playerValues.reset();
         }

        public boolean isFullScreen() {
            return isFullScreen;
        }
        public void toggleFullscreen(){
            isFullScreen = !isFullScreen;
        }
}

