package gameObjects;

import helpers.Coordinate;
import helpers.variables;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tile {

    private BufferedImage tileImage;
    private int tileType;
    protected boolean isPath;
    protected boolean isBuildable;
    private Coordinate pos;
    private Rectangle bounds;

    public Tile(Coordinate pos,BufferedImage tileImage, int id, int tileType, boolean isPath, boolean isBuildable) {
        this.isPath = isPath;
        this.isBuildable = isBuildable;
        this.tileImage = tileImage;
        this.tileType = tileType;
        this.pos = pos;
    }
    public Tile(int tileType, boolean isPath, boolean isBuildable) {
        this.isPath = isPath;
        this.isBuildable = isBuildable;
        this.tileType = tileType;
        try {
            tileImage = ImageIO.read(variables.Maps.getTileFile(tileType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int getType() {
        return tileType;
    }
    public void render(Graphics g) {
        g.drawImage(tileImage, pos.getX()-tileImage.getWidth()/2,pos.getY()-tileImage.getHeight()/2,null);
    }

    public void setPos(Coordinate coordinate) {
        this.pos = coordinate;
        bounds = new Rectangle(pos.getX()-tileImage.getWidth()/2,pos.getY()-tileImage.getHeight()/2,tileImage.getWidth(),tileImage.getHeight());
    }

    public boolean isBuildable() {
        return isBuildable;
    }

    public boolean isPath() {
        return isPath;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}