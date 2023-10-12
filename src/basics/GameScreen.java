package basics;


import playerinputs.KListener;
import playerinputs.MListener;

import javax.swing.*;
import java.awt.*;

/**
 * Die Klasse des JPanels auf dem das Spiel gerendert wird
 */
public class GameScreen extends JPanel {

	private Game game;
	public GameScreen(Game game) {
		this.game = game;
		setDoubleBuffered(true);
		setOpaque(true);
//		setBorder(BorderFactory.createLineBorder(Color.RED, 2));
	}

	/**
	 * Die Methode zum Initialisieren der Spieler-inputs
	 */
	public void initInputs() {
		MListener mListener = new MListener(game);
		KListener kListener = new KListener(game); //keyListener muss nicht auf den gamescreen geadded Werden, da InputMaps genutzt werden, welche direkt auf die ActionMap des RootPanes des JFrames geadded werden

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

