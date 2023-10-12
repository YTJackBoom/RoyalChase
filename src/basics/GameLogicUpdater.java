package basics;

import static basics.Game.ups;

/**
 * Die Klasse des threats zum updaten der spiel logic
 */
public class GameLogicUpdater implements Runnable {
	private Game game;

	public GameLogicUpdater(Game game) {
		this.game = game;
	}

	@Override
	public void run() {
		while (true) {
			game.updateGame();
			game.incrementUPS(); //zum anzeigen der ups
			try {
				Thread.sleep(1000 / ups);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}