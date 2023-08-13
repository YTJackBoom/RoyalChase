package helpers;

import java.awt.*;
import java.awt.geom.Point2D;

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

	public boolean contains(HitBox hitbox) {
	// Check if any part of the given pixel-perfect hitbox is inside the circle

	int circleCenterX = x; // Assuming x and y are the center coordinates of the circle
	int circleCenterY = y; // Adjust accordingly if x and y represent top-left corner



	int hitBoxWidth = hitbox.getGameObject().getActiveAnimator().getWidth();
	int hitBoxHeight = hitbox.getGameObject().getActiveAnimator().getHeight();

	int hitBoxX = hitbox.getGameObject().getPos().getX()-hitBoxWidth/2;
	int hitBoxY = hitbox.getGameObject().getPos().getY()-hitBoxHeight/2;

	int circleRadiusSquared = radius * radius;

	for (int y = hitBoxY; y < hitBoxY + hitBoxHeight; y++) {
		for (int x = hitBoxX; x < hitBoxX + hitBoxWidth; x++) {
			int dx = x - circleCenterX;
			int dy = y - circleCenterY;
			int distanceSquared = dx * dx + dy * dy;

			if (distanceSquared <= circleRadiusSquared) {
				// The pixel at (x, y) is inside the circle
				return true;
			}
		}
	}

	// If none of the pixels in the hitbox are inside the circle, return false
	return false;
}

	public Coordinate getCenter() {
		return new Coordinate(x,y);
	}

	public int getRadius() {
		return radius;
	}
}
