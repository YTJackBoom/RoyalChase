package helpers;

import basics.Game;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static basics.Game.*;
import static helpers.Direction.UP;
import static helpers.math.ImageMath.resizeImage;

/**
 * Klasse zum Animieren von Gifs
 */
public class Animator implements Cloneable{
    private int currentImageIndex;
    private BufferedImage[] currentImageArray, imageArrayUp, imageArrayDown, imageArrayLeft, imageArrayRight;
    private InputStream gifFileUp, gifFileDown, gifFileLeft, gifFileRight;
    private Direction direction;
    private int updateCounter = 0; //um die bilder/sekunde auf 4 zu begrenzen

    private String path; //um den pfad des gifs zu speichern, bei fehlermeldung

    /**
     * Konstruktor für einen Animator mit 4 Gifs (für Gegner, Türme)
     * @param gifFilesPath Pfad zu den Gifs
     */
    public Animator(String gifFilesPath) {
        path = gifFilesPath;
        direction = UP;
        currentImageIndex = 0;
        if (gifFilesPath != null) {
            initGifs(gifFilesPath);
            try {
                imageArrayUp = splitGifIntoFrames(gifFileUp);
                imageArrayDown = splitGifIntoFrames(gifFileDown);
                imageArrayLeft = splitGifIntoFrames(gifFileLeft);
                imageArrayRight = splitGifIntoFrames(gifFileRight);

                currentImageArray = imageArrayUp;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Konstruktor für einen Animator mit nur einem Gif (z.B. für Gebäude)
     * @param gifFile Gif-Datei
     */
    public Animator (InputStream gifFile) {
        path = "nopath";
        direction = UP;
        currentImageIndex = 0;
        if(gifFile != null) {
            this.gifFileUp = gifFile;
            try {
                imageArrayUp = splitGifIntoFrames(gifFile);

                currentImageArray = imageArrayUp;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Methode zum initialisieren der Gifs
     * @param gifFilesPath Pfad zu den Gifs
     */
    public void initGifs(String gifFilesPath) {
        gifFileUp = AssetLocation.class.getResourceAsStream(gifFilesPath + "up.gif");
        gifFileDown = AssetLocation.class.getResourceAsStream(gifFilesPath + "down.gif");
        gifFileLeft = AssetLocation.class.getResourceAsStream(gifFilesPath + "left.gif");
        gifFileRight = AssetLocation.class.getResourceAsStream(gifFilesPath + "right.gif");
    }

    /**
     * Methode zum splitten der Gifs in einzelne Bilder
     * @param gifInputStream der Inputstream des Gifs
     * @return Array mit den einzelnen Bildern
     * @throws IOException Fehler beim Lesen der Datei
     */
    public BufferedImage[] splitGifIntoFrames(InputStream gifInputStream) throws IOException {
        BufferedImage[] tempArray;
        ImageReader reader = null;

        try {
            ImageInputStream input = ImageIO.createImageInputStream(gifInputStream);
            reader = ImageIO.getImageReadersByFormatName("gif").next();
            reader.setInput(input);

            int frameCount = reader.getNumImages(true);
            tempArray = new BufferedImage[frameCount];

            for (int i = 0; i < frameCount; i++) {
                tempArray[i] = reader.read(i);
            }
        } catch (IOException | IllegalStateException e) {
            throw new IOException("Error processing GIF stream", e);
        } finally {
            if (reader != null) {
                reader.dispose();
            }
        }

        return tempArray;
    }
    /**
     * Methode um den derzeitigen Frame in abhängigkeit der Richtung zu bekommen
     * @return derzeitiger Frame
     */
    public BufferedImage getCurrentFrame() {
        switch (direction) {
            case UP -> currentImageArray = imageArrayUp;
            case DOWN -> currentImageArray = imageArrayDown;
            case LEFT -> currentImageArray = imageArrayLeft;
            case RIGHT -> currentImageArray = imageArrayRight;
            default -> throw new IllegalStateException("Unexpected direction value: " + direction);
        }
        return currentImageArray[currentImageIndex];
    }

    /**
     * Methode zum erhöhen des Frames
     */
    public void incrementFrame() {
        updateCounter++;
        if (updateCounter >= Game.fps/currentImageArray.length) {
            if (currentImageIndex < currentImageArray.length - 1) {
                currentImageIndex++;
            } else {
                currentImageIndex = 0;
            }
            updateCounter = 0;
        }
    }

    /**
     * Methode um die Bilder um einen Faktor zu skalieren
     * @param widthScale Sklaierungsfaktor in x-Richtung
     * @param heightScale Sklaierungsfaktor in y-Richtung
     */
    public void scaleImages(double widthScale, double heightScale) {
        imageArrayUp = scaleImageArray(imageArrayUp, widthScale, heightScale);
        imageArrayDown = scaleImageArray(imageArrayDown, widthScale, heightScale);
        imageArrayLeft = scaleImageArray(imageArrayLeft, widthScale, heightScale);
        imageArrayRight = scaleImageArray(imageArrayRight, widthScale, heightScale);
//        switch (direction) {
//            case UP -> currentImageArray = imageArrayUp;
//            case DOWN -> currentImageArray = imageArrayDown;
//            case LEFT -> currentImageArray = imageArrayLeft;
//            case RIGHT -> currentImageArray = imageArrayRight;
//            default -> throw new IllegalStateException("Unexpected direction value: " + direction);
//        }
    }

    /**
     * Innere Methode zum skalieren der einzelnen Bilder
     * @param originalArray Array mit den Original-Bildern
     * @param widthScale Skalierungsfaktor in x-Richtung
     * @param heightScale Skalierungsfaktor in y-Richtung
     * @return Array mit den skalierten Bildern
     */
    private BufferedImage[] scaleImageArray(BufferedImage[] originalArray, double widthScale, double heightScale) {
        if (originalArray == null) return null;

        BufferedImage[] scaledArray = new BufferedImage[originalArray.length];
        for (int i = 0; i < originalArray.length; i++) {
            BufferedImage originalImage = originalArray[i];
            int newWidth = (int) (originalImage.getWidth() * widthScale);
            int newHeight = (int) (originalImage.getHeight() * heightScale);
            scaledArray[i] = resizeImage(originalImage, newWidth, newHeight);
        }
        return scaledArray;
    }

    /**
     * Methode zum skalieren bei Änderung der Fenstergröße
     * @param newWidth Bildschirmbreite
     * @param newHeight Bildschirmhöhe
     */
    public void notifyScreenResize(int newWidth, int newHeight) {
        double widthScale = (double) newWidth / prevfWIDTH;
        double heightScale = (double) newHeight / prevfHEIGHT;
        scaleImages(widthScale, heightScale);
    }

    public int getWidth(){
        if (currentImageIndex < currentImageArray.length && currentImageIndex >= 0) {
            return currentImageArray[currentImageIndex].getWidth();
        }
        System.out.println("Animators Width check failed");
        return imageArrayUp[0].getWidth();
    }

    public int getHeight() {
        if (currentImageIndex < currentImageArray.length && currentImageIndex >= 0) {
            return currentImageArray[currentImageIndex].getHeight();
        }
        System.out.println("Animators Height check failed");
        return imageArrayUp[0].getHeight();
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public Direction getDirection() {
        return direction;
    }

    @Override
    public Animator clone() {
        try {
            Animator clone = (Animator) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


}