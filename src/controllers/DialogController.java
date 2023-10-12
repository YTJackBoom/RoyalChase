package controllers;

import basics.Game;
import helpers.AbsoluteCoordinate;
import helpers.RelativeCoordinate;
import helpers.UiCoordinate;
import scenes.GameScenes;
import scenes.Playing;
import uiElements.Dialog;

import java.awt.*;
import java.util.ArrayList;

/**
 * Klasse für die Dialoge im Tutorial
 */
public class DialogController {
    private ArrayList<Dialog> dialogs;

    public DialogController(GameScenes scene) {
        this.dialogs = new ArrayList<Dialog>();
        if (scene instanceof Playing) {
            initPlayingDialogs();
        } else {
            initTownDialogs();
        }

    }

    /**
     * Methode zum Initialiseren der Dialoge in der PLaying-Scene
     */
    public void initPlayingDialogs() {
        AbsoluteCoordinate referencePoint = new AbsoluteCoordinate(0, 0); // Oben links als Referenzpunkt

        float[][] relativePositions = {
                {0.2f, 0.5f},
                {0.3f, 0.6f},
                {0.4f, 0.6f},
                {0.5f, 0.6f},
                {0.5f, 0.5f},
                {0.4f, 0.5f},
                {0.3f, 0.5f},
                {0.2f, 0.5f},
                {0.1f, 0.5f}
        };

        String[] messages = {
                "Du wurdest zum neuen Groß-General des Königreichs Eldoria ernannt!",
                "Seit der König von den Dunklen Magiern entführt wurde herrscht das Kriegs-Gesetzt und du hast nun die größte Macht",
                "Doch dies ist noch nicht dass Ende, denn deinen Wissenschaftlern und Magiern ist es gelungen die Hauptstdt mobil zu machen",
                "Aus diesem Grund hast du begonnen Monster-Lager in defensiven Kämpfen einzunehmen, bis du zum König vordringst!",
                "Gerade ist das Spiel pausiert. Du kannst jederzeit mit der Leertaste Pausieren/Fortzsetzen",
                "Im oberen Teil des Bildschirms werden deine Diversen Resourcen angezeigt, welche genutzt werden können, um Türme oder Gebäude zu Bauen",
                "Durchs clicken auf diesen Button erscheint deine Bau-Übericht für neue Türme",
                "Versuche doch einmal einen Turm auf ein Feld zu platzieren. Freie Felder werden Grün umrahmt und falls du einen Turm ersetzt erscheint dass Feld Rot",
                "Schauen wir uns nun deine Stadt an. Clicke hierfür einfach af Stadt"
        };

        for (int i = 0; i < relativePositions.length; i++) {
            float relativeX = relativePositions[i][0];
            float relativeY = relativePositions[i][1];
            int width = 200;
            int height = 0; // Dialog größen werden automatisch berechnet(in Dialogs Constructor)

            UiCoordinate dialogCoordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, relativeX, relativeY));

            dialogs.add(new Dialog(dialogCoordinate, width, height, messages[i], this));
        }
        dialogs.get(0).setVisible(true);
    }

    /**
     * Methode zum Initialiseren der Dialoge in der Town-Scene
     */
    public void initTownDialogs() {
        AbsoluteCoordinate referencePoint = new AbsoluteCoordinate(0, 0); // Oben links als Referenzpunkt

        // Define relative positions for the dialogs
        float[][] relativePositions = {
                {0.5f, 0.5f},
                {0.5f, 0.6f},
                {0.5f, 0.6f}
        };

        String[] messages = {
                "This is a dialog",
                "This is another dialog",
                "This is another dialog"
        };

        for (int i = 0; i < relativePositions.length; i++) {
            float relativeX = relativePositions[i][0];
            float relativeY = relativePositions[i][1];
            int width = 100;
            int height = 100;

            UiCoordinate dialogCoordinate = new UiCoordinate(
                    new RelativeCoordinate(referencePoint, relativeX, relativeY));

            dialogs.add(new Dialog(dialogCoordinate, width, height, messages[i], this));
        }
        dialogs.get(0).setVisible(true);
    }

    public void render(Graphics g) {
        for (Dialog dialog : dialogs) {
            dialog.render(g);
        }
    }

    /**
     * Methode zum weiterklicken der Dialoge
     * @param d Angeklickter Dialog
     */
    public void clickedOk(Dialog d) {
        d.setVisible(false);
        int nextIndex = dialogs.indexOf(d) + 1;
        System.out.println(nextIndex);
        if (nextIndex < dialogs.size()) {
            dialogs.get(nextIndex).setVisible(true);
            System.out.println(dialogs.get(nextIndex).isVisible());
        } else {
            return;
        }
    }

    public void mouseReleased(int x, int y) {
        for (Dialog dialog : dialogs) {
            if (dialog.isVisible() && dialog.contains(x, y)) {
                dialog.mouseReleased(x, y);
            }
        }
    }
}
