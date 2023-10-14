package helpers;

import static basics.Game.fHEIGHT;
import static basics.Game.fWIDTH;

/**
 * Klasse für die relativen Koordinaten (Eine Koordinate, welche die Relatvität zu einem anderen Punkt speichert)
 */
public class RelativeCoordinate {
    private AbsoluteCoordinate referencePoint;
    private float xOffsetPercentage;
    private float yOffsetPercentage;
    private int referenceWidth;
    private int referenceHeight;

    /**
     * Konstruktor für relatvität zu einem anderen Object
     * @param referencePoint Referenzpunkt
     * @param xOffsetPercentage x-Offset in Prozent
     * @param yOffsetPercentage y-Offset in Prozent
     * @param referenceWidth Referenzbreite
     * @param referenceHeight Referenzhöhe
     */
    public RelativeCoordinate(AbsoluteCoordinate referencePoint, float xOffsetPercentage, float yOffsetPercentage, int referenceWidth, int referenceHeight) {
        this.referencePoint = referencePoint;
        this.xOffsetPercentage = xOffsetPercentage;
        this.yOffsetPercentage = yOffsetPercentage;
        this.referenceWidth = referenceWidth;
        this.referenceHeight = referenceHeight;
    }

    /**
     * Konstruktor für relatvität zum Fenster
     * @param referencePoint Referenzpunkt
     * @param xOffsetPercentage x-Offset in Prozent
     * @param yOffsetPercentage y-Offset in Prozent
     */
    public RelativeCoordinate(AbsoluteCoordinate referencePoint, float xOffsetPercentage, float yOffsetPercentage) {
        this.referencePoint = referencePoint;
        this.xOffsetPercentage = xOffsetPercentage;
        this.yOffsetPercentage = yOffsetPercentage;
        this.referenceWidth = fWIDTH;  // Set in the constructor
        this.referenceHeight = fHEIGHT;  // Set in the constructor
    }

    /**
     * Methode um die absolute Koordinate durch Relativität zu berechnen
     * @return Absolute Koordinate
     */
    public AbsoluteCoordinate calculateInitAbs() {
        int x = referencePoint.getX() + (int) (xOffsetPercentage * referenceWidth);
        int y = referencePoint.getY() + (int) (yOffsetPercentage * referenceHeight);
        return new AbsoluteCoordinate(x, y);
    }

    /**
     * Methode um eine gegebene absolute Koordinate durch Relativität zu verändern
     * @param absoluteCoordinate Absolute Koordinate
     * @return Umgerechnete Absolute Koordinate
     */
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
