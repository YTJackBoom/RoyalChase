package uiElements;

import java.awt.Graphics;

public class Dialog {
    private String message;
    private MyButton okButton;
    private MyButton nextButton;
    private boolean hasNext;
    private boolean isVisible;

    public Dialog(String message, boolean hasNext) {
        this.message = message;
        this.hasNext = hasNext;
        this.isVisible = true;

        // Adjust button positions as needed.
        if (hasNext) {
            this.nextButton = new MyButton("Next",  100, 100, 50, 30,true);
        }else {
            this.okButton = new MyButton("OK", 100, 100, 50, 30,true);
        }
    }

    public void render(Graphics g) {
        if (!isVisible) return;

        // Draw the message
        g.drawString(message, 50, 50);

        // Render the OK button

        if (hasNext) {
            nextButton.render(g);
        } else {
            okButton.render(g);
        }
    }

    public boolean mouseReleased(int x, int y) {
        if (okButton.getBounds().contains(x, y)) {
            isVisible = false;
            return false;
        } else if (hasNext && nextButton.getBounds().contains(x, y)) {
            isVisible = false;
            return true;
        }
        return false;
    }

    public boolean isVisible() {
        return isVisible;
    }
}