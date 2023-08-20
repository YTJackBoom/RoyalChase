package controllers;

import uiElements.Dialog;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class DialogController {
    private ArrayList<Dialog> visibleDialogs;

    public DialogController() {
        this.visibleDialogs = new ArrayList<Dialog>();

    }
    public void addDialog(Dialog dialog) {
        visibleDialogs.add(dialog);
    }

    public void render(Graphics g) {
        for (Dialog dialog : visibleDialogs) {
            dialog.render(g);
        }
    }

//    public void mouseClicked(int x, int y) {
//        for (Dialog dialog : visibleDialogs) {
//            if(!dialog.isVisible()) visibleDialogs.remove(dialog);
//            boolean moveToNext = dialog.onClick(x, y);
//
//            if (moveToNext) {
//                currentDialog = dialogsQueue.poll();
//            }
//        }
//    }
//    }
}
