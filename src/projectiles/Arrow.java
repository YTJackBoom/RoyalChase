package projectiles;

import controllers.ProjectileController;
import helpers.Constants;
import helpers.Coordinate;
import enemy.Enemy;
import helpers.math;

public class Arrow extends Projectile {
	private Coordinate pos;
	private double pSpeed;
	private int distanceX;
	private int distanceY;
	private int distance;
	private Enemy target;
	private ProjectileController projectileController;
	private int i=0;

	public Arrow(ProjectileController projectileController, Coordinate start, Enemy target, int type) {
		super(projectileController, start, target,type);
//		System.out.println("arro");
		pos = start;
		pSpeed =5;
		this.target = target;
		this.projectileController = projectileController;
	}

	public void update() {
		if(target != null) {
			if(i>= Constants.ObjectConstants.SPEEDOFFSET) {
				move();
				i=0;
			}else i++;

		}
    }
	
	public void move() { //berechnung der neuen position durch winkel  zwichen den punkten. vgl Quelle 1
		int[] tempArray = math.ProjectileMath.calculateArrowChange(pos, target.getPos());
		pos.setX(pos.getX()+tempArray[0]);
		pos.setY(pos.getY()+tempArray[1]);
	}

	public void setPos(Coordinate pos) {
		this.pos = pos;
	}
}



