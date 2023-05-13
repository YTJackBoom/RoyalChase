package projectiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import basics.GameScreen;
import gameObjects.Coordinate;
import gameObjects.Enemy;

public class Arrow extends Projectile {
	private Coordinate pos;
	private Coordinate ziel;
	private double pSpeed;
	private int distanceX;
	private int distanceY;
	private int distance;
	private Enemy target;

	public Arrow(GameScreen gS, Coordinate start,Enemy target) {
		super(gS, start, target, new ImageIcon("res/images/black square small.png"));
		// TODO Auto-generated constructor stub
		System.out.println("arro");
		pos = start;
		ziel = target.getPos();
		pSpeed =5;
		this.target = target;
	}

	@Override
	public void refresh() {
		ziel = target.getPos();
		updatePos();
    	pLabel.setBounds(pos.getX(),pos.getY(),60,60);
    	//System.out.println("s"+pos.getX() +" " +  pos.getY());
    }
	
	private void updatePos() { //berechnung der neuen position durch winkel  zwichen den punkten. vgl Quelle 1
		double xMultiplyer;
		double yMultiplyer;
		double yDistance = Math.abs(ziel.getY()-pos.getY());
		double xDistance = Math.abs(ziel.getX()-pos.getX());
		double angel = Math.atan2(yDistance, xDistance);
		if(xDistance ==0) {yMultiplyer=2;}	//wenn auf einer coordinaten ebene --> verdopllung  des speeds
			else {yMultiplyer=1;} 
		if(yDistance ==0) {xMultiplyer=2;}
			else {xMultiplyer=1;}
		
		if(xDistance !=0&&ziel.getX()-pos.getX()>0) {
			pos.setX((pos.getX()+(int)Math.round(Math.cos(angel)*pSpeed*xMultiplyer)));
		} else if (xDistance !=0) {
			pos.setX((pos.getX()+(int)Math.round(-Math.cos(angel)*pSpeed*xMultiplyer)));	
		} 
		
		
		if(yDistance !=0&&ziel.getY()-pos.getY()>0) {
			pos.setY((pos.getY()+(int)Math.round(Math.sin(angel)*pSpeed*yMultiplyer)));
		}else if (yDistance != 0 ) {
			pos.setY((pos.getY()+(int)Math.round(-Math.sin(angel)*pSpeed*yMultiplyer)));
		}
		
	}

	@Override
	public void setPos(Coordinate pos) {
		// TODO Auto-generated method stub
		
	}
	

}



