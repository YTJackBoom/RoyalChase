package uiElements;

import helpers.UiCoordinate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Tooltip extends UiElement {
    private String[] texts;
    private boolean isVisible = false;
    private Font biggerFont;  // New font for the first string

    public Tooltip(String[] texts, UiCoordinate uiCoordinate) {
        super(uiCoordinate, computeWidth(texts), computeHeight(texts), UIObjectType.DIALOG, 0, true);
        this.texts = texts;

        Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
        biggerFont = g.getFont().deriveFont(g.getFont().getSize2D() * 1.5f);
        g.dispose();
    }

    static int computeWidth(String[] texts) {
        Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
        Font biggerFont = g.getFont().deriveFont(g.getFont().getSize2D() * 1.5f);
        int width = Math.max(g.getFontMetrics(biggerFont).stringWidth(texts[0]),
                Arrays.stream(texts).skip(1).mapToInt(g.getFontMetrics()::stringWidth).max().orElse(0)) + 20;
        g.dispose();
        return width;
    }

    static int computeHeight(String[] texts) {
        Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
        Font biggerFont = g.getFont().deriveFont(g.getFont().getSize2D() * 1.5f);
        int height = g.getFontMetrics(biggerFont).getHeight() + (texts.length - 1) * g.getFontMetrics().getHeight() + 10;
        g.dispose();
        return height;
    }

    public void render(Graphics g) {
        if (isVisible) {
            int x = uiCoordinate.getX();
            int y = uiCoordinate.getY();

            g.setColor(Color.BLACK);
            g.fillRect(x, y, width, height); // Adjusted the y-coordinate here for the fillRect

            g.setColor(Color.WHITE);

            // Draw the first string with a bigger font
            g.setFont(biggerFont);
            g.drawString(texts[0], x + 10, y + g.getFontMetrics().getHeight());

            // Reset font back to default for other strings
            Font defaultFont = biggerFont.deriveFont(biggerFont.getSize2D() / 1.5f);
            g.setFont(defaultFont);

            // Adding an extra half-line's height as the distance between the first and second strings
            int yOffset = (int)(1.5 * g.getFontMetrics().getHeight());

            for (int i = 1; i < texts.length; i++) {
                g.drawString(texts[i], x + 10, y + yOffset + (i * g.getFontMetrics().getHeight()));
            }
        }
    }



    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
