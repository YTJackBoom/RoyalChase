package scenes;

import basics.Game;

public class GameScenes {
    protected boolean dragingObject;
    protected int draggedObjectType;

    protected Game game;

    public GameScenes(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setDragingObject(boolean b) {
        dragingObject = b;
    }

    public int getDraggedObjectType() {
        return draggedObjectType;
    }

    public void setDraggedObjectType(int type) {
        draggedObjectType = type;
    }
}
