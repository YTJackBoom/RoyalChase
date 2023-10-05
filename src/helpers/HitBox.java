package helpers;

import gameObjects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HitBox {
	private GameObject gameObject;

	public HitBox(GameObject obj) {
		gameObject = obj;
	}

	public Rectangle getBoundingBox() {
		Animator animator = gameObject.getActiveAnimator();
		BufferedImage thisImage = animator.getCurrentFrame();
		AbsoluteCoordinate thisPos = gameObject.getPos();
		int thisX = thisPos.getX() - animator.getWidth() / 2;
		int thisY = thisPos.getY() - animator.getHeight() / 2;
		return new Rectangle(thisX, thisY, thisImage.getWidth(), thisImage.getHeight());
	}

	public boolean intersects(Rectangle otherRect) {
		return getBoundingBox().intersects(otherRect);
	}

	public boolean collidesWith(HitBox other) {
		if(!this.intersects(other.getBoundingBox())) {
			return false; // Early exit if bounding boxes don't intersect.
		}

		Rectangle intersection = this.getBoundingBox().intersection(other.getBoundingBox());
		return checkPixelCollision(other, intersection);
	}

	private boolean checkPixelCollision(HitBox other, Rectangle intersection) {
		Animator animator = gameObject.getActiveAnimator();
		BufferedImage thisImage = animator.getCurrentFrame();
		AbsoluteCoordinate thisPos = gameObject.getPos();
		int thisX = thisPos.getX() - animator.getWidth() / 2;
		int thisY = thisPos.getY() - animator.getHeight() / 2;

		Animator otherAnimator = other.getGameObject().getActiveAnimator();
		BufferedImage otherImage = otherAnimator.getCurrentFrame();
		AbsoluteCoordinate otherPos = other.getGameObject().getPos();
		int otherX = otherPos.getX() - otherAnimator.getWidth() / 2;
		int otherY = otherPos.getY() - otherAnimator.getHeight() / 2;

		for (int y = intersection.y; y < intersection.y + intersection.height; y++) {
			for (int x = intersection.x; x < intersection.x + intersection.width; x++) {
				int thisPixel = thisImage.getRGB(x - thisX, y - thisY);
				int otherPixel = otherImage.getRGB(x - otherX, y - otherY);

				if ((thisPixel & 0xFF000000) != 0x00 && (otherPixel & 0xFF000000) != 0x00) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean contains(int x, int y) {
		Animator animator = gameObject.getActiveAnimator();
		BufferedImage thisImage = animator.getCurrentFrame();
		AbsoluteCoordinate thisPos = gameObject.getPos();
		int thisX = thisPos.getX() - animator.getWidth() / 2;
		int thisY = thisPos.getY() - animator.getHeight() / 2;

		Rectangle thisRect = new Rectangle(thisX, thisY, thisImage.getWidth(), thisImage.getHeight());

		// Step 1: Check if (x, y) is inside the HitBox's rectangle
		if (thisRect.contains(x, y)) {
			// Step 2: Check pixel transparency
			int pixel = thisImage.getRGB(x - thisX, y - thisY);
			if ((pixel & 0xFF000000) != 0x00) { // Checking if alpha is not 0 (transparent)
				return true;
			}
		}

		return false;
	}


	public GameObject getGameObject() {
		return gameObject;
	}
}
