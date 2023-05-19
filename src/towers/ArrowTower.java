package towers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import gameObjects.Coordinate;
import enemy.Enemy;
import projectiles.Arrow;

public class ArrowTower extends Tower {
	private int cooldown = 20;
	private int range = 100;
	double counter;
	private Timer t;
	private boolean isLoaded;
	private boolean isFiring;
	private Enemy target;
	private JPanel gS;
	private ArrayList<Arrow> pL = new ArrayList<Arrow>(); // ArrayList für projectile hier: arrows

	public ArrowTower(JPanel sPanel, Coordinate pos, int type, ArrayList towerEntityList, int ArrayPos) {
		super(sPanel, pos, 35, 35, type, towerEntityList, ArrayPos, getBufferedImage());
		super.range = 500;
		this.gS = sPanel;
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

	public void setPos(Coordinate pos) {
		// TODO Auto-generated method stub
		
	}
	public static BufferedImage getBufferedImage() {
		try {
			return ImageIO.read(new File(imgP2));
		} catch (IOException e) {
			System.out.println(e);
			System.out.print(" Error in ArrowTower");
		}
		return null;
	}

}
