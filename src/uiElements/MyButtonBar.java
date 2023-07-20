package uiElements;

import gameObjects.GameObject;
import helpers.variables;
import scenes.GameScenes;
import scenes.GameStates;
import helpers.Coordinate;
import scenes.Playing;
import scenes.Town;

import java.awt.*;
import java.util.ArrayList;

import static helpers.variables.Buttons.*;

public class MyButtonBar {
	private Coordinate pos;
	private GameScenes scene;
	private ArrayList<MyButton> buttons;
	private int width;
	private int height;
	private Rectangle bounds;
	private boolean isVisible = false;
	private GameObject pointer;
	private MyButton hoveredButton;
	private UIPos upos;
	public MyButtonBar(GameScenes scene, Coordinate pos, int width, int height, UIPos uiPos){
		upos = uiPos;
		buttons = new ArrayList<MyButton>();
		this.pos = pos;
		this.width = width;
		this.height = height;
		this.scene = scene;
		switch (uiPos) {
			case PLAYINGDOWN -> initPlayingButtonsDown();
			case PLAYINGRIGHT -> initPlayingButtons();
			case TOWNRIGHT -> initTownButtons();
		}
		initBounds();
	}


	public void initPlayingButtons() {
		int startX = pos.getX()-10;
		int startY = pos.getY()-10;
		int xOffset = 0;
		int yOffset = 90;
		int width = 100;
		int height = 80;

		buttons.add(new MyButton("Town",startX, startY , width, height));
		buttons.add(new MyButton("Sell",startX+xOffset, startY+yOffset, width, height));
		buttons.add(new MyButton("Upgrade",startX+xOffset*2, startY+yOffset*2, width, height));

		buttons.add(new MyButton(ARROW_T_B,startX+xOffset*3, startY+yOffset*3, width, height));
		buttons.add(new MyButton(ROCKET_T_B,startX+xOffset*4, startY+yOffset*4,width,height));
		buttons.add(new MyButton(MAGE_T_B,startX+xOffset*5,startY+yOffset*5,width,height));
		buttons.add(new MyButton(SNIP_T_B,startX+xOffset*6,startY+yOffset*6,width,height));


		buttons.add(new MyButton("Menu",startX+xOffset*7, startY+yOffset*7, width, height));

	}

	public void initPlayingButtonsDown() {
		int startX = pos.getX()+10;
		int startY = pos.getY()+10;
		int xOffset = 110;
		int yOffset = 0;
		int width = 100;
		int height = 80;

		buttons.add(new MyButton("Sell",startX, startY, width, height));
		buttons.add(new MyButton("Upgrade",startX+xOffset, startY+yOffset, width, height));
	}

	public void initTownButtons() {
		int startX = pos.getX()+10;
		int startY = pos.getY()+10;
		int xOffset = 0;
		int yOffset = 90;
		int width = 100;
		int height = 80;

		buttons.add(new MyButton("Battle!",startX, startY , width, height));
		buttons.add(new MyButton("Back",startX+xOffset, startY+yOffset, width, height));
		buttons.add(new MyButton("Sell",startX+xOffset*2, startY+yOffset*2, width, height));

		buttons.add(new MyButton(MANA_B_B,startX+xOffset*3, startY+yOffset*3, width, height));

		buttons.add(new MyButton("Next",startX+xOffset*4, startY+yOffset*4 , width, height));
		buttons.add(new MyButton("Menu",startX+xOffset*5, startY+yOffset*5, width, height));
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
						Playing playing = (Playing) scene;
						playing.resetBools();
						GameStates.gameState = GameStates.TOWN;
					} else if (button.getText().equals("Exit")) {
						System.exit(0);
					} else if (button.getText().equals("Menu")) {
						GameStates.gameState = GameStates.MENU;
					} else if (button.getText().equals("Battle!")) {
						GameStates.gameState = GameStates.PLAYING;
					} else if(button.getText().equals("Upgrade")) {
						Playing playing = (Playing)  scene;
						playing.getTowerController().upgradeTower();
						System.out.println("d");
					}else if (button.getText().equals("Sell")) {
						Playing playing = (Playing)  scene;
						playing.getTowerController().sellTower();
						System.out.println("da");

					}
				}
			}
		}
	}



	public void mouseMoved(int x, int y) {
		for(MyButton button: buttons){
			if(button.getBounds().contains(x,y)){
				button.setHovered(true);
				hoveredButton = button;
				break;
			}else{
				button.setHovered(false);
				hoveredButton = null;
			}
		}


	}
	public void mousePressed(int x, int y) {
		for(MyButton button: buttons){
			if(button.getBounds().contains(x,y)){
				button.setPressed(true);
				if(button.isTowerButton()){
					if(scene.getClass() == Playing.class) {
						Playing playing = (Playing) scene;
						playing.setDragingTower(true);
						playing.setDraggedTower(button.getType());
					}
					if(scene.getClass() == Town.class) {
						Town town = (Town) scene;
						town.setDragingBuilding(true);
						town.setSelectedBuilding(button.getType());
					}
				}
			}
		}

	}

	public void mouseReleased(int x, int y) {
		resetButtons();
		hoveredButton = null;
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

	public MyButton getHoveredButton() {
		return hoveredButton;
	}


	public void setHoveredButton(MyButton b) {
		hoveredButton = b;
	}
}
