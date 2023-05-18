package basics;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;



public class GameScreen extends JPanel implements MouseListener, KeyListener { // JPanel ist eine Klasse, in der
																				// gezeichnet werden kann

	private final Dimension prefSize = new Dimension(1920, 1080); // Die Dimension des Spielfeldes k�nnte auch anders
																// gew�hlt werden...
	private GameWindow gW;
	private Render render;
	
	private Timer t;
	private Cursor c;
	
	private Map map;
	
	private BufferedImage mapImage;
	
	private boolean isStoped;

	//Player- und Shot-Variablen
	
	public GameScreen(GameWindow gW) {
		this.gW=gW;
		initMap(1);
		render = new Render(this,map);
		setLayout(null);
		setFocusable(true);
		setPreferredSize(prefSize);
		gW.add(this);
		setVisible(true);

		initGame(); // zum Erstellen der Oberfl�che (Ausgangszustand)
		startGame(); // Starten des Timers. Dieser ruft die Methode doOnTick() auf, in der die
	
	}

	//Erstellen des Spielers und festlegen der Spielervariablen	
	
	private void initMap(int mapNum) {
		map = new Map(mapNum, this);
	}			
	
	private void initGame() {

		
		
		addMouseListener(this);
		
		addKeyListener(this);

		c = new Cursor(Cursor.CROSSHAIR_CURSOR);
		this.setCursor(c);

		t = new Timer(8, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doOnTick();
			}
		});

	}
	private void startGame() {
		t.start();
	}

	public void pauseGame() {
		t.stop();
	}

	public void continueGame() {
		if (!isStoped) {
			t.start();
		}
	}

	private void doOnTick() {
		repaint();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawString("Score: ", 5, 10);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (t.isRunning()) {
		//map.paintComponent(g);
			render.render(g);
		} else {
			
		
		}
			
		}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
		} else if (e.getKeyCode() == 32) { //Spsce
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {			

		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	/*private class MyButtonListener implements ActionListener {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            System.out.println("Button clicked");
	        }
}*/
}
