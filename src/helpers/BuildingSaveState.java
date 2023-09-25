package helpers;

import java.io.Serializable;

public class BuildingSaveState implements Serializable {
    private AbsoluteCoordinate pos;
    private int type;

    public BuildingSaveState(AbsoluteCoordinate pos, int type) {
        this.pos = pos;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public AbsoluteCoordinate getPos() {
        return pos;
    }

    public void setPos(AbsoluteCoordinate pos) {
        this.pos = pos;
    }
}
