package helpers;

import gameObjects.Enemy;
import gameObjects.ObjectType;
import gameObjects.Tower;

public  class math {

    public static class TowerMath {
        public static boolean checkRange(Tower tower, Enemy object2) {
            if (object2 != null) {
                int towerRange = variables.Towers.getTowerRange(tower.getType());
                Coordinate towerPos = tower.getPos();
                Coordinate object2Pos = object2.getPos();
                double distance = math.GeneralMath.calculateDistance(towerPos,object2Pos);

                return distance <= towerRange;

            } else return false;
        }
    }

    public static class ProjectileMath {
        private static long previousTime;
        public static int[] calculateArrowChange(Coordinate pos1, Coordinate pos2, double speed) { // int[0] = x ; 1 = y //berechnung der neuen position durch winkel  zwichen den punkten. vgl Quelle 1
            int[] returnArray = new int[2];

            double xMultiplyer;
            double yMultiplyer;
            double yDistance = Math.abs(pos2.getY() - pos1.getY());
            double xDistance = Math.abs(pos2.getX() - pos1.getX());
            double angel = Math.atan2(yDistance, xDistance);

            //wenn auf einer coordinaten ebene --> verdopllung  des speeds
            if (xDistance == 0) {
                yMultiplyer = 2;
            } else {
                yMultiplyer = 1;
            }
            if (yDistance == 0) {
                xMultiplyer = 2;
            } else {
                xMultiplyer = 1;
            }

            //Berechnung x
            if (xDistance != 0 && pos2.getX() - pos1.getX() > 0) {
                returnArray[0] = (int) Math.round(Math.cos(angel) * speed * xMultiplyer);
//                pos.setX((pos.getX()+(int)Math.round(Math.cos(angel)*pSpeed*xMultiplyer)));
            } else if (xDistance != 0) {
                returnArray[0] = (int) Math.round(-Math.cos(angel) * speed * xMultiplyer);
//                pos.setX((pos.getX()+(int)Math.round(-Math.cos(angel)*pSpeed*xMultiplyer)));
            } else {
                returnArray[0] = 0;
            }

            //Berechnung y
            if (yDistance != 0 && pos2.getY() - pos1.getY() > 0) {
                returnArray[1] = (int) Math.round(Math.sin(angel) * speed * yMultiplyer);
            } else if (yDistance != 0) {
                returnArray[1] = (int) Math.round(-Math.sin(angel) * speed * yMultiplyer);
            } else {
                returnArray[1] = 0;
            }

//            System.out.println(returnArray[0]+ " "+ returnArray[1]);
            return returnArray;
        }

        public static Coordinate calculateRocketPos(Coordinate rocketPosition, Coordinate targetPosition,double sineX,double speed) {
            double newX;
            double newY;

//            int deltaTime = 2;
            double xDiff = targetPosition.getX() - rocketPosition.getX();
            double yDiff = targetPosition.getY() - rocketPosition.getY();
            double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);



            // smaller value
            double displacement = Math.min(speed, distance);

            // Calculate the angle in radians between the rocket and target
            double angleA = (Math.atan2(Math.abs(yDiff), Math.abs(xDiff)));
            double angleB = 180-90-angleA;
            double angleC = 90-angleB;

            //Pos on the straight line
            if(xDiff<0) {
                newX = rocketPosition.getX() + (displacement * Math.cos(angleB));
            }else {
                newX = rocketPosition.getX() - (displacement * Math.cos(angleB));
            }
           if(yDiff<0) {
                newY = rocketPosition.getY() - (displacement * Math.sin(angleA));
            }else {
                newY = rocketPosition.getY() + 0*(displacement * Math.sin(angleA));
            }



            //using the line from rocketPos to target Pos as x-Axis, calculates y for sine
            double sineY = 2*Math.sin(sineX);

            // converts sineY and displaycement to final coords
            double finalX = newX + (sineY*Math.cos(angleC));
            double finalY = newY + (sineY*Math.sin(180-90-angleC));

            return new Coordinate((int)finalX, (int)finalY+5);
        }


    }


    public static class GeneralMath {

        public static double calculateAngle(Coordinate pos1, Coordinate pos2) {
            double dx = pos2.getX() - pos1.getX();
            double dy = pos2.getY() - pos1.getY();
            return Math.atan2(dy, dx);
        }
        public static double calculateDistance(Coordinate pos1,Coordinate pos2){
            double dx = pos2.getX() - pos1.getX();
            double dy = pos2.getY() - pos1.getY();
            return Math.sqrt(dx*dx+dy*dy);
        }
    }
}














