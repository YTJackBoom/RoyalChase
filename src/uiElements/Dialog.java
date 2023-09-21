package uiElements;

import controllers.DialogController;
import helpers.Constants;
import helpers.UiCoordinate;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.ArrayList;
import java.util.List;

public class Dialog extends UiElement {
    private String message;
    private MyButton okButton;
    private DialogController dialogController;

    public Dialog(UiCoordinate uiCoordinate, int width, int height, String message, DialogController dialogController) {
        super(uiCoordinate, width, height, UIObjectType.DIALOG, 0, true);
        this.message = message;
        isVisible = false;
        this.dialogController = dialogController;

        // Update the bounds height based on the message content
        int totalTextHeight = calculateTotalTextHeight(message);
        super.height = totalTextHeight + 60; // 60 for button height, and margins

        int buttonWidth = 50;
        int buttonHeight = 30;
        int buttonX = uiCoordinate.getX() + (width - buttonWidth) / 2;
        int buttonY = uiCoordinate.getY() + height - buttonHeight - 10; // 10px margin from the bottom

        this.okButton = new MyButton("OK", new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight), true);
    }

    public void render(Graphics g) {
        if (!isVisible) return;
        g.drawRect(uiCoordinate.getX(), uiCoordinate.getY(), width, height);
        drawStringWithinBounds(g);
        okButton.render(g);
    }

    private void drawStringWithinBounds(Graphics g) {
        g.setFont(Constants.UIConstants.DIALOGFONT);
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Font font = g.getFont();
        List<String> lines = splitTextIntoLines(message, width, font, frc);

        int y = uiCoordinate.getY() + font.getSize();
        for (String line : lines) {
            g.drawString(line, uiCoordinate.getX(), y);
            y += font.getSize() + 5;
        }
    }

    private int calculateTotalTextHeight(String str) {
        Font font = Constants.UIConstants.DIALOGFONT;
        FontRenderContext frc = new FontRenderContext(null, true, true);
        List<String> lines = splitTextIntoLines(str, width, font, frc);

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
        if (okButton.contains(x, y)) {
            dialogController.clickedOk(this);
        }
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isVisible() {
        return isVisible;
    }

}
