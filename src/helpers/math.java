package helpers;

import gameObjects.Enemy;
import gameObjects.Tower;

import java.util.Random;

public  class math {

    public static class TowerMath {
        public static boolean checkRange(Tower tower, Enemy object2) {
            if (object2 != null) {
                int towerRange = variables.Towers.getTowerRange(tower.getType());
                AbsoluteCoordinate towerPos = tower.getPos();
                AbsoluteCoordinate object2Pos = object2.getPos();
                double distance = math.GeneralMath.calculateDistance(towerPos, object2Pos);

                if (distance <= towerRange) {
                    return true;
                } else return false;

            } else return false;
        }
    }

    public static class ProjectileMath {
        private static long previousTime;

        public static AbsoluteCoordinate calculateArrowPos(AbsoluteCoordinate pos1, AbsoluteCoordinate pos2, double speed) { // int[0] = x ; 1 = y //berechnung der neuen position durch winkel  zwichen den punkten. vgl Quelle 1
            AbsoluteCoordinate pos = pos1;

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
//                returnArray[0] = (int) Math.round(Math.cos(angel) * speed * xMultiplyer);
                pos.setX((pos.getX()+(int)Math.round(Math.cos(angel)*speed*xMultiplyer)));
            } else if (xDistance != 0) {
//                returnArray[0] = (int) Math.round(-Math.cos(angel) * speed * xMultiplyer);
                pos.setX((pos.getX()+(int)Math.round(-Math.cos(angel)*speed*xMultiplyer)));
            }

            //Berechnung y
            if (yDistance != 0 && pos2.getY() - pos1.getY() > 0) {
//                returnArray[1] = (int) Math.round(Math.sin(angel) * speed * yMultiplyer);
                pos.setY((pos.getY()+ (int) Math.round(Math.sin(angel) * speed * yMultiplyer) ));
            } else if (yDistance != 0) {
//                returnArray[1] = (int) Math.round(-Math.sin(angel) * speed * yMultiplyer);
                pos.setY((pos.getY()+ (int) Math.round(-Math.sin(angel)*speed*yMultiplyer)));
            }

//            System.out.println(returnArray[0]+ " "+ returnArray[1]);
            return pos;
        }

        public static AbsoluteCoordinate calculateRocketPos(AbsoluteCoordinate rocketPosition, AbsoluteCoordinate targetPosition, double sineX, double speed) {
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
            double sineY = 2 * Math.sin(sineX);

            // converts sineY and displaycement to final coords
            double finalX = newX + (sineY * Math.cos(angleC));
            double finalY = newY + (sineY * Math.sin(180 - 90 - angleC));

            return new AbsoluteCoordinate((int) finalX, (int) finalY + 5);
        }

        public static AbsoluteCoordinate calculateLightningBallPos(AbsoluteCoordinate ballPosition, AbsoluteCoordinate targetPosition, double speed, double maxRandomDisplacement) {
            double newX;
            double newY;


            double distance = GeneralMath.calculateDistance(ballPosition, targetPosition);

            // If the ball has reached the target, return the target position
            if (distance <= speed) {
                return targetPosition;
            }

            // Calculate the angle in radians between the ball and target
            double angleToTarget = GeneralMath.calculateAngle(ballPosition,targetPosition);

            // Introduce some randomness to the movement by applying random displacements
            Random random = new Random();
            double randomDisplacementX = random.nextDouble() * maxRandomDisplacement;
            double randomDisplacementY = random.nextDouble() * maxRandomDisplacement;

            // Calculate the new position of the ball with random displacements
            newX = ballPosition.getX() + (speed * Math.cos(angleToTarget)) + randomDisplacementX;
            newY = ballPosition.getY() + (speed * Math.sin(angleToTarget)) + randomDisplacementY;

            return new AbsoluteCoordinate((int) newX, (int) newY);
        }

        public static AbsoluteCoordinate calculateBulletPos(AbsoluteCoordinate bulletPos, double angleInRadians, double speed) {
            // Convert angle to radians if it is in degrees
//            double angleInRadians = Math.toRadians(angle);

            // Calculate new position
            double newX = bulletPos.x + speed * -Math.cos(angleInRadians);
            double newY = bulletPos.y + speed * -Math.sin(angleInRadians);

            return new AbsoluteCoordinate((int) newX, (int) newY);
        }
    }


    public static class GeneralMath {

        public static double calculateAngle(AbsoluteCoordinate pos1, AbsoluteCoordinate pos2) {
            double dx = pos2.getX() - pos1.getX();
            double dy = pos2.getY() - pos1.getY();
            double angleInRadians = Math.atan2(dy, dx);

//            angleInRadians = -angleInRadians;
//
//            // Ensure the angle is within the range of -π to π
//            if (angleInRadians < -Math.PI) {
//                angleInRadians += 2 * Math.PI;
//            } else if (angleInRadians > Math.PI) {
//                angleInRadians -= 2 * Math.PI;
//            }
            return angleInRadians;
        }

        public static double calculateDistance(AbsoluteCoordinate pos1, AbsoluteCoordinate pos2) {
            double dx = pos2.getX() - pos1.getX();
            double dy = pos2.getY() - pos1.getY();
            return Math.sqrt(dx * dx + dy * dy);
        }
    }
}














