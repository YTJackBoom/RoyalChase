package helpers;

import basics.Direction;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static basics.Direction.NORMAL;

public class Animator implements Cloneable{
    private int currentImageIndex;
    private BufferedImage[] imageArray,imageArrayUp, imageArrayDown, imageArrayLeft, imageArrayRight;
    private File gifFile, gifFileUp, gifFileDown, gifFileLeft, gifFileRight;
    private Direction direction;
    public Animator(String gifFilesPath) {
        direction = NORMAL;
        currentImageIndex = 0;
        if (gifFilesPath != null) {
            initGifs(gifFilesPath);
            try {
                imageArray = splitGifIntoFrames(gifFile);
//                imageArrayUp = splitGifIntoFrames(gifFileUp);
                imageArrayDown = splitGifIntoFrames(gifFileDown);
                imageArrayLeft = splitGifIntoFrames(gifFileLeft);
                imageArrayRight = splitGifIntoFrames(gifFileRight);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // System.out.println(imageArray.length);
        }
    }
    public Animator (File gifFile) {
        direction = NORMAL;
        currentImageIndex = 0;
        if(gifFile != null) {
            this.gifFile = gifFile;
            try {
                imageArray = splitGifIntoFrames(gifFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void initGifs(String gifFilesPath) {
        gifFile = new File(gifFilesPath + "normal.gif");
//        gifFileUp = new File(gifFilesPath + "up.gif");
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

    public BufferedImage getCurrentImage() {
        if (!(currentImageIndex ==imageArray.length-1)) {
            currentImageIndex++;
        } else {
            currentImageIndex = 0;
        }
       // System.out.println(currentImageIndex);
         switch (direction) {
            case NORMAL -> {
                if (!(currentImageIndex >=imageArray.length-1)) {
                    currentImageIndex++;
                } else {
                    currentImageIndex = 0;
                }
                return imageArray[currentImageIndex];

            }
            case DOWN -> {
                if (!(currentImageIndex >=imageArrayDown.length-1)) {
                    currentImageIndex++;
                } else {
                    currentImageIndex = 0;
                }
                return imageArrayDown[currentImageIndex];
            }
            case LEFT -> {
                if (!(currentImageIndex >=imageArrayLeft.length-1)) {
                    currentImageIndex++;
                } else {
                    currentImageIndex = 0;
                }
                return imageArrayLeft[currentImageIndex];
            }
            case RIGHT -> {
                if (!(currentImageIndex >=imageArrayRight.length-1)) {
                    currentImageIndex++;
                } else {
                    currentImageIndex = 0;
                }
                return imageArrayRight[currentImageIndex];
            }
        };
        return null;
    }

    public int getWidth(){
        return imageArray[0].getWidth();
    }

    public int getHeight() {
        return imageArray[0].getHeight();
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
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