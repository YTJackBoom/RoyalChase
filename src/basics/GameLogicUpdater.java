package basics;

import static basics.Game.ups;

public class GameLogicUpdater implements Runnable {
	private Game game;

	public GameLogicUpdater(Game game) {
		this.game = game;
	}

	@Override
	public void run() {
		while (true) {
			game.updateGame();
			game.incrementUPS();
			// Adjust the delay to control the update rate
			try {
				Thread.sleep(1000 / ups);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}