package helpers;

import gameObjects.GameObject;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class PixelPerfectHitbox {
//	private Animator animator;
//
//	public PixelPerfectHitbox(GameObject obj) {
//		this.animator = obj.getAnimator();
//	}
//
//	public boolean collidesWith(PixelPerfectHitbox other) {
//		BufferedImage thisImage = animator.getCurrentImage();
//		BufferedImage otherImage = other.animator.getCurrentImage();
//
//		int thisX = animator.getX();
//		int thisY = animator.getY();
//		int otherX = other.animator.getX();
//		int otherY = other.animator.getY();
//
//		Rectangle thisRect = new Rectangle(thisX, thisY, thisImage.getWidth(), thisImage.getHeight());
//		Rectangle otherRect = new Rectangle(otherX, otherY, otherImage.getWidth(), otherImage.getHeight());
//
//		Rectangle intersection = thisRect.intersection(otherRect);
//
//		for (int y = intersection.y; y < intersection.y + intersection.height; y++) {
//			for (int x = intersection.x; x < intersection.x + intersection.width; x++) {
//				int thisPixel = thisImage.getRGB(x - thisX, y - thisY);
//				int otherPixel = otherImage.getRGB(x - otherX, y - otherY);
//
//				if ((thisPixel & 0xFF000000) != 0x00 && (otherPixel & 0xFF000000) != 0x00) {
//					return true;
//				}
//			}
//		}
//
//		return false;
//	}
}
