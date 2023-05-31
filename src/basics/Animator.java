package basics;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Animator { //TODO: verschiedene animationen je nach bewegungsrichtung, zwichenspeichern des punktes in de ranimatiopn
    private int currentImageIndex;
    private BufferedImage[] imageArray;
    private File gifFile;
    public Animator(File gifFile) {
        currentImageIndex = 0;
        this.gifFile = gifFile;
        try {
            splitGifIntoFrames();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       // System.out.println(imageArray.length);
    }

    public void splitGifIntoFrames() throws IOException {
        ImageReader reader = null;
        ImageInputStream input = null;
        try {
            input = ImageIO.createImageInputStream(gifFile);
            reader = ImageIO.getImageReadersByFormatName("gif").next();
            reader.setInput(input);

            int frameCount = reader.getNumImages(true);
            imageArray = new BufferedImage[frameCount];

            for (int i = 0; i < frameCount; i++) {
                imageArray[i] = reader.read(i);
            }
        } finally {
            if (reader != null) {
                reader.dispose();
            }
            if (input != null) {
                input.close();
            }
        }
    }

    public BufferedImage getCurrentImage() {
        if (!(currentImageIndex ==imageArray.length-1)) {
            currentImageIndex++;
        } else {
            currentImageIndex = 0;
        }
       // System.out.println(currentImageIndex);
        return imageArray[currentImageIndex];
    }

    public int getWidth(){
        return imageArray[0].getWidth();
    }

    public int getHeight() {
        return imageArray[0].getHeight();
    }
}