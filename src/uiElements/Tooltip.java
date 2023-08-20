package uiElements;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Tooltip {
    private String[] texts;
    private int x, y;
    private int width, height;
    private boolean isVisible = false;
    private Font biggerFont;  // New font for the first string

    public Tooltip(String[] texts, int x, int y) {
        this.texts = texts;
        this.x = x;
        this.y = y;

        Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
        this.biggerFont = g.getFont().deriveFont(g.getFont().getSize2D() * 1.5f);  // 50% bigger
        g.dispose();

        // Calculate width and height based on the texts
        computeDimensions();
    }

    private void computeDimensions() {
        Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();

        // Compute the width based on the longest string in the array, considering the bigger font
        width = Math.max(g.getFontMetrics(biggerFont).stringWidth(texts[0]),
                Arrays.stream(texts).skip(1).mapToInt(g.getFontMetrics()::stringWidth).max().orElse(0)) + 20;

        // Compute the height considering the bigger font
        height = g.getFontMetrics(biggerFont).getHeight() + (texts.length - 1) * g.getFontMetrics().getHeight() + 10;

        g.dispose();
    }

    public void render(Graphics g) {
        if (isVisible) {
            g.setColor(Color.BLACK);
            g.fillRect(x, y - height, width, height);

            g.setColor(Color.WHITE);

            // Draw the first string with a bigger font
            g.setFont(biggerFont);
            g.drawString(texts[0], x + 10, y - height + g.getFontMetrics().getHeight());

            // Reset font back to default for other strings
            Font defaultFont = biggerFont.deriveFont(biggerFont.getSize2D() / 1.5f);
            g.setFont(defaultFont);

            // Adding an extra half-line's height as the distance between the first and second strings
            int yOffset = (int)(1.5 * g.getFontMetrics().getHeight());

            for (int i = 1; i < texts.length; i++) {
                g.drawString(texts[i], x + 10, y - height + yOffset + (i * g.getFontMetrics().getHeight()));
            }
        }
    }



    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
