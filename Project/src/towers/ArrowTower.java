package towers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.Target;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import basics.GameScreen;
import gameObjects.Coordinate;
import gameObjects.Enemy;
import projectiles.Arrow;

public class ArrowTower extends Tower {
	private int cooldown = 20;
	private int range = 100;
	double counter;
	private Timer t;
	private boolean isLoaded;
	private boolean isFiring;
	private Enemy target;
	private GameScreen gS;
	private ArrayList<Arrow> pL = new ArrayList<Arrow>(); // ArrayList für projectile hier: arrows

	public ArrowTower(GameScreen gS, Coordinate pos, int type, ArrayList towerEntityList, int ArrayPos) {
		super(gS, pos, 35, 35, type, towerEntityList, ArrayPos, new ImageIcon(imgP2));
		super.range = 500;
		this.gS = gS;
		/*
		 * towerLabel = new JLabel(towerPassive);
		 * towerLabel.setBounds(pos.getX(),pos.getY(),60,60);
		 * 
		 * gS.add(towerLabel); towerLabel.setVisible(true);
		 
		t = new Timer(17, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println(isLoaded);
				if (counter - 100 == 0) {
					isLoaded = true;
					counter = 0;
				} else
					counter++;
				fire();
				reloadpL();
				// System.out.println("isFirin "+isFiring);
			}
		});
		t.stop();
	*/
	}
	
	public void refresh() {
		if (counter - cooldown == 0) {
			isLoaded = true;
			counter = 0;
		} else counter++;
		System.out.println(counter);
		fire();
		reloadpL();
	}

	@Override
	public void fire() {
		if (isLoaded && isFiring) {
			pL.add(new Arrow(gS, new Coordinate(getPos().getX(), getPos().getY()), target)); // target mitgabe, um
																								// target zu damagen
			isLoaded = false;
		}
	}

	public void reloadpL() {
		if (pL.size() != 0) {
			for (int i = 0; i < pL.size(); i++) {
				pL.get(i).refresh();
				checkCollision(i);   				//zumsparen einer for-schleife hier und nicht timer
			}
		}
	}

	public void checkCollision(int i) { // Collisionscheck kugel u. gegner + löschen u damage
		int distanceY = target.getPos().getY() - pL.get(i).getPos().getY();
		int distanceX = target.getPos().getX() - pL.get(i).getPos().getX();
		if (Math.abs(distanceX)<=3&&Math.abs(distanceY)<=3) {
			pL.get(i).vanish();
			pL.remove(i);
		}
		
	}

	public boolean getStatus() {
		return isFiring;
	}

	@Override
	public void changeStatus(boolean status) {
		isFiring = status;

	}

	@Override
	public void setTarget(Enemy target) {
		this.target = target;
	}

	@Override
	public void setPos(Coordinate pos) {
		// TODO Auto-generated method stub
		
	}

}
