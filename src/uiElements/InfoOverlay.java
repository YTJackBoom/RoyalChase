package uiElements;

import basics.Game;
import gameObjects.Tower;
import helpers.*;
import scenes.Playing;
import scenes.Town;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static helpers.Values.*;

public class InfoOverlay {
    private Game game;
    private Playing playing;
    private Town town;
    private BufferedImage pausedImage,resumeImage;
    private Tower towerPointer;
    private int draggedTowerType;
    private int mouseY,mouseX;
    private MyButton hoveredButton;
    public InfoOverlay(Game game) {
        this.game = game;
//        this.playing = game.getPlaying();
        initVariables();
    }
    private void initVariables(){
        File pausedFile = new File("res\\images\\infos\\paused.jpg");
        File resumeFile = new File("res\\images\\infos\\resume.jpg");
        try {
            pausedImage = ImageIO.read(pausedFile);
            resumeImage = ImageIO.read(resumeFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        this.playing = game.getPlaying();
        this.town = game.getTown();
        renderPlayerInfos(g);
        renderTowerRanges(g);
        renderTowerCosts(g);
        renderUpgradeCosts(g);


    }

    public void renderPlayerInfos(Graphics g) {
        int startX = 10;
        int startY = 50;
        int xOffset = 0;
        int yOffset = 20;
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Gold: " + Values.GOLD, startX, startY);
        g.drawString("Wave: " + game.getPlaying().getWaveController().getCurrentWave(), startX+xOffset, startY+yOffset);
        g.drawString("Enemies: " + game.getPlaying().getEnemyController().getEnemyList().size(), startX+xOffset*2, startY+yOffset*2);
        g.drawString("Health: " + HEALTH, startX+xOffset*3, startY+yOffset*3);
        g.drawString("Mana: " + MANA,startX+xOffset*4,startY+yOffset*4);
        g.drawString("Iron: " + IRON,startX+xOffset*5,startY+yOffset*5);
        g.drawString("Wood: " + WOOD,startX+xOffset*6,startY+yOffset*6);
        g.drawString("Stone: " + STONE,startX+xOffset*7,startY+yOffset*7);
        if(game.isPaused()){
            g.drawImage(pausedImage, 10, 0, null);
        }else {
            g.drawImage(resumeImage, 10, 0, null);
        }
    }

    public void renderTowerRanges(Graphics g) {
        if (towerPointer != null) {
            g.setColor(Color.BLACK);
            towerPointer.renderRange(g);
        }
        if (playing.getDragingTower()) {
            int range = variables.Towers.getTowerRange(draggedTowerType);
//			Circle [] circles = new Circle[Constants.UIConstants.NUMBEROFRANGECIRCLES];

            for (int i = 0; i < Constants.UIConstants.NUMBEROFRANGECIRCLES; i++) {
                Circle c = new Circle(new Coordinate(mouseX, mouseY), range - Constants.UIConstants.NUMBEROFRANGECIRCLES + i);
                c.render(g);
            }
        }
    }

    public void renderTowerCosts(Graphics g) {
        if (hoveredButton != null) {
            if (hoveredButton.isTowerButton() || (hoveredButton.getText().equals("Upgrade"))) {
                int x = hoveredButton.getX() - hoveredButton.getWidth() / 2;
                int y = hoveredButton.getY() + hoveredButton.getHeight() / 3;

                int manaCost=0, ironCost=0, woodCost=0, stoneCost=0;

                if (hoveredButton.isTowerButton()) {
                    int type = hoveredButton.getType();

                    manaCost = variables.Towers.getTowerManaCost(type);
                    ironCost = variables.Towers.getTowerIronCost(type);
                    woodCost = variables.Towers.getTowerWoodCost(type);
                    stoneCost = variables.Towers.getTowerStoneCost(type);
                }
                if(hoveredButton.getText() != null) {
                    if (hoveredButton.getText().equals("Upgrade")) {
                        manaCost = (int) Math.round(math.TowerMath.calculateTowerUpgradeCost(towerPointer, Values.VALUES.MANA));
                        ironCost = (int) Math.round(math.TowerMath.calculateTowerUpgradeCost(towerPointer, Values.VALUES.IRON));
                        woodCost = (int) Math.round(math.TowerMath.calculateTowerUpgradeCost(towerPointer, VALUES.WOOD));
                        stoneCost = (int) Math.round(math.TowerMath.calculateTowerUpgradeCost(towerPointer, VALUES.STONE));

                        g.setFont(Constants.UIConstants.TOWERCOSTFONT);
                        System.out.println("f");
                    }
                }

                    if (manaCost > MANA) {
                        g.setColor(Constants.UIConstants.TOWERCANTAFFORDCOLOR);
                    } else {
                        g.setColor(Constants.UIConstants.TOWERCANAFFORDCOLOR);
                    }
                    g.drawString(String.valueOf(manaCost), x, y);

                    if (ironCost > IRON) {
                        g.setColor(Constants.UIConstants.TOWERCANTAFFORDCOLOR);
                    } else {
                        g.setColor(Constants.UIConstants.TOWERCANAFFORDCOLOR);
                    }
                    g.drawString(String.valueOf(ironCost), x, y + (int) Constants.UIConstants.TOWERCOSTFONT.getSize2D());

                    if (woodCost > WOOD) {
                        g.setColor(Constants.UIConstants.TOWERCANTAFFORDCOLOR);
                    } else {
                        g.setColor(Constants.UIConstants.TOWERCANAFFORDCOLOR);
                    }
                    g.drawString(String.valueOf(woodCost), x, y + (int) (2 * Constants.UIConstants.TOWERCOSTFONT.getSize2D()));

                    if (stoneCost > STONE) {
                        g.setColor(Constants.UIConstants.TOWERCANTAFFORDCOLOR);
                    } else {
                        g.setColor(Constants.UIConstants.TOWERCANAFFORDCOLOR);
                    }
                    g.drawString(String.valueOf(stoneCost), x, y + (int) (3 * Constants.UIConstants.TOWERCOSTFONT.getSize2D()));


                }
            }

        }


    public void renderUpgradeCosts(Graphics g ) {}
    public void setTowerPointer(Tower t) {
        towerPointer = t;
    }
    public void setDraggedTowerType(int t) {
        draggedTowerType = t;
    }
    public void mouseDragged(int x, int y) {
        mouseX = x;
        mouseY = y;
    }
    public void mousePressed(int x,int y) {
        mouseX = x;
        mouseY = y;
    }
    public void mouseClicked(int x,int y) {
        mouseX = x;
        mouseY = y;
    }

    public void setHoveredButton(MyButton hoveredButton) {
        this.hoveredButton = hoveredButton;
    }
}
