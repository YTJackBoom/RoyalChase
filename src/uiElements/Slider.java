package uiElements;

import java.awt.*;

public class Slider {
    private Rectangle bounds;
    private int minValue;
    private int maxValue;
    private int currentValue;
    private Rectangle knob; // The draggable part of the slider
    private int knobWidth = 20;
    private boolean isDragging;

    public Slider(Rectangle bounds, int minValue, int maxValue, int startValue) {
        this.bounds = bounds;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = startValue;

        int knobX = bounds.x + (int) (((float) (currentValue - minValue) / (maxValue - minValue)) * (bounds.width - knobWidth));
        this.knob = new Rectangle(knobX, bounds.y, knobWidth, bounds.height);
    }

    public void render(Graphics g) {
        // Draw the slider bar
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // Draw the knob
        g.fillRect(knob.x, knob.y, knob.width, knob.height);
    }

    public void mousePressed(int x, int y) {
        if (knob.contains(x, y)) {
            isDragging = true;
        }
    }

    public void mouseReleased(int x, int y) {
        isDragging = false;
    }

    public void mouseDragged(int x, int y) {
        if (isDragging) {
            int newKnobX = x - knobWidth / 2;
            newKnobX = Math.max(bounds.x, Math.min(newKnobX, bounds.x + bounds.width - knobWidth));

            knob.setLocation(newKnobX, knob.y);
            updateCurrentValue();
        }
    }

    private void updateCurrentValue() {
        float positionInSlider = (float) (knob.x - bounds.x) / (bounds.width - knobWidth);
        currentValue = minValue + (int) (positionInSlider * (maxValue - minValue));
    }

    public int getValue() {
        return currentValue;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
