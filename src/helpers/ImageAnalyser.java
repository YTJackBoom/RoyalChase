package helpers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import helpers.Coordinate;

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
			ArrayList<Coordinate> blacks = new ArrayList<>();
			blacks.add(start);

			int width = image.getWidth();
			int height = image.getHeight();

			int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
			int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};

			int blackThreshold = 50; // adjust this value to change the threshold
			while (!visited.isEmpty()) {
				Coordinate currentCoordinate = visited.remove(0);
				int currentX = currentCoordinate.getX();
				int currentY = currentCoordinate.getY();
				for (int j = 0; j < 8; j++) {
					int nextX = currentX + dx[j];
					int nextY = currentY + dy[j];
					if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height) {
						Color nextColor = new Color(image.getRGB(nextX, nextY), true);
						int intensity = (nextColor.getRed() + nextColor.getGreen() + nextColor.getBlue()) / 3;
						int alpha = nextColor.getAlpha();

						// Check for non-transparent pixels and if they are black
						if (alpha != 0 && intensity <= blackThreshold) {
							Coordinate nextCoordinate = new Coordinate(nextX, nextY);
							if (!blacks.contains(nextCoordinate) && !visited.contains(nextCoordinate)) {
								visited.add(nextCoordinate);
								blacks.add(nextCoordinate);
							}
						}
					}
				}
			}
			return blacks;
		}
		return null;
	}
}
