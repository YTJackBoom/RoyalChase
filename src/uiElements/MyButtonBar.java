package uiElements;

import gameObjects.GameObject;
import gameObjects.Tower;
import helpers.RelativeCoordinate;
import helpers.UiCoordinate;
import scenes.GameScenes;
import scenes.GameStates;
import scenes.Playing;

import java.util.ArrayList;

import static helpers.AssetLocation.Buttons.*;

public class MyButtonBar extends UiElement {
	private GameScenes scene;
	private ArrayList<MyButton> buttons;
	private GameObject pointer;
	private MyButton hoveredButton;
	private UIPos uipos;

	public MyButtonBar(GameScenes scene, UiCoordinate uiCoordinate, int width, int height, UIPos uiPos) {
		super(uiCoordinate, width, height, UIObjectType.ICON, switch (uiPos) {
			case PLAYINGDOWN -> 13;
			case PLAYINGRIGHT -> 12;
			case TOWNRIGHT -> 12;
		}, true);

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
		int menuX = uiCoordinate.getX();
		int menuY = uiCoordinate.getY();

		int buttonWidth = 100;
		int buttonHeight = 80;

		int startX = (int) (menuX + 0.05 * width);
		int startY = (int) (menuY + 0.02 * height);
		int yOffset = (int) (0.055 * height);  // height + 10 pixels gap between buttons
		int xOffset = 0;


		boolean horizontal = false;

		startY = addRelativelyPositionedButton("Town", startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, xOffset, yOffset, horizontal);
		startY = addRelativelyPositionedButton(ARROW_T_B, startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, xOffset, yOffset, horizontal);
		startY = addRelativelyPositionedButton(ROCKET_T_B, startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, xOffset, yOffset, horizontal);
		startY = addRelativelyPositionedButton(MAGE_T_B, startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, xOffset, yOffset, horizontal);
		startY = addRelativelyPositionedButton(SNIP_T_B, startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, xOffset, yOffset, horizontal);

		addRelativelyPositionedButton("Menu", startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, xOffset, yOffset, horizontal);
	}




	public void initPlayingButtonsDown() {
		int menuX = uiCoordinate.getX();
		int menuY = uiCoordinate.getY();

		int startX = (int) (menuX + 0.05 * width);
		int startY = (int) (uiCoordinate.getY() + 0.05 * height);
		int xOffset = 110;
		int yOffset = 0;
		int width = 100;
		int height = 80;

		startX = addRelativelyPositionedButton("Sell", startX, startY, width, height, menuX, menuY, width, height, xOffset, yOffset, true);
		startX = addRelativelyPositionedButton("Upgrade", startX, startY, width, height, menuX, menuY, width, height, xOffset, yOffset, true);
	}

	public void initTownButtons() {
		int menuX = uiCoordinate.getX();
		int menuY = uiCoordinate.getY();

		int buttonWidth = 100;
		int buttonHeight = 80;

		int startX = (int) (menuX + 0.05 * width);
		int startY = (int) (menuY + 0.02 * height);
		int yOffset = (int) (0.055 * height);  // height + 10 pixels gap between buttons
		int xOffset = 0;


		boolean horizontal = false;

		startY = addRelativelyPositionedButton("Battle!", startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, xOffset, yOffset, horizontal);
		startY = addRelativelyPositionedButton(MINER_B_B, startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, xOffset, yOffset, horizontal);
		startY = addRelativelyPositionedButton(WOOD_B_B, startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, xOffset, yOffset, horizontal);
		startY = addRelativelyPositionedButton(HOUSE_B_B, startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, xOffset, yOffset, horizontal);
		startY = addRelativelyPositionedButton("Menu", startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, xOffset, yOffset, horizontal);
	}

	// The horizontal parameter will determine whether we're adjusting horizontally or vertically
	private int addRelativelyPositionedButton(String label, int x, int y, int width, int height, int menuX, int menuY, int menuWidth, int menuHeight, int xOffset, int yOffset, boolean horizontal) {
		float relativeX = (float) (x - menuX) / menuWidth;
		float relativeY = (float) (y - menuY) / menuHeight;

		UiCoordinate buttonCoordinate = new UiCoordinate(new RelativeCoordinate(uiCoordinate.getAbsolutePosition(), relativeX, relativeY, menuWidth, menuHeight));
		MyButton button = new MyButton(label, buttonCoordinate, width, height, true);
		buttons.add(button);
		addChild(button);
		return horizontal ? x + xOffset + width : y + yOffset + height;
	}

	private int addRelativelyPositionedButton(int imageType, int x, int y, int width, int height, int menuX, int menuY, int menuWidth, int menuHeight, int xOffset, int yOffset, boolean horizontal) {
		float relativeX = (float) (x - menuX) / menuWidth;
		float relativeY = (float) (y - menuY) / menuHeight;

		UiCoordinate buttonCoordinate = new UiCoordinate(new RelativeCoordinate(uiCoordinate.getAbsolutePosition(), relativeX, relativeY, menuWidth, menuHeight));
		MyButton button = new MyButton(imageType, buttonCoordinate, width, height, true, false, this);
		buttons.add(button);
		addChild(button);
		return horizontal ? x + xOffset + width : y + yOffset + height;
	}

	@Override
	public void updateOnFrame() {
		updateButtonPos();
	}

	public void updateButtonPos() {
		if (uipos == UIPos.PLAYINGDOWN) return;
		if (pointer == null) return;
		Tower tower = (Tower) pointer;

		if (!tower.isMaxedLevel()) {
			float relativeX = (10f / width) * 100;  // converted to percentage
			float relativeY = (10f / height) * 100; // converted to percentage

			int x = uiCoordinate.getX() + (int) (width * (relativeX / 100));  // converted back from percentage for absolute positioning
			int y = uiCoordinate.getY() + (int) (height * (relativeY / 100));

			buttons.get(0).setUiCoordinate(new UiCoordinate(new RelativeCoordinate(uiCoordinate.getAbsolutePosition(), relativeX, relativeY, width, height)));
			setButtonsVisibility(true);
			} else {
				buttons.get(1).setVisible(false);
				float relativeX = (0.5f - ((float) buttons.get(0).width / 2 / width)) * 100;  // centering the button within bounds and converting to percentage
				float relativeY = 0f;  // top of the bounds

				int x = uiCoordinate.getX() + (int) (width * (relativeX / 100));
				int y = uiCoordinate.getY();  // As it's the top of the bounds

				buttons.get(0).setUiCoordinate(new UiCoordinate(new RelativeCoordinate(uiCoordinate.getAbsolutePosition(), relativeX, relativeY, width, height)));
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
				if (button.contains(x, y)) {
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
				if (button.contains(x, y)) {
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
				if (button.contains(x, y) && button.isVisible()) {
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
