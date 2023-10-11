package helpers;

import gameObjects.Enemy;
import gameObjects.Tower;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

import static basics.GameState.userSettings;

/**
 * Diese Helfer-Klasse enthält Methoden, die mit Mathematik zu tun haben.
 */
public  class math {

//    public static class TowerMath {
//        public static boolean checkRange(Tower tower, Enemy object2) {
//            if (object2 != null) {
//                int towerRange = ObjectValues.Towers.getTowerRange(tower.getType());
//                AbsoluteCoordinate towerPos = tower.getPos();
//                AbsoluteCoordinate object2Pos = object2.getPos();
//                double distance = math.GeneralMath.calculateDistance(towerPos, object2Pos);
//
//                if (distance <= towerRange) {
//                    return true;
//                } else return false;
//
//            } else return false;
//        }
//    }

    /**
     * Diese Klasse enthält Methoden, welche die FlugBahnen von Projectilen berechnen.
     */
    public static class ProjectileMath {
        /**
         * Berechnet die neue Position von "Arrow" mit den übergebenen Parametern. Arrow fliegt auf einem Bogen.
         *
         * @param pos1 Die derzeitige Position.
         * @param pos2 Die Zielposition.
         * @param speed Die geschwindigkeit.
         * @return Die neue Position.
         */
        public static AbsoluteCoordinate calculateArrowPos(AbsoluteCoordinate pos1, AbsoluteCoordinate pos2, double speed) { // int[0] = x ; 1 = y //berechnung der neuen position durch winkel  zwichen den punkten. vgl Quelle 1

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
                pos1.setX((pos1.getX()+(int)Math.round(Math.cos(angel)*speed*xMultiplyer)));
            } else if (xDistance != 0) {
//                returnArray[0] = (int) Math.round(-Math.cos(angel) * speed * xMultiplyer);
                pos1.setX((pos1.getX()+(int)Math.round(-Math.cos(angel)*speed*xMultiplyer)));
            }

            //Berechnung y
            if (yDistance != 0 && pos2.getY() - pos1.getY() > 0) {
//                returnArray[1] = (int) Math.round(Math.sin(angel) * speed * yMultiplyer);
                pos1.setY((pos1.getY()+ (int) Math.round(Math.sin(angel) * speed * yMultiplyer) ));
            } else if (yDistance != 0) {
//                returnArray[1] = (int) Math.round(-Math.sin(angel) * speed * yMultiplyer);
                pos1.setY((pos1.getY()+ (int) Math.round(-Math.sin(angel)*speed*yMultiplyer)));
            }

//            System.out.println(returnArray[0]+ " "+ returnArray[1]);
            return pos1;
        }
        /**
         * Berechnet die neue Position von "Rocket" mit den übergebenen Parametern.
         *
         * @param rocketPosition Die derzeitige Position.
         * @param targetPosition Die Zielposition.
         * @param sineX Der derzeitige punkt auf der Flugbahn.
         * @param speed Die geschwindigkeit.
         * @return Die neue Position.
         */

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

        /**
         * Berechnet die neue Position von "LightningBall" mit den übergebenen Parametern. Mit einem maximalen zufälligen Versatz, um die bewegung zufälliger erscheinen zu lassen.
         *
         * @param ballPosition Die derzeitige Position.
         * @param targetPosition Die Zielposition.
         * @param speed Die geschwindigkeit.
         * @param maxRandomDisplacement Der maximale zufällige Versatz.
         * @return Die neue Position.
         */
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

