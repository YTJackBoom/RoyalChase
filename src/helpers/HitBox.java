package helpers;

import gameObjects.GameObject;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class HitBox {
	private GameObject gameObject;

	public HitBox(GameObject obj) {
		gameObject = obj;
	}

	public boolean collidesWith(HitBox other) {
		Animator animator = gameObject.getActiveAnimator();
		BufferedImage thisImage = animator.getCurrentImage();

		Animator otherAnimator = other.getGameObject().getActiveAnimator();
		BufferedImage otherImage = otherAnimator.getCurrentImage();

		Coordinate thisPos = gameObject.getPos();
		Coordinate otherPos = other.getGameObject().getPos();
		int thisX = thisPos.getX() - animator.getWidth() / 2;
		int thisY = thisPos.getY() - animator.getHeight() / 2;

		int otherX = otherPos.getX() - otherAnimator.getHeight() / 2;
		int otherY = otherPos.getY() - otherAnimator.getHeight() / 2;

		Rectangle thisRect = new Rectangle(thisX, thisY, thisImage.getWidth(), thisImage.getHeight());
		Rectangle otherRect = new Rectangle(otherX, otherY, otherImage.getWidth(), otherImage.getHeight());

		Rectangle intersection = thisRect.intersection(otherRect);

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

	public GameObject getGameObject() {
		return gameObject;
	}
}
