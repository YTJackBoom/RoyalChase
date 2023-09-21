package uiElements;

import helpers.Constants;
import helpers.TextReader;
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

	public MyButton(String text, Rectangle bounds, boolean visibility) {
		super(bounds, UIObjectType.BUTTON, 0, visibility, "", "");
		isVisible = visibility;
		this.text = text;
		isTextButton = true;
	}

	public MyButton(int ButtonType, Rectangle bounds, boolean visibility, boolean hasOutline) {
		super(bounds, UIObjectType.BUTTON, ButtonType, visibility, "", "");
		this.type = ButtonType;
		initButtonTooltip();
		this.hasOutline = hasOutline;
	}

	public MyButton(int level, Rectangle bounds, boolean visibility) {
		super(bounds, UIObjectType.BUTTON, 0, visibility, "", "");
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
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	private void renderButtonBox(Graphics g) {
		if (!hasOutline) return;
		g.setColor(Color.BLACK);
		g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		if(mousePressed) {
			g.drawRect(bounds.x + 1, bounds.y + 1, bounds.width - 2, bounds.height - 2);
			g.drawRect(bounds.x + 2, bounds.y + 2, bounds.width - 4, bounds.height - 4);
		}
	}
	private void renderButtonText(Graphics g) {
		g.setColor(Color.BLACK);
		int width2 = g.getFontMetrics().stringWidth(text);
		int height2 = g.getFontMetrics().getHeight();

		g.drawString(text, bounds.x - width2 / 2 + bounds.width / 2, bounds.y + height2 / 2 + bounds.height / 2);
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
