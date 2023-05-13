package projectiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import basics.GameScreen;
import gameObjects.Coordinate;
import gameObjects.Enemy;
import gameObjects.GameObject;

public abstract class Projectile extends GameObject{
	protected JLabel pLabel;
	private Coordinate pos;
	private ImageIcon pIcon;
	private GameScreen gS;
	
	public Projectile(GameScreen gS, Coordinate start, Enemy target, ImageIcon pIcon) {
		super(gS ,start, 0, 0, 0, 0);
		this.pos = start;
		this.gS = gS;
		pLabel = new JLabel(pIcon);
		pLabel.setBounds(pos.getX(),pos.getY(),60,60);

		gS.add(pLabel);
		gS.setComponentZOrder(pLabel,0);
		pLabel.setVisible(true);
		
	}

	@Override
	public void vanish() {
		pLabel.setVisible(false);
		gS.remove(pLabel);
		pLabel = null;
	}
	public abstract void refresh();

}
