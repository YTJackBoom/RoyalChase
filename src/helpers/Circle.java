package helpers;

import java.awt.*;

public class Circle {
	private int x,y;
	private int radius;
	public Circle(Coordinate pos, int radius) {
		x = pos.getX();
		y = pos.getY();
		this.radius = radius;
	}

	public boolean contains(Coordinate pos) {
		int distance = (int) Math.sqrt(Math.pow(pos.getX() - x, 2) + Math.pow(pos.getY() - y, 2));
		return distance <= radius;
	}

	public void render(Graphics g) {
		g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
	}
	public boolean contains(Rectangle rect) {
		// Check if any part of the given rectangle is inside the circle

		// Check if any of the four corners of the rectangle is inside the circle
		if (contains(new Coordinate(rect.x, rect.y))
				|| contains(new Coordinate(rect.x + rect.width, rect.y))
				|| contains(new Coordinate(rect.x, rect.y + rect.height))
				|| contains(new Coordinate(rect.x + rect.width, rect.y + rect.height))) {
			return true;
		}

		// Check if any part of the rectangle's edges is inside the circle
		for (int i = rect.x; i < rect.x + rect.width; i++) {
			if (contains(new Coordinate(i, rect.y)) || contains(new Coordinate(i, rect.y + rect.height))) {
				return true;
			}
		}
		for (int j = rect.y; j < rect.y + rect.height; j++) {
			if (contains(new Coordinate(rect.x, j)) || contains(new Coordinate(rect.x + rect.width, j))) {
				return true;
			}
		}

		// If none of the above conditions are met, return false
		return false;
	}
}
