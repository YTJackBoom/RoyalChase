package helpers;

import java.io.Serializable;

public class BuildingSaveState implements Serializable {
    private Coordinate pos;
    private int type;

    public BuildingSaveState(Coordinate pos, int type) {
        this.pos = pos;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Coordinate getPos() {
        return pos;
    }

    public void setPos(Coordinate pos) {
        this.pos = pos;
    }
}
