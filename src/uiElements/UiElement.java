package uiElements;

import collectors.UiElementCollector;
import helpers.UiCoordinate;
import helpers.variables;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class UiElement {
    protected boolean isVisible = true;
    protected UIObjectType uiObjectType;
    protected int type;
    protected BufferedImage primaryImage;
    protected UiCoordinate uiCoordinate;
    protected int height, width;

    public UiElement(UiCoordinate uiCoordinate, int width, int height, UIObjectType uiObjectType, int type, boolean visibility) {
        isVisible = visibility;
        this.uiObjectType = uiObjectType;
        this.type = type;
        this.uiCoordinate = uiCoordinate;
        this.height = height;
        this.width = width;
        initImage();
        UiElementCollector.getInstance().addUiElement(this);

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

    public void render(Graphics g) {
        if (!isVisible) return;
        if (primaryImage == null) return;
        g.drawImage(primaryImage, uiCoordinate.getX(), uiCoordinate.getY(), null);
    }

    public void updatePosOnResize() {

    }

    public UiCoordinate getUiCoordinate() {
        return uiCoordinate;
    }

    public void setUiCoordinate(UiCoordinate uiCoordinate) {
        this.uiCoordinate = uiCoordinate;
    }

    public boolean contains(int x, int y) {
        return x >= uiCoordinate.getX() && x <= uiCoordinate.getX() + width && y >= uiCoordinate.getY() && y <= uiCoordinate.getY() + height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
