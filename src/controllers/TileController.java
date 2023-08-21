package controllers;

import gameObjects.Tile;
import helpers.Coordinate;
import helpers.TextReader;
import helpers.variables;
import scenes.Playing;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class TileController extends ObjectsController implements ControllerMethods {
    private Playing playing;
    private ArrayList<Tile> tileList;

    public TileController(Playing playing) {
        this.playing = playing;
        tileList = new ArrayList<Tile>();
        initTiles();
    }

    public void initTiles() {
        TextReader textReader = new TextReader(variables.Maps.getTileTextFile(playing.getGame().getGameState().getPlayerValues().getLevel()));
        int[][] tileData;
        try {
            tileData = textReader.readTileData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int x = 0; x < tileData.length; x++) {
            for (int y = 0; y < tileData[x].length; y++) {
                Tile tile = variables.Maps.getRawTile(tileData[x][y]);
                tile.setPos(new Coordinate(x * 256 + 128, y * 256 + 128));
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
            tile.render(g);
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


}
