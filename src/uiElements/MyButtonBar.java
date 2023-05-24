package uiElements;

import basics.GameStates;
import helpers.Coordinate;
import scenes.Playing;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MyButtonBar {
	private Coordinate pos;
	private Playing playing;
	private ArrayList<MyButton> buttons;
	private int width;
	private int height;
	public MyButtonBar(Playing playing, Coordinate pos, int width, int height){
		buttons = new ArrayList<MyButton>();
		this.pos = pos;
		this.width = width;
		this.height = height;
		this.playing = playing;
		initButtons();
	}


	public void initButtons() {
		buttons.add(new MyButton("Menu",pos.getX() + 10, pos.getY() + 10, 100, 80));
		buttons.add(new MyButton(1,pos.getX() + 120, pos.getY() + 10, 100, 80));
	}
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(pos.getX(), pos.getY(), width, height);
		renderButtons(g);

	}
	public void renderButtons(Graphics g) {
		for (MyButton button : buttons) {
			button.render(g);
		}
	}



	public void resetButtons() {
		for (MyButton button : buttons) {
			button.resetBools();
		}
	}
	//Getters and Setters
	public Coordinate getPos() {
		return pos;
	}
	public int getHeight() {
		return height;
	}


	//Mouse Lsiteners
	public void mouseClicked(int x, int y) {
		for (MyButton button : buttons) {
			if (button.getBounds().contains(x, y)) {
				if (button.getText() != null) {
					if (button.getText().equals("Play")) {
						GameStates.gameState = GameStates.PLAYING;
					} else if (button.getText().equals("Settings")) {
						GameStates.gameState = GameStates.SETTINGS;
					} else if (button.getText().equals("Exit")) {
						System.exit(0);
					} else if (button.getText().equals("Menu")) {
						GameStates.gameState = GameStates.MENU;
					}
				}
			}
		}
	}



	public void mouseMoved(int x, int y) {
		for(MyButton button: buttons){
			if(button.getBounds().contains(x,y)){
				button.setHovered(true);
			}else{
				button.setHovered(false);
			}
		}

	}

	public void mousePressed(int x, int y) {
		for(MyButton button: buttons){
			if(button.getBounds().contains(x,y)){
				button.setPressed(true);
				if(button.isTowerButton()){
					playing.setDragingTower(true);
					playing.setSelectedTower(button.getType());
				}
			}
		}

	}

	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	public void mouseDragged(int x, int y) {

	}


}
