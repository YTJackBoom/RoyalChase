package helpers;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageAnalyser {
	private ArrayList<Coordinate> pathCoordinates;
	private BufferedImage image;
	
	public ImageAnalyser(BufferedImage image) {
		pathCoordinates = new ArrayList<Coordinate>();
		this.image = image;
	}

	public ArrayList<Coordinate> imgToPath(Coordinate start) {
		if (image != null) {
			ArrayList<Coordinate> visited = new ArrayList<>();
			visited.add(start);
			ArrayList<Coordinate> targetColorPoints = new ArrayList<>();
			targetColorPoints.add(start);

			int width = image.getWidth();
			int height = image.getHeight();

			int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
			int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};

			// Define target color and a tolerance
			Color targetColor = new Color(58, 32, 19);  // Rounding the values
			int tolerance = 5;  // Adjust as necessary for minor color deviations

			while (!visited.isEmpty()) {
				Coordinate currentCoordinate = visited.remove(0);
				int currentX = currentCoordinate.getX();
				int currentY = currentCoordinate.getY();
				for (int j = 0; j < 8; j++) {
					int nextX = currentX + dx[j];
					int nextY = currentY + dy[j];
					if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height) {
						Color nextColor = new Color(image.getRGB(nextX, nextY), true);
						int alpha = nextColor.getAlpha();

						// Check if the pixel matches the target color within the specified tolerance
						if (alpha != 0 && colorMatches(nextColor, targetColor, tolerance)) {
							Coordinate nextCoordinate = new Coordinate(nextX, nextY);
							if (!targetColorPoints.contains(nextCoordinate) && !visited.contains(nextCoordinate)) {
								visited.add(nextCoordinate);
								targetColorPoints.add(nextCoordinate);
							}
						}
					}
				}
			}
			return targetColorPoints;
		}
		return null;
	}

	// Helper method to check if a color matches within a certain tolerance
	private boolean colorMatches(Color c1, Color c2, int tolerance) {
		return Math.abs(c1.getRed() - c2.getRed()) <= tolerance &&
				Math.abs(c1.getGreen() - c2.getGreen()) <= tolerance &&
				Math.abs(c1.getBlue() - c2.getBlue()) <= tolerance;
	}

}
