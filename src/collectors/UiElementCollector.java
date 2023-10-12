package collectors;

import uiElements.UiElement;

import java.util.ArrayList;

/**
 * Ein Singelton-Kollektor, welcher alle Top-Level UiElemente speichert, um sie bei einem Screen-Resize zu benachrichtigen (Top-Level UiElemente sind UiElemente, welche direkt proportional zum Fenster platziert sind)
 */

public class UiElementCollector {
    private static UiElementCollector instance;
    private ArrayList<UiElement> topLevelUiElements;

    private UiElementCollector() {
        topLevelUiElements = new ArrayList<>();
    }

    public static UiElementCollector getInstance() {
        if (instance == null) {
            instance = new UiElementCollector();
        }
        return instance;
    }

    public void addUiElement(UiElement uiElement) {
        topLevelUiElements.add(uiElement);
    }


    public void removeUiElement(UiElement uiElement) {
        topLevelUiElements.remove(uiElement);
    }

    /**
     * Methode zum Aktualisieren aller Top-Level UiElemente bei einem Screen-Resize, diese  aktualisieren dann ihren "Children"
     */
    public void notifyScreenResize(int width, int height) {
        for (UiElement uiElement : topLevelUiElements) {
            uiElement.getUiCoordinate().getRelativePosition().setReferenceDimensions(width, height);
            uiElement.updatePosOnResize();
        }
    }
}

