package helpers;

import enemy.Enemy;
import towers.Tower;

public  class math {

    public static class TowerMath {
        public static boolean checkRange(Tower tower, Enemy object2) {
            if (object2 != null) {
                int towerRange = variables.Towers.getRange(tower.getType());
                Coordinate towerPos = tower.getPos();
                Coordinate enemyPos = object2.getPos();
                   //System.out.println("towerPos"+towerPos.getX()+"enemyPos"+enemyPos.getX()+"towerRange"+towerRange);
                   //System.out.println(Math.abs(object2.getPos().getY()-tower.getPos().getY()))
               if (Math.abs(enemyPos.getY() - towerPos.getY()) <= towerRange && Math.abs(enemyPos.getX() - towerPos.getX()) <= towerRange) {
                   // System.out.println("true");
                    return true;
               }else return false;

            } else return false;
        }
    }












}
