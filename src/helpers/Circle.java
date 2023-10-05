package helpers;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Circle {
	private int x,y;
	private double radius;
	public Circle(AbsoluteCoordinate pos, double radius) {
		x = pos.getX();
		y = pos.getY();
		this.radius = radius;
	}

	public boolean contains(AbsoluteCoordinate pos) {
		int distance = (int) Math.sqrt(Math.pow(pos.getX() - x, 2) + Math.pow(pos.getY() - y, 2));
		return distance <= radius;
	}

	public void render(Graphics g) {
		if (g instanceof Graphics2D) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(Constants.UIConstants.CIRCLESTHICKNESS)); // thickness is the desired line width, e.g., 3.0f
			g2d.drawOval((int) Math.round(x - radius), (int) Math.round(y - radius), (int) Math.round(2 * radius), (int) Math.round(2 * radius));
		}

//		g.drawOval((int)Math.round(x - radius), (int)Math.round(y - radius), (int)Math.round(2 * radius), (int)Math.round(2 * radius));
	}

	public boolean contains(HitBox hitbox) {
		Rectangle hitBoxRect = hitbox.getBoundingBox();

		if (!this.intersects(hitBoxRect)) {
			return false; // Early exit if bounding box doesn't intersect with the circle
		}

		BufferedImage hitboxImage = hitbox.getGameObject().getActiveAnimator().getCurrentFrame();
		for (int y = 0; y < hitboxImage.getHeight(); y++) {
			for (int x = 0; x < hitboxImage.getWidth(); x++) {
				int pixel = hitboxImage.getRGB(x, y);
				if ((pixel & 0xFF000000) != 0x00) { // If the pixel is not transparent
					int globalX = x + hitBoxRect.x;
					int globalY = y + hitBoxRect.y;
					int dx = globalX - this.x;
					int dy = globalY - this.y;
					int distanceSquared = dx * dx + dy * dy;
					if (distanceSquared <= radius * radius) {
						return true; // Pixel is inside the circle
					}
				}
			}
		}
		return false; // No pixel was found inside the circle
	}



	private boolean intersects(Rectangle rect) {
		int circleDistanceX = Math.abs(this.x - rect.x - rect.width / 2);
		int circleDistanceY = Math.abs(this.y - rect.y - rect.height / 2);

		if (circleDistanceX > (rect.width / 2 + this.radius)) {
			return false;
		}
		if (circleDistanceY > (rect.height / 2 + this.radius)) {
			return false;
		}

		if (circleDistanceX <= (rect.width / 2)) {
			return true;
		}
		if (circleDistanceY <= (rect.height / 2)) {
			return true;
		}

		int cornerDistanceSquared = (circleDistanceX - rect.width / 2) * (circleDistanceX - rect.width / 2) +
				(circleDistanceY - rect.height / 2) * (circleDistanceY - rect.height / 2);

		return cornerDistanceSquared <= (this.radius * this.radius);
	}


	public AbsoluteCoordinate getCenter() {
		return new AbsoluteCoordinate(x, y);
	}

	public double getRadius() {
		return radius;
	}

	public void setPos(AbsoluteCoordinate pos) {
		x = pos.getX();
		y = pos.getY();
	}
}
