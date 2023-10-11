package collectors;

import uiElements.UiElement;

import java.util.ArrayList;

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

    public void notifyScreenResize(int width, int height) {
        for (UiElement uiElement : topLevelUiElements) {
            uiElement.getUiCoordinate().getRelativePosition().setReferenceDimensions(width, height);
            uiElement.updatePosOnResize();
        }
    }
}

