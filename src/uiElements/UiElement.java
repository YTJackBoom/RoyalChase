package uiElements;

import helpers.variables;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class UiElement {
    protected Rectangle bounds;
    protected boolean isVisible = true;
    protected UIObjectType uiObjectType;
    protected int type;
    protected BufferedImage primaryImage;
    protected String posUpdateCalculationX, posUpdateCalculationY;

    public UiElement(Rectangle bounds, UIObjectType uiObjectType, int type, boolean visibility, String posUpdateCalculationX, String posUpdateCalculationY) {
        isVisible = visibility;
        this.uiObjectType = uiObjectType;
        this.type = type;
        this.bounds = bounds;
        this.posUpdateCalculationX = posUpdateCalculationX;
        this.posUpdateCalculationY = posUpdateCalculationY;
        initImage();

    }

    public void initImage() {
        try {
            switch (uiObjectType) {
                case BUTTON -> primaryImage = ImageIO.read(variables.Buttons.getButtonImageFile(type));
                case ICON -> primaryImage = ImageIO.read(variables.Icons.getIconImageFile(type));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ;

    public void render(Graphics g) {
        if (!isVisible) return;
        if (primaryImage == null) return;
        g.drawImage(primaryImage, bounds.x, bounds.y, null);
    }

    public void updatePosOnResize() {

    }


    public void setPos(int x, int y) {
        bounds.x = x;
        bounds.y = y;
    }


    public Rectangle getBounds() {
        return bounds;
    }

    public int getHeight() {
        return bounds.height;
    }

    public int getWidth() {
        return bounds.width;
    }

    public int getX() {
        return bounds.x;
    }

    public int getY() {
        return bounds.y;
    }

}
