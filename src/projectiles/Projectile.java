package projectiles;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import basics.GameScreen;
import gameObjects.Coordinate;
import gameObjects.Enemy;
import gameObjects.GameObject;

public abstract class Projectile extends JPanel{
	protected JLabel pLabel;
	private Coordinate pos;
	private BufferedImage pImage;
	private JPanel gS;
	
	public Projectile(JPanel gS, Coordinate start, Enemy target, BufferedImage pImage) {
		this.pos = start;
		this.gS = gS;
		setBounds(pos.getX(),pos.getY(),60,60);

		gS.add(this);
		gS.setComponentZOrder(this,0);
		setVisible(true);
		
	}
	public Coordinate getPos() {
		return pos;
	}
	public void vanish() {
		setVisible(false);
		gS.remove(this);
	}
	public abstract void refresh();
	
	 @Override
	protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(pImage, pos.getX(), pos.getY(), null);
	    }
}
