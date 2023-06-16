package basics;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import playerinputs.KListener;
import playerinputs.MListener;
import javax.swing.JPanel;




public class GameScreen extends JPanel {

	private Game game;

	private Map map;
	private final int width = 1920;
	private final int height = 1080;



	public GameScreen(Game game) {
		this.game=game;
//		initMap(1);
		setPanelSize();
	}
	public void setPanelSize() {
		Dimension prefSize = new Dimension(width, height);
		setPreferredSize(prefSize);
		setMinimumSize(prefSize);
		setMaximumSize(prefSize);
		//setVisible(true);
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
	@Override
	public int getWidth() {
		return width;
	}
	@Override
	public int getHeight() {
		return height;
	}
}

