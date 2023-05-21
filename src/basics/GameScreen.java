package basics;


import java.awt.Dimension;
import java.awt.Graphics;
import playerinputs.KListener;
import playerinputs.MListener;
import javax.swing.JPanel;




public class GameScreen extends JPanel {

	private Game game;

	private Map map;
	



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

