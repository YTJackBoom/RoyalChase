package uiElements;

import helpers.UiCoordinate;

import java.awt.*;

public class Slider extends UiElement {
    private int minValue;
    private int maxValue;
    private int currentValue;
    private Rectangle knob;
    private int knobWidth = 20;
    private boolean isDragging;
    private int snapSize;

    public Slider(UiCoordinate uiCoordinate, int width, int height, int minValue, int maxValue, int startValue) {
        super(uiCoordinate, width, height, UIObjectType.DIALOG, 0, true);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = startValue;

        // Calculate the number of snap points
        this.snapSize = width / (maxValue - minValue);

        int knobX = uiCoordinate.getX() + (int) (((float) (currentValue - minValue) / (maxValue - minValue)) * (width - knobWidth));
        this.knob = new Rectangle(knobX, uiCoordinate.getY(), knobWidth, height);
    }

    @Override
    public void render(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        // Draw the slider bar
        g.drawRect(uiCoordinate.getX(), uiCoordinate.getY(), width, height);

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
            newKnobX = Math.max(uiCoordinate.getX(), Math.min(newKnobX, uiCoordinate.getX() + width - knobWidth));
            knob.setLocation(newKnobX, knob.y);

            updateCurrentValue();
        }
    }

    private void snapToClosestValue() {
        // Calculate the closest snap point
        int relativeX = knob.x - uiCoordinate.getX();
        int snapPosition = Math.round((float) relativeX / snapSize);

        // Calculate the new knob position
        int newKnobX = uiCoordinate.getX() + snapPosition * snapSize;

        // Ensure that the knob does not extend beyond the slider's bounds
        newKnobX = Math.min(newKnobX, uiCoordinate.getX() + width - knobWidth);

        knob.setLocation(newKnobX, knob.y);
        updateCurrentValue();
    }


    private void updateCurrentValue() {
        float positionInSlider = (float) (knob.x - uiCoordinate.getX()) / (width - knobWidth);
        currentValue = minValue + (int) (positionInSlider * (maxValue - minValue));
    }

    public int getValue() {
        return currentValue;
    }

}
