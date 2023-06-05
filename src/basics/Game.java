package basics;

import javax.swing.*;


import helpers.PreLoader;
import scenes.GameOver;
import scenes.Menu;
import scenes.Playing;
import scenes.Settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static basics.GameStates.PLAYING;

public class Game extends JFrame  {

        private final int fps = 15;
        private final int ups = 120; //updates per second, for the game logic
        private final int speedOffset = 1;
        private GameScreen gameScreen;

        // Classes
        private Render render;
        private Menu menu;
        private Playing playing;
        private Settings settings;
        private GameOver gameOver;

        private Timer RenderTimer, GameTimer;
        private PreLoader preLoader;

        public Game() {
            initClasses();

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            setTitle("Bang Bang");
            add(gameScreen);
            pack();
            setVisible(true);
         // GameStates.gameState = PLAYING;
        }


        private void initClasses() {
            preLoader = new PreLoader();


            gameScreen = new GameScreen(this);
            render = new Render(this);
            menu = new Menu(this);
            playing = new Playing(this);
            settings = new Settings(this);
            gameOver = new GameOver(this);

        }

        private void start() {//initialises and starts two timers: one for the game and one for the render(in 60fps)

            RenderTimer = new Timer((int)(1000/fps), new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    repaint();
                    fpsCounter();
                }
            });
            RenderTimer.start();

            GameTimer = new Timer((int)(1000/ups), new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    updateGame();
                    //upsCounter();
                }
            });
            GameTimer.start();
        }

        private void updateGame() {
            switch (GameStates.gameState) {
                case MENU:
                    break;
                case PLAYING:
                    playing.update();
                    break;
                case SETTINGS:
                    break;
                default:
                    break;
            }
        }

        public static void main(String[] args) {
            Game game = new Game();
            game.gameScreen.initInputs();
            game.start();

        }
        public void togglePause() {
            if (RenderTimer.isRunning()) {
                RenderTimer.stop();
                GameTimer.stop();
            } else {
                RenderTimer.start();
                GameTimer.start();
            }
        }

        public void fpsCounter() { //calculates refreshes per second  and  prints them

        }
        // Getters and setters
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
        public PreLoader getPreLoader() {
            return preLoader;
        }
        public int getSpeedOffset() {
            return speedOffset;
        }


    }