        /**
         * Berechnet die neue Position von "Bullet" mit den übergebenen Parametern.
         *
         * @param bulletPos Die derzeitige Position.
         * @param angleInRadians Der Richtungs-Winkel in radians.
         * @param speed Die geschwindigkeit.
         * @return Die neue Position.
         */
        public static AbsoluteCoordinate calculateBulletPos(AbsoluteCoordinate bulletPos, double angleInRadians, double speed) {
            // Convert angle to radians if it is in degrees
//            double angleInRadians = Math.toRadians(angle);

            // Calculate new position
            double newX = bulletPos.x + speed * -Math.cos(angleInRadians);
            double newY = bulletPos.y + speed * -Math.sin(angleInRadians);

            return new AbsoluteCoordinate((int) newX, (int) newY);
        }
    }

    /**
     * Diese Klasse enthält Methoden, "noemale Mathematik" durchführen.
     */
    public static class GeneralMath {

        /**
         * Berechnet den Winkel zwischen zwei Punkten.
         *
         * @param pos1 Der erste Punkt.
         * @param pos2 Der zweite Punkt.
         * @return Der Winkel zwischen den beiden Punkten in Radian.
         */
        public static double calculateAngle(AbsoluteCoordinate pos1, AbsoluteCoordinate pos2) {
            double dx = pos2.getX() - pos1.getX();
            double dy = pos2.getY() - pos1.getY();
            double angleInRadians = Math.atan2(dy, dx);
            return angleInRadians;
        }

        /**
         * Berechnet die Distanz zwischen zwei Punkten.
         *
         * @param pos1 Der erste Punkt.
         * @param pos2 Der zweite Punkt.
         * @return Die Distanz zwischen den beiden Punkten.
         */
        public static double calculateDistance(AbsoluteCoordinate pos1, AbsoluteCoordinate pos2) {
            double dx = pos2.getX() - pos1.getX();
            double dy = pos2.getY() - pos1.getY();
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

    /**
     * Diese Klasse enthält Methoden, welche schwierigkeitswerte in tatsächliche veränderungen umrechnen.
     */
    public static class DifficultyMath {
        public static double calculateEnemyHealthPercentChange() {
            double difficulty = userSettings.getDifficulty();
            return 1.1 + difficulty/10 ;
        }
        public static double calculateEnemySpeedPercentChange() {
            double difficulty = userSettings.getDifficulty();
            return 1.1 + difficulty/10 ;
        }
        public static double calculateEnemyDamagePercentChange() {
            double difficulty = userSettings.getDifficulty();
            return 1.1 + difficulty/10 ;
        }
        public static double calculateEnemyReloadTimePercentChange() {
            double difficulty = userSettings.getDifficulty();
            return 1.1 + difficulty/10 ;
        }
        public static double calculateTowerDamagePercentChange() {
            double difficulty = userSettings.getDifficulty();
            return 1.1 + difficulty/10 ;
        }
        public static double calculateTowerHealthPercentChange() {
            double difficulty = userSettings.getDifficulty();
            return 1.1 + difficulty/10 ;
        }
        public static double calculateEnemyRewardPercentChange() {
            double difficulty = userSettings.getDifficulty();
            return 1.1 + difficulty/10 ;
        }
        public static double calculateTownProductionPercentChange() {
            double difficulty = userSettings.getDifficulty();
            return 1.1 + difficulty/10 ;
        }



    }
    /**
     * Diese Klasse enthält Methoden, die welche mit Bildern rechen/diese verändern.
     */

    public static class ImageMath {
        /**
         * Skaliert das übergebene Bild. Zunächst wird das Bild 6fach vergrößert und anschließend auf die gewünschten Dimensionen verkleinert,
         * um Unschärfen zu vermeiden.
         *
         * @param originalImage Das Originalbild, das skaliert werden soll.
         * @param newWidth Die neue Breite des Bildes.
         * @param newHeight Die neue Höhe des Bildes.
         * @return Das skalierte Bild.
         */
        public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
            int upscaleFactor = 6;
            int upscaledWidth = originalImage.getWidth() * upscaleFactor;
            int upscaledHeight = originalImage.getHeight() * upscaleFactor;

            AffineTransform upscaleTransform = new AffineTransform();
            upscaleTransform.scale(upscaleFactor, upscaleFactor);

            AffineTransformOp upscaleOp = new AffineTransformOp(upscaleTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            BufferedImage upscaledImage = new BufferedImage(upscaledWidth, upscaledHeight, BufferedImage.TYPE_INT_ARGB);
            upscaleOp.filter(originalImage, upscaledImage);

            double widthScale = (double) newWidth / upscaledImage.getWidth();
            double heightScale = (double) newHeight / upscaledImage.getHeight();

            AffineTransform downscaleTransform = new AffineTransform();
            downscaleTransform.scale(widthScale, heightScale);

            AffineTransformOp downscaleOp = new AffineTransformOp(downscaleTransform, AffineTransformOp.TYPE_BILINEAR);
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            return downscaleOp.filter(upscaledImage, resizedImage);
        }



    }
}














