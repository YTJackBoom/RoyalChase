package collectors;

import uiElements.UiElement;

import java.util.ArrayList;

import static basics.Game.fHEIGHT;
import static basics.Game.fWIDTH;

public class UiElementCollector {
    private static UiElementCollector instance;
    private ArrayList<UiElement> activeUiElements;

    private UiElementCollector() {
        activeUiElements = new ArrayList<>();
    }

    public static UiElementCollector getInstance() {
        if (instance == null) {
            instance = new UiElementCollector();
        }
        return instance;
    }

    public void addUiElement(UiElement uiElement) {
        activeUiElements.add(uiElement);
    }


    public void removeUiElement(UiElement uiElement) {
        activeUiElements.remove(uiElement);
    }

    public void notifyScreenResize() {
        for (UiElement uiElement : activeUiElements) {
            uiElement.getUiCoordinate().getRelativePosition().setReferenceDimensions(fWIDTH, fHEIGHT);
            uiElement.updatePosOnResize();
        }
    }
}

