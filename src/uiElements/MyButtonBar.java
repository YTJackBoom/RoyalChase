package uiElements;

import gameObjects.GameObject;
import gameObjects.Tower;
import scenes.GameScenes;
import scenes.GameStates;
import scenes.Playing;

import java.awt.*;
import java.util.ArrayList;

import static helpers.variables.Buttons.*;

public class MyButtonBar extends UiElement {
	private GameScenes scene;
	private ArrayList<MyButton> buttons;
	private boolean isVisible = false;
	private GameObject pointer;
	private MyButton hoveredButton;
	private UIPos uipos;

	public MyButtonBar(GameScenes scene, Rectangle bounds, UIPos uiPos) {
		super(bounds, UIObjectType.ICON, switch (uiPos) {
			case PLAYINGDOWN -> 13;
			case PLAYINGRIGHT -> 12;
			case TOWNRIGHT -> 12;
		}, true, "", "");

		this.uipos = uiPos;
		buttons = new ArrayList<MyButton>();

		this.scene = scene;
		switch (uiPos) {
			case PLAYINGDOWN -> initPlayingButtonsDown();
			case PLAYINGRIGHT -> initPlayingButtons();
			case TOWNRIGHT -> initTownButtons();
		}
	}


	public void initPlayingButtons() {
		int startX = bounds.x + 10;
		int startY = bounds.y + 20;
		int xOffset = 0;
		int yOffset = 90;
		int width = 100;
		int height = 80;

		buttons.add(new MyButton("Town", new Rectangle(startX, startY, width, height), true));

		buttons.add(new MyButton(ARROW_T_B, new Rectangle(startX + xOffset * 3, startY + yOffset * 3, width, height), true, false));
		buttons.add(new MyButton(ROCKET_T_B, new Rectangle(startX + xOffset * 4, startY + yOffset * 4, width, height), true, false));
		buttons.add(new MyButton(MAGE_T_B, new Rectangle(startX + xOffset * 5, startY + yOffset * 5, width, height), true, false));
		buttons.add(new MyButton(SNIP_T_B, new Rectangle(startX + xOffset * 6, startY + yOffset * 6, width, height), true, false));


		buttons.add(new MyButton("Menu", new Rectangle(startX + xOffset * 7, startY + yOffset * 7, width, height), true));


	}

	public void initPlayingButtonsDown() {
		int startX = bounds.x + 10;
		int startY = bounds.y + 10;
		int xOffset = 110;
		int yOffset = 0;
		int width = 100;
		int height = 80;

		buttons.add(new MyButton("Sell", new Rectangle(startX, startY, width, height), true));
		buttons.add(new MyButton("Upgrade", new Rectangle(startX + xOffset, startY + yOffset, width, height), true));
	}

	public void initTownButtons() {
		int startX = bounds.x + 10;
		int startY = bounds.y + 20;
		int xOffset = 0;
		int yOffset = 90;
		int width = 100;
		int height = 80;

		buttons.add(new MyButton("Battle!", new Rectangle(startX, startY, width, height), true));

		buttons.add(new MyButton(MANA_B_B, new Rectangle(startX + xOffset * 2, startY + yOffset * 2, width, height), true, false));
		buttons.add(new MyButton(HOUSE_B_B, new Rectangle(startX + xOffset * 3, startY + yOffset * 3, width, height), true, false));

		buttons.add(new MyButton("Menu", new Rectangle(startX + xOffset * 5, startY + yOffset * 5, width, height), true));


	}

	@Override
	public void render(Graphics g) {
		if (isVisible) {
			super.render(g);
			renderButtons(g);
		}

	}
	public void renderButtons(Graphics g) {
		if(!(uipos == UIPos.PLAYINGDOWN)) {
			renderButtonsList(g);
		}else {
			Tower tower = (Tower) pointer;
			if (!tower.isMaxedLevel()) {
				buttons.get(0).setPos(bounds.x + 10, bounds.y + 10);
				setButtonsVisibility(true);
				renderButtonsList(g);
			}else {
				buttons.get(1).setVisible(false);
				int x = bounds.x + bounds.width / 2 - buttons.get(0).getBounds().width / 2;
				int y = bounds.y;
				buttons.get(0).setPos(x, y);
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


	//Mouse Listeners
	public void mouseClicked(int x, int y) {

	}



	public void mouseMoved(int x, int y) {
		for (MyButton button : buttons) {
			button.setHovered(false); // zurücksetzten des Hovered-Status aller Buttons
		}
		hoveredButton = null;

		if (isVisible) {
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
				if (button.getBounds().contains(x, y)) {
					button.setPressed(true);
					if (button.isTowerButton() || button.isBuildingButton()) {
						scene.setDragingObject(true);
						scene.setDraggedObjectType(button.getType());
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
		scene.setDragingObject(false);
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
