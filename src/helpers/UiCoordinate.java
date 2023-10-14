package helpers;

/**
 *  Klasse für UI-Koordinaten (Die speicherung von Koordinaten von UI-Elementen als relative und absolute Koordinaten)
 */
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

    /**
     * Methode um die absolute Koordinate durch die relative Koordinate bei einem Resize zu aktualisieren
     */
    public void updateBasedOnFrame() {
        if (relativePosition != null) {
            absolutePosition = relativePosition.adjustAbsolute(absolutePosition);
        }
    }
}
