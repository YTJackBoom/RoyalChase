package controllers;

import gameObjects.Tile;
import helpers.Coordinate;
import helpers.TextReader;
import helpers.variables;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TileController extends ObjectsController implements ControllerMethods {
    private Playing playing;
    private ArrayList<Tile> tileList;
    private int[][] tileData = new int[0][0];
    private static final Random RANDOM = new Random();


    public TileController(Playing playing) {
        this.playing = playing;
        tileList = new ArrayList<Tile>();
        initTiles();
    }

    public void initTiles() {
        TextReader textReader = new TextReader(variables.Maps.getTileTextFile(playing.getGame().getGameState().getPlayerValues().getLevel()));
        try {
            tileData = textReader.readTileData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int x = 0; x < tileData.length; x++) {
            for (int y = 0; y < tileData[x].length; y++) {
                Tile tile = variables.Tiles.getRawTile(tileData[x][y]);
                tile.setPos(new Coordinate(x * 256 + 128, y * 256 + 128));
                tileList.add(tile);
            }
        }
    }
    public void extendTiles(int newWidth, int newHeight) {
        int tileSize = 256; // assuming each tile is 256x256 pixels, adjust if needed

        int tilesRequiredX = (int) Math.ceil((double) newWidth / tileSize) - tileData.length;
        int tilesRequiredY = (int) Math.ceil((double) newHeight / tileSize) - (tileData.length > 0 ? tileData[0].length : 0);

        int[] predefinedTiles = {0, 1};

        // Add tiles horizontally
        for (int x = tileData.length; x < tilesRequiredX + tileData.length; x++) {
            for (int y = 0; y < (tileData.length > 0 ? tileData[0].length : 0); y++) {
                int randomTileType = predefinedTiles[RANDOM.nextInt(predefinedTiles.length)];
                Tile tile = variables.Tiles.getRawTile(randomTileType);
                tile.setPos(new Coordinate(x * tileSize + tileSize / 2, y * tileSize + tileSize / 2));
                tileList.add(tile);
            }
        }

        // Add tiles vertically
        for (int x = 0; x < tileData.length; x++) {
            for (int y = (tileData.length > 0 ? tileData[0].length : 0); y < tilesRequiredY + (tileData.length > 0 ? tileData[0].length : 0); y++) {
                int randomTileType = predefinedTiles[RANDOM.nextInt(predefinedTiles.length)];
                Tile tile = variables.Tiles.getRawTile(randomTileType);
                tile.setPos(new Coordinate(x * tileSize + tileSize / 2, y * tileSize + tileSize / 2));
                tileList.add(tile);
            }
        }

        // Add tiles for the bottom-right corner
        for (int x = tileData.length; x < tilesRequiredX + tileData.length; x++) {
            for (int y = (tileData.length > 0 ? tileData[0].length : 0); y < tilesRequiredY + (tileData.length > 0 ? tileData[0].length : 0); y++) {
                int randomTileType = predefinedTiles[RANDOM.nextInt(predefinedTiles.length)];
                Tile tile = variables.Tiles.getRawTile(randomTileType);
                tile.setPos(new Coordinate(x * tileSize + tileSize / 2, y * tileSize + tileSize / 2));
                tileList.add(tile);
            }
        }
    }

    @Override
    public void workAddQueue() {

    }

    @Override
    public void workRemoveQueue() {

    }

    @Override
    public void render(Graphics g) {
        for (Tile tile : tileList) {
            BufferedImage tileImg = tile.getTileImage();
            g.drawImage(tileImg, tile.getPos().getX() - tileImg.getWidth() / 2, tile.getPos().getY() - tileImg.getHeight() / 2, null);
        }
    }

    @Override
    public void update() {

    }

    public Tile getTile(int x, int y) {
        for (Tile tile : tileList) {
            if (tile.getBounds().contains(x, y)) {
                return tile;
            }
        }
        return null;
    }


    public ArrayList<Tile> getTileList() {
        return tileList;
    }

    public void mouseDragged(int mouseX, int mouseY) {
        if (playing.getDragingTower()) {
            for (Tile tile : tileList) {
                if (tile.getBounds().contains(mouseX, mouseY) && tile.isBuildable()) {
                    tile.setHovered(true);
                } else {
                    tile.setHovered(false);
                }
            }
        }
    }

    public void mouseReleased(int x, int y) {
        for (Tile tile : tileList) {
            tile.setHovered(false);
        }
    }
}
