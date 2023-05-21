package uiElements;

import java.awt.*;
import java.util.Collection;

public class MyButton {
	private boolean mouseHover = false;
	private boolean mousePressed = false;
	private String text;
	private Rectangle bounds;
	private int x,y,width,height;
	public MyButton(String text, int x, int y, int width, int height) {
		this.text = text;

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
		renderButtonBody(g);

		renderButtonBox(g);

		renderButtonText(g);
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
}
