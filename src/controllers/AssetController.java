package controllers;

import helpers.Animator;
import helpers.AssetLocation;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Singelton Klasse zum laden und verwalten der Spiel-Assets
 */
public class AssetController {
    private static AssetController instance;

    private Map<String, Animator> animators;
    private Map<String, BufferedImage> icons;
    private Map<String, AudioInputStream> sounds;

    private AssetController() {
        animators = new HashMap<>();
        icons = new HashMap<>();
        sounds = new HashMap<>();
        preloadAssets();
    }

    private void preloadAssets() {
            // Lade der Gegner-Animationen
            for (int i = AssetLocation.Enemies.getLowestInt(); i <= AssetLocation.Enemies.getNumberOfIntDeclarations(); i++) {
                animators.put("enemyActive_" + i, new Animator(AssetLocation.Enemies.getEnemyActiveGifPath(i)));
            }
            // Lade der Turm-Animationen
            for (int i = AssetLocation.Towers.getLowestInt(); i <= AssetLocation.Towers.getNumberOfIntDeclarations(); i++) {
                animators.put("towerActive_" + i, new Animator(AssetLocation.Towers.getTowerActiveGifPath(i)));
                animators.put("towerPassive_" + i, new Animator(AssetLocation.Towers.getTowerPassiveGifFile(i)));
            }
            // Lade der Projektil-Animationen
            for (int i = AssetLocation.Projectiles.getLowestInt(); i <= AssetLocation.Projectiles.getNumberOfIntDeclarations(); i++) {
                animators.put("projectile_" + i, new Animator(AssetLocation.Projectiles.getProjectileGifFile(i)));
            }
            // Lade der Gebäude-Animationen
            for (int i = AssetLocation.Buildings.getLowestInt(); i <= AssetLocation.Buildings.getNumberOfIntDeclarations(); i++) {
                animators.put("building_" + i, new Animator(AssetLocation.Buildings.getBuildingGifFile(i)));
            }
            // Lade der Icon-Bilder
            for (int i = AssetLocation.Icons.getLowestInt(); i <= AssetLocation.Icons.getNumberOfIntDeclarations(); i++) {
                try {
                    icons.put("icon_" + i, ImageIO.read(AssetLocation.Icons.getIconImageFile(i)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            // Laden der Button-Bilder
            for (int i = AssetLocation.Buttons.getLowestInt(); i <= AssetLocation.Buttons.getNumberOfIntDeclarations(); i++) {
                try {
                    icons.put("button_" + i, ImageIO.read(AssetLocation.Buttons.getButtonImageFile(i)));
                } catch (IOException e) {

                    throw new RuntimeException("Error loading button with index: " + i, e);
                }
            }
            // Laden der Sounds
            //
            //Todes-Sounds
            for (int i = AssetLocation.Sounds.Deaths.getLowestInt(); i <= AssetLocation.Sounds.Deaths.getNumberOfIntDeclarations(); i++) {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(AssetLocation.Sounds.Deaths.getSoundFile(i));
                    sounds.put("deaths_" + i, audioInputStream);
                } catch (Exception e) {
                    throw new RuntimeException(e + " On "+AssetLocation.getFieldNameByValue(AssetLocation.Sounds.Deaths.class,i));
                }
            }
            //Prokjektile-Sounds
            for (int i = AssetLocation.Sounds.ProjectileFired.getLowestInt(); i <= AssetLocation.Sounds.ProjectileFired.getNumberOfIntDeclarations(); i++) {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(AssetLocation.Sounds.ProjectileFired.getSoundFile(i));
                    sounds.put("projectileFired_" + i, audioInputStream);
                } catch (Exception e) {
                    throw new RuntimeException(e+ " On "+AssetLocation.getFieldNameByValue(AssetLocation.Sounds.ProjectileFired.class,i));
                }
            }
            for(int i = AssetLocation.Sounds.OtherSounds.getLowestInt(); i <= AssetLocation.Sounds.OtherSounds.getNumberOfIntDeclarations(); i++) {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(AssetLocation.Sounds.OtherSounds.getSoundFile(i));
                    sounds.put("otherSounds_" + i, audioInputStream);
                } catch (Exception e) {
                    throw new RuntimeException(e+ " On "+AssetLocation.getFieldNameByValue(AssetLocation.Sounds.OtherSounds.class,i));
                }
            }
    }


    /**
     * Methode zum clonen der Animator-Objekte aus dem Array
     * @param key Identifier des zu clonenden Animators
     * @return Animator-Objekt
     */
    public Animator getAnimator(String key) {
        Animator animator = animators.get(key);
        if (animator == null) {
            throw new IllegalArgumentException("No Animator found for key: " + key);
        }
        return animator.clone();
    }

    /**
     * Methode zum clonen der BufferedImage-Objekte aus dem Array (für Icons und Buttons)
     * @param key Identifier des zu clonenden BufferedImages
     * @return BufferedImage-Objekt
     */
    public BufferedImage getIcon(String key) {
        BufferedImage icon = icons.get(key);
        if (icon == null) {
            throw new IllegalArgumentException("No Icon found for key: " + key);
        }
        return icon;
    }

    /**
     * Methode zum clonen der AudioInputStream-Objekte aus dem Array
     * @param key Identifier des zu clonenden AudioInputStreams
     * @return AudioInputStream-Objekt
     */
    public AudioInputStream getSound(String key) {
        AudioInputStream sound = sounds.get(key);
        if (sound == null) {
            throw new IllegalArgumentException("No Sound found for key: " + key);
        }
        try {
            // Get all bytes from the original AudioInputStream
            byte[] audioBytes = sound.readAllBytes();
            // Create a new ByteArrayInputStream for the bytes and wrap it in a BufferedInputStream
            ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(audioBytes);
            return new AudioInputStream(byteArrayStream, sound.getFormat(), audioBytes.length / sound.getFormat().getFrameSize());

        } catch (IOException e) {
            throw new RuntimeException("Error buffering the sound", e);
        }
    }


    /**
     * Methode, um alle Sound-Keys zu bekommen, diese werden genutzt, um die Sounds im SoundController in den Arbeitsspeiher zu laden
     */
    public Set<String> getSoundKeys() {
        return sounds.keySet();
    }
    public static synchronized AssetController getInstance() {
        if (instance == null) {
            instance = new AssetController();
        }
        return instance;
    }


}
