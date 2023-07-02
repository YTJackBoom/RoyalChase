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
}
