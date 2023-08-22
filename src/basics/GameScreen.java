package basics;


import java.awt.*;

import playerinputs.KListener;
import playerinputs.MListener;
import javax.swing.JPanel;




public class GameScreen extends JPanel {

	private Game game;

	public static final Rectangle fBOUNDS = new Rectangle(Game.initGameWidth, Game.initGameHeight);



	public GameScreen(Game game) {
		this.game=game;
//		initMap(1);
		setPanelSize();
		setLayout(null);
	}
	public void setPanelSize() {
		Dimension prefSize = new Dimension(Game.initGameWidth, Game.initGameHeight);
		setSize(prefSize);
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
		game.getRenderUpdater().render(g);
	}

}

