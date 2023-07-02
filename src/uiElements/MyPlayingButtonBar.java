package uiElements;

import basics.Direction;
import gameObjects.GameObject;
import scenes.GameStates;
import helpers.Coordinate;
import scenes.Playing;

import java.awt.*;
import java.util.ArrayList;

import static helpers.variables.Buttons.*;

public class MyPlayingButtonBar {
	private Coordinate pos;
	private Playing playing;
	private ArrayList<MyButton> buttons;
	private int width;
	private int height;
	private Rectangle bounds;
	private boolean isVisible = false;
	private GameObject pointer;
	public MyPlayingButtonBar(Playing playing, Coordinate pos, int width, int height, Direction dir){ //1: playingRight, 2: playingDown
		buttons = new ArrayList<MyButton>();
		this.pos = pos;
		this.width = width;
		this.height = height;
		this.playing = playing;
		switch (dir) {
			case DOWN -> initButtonsDown();
			default -> initButtons();
		}
		initBounds();
	}


	public void initButtons() {
		int startX = pos.getX()+10;
		int startY = pos.getY()+10;
		int xOffset = 0;
		int yOffset = 90;
		int width = 100;
		int height = 80;

		buttons.add(new MyButton("Town",startX, startY , width, height));
		buttons.add(new MyButton("Sell",startX+xOffset, startY+yOffset, width, height));
		buttons.add(new MyButton("Upgrade",startX+xOffset*2, startY+yOffset*2, width, height));

		buttons.add(new MyButton(ARROW_T_B,startX+xOffset*3, startY+yOffset*3, width, height));
		buttons.add(new MyButton(ROCKET_T_B,startX+xOffset*4, startY+yOffset*4,width,height));


		buttons.add(new MyButton("Menu",startX+xOffset*5, startY+yOffset*5, width, height));

	}

	public void initButtonsDown() {
		int startX = pos.getX()+10;
		int startY = pos.getY()+10;
		int xOffset = 50;
		int yOffset = 0;
		int width = 100;
		int height = 80;

		buttons.add(new MyButton("Sell",startX, startY, width, height));
		buttons.add(new MyButton("Upgrade",startX+xOffset, startY+yOffset, width, height));
	}
	public void initBounds() {
		bounds = new Rectangle(pos.getX(), pos.getY(), width, height);
	}

	public void render(Graphics g) {
		if (isVisible) {
			g.setColor(Color.BLACK);
			g.fillRect(pos.getX(), pos.getY(), width, height);
			renderButtons(g);
		}

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


	//Mouse Listeners
	public void mouseClicked(int x, int y) {
		for (MyButton button : buttons) {
			if (button.getBounds().contains(x, y)) {
				if (button.getText() != null) {
					if (button.getText().equals("Play")) {
						GameStates.gameState = GameStates.PLAYING;
					} else if (button.getText().equals("Town")) {
						GameStates.gameState = GameStates.TOWN;
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
					playing.setDraggedTower(button.getType());
				}
			}
		}

	}

	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	public void mouseDragged(int x, int y) {

	}
	public void setVisible(boolean b) {
		isVisible = b;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	public void setPointer(GameObject pointer) {
		this.pointer = pointer;
	}


}
