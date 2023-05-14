package basics;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;



public class GameWindow extends JFrame{

	private GameScreen gamescreen;
	public GameWindow() {
		
		//Spielfeld erzeugen
		gamescreen = new GameScreen(this);		
		
		registerWindowListener();    // WindowListener registrieren (z.B. Schlie�en des Fensters)
		
		add(gamescreen);  //Hinzuf�gen des Spielfeldes zum SpielFenster ; (add() erben alle von Container)
		pack();  //Ideale Gr��e berechnen
		
		this.setTitle("s Ball");
		this.setLocation(10,10); //Linke obere Fensterecke festlegen
		this.setResizable(false);
		this.setVisible(true);
		
		repaint();
	}	

	private void registerWindowListener() {        
	    addWindowListener(new WindowAdapter() {  
	    	//Hier wird von der Abstrakten Klasse WindowAdapter, einer abstrakten Klasse,
	    	//abgeleitet, die Fensterereignisse empf�ngt.
			//Es m�ssen nur ben�tigte Methoden der Klasse ausprogrammiert werden (sonst bleiben sie leer).
	        @Override
	        public void windowClosing(WindowEvent e) { 	        	
	        	System.exit(0); 
	        }
	        @Override
	        public void windowDeactivated(WindowEvent e) {
	            // hier k�nnen wir unser Spiel pausieren                
	        }
	        @Override
	        public void windowActivated(WindowEvent e) {
	            // hier k�nnen wir unser Spiel wieder fortsetzen
	        }            
	    });        
	}

	public GameWindow getGameWindow() {
		return this;
	}
}
