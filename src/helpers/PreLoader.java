package helpers;

import java.util.ArrayList;

public class PreLoader {
    public ArrayList<Animator> enemyActiveAnimators,enemyPassiveAnimators, towerActiveTurretAnimators, towerPassiveTurretAnimators,towerBaseAnimators, projectileAnimators,buildingsAnimators;
    public PreLoader() {
        initVars();
        preLoadAnimators();
    }
    public void initVars() {
        enemyActiveAnimators = new ArrayList<Animator>();
        enemyPassiveAnimators = new ArrayList<Animator>();
        towerActiveTurretAnimators = new ArrayList<Animator>();
        towerPassiveTurretAnimators = new ArrayList<Animator>();
        towerBaseAnimators = new ArrayList<Animator>();

        projectileAnimators = new ArrayList<Animator>();
        buildingsAnimators = new ArrayList<Animator>();

    }

    public void preLoadAnimators() {
//        System.out.println( variables.Enemies.getNumberOfIntDeclarations());
        for(int i = variables.Enemies.getLowestInt(); i < variables.Enemies.getNumberOfIntDeclarations();i++) {
            enemyActiveAnimators.add(new Animator(variables.Enemies.getEnemyActiveGifPath(i)));
            enemyPassiveAnimators.add(new Animator(variables.Enemies.getEnemyPassiveGifPath(i)));
        }
        for(int i =variables.Towers.getLowestInt() ; i < variables.Towers.getNumberOfIntDeclarations();i++) {
            towerActiveTurretAnimators.add(new Animator(variables.Towers.getTowerActiveGifPath(i)));
            towerPassiveTurretAnimators.add(new Animator(variables.Towers.getTowerPassiveGifPath(i)));
            towerBaseAnimators.add(new Animator(variables.Towers.getTowerBaseGifFile(i)));
        }
        for(int i = variables.Projectiles.getLowestInt(); i < variables.Projectiles.getNumberOfIntDeclarations();i++) {
            projectileAnimators.add(new Animator(variables.Projectiles.getProjectileGifPath(i)));
        }
        for(int i = variables.Buildings.getLowestInt(); i <variables.Buildings.getNumberOfIntDeclarations();i++) {
            buildingsAnimators.add(new Animator(variables.Buildings.getBuidingGifFile(i)));
        }
    }

    public Animator getEnemyActiveAnimator(int index) {
    	return enemyActiveAnimators.get(index);
    }
    public Animator getEnemyPassiveAnimator(int index) {
    	return enemyPassiveAnimators.get(index);
    }
    public Animator getTowerActiveAnimator(int index) {
    	return towerActiveTurretAnimators.get(index);
    }
    public Animator getTowerPassiveAnimator(int index) {
    	return towerPassiveTurretAnimators.get(index);
    }
    public Animator getTowerBaseAnimator(int index) {return towerBaseAnimators.get(index);}
    public Animator getProjectileAnimator(int index) {
    	return projectileAnimators.get(index);
    }
    public Animator getBuildingAnimator(int index) {
        return buildingsAnimators.get(index);
    }
}
