package helpers;

import static basics.Game.fHEIGHT;
import static basics.Game.fWIDTH;

public class RelativeCoordinate {
    private AbsoluteCoordinate referencePoint;
    private float xOffsetPercentage;
    private float yOffsetPercentage;
    private int referenceWidth;  // Added
    private int referenceHeight;  // Added

    public RelativeCoordinate(AbsoluteCoordinate referencePoint, float xOffsetPercentage, float yOffsetPercentage, int referenceWidth, int referenceHeight) {
        this.referencePoint = referencePoint;
        this.xOffsetPercentage = xOffsetPercentage;
        this.yOffsetPercentage = yOffsetPercentage;
        this.referenceWidth = referenceWidth;  // Set in the constructor
        this.referenceHeight = referenceHeight;  // Set in the constructor
    }

    public RelativeCoordinate(AbsoluteCoordinate referencePoint, float xOffsetPercentage, float yOffsetPercentage) {
        this.referencePoint = referencePoint;
        this.xOffsetPercentage = xOffsetPercentage;
        this.yOffsetPercentage = yOffsetPercentage;
        this.referenceWidth = fWIDTH;  // Set in the constructor
        this.referenceHeight = fHEIGHT;  // Set in the constructor
    }

    public AbsoluteCoordinate calculateInitAbs() {
        int x = referencePoint.getX() + (int) (xOffsetPercentage * referenceWidth);
        int y = referencePoint.getY() + (int) (yOffsetPercentage * referenceHeight);
        return new AbsoluteCoordinate(x, y);
    }

    public AbsoluteCoordinate adjustAbsolute(AbsoluteCoordinate absoluteCoordinate) {
        int x = referencePoint.getX() + (int) (xOffsetPercentage * referenceWidth);
        int y = referencePoint.getY() + (int) (yOffsetPercentage * referenceHeight);

        absoluteCoordinate.setX(x);
        absoluteCoordinate.setY(y);
        return absoluteCoordinate;
    }

    public void setReferenceDimensions(int width, int height) {
        this.referenceWidth = width;
        this.referenceHeight = height;
    }

    public float getxOffsetPercentage() {
        return xOffsetPercentage;
    }

    public float getyOffsetPercentage() {
        return yOffsetPercentage;
    }
}
