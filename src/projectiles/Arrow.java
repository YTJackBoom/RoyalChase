package projectiles;

import controllers.ProjectileController;
import helpers.Coordinate;
import enemy.Enemy;

public class Arrow extends Projectile {
	private Coordinate pos;
	private Coordinate ziel;
	private double pSpeed;
	private int distanceX;
	private int distanceY;
	private int distance;
	private Enemy target;
	private ProjectileController projectileController;
	private int i=0;

	public Arrow(ProjectileController projectileController, Coordinate start, Enemy target, int type) {
		super(projectileController, start, target,type);
		System.out.println("arro");
		pos = start;
		ziel = target.getPos();
		pSpeed =5;
		this.target = target;
		this.projectileController = projectileController;
	}

	public void update() {
		if(target != null) {
			ziel = target.getPos();
			if(i>=projectileController.getPlaying().getGame().getSpeedOffset()) {
				move();
				i=0;
			}else i++;

		}
    }
	
	public void move() { //berechnung der neuen position durch winkel  zwichen den punkten. vgl Quelle 1

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

	public void setPos(Coordinate pos) {
		this.pos = pos;
	}
}



