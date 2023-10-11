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

import static basics.Game.fHEIGHT;
import static basics.Game.fWIDTH;
import static helpers.Direction.UP;
import static helpers.math.ImageMath.resizeImage;

public class Animator implements Cloneable{
    private int currentImageIndex;
    private BufferedImage[] currentImageArray, imageArrayUp, imageArrayDown, imageArrayLeft, imageArrayRight;
    private File gifFileUp, gifFileDown, gifFileLeft, gifFileRight;
    private Direction direction;
    private int updateCounter = 0; //to scale down the fps to accomaedate lower frames(4) of the gifs

    private String path; //just for potential errors
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
            // System.out.println(imageArray.length);
        }
    }
    public Animator (File gifFile) {
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
    public void initGifs(String gifFilesPath) {
        gifFileUp = new File(gifFilesPath + "up.gif");
        gifFileDown = new File(gifFilesPath + "down.gif");
        gifFileLeft = new File(gifFilesPath + "left.gif");
        gifFileRight = new File(gifFilesPath + "right.gif");
    }

    public BufferedImage[] splitGifIntoFrames(File tgifFile) throws IOException {
        BufferedImage[] tempArray;
        ImageReader reader = null;
        ImageInputStream input = null;
        try {
            input = ImageIO.createImageInputStream(tgifFile);
            reader = ImageIO.getImageReadersByFormatName("gif").next();
            reader.setInput(input);

            int frameCount = reader.getNumImages(true);
            tempArray = new BufferedImage[frameCount];

            for (int i = 0; i < frameCount; i++) {
                tempArray[i] = reader.read(i);
            }
        } catch (IOException | IllegalStateException e) {
            // Append file name to the exception and re-throw
            throw new IOException("Error processing GIF file: " + tgifFile.getName()+ " in "+path, e);
        } finally {
            if (reader != null) {
                reader.dispose();
            }
            if (input != null) {
                input.close();
            }
        }
        return tempArray;
    }


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

    public void incrementFrame() {
        updateCounter++;

        if (updateCounter >= Game.fps/currentImageArray.length) {
            if (currentImageIndex < currentImageArray.length - 1) {
                currentImageIndex++;
            } else {
                currentImageIndex = 0;
            }
            updateCounter = 0; // reset the counter after switching to the next frame
        }
    }
    public void scaleImages(double widthScale, double heightScale) {
        imageArrayUp = scaleImageArray(imageArrayUp, widthScale, heightScale);
        imageArrayDown = scaleImageArray(imageArrayDown, widthScale, heightScale);
        imageArrayLeft = scaleImageArray(imageArrayLeft, widthScale, heightScale);
        imageArrayRight = scaleImageArray(imageArrayRight, widthScale, heightScale);

        // Update the currentImageArray to reflect the direction currently set
        switch (direction) {
            case UP -> currentImageArray = imageArrayUp;
            case DOWN -> currentImageArray = imageArrayDown;
            case LEFT -> currentImageArray = imageArrayLeft;
            case RIGHT -> currentImageArray = imageArrayRight;
            default -> throw new IllegalStateException("Unexpected direction value: " + direction);
        }
    }


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

    public void notifyScreenResize(int newWidth, int newHeight) {
        double widthScale = (double) newWidth / fWIDTH;
        double heightScale = (double) newHeight / fHEIGHT;
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