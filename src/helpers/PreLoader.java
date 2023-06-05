package helpers;

import basics.Animator;

import java.util.ArrayList;

public class PreLoader {
    public ArrayList<Animator> enemyActiveAnimators,enemyPassiveAnimators,towerActiveAnimators,towerPassiveAnimators,projectileAnimators;
    public PreLoader() {
        initVars();
        preLoadAnimators();
    }
    public void initVars() {
        enemyActiveAnimators = new ArrayList<Animator>();
        enemyPassiveAnimators = new ArrayList<Animator>();
        towerActiveAnimators = new ArrayList<Animator>();
        towerPassiveAnimators = new ArrayList<Animator>();
        projectileAnimators = new ArrayList<Animator>();
    }

    public void preLoadAnimators() {
        System.out.println( variables.Enemies.getNumberOfIntDeclarations());
        for(int i = 0; i < variables.Enemies.getNumberOfIntDeclarations();i++) {
            enemyActiveAnimators.add(new Animator(variables.Enemies.getEnemyActiveGifPath(i)));
            enemyPassiveAnimators.add(new Animator(variables.Enemies.getEnemyPassiveGifPath(i)));
        }
        for(int i = 0; i < variables.Towers.getNumberOfIntDeclarations();i++) {
            towerActiveAnimators.add(new Animator(variables.Towers.getTowerActiveGifPath(i)));
            towerPassiveAnimators.add(new Animator(variables.Towers.getTowerPassiveGifPath(i)));
        }
        for(int i = 0; i < variables.Projectiles.getNumberOfIntDeclarations();i++) {
            projectileAnimators.add(new Animator(variables.Projectiles.getProjectileGifPath(i)));
        }
    }

    public Animator getEnemyActiveAnimator(int index) {
    	return enemyActiveAnimators.get(index);
    }
    public Animator getEnemyPassiveAnimator(int index) {
    	return enemyPassiveAnimators.get(index);
    }
    public Animator getTowerActiveAnimator(int index) {
    	return towerActiveAnimators.get(index);
    }
    public Animator getTowerPassiveAnimator(int index) {
    	return towerPassiveAnimators.get(index);
    }
    public Animator getProjectileAnimator(int index) {
    	return projectileAnimators.get(index);
    }
}
