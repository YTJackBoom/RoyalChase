package basics;

import static basics.Game.ups;

public class GameLogicUpdater implements Runnable { //der threat zum updaten der spiel logic
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