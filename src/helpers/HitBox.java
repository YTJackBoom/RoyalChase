package helpers;

import gameObjects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Klasse für die Pixel-Perfect Hitboxen der Objekte
 */
public class HitBox {
	private GameObject gameObject;

	public HitBox(GameObject obj) {
		gameObject = obj;
	}

	/**
	 * Methode um ein Rechteck zu bekommen, welches die Hitbox umschließt
	 * @return Rechteck, welches die Hitbox umschließt
	 */
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

	/**
	 * Methode zum Überprüfen, ob zwei Hitboxen sich schneiden, durch Pixel-Perfect detection
	 * @param other Hitbox-Objekt
	 */
	public boolean collidesWith(HitBox other) {
		if(!this.intersects(other.getBoundingBox())) {
			return false; // Frühes checken, ob die Hitboxen überhaupt schneiden
		}

		Rectangle intersection = this.getBoundingBox().intersection(other.getBoundingBox());
		return checkPixelCollision(other, intersection);
	}

	/**
	 * Check auf Pixel-Ebene, nur in der Schnittmenge der beiden Bounding-Boxen
	 * @param other Hitbox-Objekt
	 * @param intersection Rechteck, welches die Potentielle Schnittmenge der beiden Hitboxen umschließt
	 */
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

				if ((thisPixel & 0xFF000000) != 0x00 && (otherPixel & 0xFF000000) != 0x00) { //Wenn beide Pixel nicht transparent sind
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Methode zum Überprüfen, ob ein Hitbox-Objekt einen Punkt beinhaltet, durch Pixel-Perfect detection
	 * @param x x-Koordinate des Punktes
	 * @param y y-Koordinate des Punktes
	 */
	public boolean contains(int x, int y) {
		Animator animator = gameObject.getActiveAnimator();
		BufferedImage thisImage = animator.getCurrentFrame();
		AbsoluteCoordinate thisPos = gameObject.getPos();
		int thisX = thisPos.getX() - animator.getWidth() / 2;
		int thisY = thisPos.getY() - animator.getHeight() / 2;

		Rectangle boundingBox = getBoundingBox();

		if (boundingBox.contains(x, y)) { // Früher check, ob der Punkt überhaupt in der Bounding-Box ist
			int pixel = thisImage.getRGB(x - thisX, y - thisY);
			if ((pixel & 0xFF000000) != 0x00) { //wenn der pixel auf dem Punkt nicht transparent ist
				return true;
			}
		}

		return false;
	}


	public GameObject getGameObject() {
		return gameObject;
	}
}
