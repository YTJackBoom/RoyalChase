package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import gameObjects.Tower;
import helpers.AbsoluteCoordinate;

import java.util.ArrayList;
import java.util.HashSet;

import static helpers.ObjectValues.Enemies.ZOMBIE_GIANT;
import static helpers.ObjectValues.Projectiles.ROCKET;

public class Zombie_Giant extends Enemy {
    public Zombie_Giant(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, ZOMBIE_GIANT, EnemyAttackPattern.RANGED, EnemyType.BOSS);
    }

    @Override
    public void fire() {
        if (isLoaded && isActive && target != null) {

            ArrayList<Tower> targetList = new ArrayList<>();
            int missileNum = 2;

            // First loop: Prioritize towers where getType() == 0
            for (Tower t : enemyController.getPlaying().getTowerController().getTowerList()) {
                if (t.getType() == 0 && range.contains(t.getHitBox()) && !targetList.contains(t)) {
                    targetList.add(t);
                }
                if (targetList.size() >= missileNum) break;
            }

            // Second loop: Target the remaining towers if there's still space in targetList
            if (targetList.size() < missileNum) {
                for (Tower t : enemyController.getPlaying().getTowerController().getTowerList()) {
                    if (t.getType() != 0 && range.contains(t.getHitBox()) && !targetList.contains(t)) {
                        targetList.add(t);
                    }
                    if (targetList.size() >= missileNum) break;
                }
            }

            if (!targetList.isEmpty()) {
                for (Tower t : targetList) {
                    enemyController.getPlaying().getProjectileController().spawnProjectile(this, t, ROCKET);
                }
                isLoaded = false;
            }
        }
    }

}
