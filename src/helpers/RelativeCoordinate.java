package helpers;

public class RelativeCoordinate {
    private AbsoluteCoordinate referencePoint;
    private float xOffsetPercentage;
    private float yOffsetPercentage;

    public RelativeCoordinate(AbsoluteCoordinate referencePoint, float xOffsetPercentage, float yOffsetPercentage) {
        this.referencePoint = referencePoint;
        this.xOffsetPercentage = xOffsetPercentage;
        this.yOffsetPercentage = yOffsetPercentage;
    }

    public AbsoluteCoordinate calculate(int frameWidth, int frameHeight) {
        int x = referencePoint.getX() + (int) (xOffsetPercentage * frameWidth);
        int y = referencePoint.getY() + (int) (yOffsetPercentage * frameHeight);
        return new AbsoluteCoordinate(x, y);
    }
}
