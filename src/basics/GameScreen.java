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
import playerinputs.KListener;
import playerinputs.MListener;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;



public class GameScreen extends JPanel {



	private GameWindow gW;
	private Render render;
	private Game game;
	private Timer t;
	private Cursor c;
	
	private Map map;
	
	private BufferedImage mapImage;
	
	private boolean isStoped;


	public GameScreen(Game game) {
		this.game=game;
		initMap(1);
		setPanelSize();
	}
	public void setPanelSize() {
		Dimension prefSize = new Dimension(1920, 1080);
		setPreferredSize(prefSize);
		setMinimumSize(prefSize);
		setMaximumSize(prefSize);
		//setVisible(true);
	}


	private void initMap(int mapNum) {
		map = new Map(mapNum, this);
	}			
	public void initInputs() {
		MListener mListener = new MListener(game);
		KListener kListener = new KListener(game);

		addMouseListener(mListener);
		addMouseMotionListener(mListener);
		addKeyListener(kListener);

		requestFocus();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.getRender().render(g);
	}

	public Map getMap() {
		return map;
	}
}

