package uiElements;

import controllers.DialogController;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.ArrayList;
import java.util.List;

public class Dialog {
    private String message;
    private MyButton okButton;
    private boolean isVisible;
    private DialogController dialogController;
    private Rectangle bounds;

    public Dialog(Rectangle bounds, String message, DialogController dialogController) {
        this.message = message;
        this.isVisible = false;
        this.dialogController = dialogController;
        this.bounds = bounds;

        int buttonWidth = 50;
        int buttonHeight = 30;
        int buttonX = bounds.x + (bounds.width - buttonWidth) / 2;
        int buttonY = bounds.y + bounds.height - buttonHeight + 10; // 10px margin from the bottom

        this.okButton = new MyButton("OK", buttonX, buttonY, buttonWidth, buttonHeight, true);
    }

    public void render(Graphics g) {
        if (!isVisible) return;

        // Render a rectangle around the text
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // Draw the message respecting the bounds
        drawStringWithinBounds(g, message, bounds);

        // Render the OK button
        okButton.render(g);
    }

    private void drawStringWithinBounds(Graphics g, String str, Rectangle rect) {
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Font font = g.getFont();
        List<String> lines = new ArrayList<>();

        String[] words = str.split(" ");
        StringBuilder currentLine = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            TextLayout tl = new TextLayout(currentLine.toString() + " " + words[i], font, frc);
            if (tl.getBounds().getWidth() > rect.width) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(words[i]);
            } else {
                currentLine.append(" ").append(words[i]);
            }
        }
        lines.add(currentLine.toString());

        int y = rect.y + font.getSize(); // Start from top of the rectangle plus font size
        for (String line : lines) {
            g.drawString(line, rect.x, y);
            y += font.getSize() + 5; // Increase y position by font size and some padding
        }
    }

    public void mouseReleased(int x, int y) {
        if (okButton.getBounds().contains(x, y)) {
            dialogController.clickedOk(this);
        }
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
