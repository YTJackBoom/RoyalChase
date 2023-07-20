package scenes;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public interface SceneMethods {

    public void render(Graphics g);
    public void update();

    public void mouseClicked(MouseEvent e);

    public void mouseMoved(MouseEvent e);

    public void mousePressed(MouseEvent e);

    public void mouseReleased(MouseEvent e);

    public void mouseDragged(MouseEvent e);

    public void reset();
    // public void keyPressed(int keyCode);

}