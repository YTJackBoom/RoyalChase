package helpers;

import java.awt.*;

/**
 * Klasse für die Konstanten
 */
public class Constants {
	public static class UIConstants {
		public static final int CANTAFFORDTIMEONSCREEN = 5; //in seconds
		public static final int RECENTLYSOLDTIMEONSCREEN = 5; //seconds

		public static final int SUCCESFULLSAVETIMEONSCREEN = 5; //in seconds

		public static final Font CANTAFFORDFONT = new Font("TimesRoman", Font.PLAIN, 50);
		public static final Font TOWERCOSTFONT = new Font("TimesRoman", Font.PLAIN, 20);
		public static final Font SUCCESFULLSAVEFONT = new Font("TimesRoman", Font.PLAIN, 50);
		public static final Font RECENTLYSOLDFONT = new Font("TimesRoman", Font.PLAIN, 50);
		public static final Font TOWERLEVELFONT = new Font("TimesRoman", Font.PLAIN, 50);
		public static final Font DIALOGFONT = new Font("Arial", Font.PLAIN, 20);
		public static final Font BUTTONFONT = new Font("TimesRoman", Font.PLAIN, 15);

		public static final int NUMBEROFRANGECIRCLES = 5;


		//Colors
		public static final Color TOWERCANTAFFORDCOLOR = new Color(Color.RED.getRGB());
		public static final Color TOWERCANAFFORDCOLOR = new Color(Color.GREEN.getRGB());
		public static final Color TOWERLEVELCOLOR = new Color(Color.GREEN.getRGB());
		public static final Color TOWERMAXEDLEVELCOLOR = new Color(103, 38, 141);
		public static final int TOWERSCALEFACTOR = 2;


		public static final float CIRCLESTHICKNESS = 2.5f;

	}

	public static class ObjectConstants {
		public static final int SPEEDOFFSET = 1; //delay for certasin speed adjustments
		public static final int EXPLOSIONRADIUS = 75;
		public static final double EXPLOSIONLIFETIME = 1;
		public static final double EXPLOSIONBORDERDAMAGEPERCET = 0.75;

		public static final int TOWERMAXLEVEL = 10;
		public static final double BASEUPGRADEMULTIPLIER = 0.5;

		public static final double DMGUPGRADE = 0.15;
		public static final double SPEEDUPGRADE = 0.15;

		public static final double LOWESTRELOADSPEED = 300;


        public static final double MELEEATTACKDISTANCE = 15;
        public static final double HEALTHUPGRADE = 1.5;

    }
	public static class OtherConstants {
		public static final String SAVEGAMELOCATION ="res/savegame";

        public static final double SELLALLTOWERSPERCENT = 0.5;
		public static final double REPLACETOWERPERCENT = 0.75;
    }
}
