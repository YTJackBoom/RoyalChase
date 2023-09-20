package uiElements;

import controllers.DialogController;
import helpers.Constants;

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

        // Update the bounds height based on the message content
        int totalTextHeight = calculateTotalTextHeight(message);
        bounds.height = totalTextHeight + 60; // 60 for button height, and margins

        int buttonWidth = 50;
        int buttonHeight = 30;
        int buttonX = bounds.x + (bounds.width - buttonWidth) / 2;
        int buttonY = bounds.y + bounds.height - buttonHeight - 10; // 10px margin from the bottom

        this.okButton = new MyButton("OK", buttonX, buttonY, buttonWidth, buttonHeight, true);
    }

    public void render(Graphics g) {
        if (!isVisible) return;
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        drawStringWithinBounds(g, message, bounds);
        okButton.render(g);
    }

    private void drawStringWithinBounds(Graphics g, String str, Rectangle rect) {
        g.setFont(Constants.UIConstants.DIALOGFONT);
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Font font = g.getFont();
        List<String> lines = splitTextIntoLines(str, rect.width, font, frc);

        int y = rect.y + font.getSize();
        for (String line : lines) {
            g.drawString(line, rect.x, y);
            y += font.getSize() + 5;
        }
    }

    private int calculateTotalTextHeight(String str) {
        Font font = Constants.UIConstants.DIALOGFONT;
        FontRenderContext frc = new FontRenderContext(null, true, true);
        List<String> lines = splitTextIntoLines(str, bounds.width, font, frc);

        // Calculate total height
        int totalHeight = 0;
        for (String line : lines) {
            totalHeight += font.getSize() + 5;
        }
        return totalHeight;
    }

    private List<String> splitTextIntoLines(String str, int width, Font font, FontRenderContext frc) {
        List<String> lines = new ArrayList<>();
        String[] words = str.split(" ");
        StringBuilder currentLine = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            TextLayout tl = new TextLayout(currentLine.toString() + " " + words[i], font, frc);
            if (tl.getBounds().getWidth() > width) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(words[i]);
            } else {
                currentLine.append(" ").append(words[i]);
            }
        }
        lines.add(currentLine.toString());
        return lines;
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
