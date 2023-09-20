package controllers;

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
        // Adjust dialog positions as needed.
        dialogs.add(0, new Dialog(new Rectangle(200, 500, 200, 0), "Du wurdest zum neuen Groß-General des Königreichs Eldoria ernannt!", this));
        dialogs.get(0).setVisible(true);
        dialogs.add(1, new Dialog(new Rectangle(300, 600, 200, 0), "Seit der König von den Dunklen Magiern entführt wurde herrscht das Kriegs-Gesetzt und du hast nun die größte Macht", this));
        dialogs.add(2, new Dialog(new Rectangle(400, 600, 200, 0), "Doch dies ist noch nicht dass Ende, denn deinen Wissenschaftlern und Magiern ist es gelungen die Hauptstdt mobil zu machen ", this));
        dialogs.add(3, new Dialog(new Rectangle(500, 600, 200, 0), "Aus diesem Grund hast du begonnen Monster-Lager in defensiven Kämpfen einzunehmen, bis du zum König vordringst!", this));
        dialogs.add(4, new Dialog(new Rectangle(500, 500, 200, 0), "Gerade ist das Spiel pausiert. Du kannst jederzeit mit der Leertaste Pausieren/Fortzsetzen", this));
        dialogs.add(5, new Dialog(new Rectangle(400, 500, 200, 0), "Im oberen Teil des Bildschirms werden deine Diversen Resourcen angezeigt, welche genutzt werden können, um Türme oder Gebäude zu Bauen", this));

        dialogs.add(6, new Dialog(new Rectangle(300, 500, 200, 0), "Durchs clicken auf diesen Button erscheint deine Bau-Übericht für neue Türme", this));
        dialogs.add(7, new Dialog(new Rectangle(200, 500, 200, 0), "Versuche doch einmal einen Turm auf ein Feld zu platzieren. Freie Felder werden Grün umrahmt und falls du einen Turm ersetzt erscheint dass Feld Rot", this));
        dialogs.add(8, new Dialog(new Rectangle(100, 500, 200, 0), "Schauen wir uns nun deine Stadt an. Clicke hierfür einfach af Stadt", this));


//        dialogs.add(new Dialog(new Rectangle(500, 500, 100, 100), "Dein Königreich wird von den Dunklen Magieren und ihren Schergen angegriffen!!", this));

    }

    public void initTownDialogs() {
        // Adjust dialog positions as needed.
        dialogs.add(new Dialog(new Rectangle(500, 500, 100, 100), "This is a dialog", this));
        dialogs.get(0).setVisible(true);

        dialogs.add(new Dialog(new Rectangle(500, 600, 100, 100), "This is another dialog", this));
        dialogs.add(new Dialog(new Rectangle(500, 600, 100, 100), "This is another dialog", this));


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
            if (dialog.isVisible() && dialog.getBounds().contains(x, y)) {
                dialog.mouseReleased(x, y);
            }
        }
    }
}
