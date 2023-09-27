package uiElements;

import collectors.UiElementCollector;
import controllers.AssetController;
import helpers.UiCoordinate;
import helpers.AssetLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public abstract class UiElement {
    protected boolean isVisible = true;
    protected UIObjectType uiObjectType;


    protected int type;
    protected UiElement parent;
    private ArrayList<UiElement> children;

    protected BufferedImage primaryImage;
    protected UiCoordinate uiCoordinate;
    protected int height, width;

    public UiElement(UiCoordinate uiCoordinate, int width, int height, UIObjectType uiObjectType, int type, boolean visibility) {
        this(uiCoordinate, width, height, uiObjectType, type, visibility, null);
    }

    public UiElement(UiCoordinate uiCoordinate, int width, int height, UIObjectType uiObjectType, int type, boolean visibility, UiElement parent) {
        isVisible = visibility;
        this.uiObjectType = uiObjectType;
        this.uiCoordinate = uiCoordinate;
        this.height = height;
        this.width = width;
        this.type = type;
        this.parent = parent;  // Set the parent
        children = new ArrayList<UiElement>();
        initImage();
        UiElementCollector.getInstance().addUiElement(this);
    }


    public void initImage() {
        switch (uiObjectType) {
            case BUTTON -> {
                primaryImage = AssetController.getInstance().getIcon( "button_"+type);
            }
            case ICON -> {
                primaryImage = AssetController.getInstance().getIcon( "icon_"+type);
            }
        }
    }

    public void updateOnFrame() {

    }

    public void render(Graphics g) {
        if (!isVisible) return;
        updateOnFrame();
        if (primaryImage != null) {
            g.drawImage(primaryImage, uiCoordinate.getX(), uiCoordinate.getY(), null);
        }
        for (UiElement uiElement : children) {
            uiElement.render(g);
        }
    }

    public void addChild(UiElement uiElement) {
        children.add(uiElement);
        uiElement.setParent(this);
        UiElementCollector.getInstance().removeUiElement(uiElement);
    }

    public void removeChild(UiElement uiElement) {
        children.remove(uiElement);
    }


    public void updatePosOnResize() {
        uiCoordinate.updateBasedOnFrame();
        for (UiElement uiElement : children) {
            uiElement.updatePosOnResize();
        }
    }

    public UiCoordinate getUiCoordinate() {
        return uiCoordinate;
    }

    public UiElement getParent() {
        return parent;
    }

    public void setParent(UiElement parent) {
        this.parent = parent;
    }

    public void setUiCoordinate(UiCoordinate uiCoordinate) {
        this.uiCoordinate = uiCoordinate;
    }

    public boolean contains(int x, int y) {
        return x >= uiCoordinate.getX() && x <= uiCoordinate.getX() + width && y >= uiCoordinate.getY() && y <= uiCoordinate.getY() + height && isVisible;
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
