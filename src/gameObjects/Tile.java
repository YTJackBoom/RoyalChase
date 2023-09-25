package gameObjects;

import helpers.AbsoluteCoordinate;
import helpers.ImageAnalyser;
import helpers.variables;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Tile {

    private BufferedImage tileImage;
    private int tileType;
    protected boolean isPath,isBuildable,isSpawn, isGate;
    private AbsoluteCoordinate pos;
    private Rectangle bounds;
    private ImageAnalyser imageAnalyser;
    private boolean isHovered = false;

    public Tile(AbsoluteCoordinate pos, BufferedImage tileImage, int id, int tileType, boolean isPath, boolean isBuildable, boolean isSpawn, boolean isGate) {
        this.isSpawn = isSpawn;
        this.isGate = isGate;
        this.isPath = isPath;
        this.isBuildable = isBuildable;
        this.tileImage = tileImage;
        this.tileType = tileType;
        this.pos = pos;

        imageAnalyser = new ImageAnalyser(tileImage);
    }
    public Tile(int tileType, boolean isPath, boolean isBuildable,boolean isSpawn,boolean isGate) {
        this.isSpawn = isSpawn;
        this.isGate = isGate;
        this.isPath = isPath;
        this.isBuildable = isBuildable;
        this.tileType = tileType;
        try {
            tileImage = ImageIO.read(variables.Tiles.getTileFile(tileType));
        } catch (IOException e) {
            throw new RuntimeException(e + " Tile " + tileType + " not found");
        }
        imageAnalyser = new ImageAnalyser(tileImage);

    }


    public ArrayList<AbsoluteCoordinate> getGlobalPath(AbsoluteCoordinate globalCoord) {
        int halfWidth = tileImage.getWidth() / 2;  // assuming tileImage is accessible; adjust as needed
        int halfHeight = tileImage.getHeight() / 2;

        ArrayList<AbsoluteCoordinate> localPath = getLocalPath(globalCoord);

        // Translate local path back to global coordinates
        ArrayList<AbsoluteCoordinate> globalPath = new ArrayList<>();
        for (AbsoluteCoordinate coord : localPath) {
            int globalX = coord.getX() + getPos().getX() - halfWidth;
            int globalY = coord.getY() + getPos().getY() - halfHeight;
            globalPath.add(new AbsoluteCoordinate(globalX, globalY));
        }

        return globalPath;
    }

    private ArrayList<AbsoluteCoordinate> getLocalPath(AbsoluteCoordinate globalCoord) {
        int localX = globalCoord.getX() - getPos().getX() + tileImage.getWidth() / 2;
        int localY = globalCoord.getY() - getPos().getY() + tileImage.getHeight() / 2;

        return imageAnalyser.imgToPath(new AbsoluteCoordinate(localX, localY));
    }

    //getters and setters
    public int getType() {
        return tileType;
    }

    public AbsoluteCoordinate getPos() {
        return pos;
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

    public void setPos(AbsoluteCoordinate absoluteCoordinate) {
        this.pos = absoluteCoordinate;
        bounds = new Rectangle(pos.getX() - tileImage.getWidth() / 2, pos.getY() - tileImage.getHeight() / 2, tileImage.getWidth(), tileImage.getHeight());
    }

    public boolean isStart() {
        return isSpawn;
    }

    public boolean isGate() {
        return isGate;
    }

    public void setHovered(boolean b) {
        isHovered = b;
    }

    public BufferedImage getTileImage() {
        return tileImage;
    }

    public boolean isHovered() {
        return isHovered;
    }

    public int getWidth() {
        return tileImage.getWidth();
    }

    public int getHeight() {
        return tileImage.getHeight();
    }
}