package basics;

import java.awt.Graphics;
import java.util.ArrayList;

import towers.Tower;

public class Render {
	private GameScreen gameScreen;
	private Map currentMap;
	
	public Render (GameScreen gameScreen, Map currentMap) {
		this.gameScreen = gameScreen;
		this.currentMap = currentMap;
	}
	
	public void render (Graphics g) {
		
		switch(GameStates.gameState) {
		case MENU:
			renderMenu(g);
			break;
		case PLAYING:
			
			break;
		case SETTINGS:
			
			break;
		
		}
	}
	
	public void renderMenu(Graphics g) {
		ArrayList<Tower> tEL = currentMap.getTowerEntityList();  //weiter unten wird als letztes gepaintet
		currentMap.paintComponent(g);
		for(Tower Tower : tEL) {
			Tower.paintComponent(g);
		}
		
	}
	
}
