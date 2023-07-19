package helpers;

import java.awt.*;

public class Hitbox extends Rectangle {
	public Hitbox(int x,int y,int width, int height){
		super(x, y,width,height);
	}

	public boolean overlaps(Hitbox other) {
		int thisRight = x + width;
		int thisBottom = y + height;
		int otherRight = other.x + other.width;
		int otherBottom = other.y + other.height;

		boolean horizontalOverlap = x < otherRight && thisRight > other.x;
		boolean verticalOverlap = y < otherBottom && thisBottom > other.y;

		return horizontalOverlap && verticalOverlap;
	}
}
