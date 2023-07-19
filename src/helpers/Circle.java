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
//	public boolean contains(Rectangle rect) {
//		// Check if any part of the given rectangle is inside the circle
//
//		// Check if any of the four corners of the rectangle is inside the circle
//		if (contains(new Coordinate(rect.x, rect.y))
//				|| contains(new Coordinate(rect.x + rect.width, rect.y))
//				|| contains(new Coordinate(rect.x, rect.y + rect.height))
//				|| contains(new Coordinate(rect.x + rect.width, rect.y + rect.height))) {
//			return true;
//		}
//
//		// Check if any part of the rectangle's edges is inside the circle
//		for (int i = rect.x; i < rect.x + rect.width; i++) {
//			if (contains(new Coordinate(i, rect.y)) || contains(new Coordinate(i, rect.y + rect.height))) {
//				return true;
//			}
//		}
//		for (int j = rect.y; j < rect.y + rect.height; j++) {
//			if (contains(new Coordinate(rect.x, j)) || contains(new Coordinate(rect.x + rect.width, j))) {
//				return true;
//			}
//		}
//
//		// If none of the above conditions are met, return false
//		return false;

//	}
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
}
