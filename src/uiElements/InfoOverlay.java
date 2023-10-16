package uiElements;

import basics.Game;
import controllers.AssetController;
import gameObjects.Tile;
import gameObjects.Tower;
import helpers.*;
import scenes.Playing;
import scenes.Town;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static basics.Game.fWIDTH;
import static helpers.AssetLocation.Icons.*;

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
        iconImages = new ArrayList<BufferedImage>();
        for (int i = 0; i<=10;i++) {
            iconImages.add(AssetController.getInstance().getIcon("icon_"+i));
        }
    }

    public void render(Graphics g) {
        this.playing = game.getPlaying();
        this.town = game.getTown();
        renderPlayerInfos(g);
        renderTowerRanges(g);
        renderTowerCosts(g);
        renderTileBoundaries(g);


    }

    public void renderPlayerInfos(Graphics g) {
        int startY = (int) (0.02 * fWIDTH);
        int marginForFirstIcon = (int) (0.01 * fWIDTH);  // Setting a margin for the first icon

        int totalIcons = 10; // This includes the 9 original icons and the pause/play icon
        int spacing = fWIDTH / totalIcons;

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        int textHeight = fm.getAscent() - fm.getDescent();

        int[] icons = {WAVES, ENEMIES, HEART, WORKERS, GOLD, MANA, IRON, WOOD, STONE};
        int[] values = {
                game.getPlaying().getWaveController().getRemainingWaves(),
                game.getPlaying().getWaveController().getCurrentWaveNotSpawnedEnemies() + game.getPlaying().getEnemyController().getEnemyList().size(),
                (int) playerValues.getHealth(),
                (int) playerValues.getWorkers(),
                (int) playerValues.getGold(),
                (int) playerValues.getMana(),
                (int) playerValues.getIron(),
                (int) playerValues.getWood(),
                (int) playerValues.getStone()
        };

        BufferedImage img;
        if (game.isPaused()) {
            img = iconImages.get(1);
        } else {
            img = iconImages.get(0);
        }
        g.drawImage(img, marginForFirstIcon, startY - img.getHeight() / 2, null);

        for (int i = 0; i < icons.length; i++) {
            img = iconImages.get(icons[i]);

            int imageCenterX = marginForFirstIcon + (i + 1) * spacing; // +1 because the first spot is occupied by the pause/play icon, and adding the margin for the starting position
            int imageCenterY = startY;

            g.drawImage(img, imageCenterX - img.getWidth() / 2, imageCenterY - img.getHeight() / 2, null);

            int textCenterX = imageCenterX + img.getWidth() + 10;
            int textCenterY = imageCenterY + textHeight / 2;

            int value = values[i] < 0 ? 0 : values[i];
            g.drawString(Integer.toString(value), textCenterX, textCenterY);
        }
    }




    public void renderTowerRanges(Graphics g) {
        if (towerPointer != null) {
            g.setColor(Color.BLACK);
            towerPointer.renderRange(g);
        }
        if (playing.getDragingTower()) {
            int range = ObjectValues.Towers.getTowerRange(draggedTowerType);
//			Circle [] circles = new Circle[Constants.UIConstants.NUMBEROFRANGECIRCLES];

            for (int i = 0; i < Constants.UIConstants.NUMBEROFRANGECIRCLES; i++) {
                Circle c = new Circle(new AbsoluteCoordinate(mouseX, mouseY), range - Constants.UIConstants.NUMBEROFRANGECIRCLES + i);
                c.render(g);
            }
        }
    }

    public void renderTowerCosts(Graphics g) {
        if (hoveredButton == null) return;
        g.setFont(new Font("Arial", Font.BOLD, 20));

        String buttonText = hoveredButton.getText();
        boolean isSellText = "Sell".equals(buttonText);

        if (buttonText != null && buttonText.isBlank()) return;
        if (!hoveredButton.isTowerButton() && !hoveredButton.isBuildingButton() && buttonText == null) return;
        if (!hoveredButton.isVisible()) return;

        FontMetrics fm = g.getFontMetrics();
//        int x = hoveredButton.uiCoordinate.getX() - hoveredButton.width / 2;
//        int y = hoveredButton.uiCoordinate.getY() + fm.getHeight() / 2;

        Values costValues = null;

        if (hoveredButton.isTowerButton() || hoveredButton.isBuildingButton()) {
            int type = hoveredButton.getType();
            costValues = hoveredButton.isTowerButton() ? ObjectValues.Towers.getCost(type) : ObjectValues.Buildings.getCost(type);
        } else if (buttonText != null && towerPointer != null) {
            if ("Upgrade".equals(buttonText)) {
                costValues = towerPointer.getWorth().getUpgradeCost(towerPointer.getLevel());
                g.setFont(Constants.UIConstants.TOWERCOSTFONT);
            } else if (isSellText) {
                costValues = towerPointer.getWorth();
            }
        }

        if (costValues == null) return;

        double[] costs = {costValues.getWorkers(), costValues.getGold(), costValues.getMana(), costValues.getIron(), costValues.getWood(), costValues.getStone()};
        double[] playerResources = {playerValues.getWorkers(), playerValues.getGold(), playerValues.getMana(), playerValues.getIron(), playerValues.getWood(), playerValues.getStone()};

        int imagePadding = 5;  // gap between image and number
        int textHeight = (int) Constants.UIConstants.TOWERCOSTFONT.getSize2D();

        BufferedImage sampleImg = AssetController.getInstance().getIcon("icon_5");
        int imgHeight = sampleImg.getHeight();

        // Calculate the total height of all icons including spacing.
        int totalHeight = costs.length * (imgHeight/2 + imagePadding);

        // Adjust the y-coordinate by half of the total height.
        int adjustedY = hoveredButton.uiCoordinate.getY() - (totalHeight / 2);

        for (int i = 0; i < costs.length; i++) {
            BufferedImage img = AssetController.getInstance().getIcon("icon_" + (i + 5));
            int x = hoveredButton.uiCoordinate.getX() - hoveredButton.width / 2;
            int y = adjustedY + i * (imgHeight/2 + imagePadding*2);

            g.drawImage(img, x, y, null);

            if (costs[i] <= playerResources[i] || isSellText) {
                g.setColor(Constants.UIConstants.TOWERCANAFFORDCOLOR);
            } else {
                g.setColor(Constants.UIConstants.TOWERCANTAFFORDCOLOR);
            }

            String costString = String.valueOf((int) costs[i]);
            g.drawString(costString, x + img.getWidth() + imagePadding, y + imgHeight + (textHeight - imgHeight) / 2);
        }
    }
    public void renderTileBoundaries(Graphics g) {
        for (Tile tile : playing.getTileController().getTileList()) {
            if (tile.isHovered()) {
                int tileWidth = tile.getWidth();
                int tileHeight = tile.getHeight();
                if (playing.getTowerController().towerOn(tile.getPos().getX(), tile.getPos().getY()) == null) {
                    g.setColor(Color.ORANGE);
                    g.drawRect(tile.getPos().getX() - tileWidth / 2, tile.getPos().getY() - tileHeight / 2, tileWidth - 1, tileHeight - 1);
                } else {
                    g.setColor(Color.RED);
                    g.drawRect(tile.getPos().getX() - tileWidth / 2, tile.getPos().getY() - tileHeight / 2, tileWidth - 1, tileHeight - 1);
                    g.drawRect(tile.getPos().getX() - tileWidth / 2, tile.getPos().getY() - tileHeight / 2, tileWidth, tileHeight);

                }
            }
            if(towerPointer != null) {
                if (towerPointer.getPos().getX() == tile.getPos().getX() && towerPointer.getPos().getY() == tile.getPos().getY()) {
                    g.setColor(Color.BLUE);
                    g.drawRect(tile.getPos().getX() - tile.getWidth() / 2, tile.getPos().getY() - tile.getHeight() / 2, tile.getWidth()-1, tile.getHeight()-1);
                }
            }
        }
    }

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
