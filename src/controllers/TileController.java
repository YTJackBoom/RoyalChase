package controllers;

import gameObjects.Tile;
import helpers.AbsoluteCoordinate;
import helpers.ObjectValues;
import helpers.TextReader;
import helpers.AssetLocation;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static basics.Game.fHEIGHT;
import static basics.Game.fWIDTH;

/**
 * Controller Klasse für die Tiles in PLaying
 */
public class TileController extends ObjectsController implements ControllerMethods {
    private Playing playing;
    private ArrayList<Tile> tileList;
    private int[][] tileData = new int[0][0];
    private static final Random RANDOM = new Random();


    public TileController(Playing playing) {
        this.playing = playing;
        tileList = new ArrayList<Tile>();
        initTiles();
        extendTiles(fWIDTH, fHEIGHT);
    }

    /**
     * Methode, um die Tiles aus der Text-Datei zum derzeitigen Level zu initialisieren
     */
    private void initTiles() {
        TextReader textReader = new TextReader(AssetLocation.Maps.getTileTextFile(playing.getGame().getGameState().getPlayerValues().getLevel()));
        try {
            tileData = textReader.readTileData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int x = 0; x < tileData.length; x++) {
            for (int y = 0; y < tileData[x].length; y++) {
                Tile tile = ObjectValues.Tiles.getRawTile(tileData[x][y]);
                tile.setPos(new AbsoluteCoordinate(x * 256 + 128, y * 256 + 128));
                tileList.add(tile);
            }
        }
    }

    /**
     * Methode, um die Tiles zu erweitern, falls der Bildschirm resizet wird
     * @param newWidth Bildschirmbreite
     * @param newHeight Bildschirmhöhe
     */
    public void extendTiles(int newWidth, int newHeight) {
        int tileSize = 256;

        int tilesRequiredX = (int) Math.ceil((double) newWidth / tileSize) - tileData.length;
        int tilesRequiredY = (int) Math.ceil((double) newHeight / tileSize) - (tileData.length > 0 ? tileData[0].length : 0);

        int[] predefinedTiles = {0, 1}; //zufälliges tile (1 == Gras oder 0 == Wald)

        for (int x = 0; x < tilesRequiredX + tileData.length; x++) {
            for (int y = 0; y < tilesRequiredY + (tileData.length > 0 ? tileData[0].length : 0); y++) {
                if ((x >= tileData.length || y >= (tileData.length > 0 ? tileData[0].length : 0)) && getTile(x*tileSize, y*tileSize) == null) {
                    int randomTileType = predefinedTiles[RANDOM.nextInt(predefinedTiles.length)];
                    Tile tile = ObjectValues.Tiles.getRawTile(randomTileType);
                    tile.setPos(new AbsoluteCoordinate(x * tileSize + tileSize / 2, y * tileSize + tileSize / 2));
                    tileList.add(tile);
                }
            }
        }
    }


    @Override
    public void workAddQueue() {

    }

    @Override
    public void workRemoveQueue() {

    }

    /**
     * Methode zum rendern der Tiles. "Manuell" da tile kein Gameobject ist
     * @param g Graphics Objekt
     */
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

    /**
     * ZUm setzen der Hovered-Variable, wenn ein Tower gebaut wird
     * @param mouseX
     * @param mouseY
     */
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
