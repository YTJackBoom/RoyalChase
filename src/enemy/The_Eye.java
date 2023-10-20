package enemy;

import controllers.EnemyController;
import gameObjects.Enemy;
import gameObjects.Tower;
import helpers.AbsoluteCoordinate;

import java.util.ArrayList;

import static helpers.ObjectValues.Enemies.THE_EYE;
import static helpers.ObjectValues.Projectiles.LIGHTNINGBALL;
import static helpers.ObjectValues.Projectiles.ROCKET;

public class The_Eye extends Enemy {
    public The_Eye(EnemyController enemyController, AbsoluteCoordinate pos) {
        super(enemyController, pos, THE_EYE, EnemyAttackPattern.RANGED, EnemyType.BOSS);
    }


    @Override
    public void fire() {
        if (isLoaded && isActive && target != null) {

            ArrayList<Tower> targetList = new ArrayList<>();
            int shotNum = 3;
                for (Tower t : enemyController.getPlaying().getTowerController().getTowerList()) {
                    if (t.getType() != 0 && range.contains(t.getHitBox()) && !targetList.contains(t)) {
                        targetList.add(t);
                    }
                    if (targetList.size() >= shotNum) break;
                }

            if (!targetList.isEmpty()) {
                for (Tower t : targetList) {
                    enemyController.getPlaying().getProjectileController().spawnProjectile(this, t, LIGHTNINGBALL);
                }
                isLoaded = false;
            }
        }
    }
}
