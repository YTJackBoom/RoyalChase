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
import java.util.ArrayList;

import static helpers.variables.Icons.*;

public class InfoOverlay {
    private Game game;
    private Playing playing;
    private Town town;
    private Tower towerPointer;
    private int draggedTowerType;
    private int mouseY,mouseX;
    private MyButton hoveredButton;
    private Values playerValues;
    private ArrayList<BufferedImage> iconImages;
    public InfoOverlay(Game game) {
        this.game = game;
        playerValues = game.getPlayerValues();
//        this.playing = game.getPlaying();
        initVariables();
    }
    private void initVariables(){
        ArrayList<File> iconFiles = new ArrayList<File>();
        iconImages = new ArrayList<BufferedImage>();
        for (int i = 0; i<=9;i++) {
            iconFiles.add(variables.Icons.getIconImageFile(i));
        }

        try {
            for (File file : iconFiles) {
                iconImages.add(ImageIO.read(file));
            }
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
        int startX = 100;
        int startY = 50;
        int xOffset = 200;

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        int textHeight = fm.getAscent() - fm.getDescent();

        int[] icons = { WAVES, ENEMIES, HEART, GOLD, MANA, IRON, WOOD, STONE };
        int[] values = {
                game.getPlaying().getWaveController().getRemainingWaves(),
                game.getPlaying().getWaveController().getCurrentWaveNotSpawnedEnemies() + game.getPlaying().getEnemyController().getEnemyList().size(),
                (int) playerValues.getHealth(),
                (int) playerValues.getGold(),
                (int) playerValues.getMana(),
                (int) playerValues.getIron(),
                (int) playerValues.getWood(),
                (int) playerValues.getStone()
        };

        for (int i = 0; i < icons.length; i++) {
            BufferedImage img = iconImages.get(icons[i]);

            int imageCenterX = startX + i * xOffset;
            int imageCenterY = startY;

            g.drawImage(img, imageCenterX - img.getWidth() / 2, imageCenterY - img.getHeight() / 2, null);

            int textCenterX = imageCenterX + img.getWidth() + 10;
            int textCenterY = imageCenterY + textHeight / 2 ;

            g.drawString(Integer.toString(values[i]), textCenterX, textCenterY);
        }

        BufferedImage img;
        if(game.isPaused()) {
            img = iconImages.get(0);
        } else {
            img = iconImages.get(1);
        }
        g.drawImage(img,25,startY-img.getHeight()/2,null);


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
        if (hoveredButton == null) return;

        String buttonText = hoveredButton.getText();
        boolean isSellText = "Sell".equals(buttonText);

        if (buttonText != null && buttonText.isBlank()) return;
        if (!hoveredButton.isTowerButton() && !hoveredButton.isBuildingButton() && buttonText == null) return;

        int x = hoveredButton.getX() - hoveredButton.getWidth() / 2;
        int y = hoveredButton.getY() + hoveredButton.getHeight() / 3;

        Values costValues = null;

        if (hoveredButton.isTowerButton() || hoveredButton.isBuildingButton()) {
            int type = hoveredButton.getType();
            costValues = hoveredButton.isTowerButton() ? variables.Towers.getCost(type) : variables.Buildings.getCost(type);
        } else if (buttonText != null && towerPointer != null) {
            if ("Upgrade".equals(buttonText)) {
                costValues = towerPointer.getWorth().getUpgradeCost();
                g.setFont(Constants.UIConstants.TOWERCOSTFONT);
            } else if (isSellText) {
                costValues = towerPointer.getWorth();
            }
        }

        if (costValues == null) return;

        double[] costs = {costValues.getMana(), costValues.getIron(), costValues.getWood(), costValues.getStone()};
        double[] playerResources = {playerValues.getMana(), playerValues.getIron(), playerValues.getWood(), playerValues.getStone()};

        for (int i = 0; i < costs.length; i++) {
            if (costs[i] <= playerResources[i] || isSellText) {
                g.setColor(Constants.UIConstants.TOWERCANAFFORDCOLOR);
            } else {
                g.setColor(Constants.UIConstants.TOWERCANTAFFORDCOLOR);
            }
            g.drawString(String.valueOf((int) costs[i]), x, y + i * (int) Constants.UIConstants.TOWERCOSTFONT.getSize2D());
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
    public void setPlayerValues(Values playerValues) {
        this.playerValues = playerValues;
    }
}
