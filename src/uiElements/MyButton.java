package uiElements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class MyButton {
	private boolean isTextButton = false;
	private boolean mouseHover = false;
	private boolean mousePressed = false;
	private String text = null;
	private int type;
	private Rectangle bounds;
	private int x,y,width,height;
	public MyButton(String text, int x, int y, int width, int height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		isTextButton = true;
		initBounds();
	}
	public MyButton(int ButtonType, int x, int y, int width, int height) {
		this.type = ButtonType;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		initBounds();
	}


	private void initBounds() {
		bounds = new Rectangle(x,y,width,height);
	}

	public void render(Graphics g) {
		if (isTextButton) {
			renderButtonBody(g);
			renderButtonBox(g);
			renderButtonText(g);
		}else {
			renderButtonImage(g);
			renderButtonBody(g);
			renderButtonBox(g);
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
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		if(mousePressed) {
			g.drawRect(x+1, y+1, width-2, height-2);
			g.drawRect(x+2, y+2, width-4, height-4);
		}
	}
	private void renderButtonText(Graphics g) {
		int width2 = g.getFontMetrics().stringWidth(text);
		int height2 = g.getFontMetrics().getHeight();

		g.drawString(text, x - width2/2 + width/2, y + height2/2 + height/2 );
	}
	private void renderButtonImage(Graphics g) {
		BufferedImage ButtonImage;
		try {
			ButtonImage = ImageIO.read(helpers.variables.Buttons.getButtonImageFile(type));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		g.drawImage(ButtonImage, x, y, width, height, null);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setHovered(boolean mouseHover) {
		this.mouseHover = mouseHover;
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
}
