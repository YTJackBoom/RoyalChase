package scenes;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public interface SceneMethods {

    public void render(Graphics g);
    public void update();

    public void mouseClicked(MouseEvent e);

    public void mouseMoved(int x, int y);

    public void mousePressed(MouseEvent e);

    public void mouseReleased(MouseEvent e);

    public void mouseDragged(int x, int y);

    public void reset();
    // public void keyPressed(int keyCode);

}