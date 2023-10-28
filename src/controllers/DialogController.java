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

import static basics.Game.*;

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
                {0.5f, 0.5f},
                {0.5f, 0.5f},
                {0.5f, 0.5f},
                {0.5f, 0.5f},
                {0.5f, 0.5f},
                {0.5f, 0.5f},
                {0.5f, 0.5f},
                {0.5f, 0.5f},
                {0.5f, 0.1f},
                {(float) 1550/ initGameWidth, (float) 450 / initGameHeight },
                {0.3f, 0.5f},
                {0.4f, 0.5f}
        };

        String[] messages = {
                "Du wurdest zum neuen Groß-General des Königreichs Eldoria ernannt!",
                "Seit der König von den Dunklen Magiern entführt wurde ist das Kriegs-Gesetzt in Aktion und du hast nun die größte Macht",
                "Jedoch ist das Königreich nach der Entführung immer Tiefre gefallen und steht nun vor dem Kollaps ",
                "Doch dies ist noch nicht das Ende, denn deinen Wissenschaftlern und Magiern ist es gelungen die Hauptstadt mobil zu machen",
                "Und so kannst du den Spuren des Königs zu folgen",
                "Aus diesem Grund hast du begonnen die magischen Spuren des Königs in der Nähe verschiendener Monster-Lager mithilfe deiner Magier zu verfolgen",
                "Hierfür sind deine Strategen und du zum Ergebnis gekommen, dass es wohl am besten ist, die angreifenden Monster defensiv zu bekämpfen, bis die magier fertig sind",
                "Gerade ist das Spiel pausiert. Du kannst jederzeit mit der Leertaste Pausieren/Fortzsetzen",
                "Im oberen Teil des Bildschirms werden deine Diversen Resourcen angezeigt, welche genutzt werden können, um Türme oder Gebäude zu Bauen",
                "Durchs klicken auf diesen Button erscheint deine Bau-Übericht für neue Türme",
                "Versuche doch einmal einen Turm auf ein Feld zu platzieren. Freie Felder werden Grün umrahmt und falls du einen Turm ersetzt erscheint dass Feld Rot",
                "Schauen wir uns nun deine Stadt an. Clicke hierfür einfach auf Stadt"
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
                {0.5f, 0.3f},
                {0.5f, 0.3f},
                {0.5f, 0.3f},
                {0.5f, 0.3f},
                {0.5f, 0.3f}
        };

        String[] messages = {
                "Willkommen in deiner Hauptstadt!",
                "Wie du siehst ist die gesamte Infrastructure zusammengebrochen!",
                "Der wiederaufbau der Produktion erfolgt gleich wie das Bauen von Türmen",
                "Achte nur darauf immer genug bewohner für alle deine Vorhaben zu haben, alle anderen Ressourcen generieren ein wenig, selbst wenn du nichts baust, falls du mal leer lauffen solltest",
                "Nimm dich auch vor den Leveln 3,6 und 9 in Acht, dort hausen die Monster-Generäle "
        };

        for (int i = 0; i < relativePositions.length; i++) {
            float relativeX = relativePositions[i][0];
            float relativeY = relativePositions[i][1];
            int width = 200;
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
        if(ISDEVMODE) System.out.println(nextIndex);
        if (nextIndex < dialogs.size()) {
            dialogs.get(nextIndex).setVisible(true);
            if(ISDEVMODE) System.out.println(dialogs.get(nextIndex).isVisible());
        } else {
            return;
        }
    }

    public void mouseReleased(int x, int y) {
        for (Dialog dialog : dialogs) {
            if (dialog.isVisible() && dialog.contains(x, y)) {
                dialog.mouseReleased(x, y);
                break;
            }
        }
    }
}
