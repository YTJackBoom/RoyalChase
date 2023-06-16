package uiElements;

import basics.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class InfoBar {
    private Game game;
    private BufferedImage pausedImage,resumeImage;
    public InfoBar(Game game) {
        this.game = game;
        initVariables();
    }
    private void initVariables(){
        File pausedFile = new File("res\\images\\infos\\paused.jpg");
        File resumeFile = new File("res\\images\\infos\\resume.jpg");
        try {
            pausedImage = ImageIO.read(pausedFile);
            resumeImage = ImageIO.read(resumeFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        if(game.isPaused()){
            g.drawImage(pausedImage, 10, 0, null);
        }else {
            g.drawImage(resumeImage, 10, 0, null);
        }
    }
}
