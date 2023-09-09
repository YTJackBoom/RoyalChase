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
        dialogs.add(new Dialog(new Rectangle(500, 500, 100, 100), "This is a dialog", this));
        dialogs.get(0).setVisible(true);

        dialogs.add(new Dialog(new Rectangle(500, 600, 100, 100), "This is another dialog", this));
    }

    public void initTownDialogs() {
        // Adjust dialog positions as needed.
        dialogs.add(new Dialog(new Rectangle(500, 500, 100, 100), "This is a dialog", this));
        dialogs.get(0).setVisible(true);

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
        if (nextIndex < dialogs.size()) dialogs.get(nextIndex).setVisible(true);

    }

    public void mouseReleased(int x, int y) {
        for (Dialog dialog : dialogs) {
            if (dialog.isVisible() && dialog.getBounds().contains(x, y)) {
                dialog.mouseReleased(x, y);
            }
        }
    }
}
