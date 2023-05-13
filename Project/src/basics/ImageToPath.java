package basics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

import gameObjects.Coordinate;

public class ImageToPath {
	private ArrayList<Coordinate> pathCoordinates;
	private BufferedImage image;
	
	public ImageToPath() {  
		pathCoordinates = new ArrayList<Coordinate>();
	}
	
	public ArrayList<Coordinate> imgToPath(String imagePath) {
		try {
		    image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
		    System.out.println("Error when reading Path");
		}
		Coordinate start = new Coordinate(110, 61);

		ArrayList<Coordinate> visited = new ArrayList<>();
		visited.add(start);
		ArrayList<Coordinate> blacks = new ArrayList<>();
		blacks.add(start);

		int width = image.getWidth();
		int height = image.getHeight();

		int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1, -1};
		int[] dy = {0, -1, -1, -1, 0, 1, 1, 1, 0};
		
		while (!visited.isEmpty()) {
		    Coordinate currentCoordinate = visited.remove(0);
		    int currentX = currentCoordinate.getX();
		    int currentY = currentCoordinate.getY();
		    for (int j = 0; j < 9; j++) {
		        int nextX = currentX + dx[j];
		        int nextY = currentY + dy[j];
		        if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height) {
		            int rgb = image.getRGB(nextX, nextY);
		            if (rgb == 0xFF000000) {
		                Coordinate nextCoordinate = new Coordinate(nextX, nextY);
		                if (!blacks.contains(nextCoordinate)) {
		                    visited.add(nextCoordinate);
		                    blacks.add(nextCoordinate);
		                    System.out.println(nextX + " " + nextY);
		                }
		            }
		        }
		    }
		}
		return blacks;
	}
       
        
        
        
        
        
        
        
        
        
        
      /*  
        
        
        
        diagonalLenght = Math.sqrt(image.getHeight()*image.getHeight() + image.getWidth()*image.getWidth());
        while(diagonalIndex <diagonalLenght) {
        	System.out.println(diagonalIndex);
        for(int a=0;a<diagonalIndex-1;a++) {
        	for(int b=0;b<diagonalIndex-1;b++) {
        		int color = image.getRGB(a, b);
        		if(color==0xFF000000) {
        			pathCoordinates.add(new Coordinate(a,b));
        			//System.out.println(a+" "+b);
        		}
        		
        	}
        }
        diagonalIndex++;
        }
        return pathCoordinates;
        
        
        
   /*     for (int x=0;x<image.getWidth();x++ ) {
        	for (int y=0;y<image.getHeight();y++) {
        		int color = image.getRGB(x, y);
        		if(color == 0xFF000000) {
        			pathCoordinates.add(new Coordinate(x,y));
        			System.out.println(x+" "+y);
        		}
        	}
        }
		
		return pathCoordinates;
		

	*/
}
