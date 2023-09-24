package helpers;

public class UiCoordinate {
    protected AbsoluteCoordinate absolutePosition;
    protected RelativeCoordinate relativePosition;

    public UiCoordinate(RelativeCoordinate relativePosition) {
        this.relativePosition = relativePosition;
        this.absolutePosition = relativePosition.calculateInitAbs();

    }

    public int getX() {
        return absolutePosition.x;
    }

    public int getY() {
        return absolutePosition.y;
    }

    public AbsoluteCoordinate getAbsolutePosition() {
        return absolutePosition;
    }

    public RelativeCoordinate getRelativePosition() {
        return relativePosition;
    }

    public void updateBasedOnFrame() {
        // If there's a relative position set, use it to update the absolute position
        if (relativePosition != null) {
            absolutePosition = relativePosition.adjustAbsolute(absolutePosition);
        }
    }
}
