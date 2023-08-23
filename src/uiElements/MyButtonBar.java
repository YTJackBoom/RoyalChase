package uiElements;

import gameObjects.GameObject;
import gameObjects.Tower;
import helpers.variables;
import scenes.GameScenes;
import scenes.GameStates;
import helpers.Coordinate;
import scenes.Playing;
import scenes.Town;

import java.awt.*;
import java.util.ArrayList;

import static helpers.variables.Buttons.*;
import static uiElements.UIPos.PLAYINGDOWN;

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
	private UIPos uipos;
	public MyButtonBar(GameScenes scene, Coordinate pos, int width, int height, UIPos uiPos){
		this.uipos = uiPos;
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

		buttons.add(new MyButton("Town",startX, startY , width, height,true));
		buttons.add(new MyButton("Sell",startX+xOffset, startY+yOffset, width, height,true));
		buttons.add(new MyButton("Upgrade",startX+xOffset*2, startY+yOffset*2, width, height,true));

		buttons.add(new MyButton(ARROW_T_B,startX+xOffset*3, startY+yOffset*3, width, height,true));
		buttons.add(new MyButton(ROCKET_T_B,startX+xOffset*4, startY+yOffset*4,width,height,true));
		buttons.add(new MyButton(MAGE_T_B,startX+xOffset*5,startY+yOffset*5,width,height,true));
		buttons.add(new MyButton(SNIP_T_B,startX+xOffset*6,startY+yOffset*6,width,height,true));


		buttons.add(new MyButton("Menu",startX+xOffset*7, startY+yOffset*7, width, height,true));

	}

	public void initPlayingButtonsDown() {
		int startX = pos.getX()+10;
		int startY = pos.getY()+10;
		int xOffset = 110;
		int yOffset = 0;
		int width = 100;
		int height = 80;

		buttons.add(new MyButton("Sell",startX, startY, width, height,true));
		buttons.add(new MyButton("Upgrade",startX+xOffset, startY+yOffset, width, height,true));
	}

	public void initTownButtons() {
		int startX = pos.getX()+10;
		int startY = pos.getY()+10;
		int xOffset = 0;
		int yOffset = 90;
		int width = 100;
		int height = 80;

		buttons.add(new MyButton("Battle!",startX, startY , width, height,true));
		buttons.add(new MyButton("Back",startX+xOffset, startY+yOffset, width, height,true));

		buttons.add(new MyButton(MANA_B_B,startX+xOffset*2, startY+yOffset*2, width, height,true));
		buttons.add(new MyButton(HOUSE_B_B,startX+xOffset*3, startY+yOffset*3,width,height,true));

		buttons.add(new MyButton("Next",startX+xOffset*4, startY+yOffset*4 , width,height, true));
		buttons.add(new MyButton("Menu",startX+xOffset*5, startY+yOffset*5, width,height, true));
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
		if(!(uipos == UIPos.PLAYINGDOWN)) {
			renderButtonsList(g);
		}else {
			Tower tower = (Tower) pointer;
			if (!tower.isMaxedLevel()) {
				buttons.get(0).setPos(new Coordinate(pos.getX()+10,pos.getY()+10));
				setButtonsVisibility(true);
				renderButtonsList(g);
			}else {
				buttons.get(1).setVisible(false);
				int x = pos.getX()+width/2-buttons.get(0).getWidth()/2;
				int y = pos.getY();
				buttons.get(0).setPos(new Coordinate(x,y));
				renderButtonsList(g);
			}
		}
	}


	public void renderButtonsList(Graphics g) {
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

	}



	public void mouseMoved(int x, int y) {
		if (isVisible) {
			for (MyButton button : buttons) {
				button.setHovered(false); // zurücksetzten des Hovered-Status aller Buttons
			}
			for (MyButton button : buttons) {
				if (button.getBounds().contains(x, y)) {
					button.setHovered(true);
					hoveredButton = button;
					break;
				}
			}


		}
	}
	public void mousePressed(int x, int y) {
		if (isVisible) {
			for (MyButton button : buttons) {
				if (button.getBounds().contains(x, y) ) {
					button.setPressed(true);
					if (scene.getClass() == Playing.class && button.isTowerButton()) {
						Playing playing = (Playing) scene;
						playing.setDragingTower(true);
						playing.setDraggedTower(button.getType());
					} else if (scene.getClass() == Town.class && button.isBuildingButton()) {
						Town town = (Town) scene;
						town.setDragingBuilding(true);
						town.setSelectedBuilding(button.getType());
					}
				}
			}
		}
		}


	public void mouseReleased(int x, int y) {
		if(isVisible) {
			for (MyButton button : buttons) {
				if (button.getBounds().contains(x, y)&&button.isVisible()) {
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
						} else if (button.getText().equals("Upgrade")) {
							Playing playing = (Playing) scene;
							playing.getTowerController().upgradeTower();
//						System.out.println("d");
						} else if (button.getText().equals("Sell")) {
							Playing playing = (Playing) scene;
							playing.getTowerController().sellTower();
//						System.out.println("da");

						}
					}
				}
			}
		}
		resetButtons();
		hoveredButton = null;
	}

	public void mouseDragged(int x, int y) {

	}
	public void setVisible(boolean b) {
		isVisible = b;
	}
	public void setButtonsVisibility(boolean b) {
		for (MyButton button : buttons) {
			button.setVisible(b);
		}
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
