package basics;

import java.awt.Graphics;
import java.util.ArrayList;

import towers.Tower;

public class Render {
	private Map currentMap;
	private Game game;
	public Render (Game game) {
		this.game = game;
	}
	
	public void render (Graphics g) {
		switch(GameStates.gameState) {
		case MENU:
			game.getMenu().render(g);
			break;
		case PLAYING:
			game.getPlaying().render(g);
			break;
		case SETTINGS:
			
			break;
		
		}
	}

}
