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

    public void initPlayingDialogs() {
        int frameWidth = Game.initGameWidth;
        int frameHeight = Game.initGameHeight;
        AbsoluteCoordinate referencePoint = new AbsoluteCoordinate(0, 0); // Top-left corner as reference

        // Define relative positions for the dialogs
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
            int height = 0; // Adjust height based on the message, done in Dialog's constructor

            UiCoordinate dialogCoordinate = new UiCoordinate(new RelativeCoordinate(referencePoint, relativeX, relativeY));

            dialogs.add(new Dialog(dialogCoordinate, width, height, messages[i], this));
        }
        dialogs.get(0).setVisible(true);
    }

    public void initTownDialogs() {
        int frameWidth = Game.initGameWidth;
        int frameHeight = Game.initGameHeight;
        AbsoluteCoordinate referencePoint = new AbsoluteCoordinate(0, 0); // Top-left corner as reference

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


    public void addDialog(Dialog dialog) {
        dialogs.add(dialog);
    }

    public void render(Graphics g) {
        for (Dialog dialog : dialogs) {
            dialog.render(g);
        }
    }

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
