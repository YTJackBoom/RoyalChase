package uiElements;

import java.awt.*;

public class Slider extends UiElement {
    private int minValue;
    private int maxValue;
    private int currentValue;
    private Rectangle knob;
    private int knobWidth = 20;
    private boolean isDragging;
    private int snapSize;

    public Slider(Rectangle bounds, int minValue, int maxValue, int startValue) {
        super(bounds, UIObjectType.DIALOG, 0, true, "", "");
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = startValue;

        // Calculate the number of snap points
        this.snapSize = bounds.width / (maxValue - minValue);

        int knobX = bounds.x + (int) (((float) (currentValue - minValue) / (maxValue - minValue)) * (bounds.width - knobWidth));
        this.knob = new Rectangle(knobX, bounds.y, knobWidth, bounds.height);
    }

    @Override
    public void render(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        // Draw the slider bar
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // Draw the knob
        g.fillRect(knob.x, knob.y, knob.width, knob.height);

        // Draw the current value on the knob
        String valueStr = Integer.toString(currentValue);
        FontMetrics fm = g.getFontMetrics();
        int stringWidth = fm.stringWidth(valueStr);
        int stringHeight = fm.getAscent();

        int textX = knob.x + (knob.width - stringWidth) / 2;
        int textY = knob.y + (knob.height + stringHeight) / 2;

        g.setColor(Color.WHITE); // Choose a color that contrasts with the knob color
        g.drawString(valueStr, textX, textY);
    }


    public void mousePressed(int x, int y) {
        if (knob.contains(x, y)) {
            isDragging = true;
        }
    }

    public void mouseReleased(int x, int y) {
        isDragging = false;
        snapToClosestValue();
    }

    public void mouseDragged(int x, int y) {
        if (isDragging) {
            int newKnobX = x - knobWidth / 2;
            // Ensure the knob stays within the bounds of the slider
            newKnobX = Math.max(bounds.x, Math.min(newKnobX, bounds.x + bounds.width - knobWidth));
            knob.setLocation(newKnobX, knob.y);

            updateCurrentValue();
        }
    }

    private void snapToClosestValue() {
        // Calculate the closest snap point
        int relativeX = knob.x - bounds.x;
        int snapPosition = Math.round((float) relativeX / snapSize);

        // Calculate the new knob position
        int newKnobX = bounds.x + snapPosition * snapSize;

        // Ensure that the knob does not extend beyond the slider's bounds
        newKnobX = Math.min(newKnobX, bounds.x + bounds.width - knobWidth);

        knob.setLocation(newKnobX, knob.y);
        updateCurrentValue();
    }


    private void updateCurrentValue() {
        float positionInSlider = (float) (knob.x - bounds.x) / (bounds.width - knobWidth);
        currentValue = minValue + (int) (positionInSlider * (maxValue - minValue));
    }

    public int getValue() {
        return currentValue;
    }

}
