package gameObjects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import  javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import basics.GameScreen;
import basics.GameWindow;
import basics.Status;

public class Enemy extends GameObject {
	private File enemyGifFile;
	private ImageIcon enemyGif;
	private JLabel enemyLabel;
	private Status status;
	private Coordinate pos;
	private JPanel gS;
	private String imgP;
	private int pathIndex;
	private int speed;
	private int width;
	private int height;
	
	
	public Enemy(GameScreen gS, Coordinate pos, int width, int height) {
		super(gS, pos, width, height,0,0);
		status = Status.OK;
		this.pos = pos;
		this.gS = gS;
		this.width = width;
		this.height = height;
		pathIndex = 1;
		speed=3;
		
			imgP = ("res/images/giphy.gif");
		
			enemyGif = new ImageIcon(imgP);
			enemyLabel = new JLabel(enemyGif);
			enemyLabel.setBounds(this.pos.getX(),this.pos.getY(),width,height);
			enemyLabel.setVisible(true);
	        
		    this.gS.add(enemyLabel);

		enemyLabel.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	            System.out.println("Image clicked!");
	        	}
	    });
		}


	@Override
	public void vanish() {
		// TODO Auto-generated method stub
		
	}
	public void move() {
		enemyLabel.setBounds(pos.getX(),pos.getY(),width,height);
	}
	public int getPathIndex() {
		return pathIndex;
	}
	public int getSpeed() {
		return speed;
	}


	public void setPathIndex(int i) {
		pathIndex =i;
	}
	@Override
	public void setPos(Coordinate pos) {
		this.pos = pos;
	}
	@Override 
	public Coordinate getPos() {
		return pos;
	}
	
}