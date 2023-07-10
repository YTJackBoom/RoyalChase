package helpers;

import java.awt.*;

public class Constants {
	public static class UIConstants {
		public static final int CANTAFFORDTIMEONSCREEN = 5; //in seconds
		public static final Font CANTAFFORDFONT = new Font("TimesRoman", Font.PLAIN, 50);
		public static final Font TOWERCOSTFONT = new Font("TimesRoman", Font.PLAIN, 20);

		public static final int NUMBEROFRANGECIRCLES = 5;


		//Colors
		public static final Color TOWERCANTAFFORDCOLOR = new Color(Color.RED.getRGB());
		public static final Color TOWERCANAFFORDCOLOR = new Color(Color.GREEN.getRGB());



	}
	public static class ObjectConstants {
		public static final int SPEEDOFFSET = 1; //delay for certasin speed adjustments
		public static final int EXPLOSIONRADIUS = 500;
		public static final double UPGRADEMULTIPLYER = 0.5;
	}
}
