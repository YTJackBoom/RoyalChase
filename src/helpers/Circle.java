package helpers;

import java.awt.*;

public class Circle {
	private int x,y;
	private double radius;
	public Circle(Coordinate pos, double radius) {
		x = pos.getX();
		y = pos.getY();
		this.radius = radius;
	}

	public boolean contains(Coordinate pos) {
		int distance = (int) Math.sqrt(Math.pow(pos.getX() - x, 2) + Math.pow(pos.getY() - y, 2));
		return distance <= radius;
	}

	public void render(Graphics g) {
		g.drawOval((int)Math.round(x - radius), (int)Math.round(y - radius), (int)Math.round(2 * radius), (int)Math.round(2 * radius));
	}

	public boolean contains(HitBox hitbox) {
	// Check if any part of the given pixel-perfect hitbox is inside the circle

	int circleCenterX = x; // Assuming x and y are the center coordinates of the circle
	int circleCenterY = y; // Adjust accordingly if x and y represent top-left corner



	int hitBoxWidth = hitbox.getGameObject().getActiveAnimator().getWidth();
	int hitBoxHeight = hitbox.getGameObject().getActiveAnimator().getHeight();

	int hitBoxX = hitbox.getGameObject().getPos().getX()-hitBoxWidth/2;
	int hitBoxY = hitbox.getGameObject().getPos().getY()-hitBoxHeight/2;

	double circleRadiusSquared = radius * radius;
	if (circleRadiusSquared <= 0) return false; //Wenn der radius 0 ist, wäre es eine pixel zu pixel detection, was für bestimmte gegner fernkampf verhalten erzwingen würde -> nicht gewollt

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

	public double getRadius() {
		return radius;
	}

    public void setPos(Coordinate pos) {
		x= pos.getX();
		y=pos.getY();
    }
}
