package basics;


import java.awt.*;

import playerinputs.KListener;
import playerinputs.MListener;
import javax.swing.JPanel;




public class GameScreen extends JPanel {

	private Game game;

	public static final int fWIDTH = 1920;
	public static final int fHEIGHT = 1080;
	public static final Rectangle fBOUNDS = new Rectangle(fWIDTH,fHEIGHT);



	public GameScreen(Game game) {
		this.game=game;
//		initMap(1);
		setPanelSize();
	}
	public void setPanelSize() {
		Dimension prefSize = new Dimension(fWIDTH, fHEIGHT);
		setPreferredSize(prefSize);
		setMinimumSize(prefSize);
		setMaximumSize(prefSize);
		//setVisible(true);
	}

	public void initInputs() { //Initialiesieren der Spieler Inputs auf den Gamescreen
		MListener mListener = new MListener(game);
		KListener kListener = new KListener(game);

		addMouseListener(mListener);
		addMouseMotionListener(mListener);
		addKeyListener(kListener);

		requestFocus(); //focus auf den gamescreen setzten
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.getRenderUpdater().render(g);
	}
	@Override
	public int getWidth() {
		return fWIDTH;
	}
	@Override
	public int getHeight() {
		return fHEIGHT;
	}
}

