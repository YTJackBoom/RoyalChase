package helpers;

import controllers.TileController;
import gameObjects.Tile;
import helpers.Coordinate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PathFinder {
    private TileController tileController;
    private Tile startTile;
    private Tile endTile;

    public PathFinder(TileController tileController) {
            this.tileController = tileController;

            // Initialize the start and end tiles based on tiles with isStart and isEnd properties
            for (Tile tile : tileController.getTileList()) {
                if (tile.isStart()) {
                    this.startTile = tile;
                }
                if (tile.isGate()) {
                    this.endTile = tile;
                }
            }
        }

        public ArrayList<Coordinate> getPath() {
            ArrayList<Coordinate> fullPath = new ArrayList<>();
            Coordinate currentCoord = startTile.getPos();

            // Calculate the sequence of tiles based on the path.
            ArrayList<Tile> tileSequence = determineTileSequence();

            for (Tile tile : tileSequence) {
                List<Coordinate> pathThroughTile = tile.getGlobalPath(currentCoord);
                fullPath.addAll(pathThroughTile);

                if (pathThroughTile.size() > 0) {
                    currentCoord = pathThroughTile.get(pathThroughTile.size() - 1);
                }
            }

            return fullPath;
        }

    private ArrayList<Tile> determineTileSequence() {
        ArrayList<Tile> sequence = new ArrayList<>();
        HashSet<Tile> visited = new HashSet<>();
        Tile currentTile = startTile;

        while (currentTile != null && !currentTile.equals(endTile) && !visited.contains(currentTile)) {
            visited.add(currentTile);
            sequence.add(currentTile);

            List<Tile> neighbours = getNeighbours(currentTile);
            Tile nextTile = null;
            for (Tile neighbour : neighbours) {
                if (neighbour.isPath() && !visited.contains(neighbour)) {
                    nextTile = neighbour;
                    break;
                }
            }
            currentTile = nextTile;
        }

        // Ensure the end tile is added to the sequence
        if (endTile != null && !sequence.contains(endTile)) {
            sequence.add(endTile);
        }

        return sequence;
    }

    private ArrayList<Tile> getNeighbours(Tile tile) {
        ArrayList<Tile> neighbours = new ArrayList<>();

        int tileSize = 256;  // Adjust this value based on your actual tile size if different
        int[][] directions = {
                {-tileSize, 0}, // Left
                {tileSize, 0},  // Right
                {0, -tileSize}, // Up
                {0, tileSize}   // Down
        };

        for (int[] direction : directions) {
            int x = tile.getPos().getX() + direction[0];
            int y = tile.getPos().getY() + direction[1];
            Tile neighbourTile = tileController.getTile(x, y);

            if (neighbourTile != null) {
                neighbours.add(neighbourTile);
            }
        }

        return neighbours;
    }

}