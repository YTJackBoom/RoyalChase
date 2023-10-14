package helpers;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Klasse zum Analysieren von Bildern
 */
public class ImageAnalyser {
	private ArrayList<AbsoluteCoordinate> pathAbsoluteCoordinates;
	private BufferedImage image;
	private final Color targetColor = new Color(58, 32, 19);  // Farb-Wert des Pfades
	private final int tolerance = 5;  // Toleranz, in der die Farbe noch als gleich angesehen wird



	public ImageAnalyser(BufferedImage image) {
		pathAbsoluteCoordinates = new ArrayList<AbsoluteCoordinate>();
		this.image = image;
	}

	/**
	 * Methode um den Pfad der Pixel einer bestimmten farbe durch das Bild zu finden, ausgehend von einem Startpunkt
	 * @param start Startpunkt
	 * @return Pfad duch das Bild
	 */
	public ArrayList<AbsoluteCoordinate> imgToPath(AbsoluteCoordinate start) {
		if (image != null) {
			start = findStartingPixel(start);

			ArrayList<AbsoluteCoordinate> visited = new ArrayList<>();
			visited.add(start);
			ArrayList<AbsoluteCoordinate> targetColorPoints = new ArrayList<>();
			targetColorPoints.add(start);

			int width = image.getWidth();
			int height = image.getHeight();

			int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1}; // alle 8 Nachbar-Pixel, welche geprüft werden müssen
			int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};


			while (!visited.isEmpty()) {
				AbsoluteCoordinate currentAbsoluteCoordinate = visited.remove(0);
				int currentX = currentAbsoluteCoordinate.getX();
				int currentY = currentAbsoluteCoordinate.getY();
				for (int j = 0; j < 8; j++) {
					int nextX = currentX + dx[j];
					int nextY = currentY + dy[j];
					if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height) {
						Color nextColor = new Color(image.getRGB(nextX, nextY), true);
						int alpha = nextColor.getAlpha();

						// Wenn das Pixel nicht transparent ist und die Farbe passt, wird es zum Pfad hinzugefügt
						if (alpha != 0 && colorMatches(nextColor, targetColor, tolerance)) {
							AbsoluteCoordinate nextAbsoluteCoordinate = new AbsoluteCoordinate(nextX, nextY);
							if (!targetColorPoints.contains(nextAbsoluteCoordinate) && !visited.contains(nextAbsoluteCoordinate)) {
								visited.add(nextAbsoluteCoordinate);
								targetColorPoints.add(nextAbsoluteCoordinate);
							}
						}
					}
				}
			}
			return targetColorPoints;
		}
		return null;
	}

	/**
	 * Methode um den die erste coordinate in einem Bild zu finden, welche die Farbe des Pfades hat, suche startet von start in einem Rechteckingen Bereich, benötigt für hauptsächlich spawn tiles
	 * @param start Startpunkt
	 * @return Erste coordinate mit der Farbe des Pfades
	 */
	private AbsoluteCoordinate findStartingPixel(AbsoluteCoordinate start) {
		if (start.getX() < 0 || start.getX() >= image.getWidth() || start.getY() < 0 || start.getY() >= image.getHeight()) {
			throw new IllegalArgumentException("The provided starting coordinate is out of the image's boundaries. X = " + start.getX() + ", Y = " + start.getY() + ", image width = " + image.getWidth() + ", image height = " + image.getHeight() + ".");
		}

		Color initialColor = new Color(image.getRGB(start.getX(), start.getY()), true);

		if (!colorMatches(initialColor, targetColor, tolerance)) {
			int radius = 1;
			boolean found = false;

			while (!found) {
				for (int i = -radius; i <= radius; i++) {
					for (int j = -radius; j <= radius; j++) {
						if (i == -radius || i == radius || j == -radius || j == radius) {
							int x = start.getX() + i;
							int y = start.getY() + j;

							if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
								Color currentColor = new Color(image.getRGB(x, y), true);
								if (colorMatches(currentColor, targetColor, tolerance)) {
									return new AbsoluteCoordinate(x, y);
								}
							}
						}
					}
				}
				radius++;
			}
		}
		return start;
	}

	/**
	 * Methode zum Überprüfen, ob die Farben zweier Pixel übereinstimmen
	 * @param c1 Farbe 1
	 * @param c2 Farbe 2
	 * @param tolerance Toleranz, in der die Farbe noch als gleich angesehen wird
	 */
	private boolean colorMatches(Color c1, Color c2, int tolerance) {
		return Math.abs(c1.getRed() - c2.getRed()) <= tolerance &&
				Math.abs(c1.getGreen() - c2.getGreen()) <= tolerance &&
				Math.abs(c1.getBlue() - c2.getBlue()) <= tolerance;
	}

}
