package projectiles;


import javax.swing.JPanel;

import basics.Animator;
import basics.Direction;
import controllers.ProjectileController;
import helpers.Coordinate;
import enemy.Enemy;
import helpers.variables;


public abstract class Projectile {
	private Coordinate pos;
	private JPanel gS;
	private int type;
	private Animator animator;
	private Enemy target;
	private ProjectileController projectileController;


	public Projectile(ProjectileController projectileController, Coordinate start, Enemy target, int type) {  // TODO: eine "animaion" pro richtung, prob in animation selber. wie enemys und towers verbessern
		this.pos = start;
		this.gS = gS;
		this.type = type;
		this.target = target;
		this.projectileController = projectileController;
		initAnimator();
	}

	private void initAnimator() {
		animator = projectileController.getPlaying().getGame().getPreLoader().getProjectileAnimator(type);

	}
	public Coordinate getPos() {
		return pos;
	}

	public abstract void update();

	public Animator getAnimator() {
		return animator;
	}
	public Enemy getTarget() {return target;}
	public int getType() {return type;}

	public int getDamage() {
		return variables.Projectiles.getProjectileDamage(type);
	}
	public void removeAnimators() {
		animator = null;
	}
}
