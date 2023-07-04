package basics;

import java.awt.Graphics;

import scenes.GameStates;

public class Render {
	private Map currentMap;
	private Game game;
	public Render (Game game) {
		this.game = game;
	}
	
	public void render (Graphics g) {
		switch (GameStates.gameState) {
			case MENU -> game.getMenu().render(g);
			case PLAYING -> {
				game.getPlaying().render(g);
				game.getInfoOverlay().render(g);
			}
			case SETTINGS -> game.getSettings().render(g);
			case GAMEOVER -> {
				game.getGameOver().render(g);
				game.getInfoOverlay().render(g);
			}
			case LEVELCLEARED -> game.getLevelCleared().render(g);
			case LEVELSELECT -> game.getLevelSelect().render(g);
			case TUTORIAL -> {
				game.getTutorial().render(g);
				game.getInfoOverlay().render(g);
			}
			case TOWN -> {
				game.getTown().render(g);
				game.getInfoOverlay().render(g);
			}
		}


	}

}
