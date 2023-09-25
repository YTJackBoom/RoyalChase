package basics;


import playerinputs.KListener;
import playerinputs.MListener;

import javax.swing.*;
import java.awt.*;


public class GameScreen extends JPanel {

	private Game game;

	public static Rectangle fBOUNDS = new Rectangle(0, 0, Game.initGameWidth, Game.initGameHeight);



	public GameScreen(Game game) {
		this.game = game;
		setDoubleBuffered(true);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.RED, 2));

//		initMap(1);
		setPanelSize();
	}
	public void setPanelSize() {
		Dimension prefSize = new Dimension(Game.initGameWidth, Game.initGameHeight);
//		setSize(prefSize);
//		setBounds(fBOUNDS);
		//setVisible(true);
	}

	public void initInputs() { //Initialiesieren der Spieler Inputs auf den Gamescreen
		MListener mListener = new MListener(game);
		KListener kListener = new KListener(game);

		addMouseListener(mListener);
		addMouseMotionListener(mListener);
		requestFocus(); //focus auf den gamescreen setzten
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setClip(0, 0, getWidth(), getHeight());
		game.getRenderUpdater().render(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Game.fWIDTH, Game.fHEIGHT);
	}


}

