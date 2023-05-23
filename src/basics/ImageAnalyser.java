package basics;

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
	
	public ImageAnalyser(File PMapFile) {  
		pathCoordinates = new ArrayList<Coordinate>();
		try {
		    image = ImageIO.read(PMapFile);
		} catch (IOException e) {
		    System.out.println("Error when reading Path");
		}
	}
	
	public ArrayList<Coordinate> imgToPath() {
		if (image != null) {
			Coordinate start = getStart(image);

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
						Color nextColor = new Color(image.getRGB(nextX, nextY));
						int intensity = (nextColor.getRed() + nextColor.getGreen() + nextColor.getBlue()) / 3;
						if (intensity <= blackThreshold) {
							Coordinate nextCoordinate = new Coordinate(nextX, nextY);
							if (!blacks.contains(nextCoordinate) && !visited.contains(nextCoordinate)) {
								System.out.println(nextX + " " + nextY + "   " + visited.size() + "  " + blacks.size());
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
	
	public ArrayList<Coordinate> imgToFoundList() {
		ArrayList<Coordinate> foundList = new ArrayList<Coordinate>();
		if(image != null) {
			int blueThreshold = 255;
			for (int a = 0; a < image.getWidth(); a++) {
				for (int b = 0; b < image.getHeight(); b++) {
					int blueValue = new Color(image.getRGB(a, b)).getBlue();
					int redValue = new Color(image.getRGB(a, b)).getRed();
					if (blueValue >= blueThreshold && redValue < 100) {
						foundList.add(new Coordinate(a, b));
						System.out.println("Found blue pixel at (" + a + ", " + b + ")");
					}
				}
			}
			return foundList;
		}else {
			foundList.add(new Coordinate(0,0));
			return foundList;
		}
	}
	
	public Coordinate getStart(BufferedImage image) {
		int redThreshold = 255; 
		for(int a=0;a<image.getWidth();a++) {
		    for(int b=0;b<image.getHeight();b++) {
		        int blueValue = new Color(image.getRGB(a, b)).getBlue();
		        int redValue = new Color(image.getRGB(a, b)).getRed();
		        if(redValue >= redThreshold&&blueValue < 100) {
		            System.out.println("Found red pixel at (" + a + ", " + b + ")");

		            return new Coordinate(a, b);
		        }
		    }
		}
		return new Coordinate(0, 0);
	} 
}
