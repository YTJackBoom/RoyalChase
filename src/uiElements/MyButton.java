package uiElements;

import helpers.Constants;
import helpers.TextReader;
import helpers.UiCoordinate;
import helpers.variables;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static helpers.variables.Buttons.*;

public class MyButton extends UiElement {
	private boolean checked = false;
	private boolean isTextButton = false;
	private boolean mouseHover = false;
	private boolean mousePressed = false;
	private String text = null;
	private int type;
	private int level = -1;
	private BufferedImage ButtonImage;
	private Tooltip tooltip;
	private boolean hasOutline = true;

	public MyButton(String text, UiCoordinate uiCoordinate, int width, int height, boolean visibility) {
		super(uiCoordinate, width, height, UIObjectType.BUTTON, 0, visibility);
		isVisible = visibility;
		this.text = text;
		isTextButton = true;
	}

	public MyButton(int ButtonType, UiCoordinate uiCoordinate, int width, int height, boolean visibility, boolean hasOutline) {
		super(uiCoordinate, width, height, UIObjectType.BUTTON, ButtonType, visibility);
		this.type = ButtonType;
		initButtonTooltip();
		this.hasOutline = hasOutline;
	}

	public MyButton(int level, UiCoordinate uiCoordinate, int width, int height, boolean visibility) {
		super(uiCoordinate, width, height, UIObjectType.BUTTON, 0, visibility);
		isVisible = visibility;
		this.level = level;
		isTextButton = true;
		if (level == 0) {
			text = "Tutorial";
		} else {
			text = "Level " + level;
		}
	}



	private void initButtonTooltip() {
		File tooltipText;
		if ((tooltipText = variables.Buttons.getTooltipTextFile(type)) == null) return;
		TextReader textReader = new TextReader(tooltipText);
		String[] texts;
		try {
			texts = textReader.readLines();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		tooltip = new Tooltip(texts, 1700, 900);

	}

	@Override
	public void render(Graphics g) {
		if (isVisible) {
			g.setFont(Constants.UIConstants.BUTTONFONT);
			if (isTextButton) {
				renderButtonBody(g);
				renderButtonBox(g);
				renderButtonText(g);
			} else {
				super.render(g);
				renderButtonBox(g);
				if(tooltip != null)	tooltip.render(g);

			}
		}
	}

	private void renderButtonBody(Graphics g) {
		if(mouseHover) {
			g.setColor(Color.GRAY);
		} else {
			g.setColor(Color.WHITE);
		}
		g.fillRect(uiCoordinate.getX(), uiCoordinate.getY(), width, height);
	}

	private void renderButtonBox(Graphics g) {
		if (!hasOutline) return;
		g.setColor(Color.BLACK);
		g.drawRect(uiCoordinate.getX(), uiCoordinate.getY(), width, height);
		if(mousePressed) {
			g.drawRect(uiCoordinate.getX() + 1, uiCoordinate.getY() + 1, width - 2, height - 2);
			g.drawRect(uiCoordinate.getX() + 2, uiCoordinate.getY() + 2, width - 4, height - 4);
		}
	}
	private void renderButtonText(Graphics g) {
		g.setColor(Color.BLACK);
		int width2 = g.getFontMetrics().stringWidth(text);
		int height2 = g.getFontMetrics().getHeight();

		g.drawString(text, uiCoordinate.getX() - width2 / 2 + width / 2, uiCoordinate.getY() + height2 / 2 + height / 2);
	}


	public void setHovered(boolean mouseHover) {
		this.mouseHover = mouseHover;
		if(tooltip != null) tooltip.setVisible(mouseHover);
	}
	public void setPressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	public void resetBools() {
		this.mouseHover = false;
		this.mousePressed = false;
	}

	public String getText() {
		return text;
	}

	public int getType() {
		return type;
	}
	public boolean isTowerButton() {
		//TODO: Change this to a better way of checking if it is a tower button
		return type >= Foundation_T_B && !isTextButton && type <= SNIP_T_B;
	}
	public boolean isBuildingButton() {
		return type >= MANA_B_B && !isTextButton && type <= HOUSE_B_B;
	}
	public void setChecked(boolean b) {
		checked = b;
	}

	public boolean isChecked() {
		return checked;
	}

	public int getLevel() {
		return level;
	}
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean visible) {
		isVisible = visible;
	}

}
