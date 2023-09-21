package uiElements;

import helpers.Constants;
import helpers.Coordinate;
import helpers.TextReader;
import helpers.variables;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static helpers.variables.Buttons.*;

public class MyButton {
	private boolean checked = false;
	private boolean isTextButton = false;
	private boolean mouseHover = false;
	private boolean mousePressed = false;
	private boolean isVisible = false;
	private String text = null;
	private int type;
	private Rectangle bounds;
	private int x,y,width,height;
	private int level = -1;
	private BufferedImage ButtonImage;
	private Tooltip tooltip;
	private boolean hasOutline = true;

	public MyButton(String text, int x, int y, int width, int height, boolean visibility) {
		isVisible = visibility;
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		isTextButton = true;
		initBounds();
	}

	public MyButton(int ButtonType, int x, int y, int width, int height, boolean visibility, boolean hasOutline) {
		isVisible = visibility;
		this.type = ButtonType;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		initBounds();
		initButtonImage();
		initButtonTooltip();
		this.hasOutline = hasOutline;
	}
	public MyButton(int level, Coordinate pos, int width, int height,boolean visibility) {
		isVisible = visibility;
		this.level = level;
		this.x = pos.getX();
		this.y = pos.getY();
		this.width = width;
		this.height = height;
		isTextButton = true;
		if(level ==0) {
			text = "Tutorial";
		}else {
			text = "Level "+level;
		}
		initBounds();
	}



	private void initBounds() {
		bounds = new Rectangle(x,y,width,height);
	}
	private void initButtonImage() {
		try {
			ButtonImage = ImageIO.read(helpers.variables.Buttons.getButtonImageFile(type));
		} catch (IOException e) {
			throw new RuntimeException(e);
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

	public void render(Graphics g) {
		if (isVisible) {
			g.setFont(Constants.UIConstants.BUTTONFONT);
			if (isTextButton) {
				renderButtonBody(g);
				renderButtonBox(g);
				renderButtonText(g);
			} else {
				renderButtonImage(g);
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
		g.fillRect(x, y, width, height);
	}

	private void renderButtonBox(Graphics g) {
		if (!hasOutline) return;
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		if(mousePressed) {
			g.drawRect(x+1, y+1, width-2, height-2);
			g.drawRect(x+2, y+2, width-4, height-4);
		}
	}
	private void renderButtonText(Graphics g) {
		g.setColor(Color.BLACK);
		int width2 = g.getFontMetrics().stringWidth(text);
		int height2 = g.getFontMetrics().getHeight();

		g.drawString(text, x - width2/2 + width/2, y + height2/2 + height/2 );
	}
	private void renderButtonImage(Graphics g) {
		g.drawImage(ButtonImage, x, y, width, height, null);
	}

	public Rectangle getBounds() {
		return bounds;
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
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
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

	public void setPos(Coordinate pos) {
		x= pos.getX();
		y= pos.getY();
		initBounds();
	}
}
