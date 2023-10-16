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


	public void initTownButtons() {
		int menuX = uiCoordinate.getX();
		int menuY = uiCoordinate.getY();

		int buttonWidth = 100;
		int buttonHeight = 80;

		int startX = (int) (menuX + 0.05 * width);
		int startY = (int) (menuY + 0.02 * height);
		int yOffset = (int) (0.055 * height);  // height + 10 pixels gap between buttons

		MyButton battleButton = addRelativelyPositionedButton("Battle!", startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, 0, yOffset, false);
		startY += battleButton.height + yOffset;

		MyButton minerButton = addRelativelyPositionedButton(MINER_B_B, startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, 0, yOffset, false);
		startY += minerButton.height + yOffset;

		MyButton woodButton = addRelativelyPositionedButton(WOOD_B_B, startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, 0, yOffset, false);
		startY += woodButton.height + yOffset;

		MyButton houseButton = addRelativelyPositionedButton(HOUSE_B_B, startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, 0, yOffset, false);
		startY += houseButton.height + yOffset;

		addRelativelyPositionedButton("Menu", startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, 0, yOffset, false);
	}

	public void initPlayingButtons() {
		int menuX = uiCoordinate.getX();
		int menuY = uiCoordinate.getY();

		int buttonWidth = 100;
		int buttonHeight = 80;

		int startX = (int) (menuX + 0.05 * width);
		int startY = (int) (menuY + 0.02 * height);
		int yOffset = (int) (0.055 * height);  // height + 10 pixels gap between buttons

		MyButton townButton = addRelativelyPositionedButton("Town", startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, 0, yOffset, false);
		startY += townButton.height + yOffset;

		MyButton arrowButton = addRelativelyPositionedButton(ARROW_T_B, startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, 0, yOffset, false);
		startY += arrowButton.height + yOffset;

		MyButton rocketButton = addRelativelyPositionedButton(ROCKET_T_B, startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, 0, yOffset, false);
		startY += rocketButton.height + yOffset;

		MyButton mageButton = addRelativelyPositionedButton(MAGE_T_B, startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, 0, yOffset, false);
		startY += mageButton.height + yOffset;

		MyButton snipButton = addRelativelyPositionedButton(SNIP_T_B, startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, 0, yOffset, false);
		startY += snipButton.height + yOffset;

		addRelativelyPositionedButton("Menu", startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, 0, yOffset, false);
	}

	public void initPlayingButtonsDown() {
		int menuX = uiCoordinate.getX();
		int menuY = uiCoordinate.getY();

		int startX = (int) (menuX + 0.05 * width);
		int startY = (int) (uiCoordinate.getY() + 0.05 * height);
		int xOffset = 25;
		int buttonWidth = 100;
		int buttonHeight = 80;

		MyButton sellButton = addRelativelyPositionedButton("Sell", startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, xOffset, 0, true);
		startX += sellButton.width + xOffset;
		MyButton upgradeButton = addRelativelyPositionedButton("Upgrade", startX, startY, buttonWidth, buttonHeight, menuX, menuY, width, height, xOffset, 0, true);
	}
	private MyButton addRelativelyPositionedButton(String label, int x, int y, int width, int height, int menuX, int menuY, int menuWidth, int menuHeight, int xOffset, int yOffset, boolean horizontal) {
		float relativeX = (float) (x - menuX) / menuWidth;
		float relativeY = (float) (y - menuY) / menuHeight;

		UiCoordinate buttonCoordinate = new UiCoordinate(new RelativeCoordinate(uiCoordinate.getAbsolutePosition(), relativeX, relativeY, menuWidth, menuHeight));
		MyButton button = new MyButton(label, buttonCoordinate, width, height, true);
		buttons.add(button);
		addChild(button);
		return button;
	}

	private MyButton addRelativelyPositionedButton(int imageType, int x, int y, int width, int height, int menuX, int menuY, int menuWidth, int menuHeight, int xOffset, int yOffset, boolean horizontal) {
		float relativeX = (float) (x - menuX) / menuWidth;
		float relativeY = (float) (y - menuY) / menuHeight;

		UiCoordinate buttonCoordinate = new UiCoordinate(new RelativeCoordinate(uiCoordinate.getAbsolutePosition(), relativeX, relativeY, menuWidth, menuHeight));
		MyButton button = new MyButton(imageType, buttonCoordinate, width, height, true, false, this);
		buttons.add(button);
		addChild(button);
		return button;
	}
	@Override
	public void updateOnFrame() {
		updateButtonPos();
	}

	public void updateButtonPos() {
		if (uipos != UIPos.PLAYINGDOWN) return;
		if (pointer == null) return;
		Tower tower = (Tower) pointer;

		if (!tower.isMaxedLevel()) {
			int menuX = uiCoordinate.getX();
			int menuY = uiCoordinate.getY();

			int startX = (int) (menuX + 0.05 * width);
			int startY = (int) (menuY + 0.05 * height);
			int xOffset = 25;

			float relativeX1 = (float) (startX - menuX) / width;
			float relativeY = (float) (startY - menuY) / height;

			buttons.get(0).setUiCoordinate(new UiCoordinate(new RelativeCoordinate(this.uiCoordinate.getAbsolutePosition(), relativeX1, relativeY, width, height)));

			startX += buttons.get(0).width + xOffset;

			float relativeX2 = (float) (startX - menuX) / width;

			buttons.get(1).setUiCoordinate(new UiCoordinate(new RelativeCoordinate(this.uiCoordinate.getAbsolutePosition(), relativeX2, relativeY, width, height)));
			setButtonsVisibility(true);

		} else {
			buttons.get(1).setVisible(false);
			float relativeX = (0.5f - ((float) buttons.get(0).width / 2 / width));
			float relativeY = 0.05f;

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
