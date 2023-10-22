package helpers;

import controllers.TileController;
import controllers.TowerController;
import gameObjects.Tile;

import java.util.ArrayList;

/**
 * Klasse zum Finden des Pfades durch die Tiles
 */
public class PathFinder {
    private TileController tileController;
    private Tile startTile;
    private Tile endTile;
    private ArrayList<AbsoluteCoordinate> path;

    public PathFinder(TileController tileController) {
            this.tileController = tileController;

        for (Tile tile : tileController.getTileList()) { // Speichern der Start und End Tiles
            if (tile.isStart()) {
                this.startTile = tile;
            }
            if (tile.isGate()) {
                this.endTile = tile;
            }
        }
        readPath();
    }

    /**
     * Methode um den Pfad durch die Tiles zu finden
     * @return Pfad durch die Tiles
     */
    public void readPath() {
        path = new ArrayList<>();
        int startX = startTile.getPos().getX();
        int startY = startTile.getPos().getY();
        AbsoluteCoordinate currentCoord = new AbsoluteCoordinate(startX, startY);
        Tile currentTile = startTile;

        while (currentTile != null) {
            ArrayList<AbsoluteCoordinate> currentTilePath = currentTile.getGlobalPath(currentCoord);
            path.addAll(currentTilePath);

            if (currentTilePath.size() > 0) {
                currentCoord = currentTilePath.get(currentTilePath.size() - 1);
            }

            // If the currentTile equals the endTile, stop the loop after processing the current tile
            if (currentTile.equals(endTile)) {
                break;
            }

            currentTile = getNextTileFromCoord(currentTile, currentCoord);
        }
    }


    /**
     * Methode um das Nachbar-Tile zu finden
     * @param currentTile
     * @param coord
     * @return
     */
    private Tile getNextTileFromCoord(Tile currentTile, AbsoluteCoordinate coord) {
        for (Tile neighbour : getNeighbours(currentTile)) {
            if (neighbour.isPath() && coordAndTileAreAdjected(coord,neighbour)) {
                return neighbour;
            }
        }
        return null;
    }
    public ArrayList<AbsoluteCoordinate> getPath() {
       return path;
    }

    /**
     * Methode, um zu prüfen, ob die erste Koordinate auf dem pfad des nächsten tiles der letzten des derzeitigen tiles anliegt.
     * @param coord Letzte Koordinate des Vorherigen Tiles
     * @param tile Nächstes Tile
     * @return
     */
    private boolean coordAndTileAreAdjected(AbsoluteCoordinate coord, Tile tile) {
        ArrayList<AbsoluteCoordinate> tilePath = tile.getGlobalPath(coord);
        if (tilePath.isEmpty()) {
            return false;
        }

        AbsoluteCoordinate firstCoordOfTilePath = tilePath.get(0);

        int deltaX = Math.abs(coord.getX() - firstCoordOfTilePath.getX());
        int deltaY = Math.abs(coord.getY() - firstCoordOfTilePath.getY());

        return (deltaX <= 1 && deltaY <= 1);
    }

//    /**
//     * Methode um die Liste der Tiles welche dem Pfad entsprechen zu finden
//     */
//    private ArrayList<Tile> determineTileSequence() {
//        ArrayList<Tile> sequence = new ArrayList<>();
//        HashSet<Tile> visited = new HashSet<>();
//        Tile currentTile = startTile;
//
//        while (currentTile != null && !currentTile.equals(endTile) && !visited.contains(currentTile)) {
//            visited.add(currentTile);
//            sequence.add(currentTile);
//
//            List<Tile> neighbours = getNeighbours(currentTile);
//            Tile nextTile = null;
//            for (Tile neighbour : neighbours) {
//                if (neighbour.isPath() && !visited.contains(neighbour)) {
//                    nextTile = neighbour;
//                    break;
//                }
//            }
//            currentTile = nextTile;
//        }
//
//        if (endTile != null && !sequence.contains(endTile)) {
//            sequence.add(endTile);
//        }
//
//        return sequence;
//    }

    /**
     * Methode um die Nachbarn eines Tiles zu finden
     * @param tile Tile von welchem die Nachbarn gefunden werden sollen
     * @return Liste der Nachbarn
     */
    private ArrayList<Tile> getNeighbours(Tile tile) {
        ArrayList<Tile> neighbours = new ArrayList<>();

        int tileSize = 256;
        int[][] directions = {
                {-tileSize, 0}, // Left
                {tileSize, 0},  // Right
                {0, -tileSize}, // Up
                {0, tileSize},   // Down
                {tileSize, tileSize}, // Down-Right
                {-tileSize, tileSize}, // Down-Left
                {tileSize, -tileSize}, // Up-Right
                {-tileSize, -tileSize} // Up-Left
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

    public Tile getEndTile() {
        return endTile;
    }
}