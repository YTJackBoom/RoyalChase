package controllers;

import helpers.Animator;
import helpers.AssetLocation;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
            // Preload enemy animators
            for (int i = AssetLocation.Enemies.getLowestInt(); i <= AssetLocation.Enemies.getNumberOfIntDeclarations(); i++) {
                animators.put("enemyActive_" + i, new Animator(AssetLocation.Enemies.getEnemyActiveGifPath(i)));
                animators.put("enemyPassive_" + i, new Animator(AssetLocation.Enemies.getEnemyPassiveGifPath(i)));
            }
            // Preload tower animators
            for (int i = AssetLocation.Towers.getLowestInt(); i <= AssetLocation.Towers.getNumberOfIntDeclarations(); i++) {
                animators.put("towerActive_" + i, new Animator(AssetLocation.Towers.getTowerActiveGifPath(i)));
                animators.put("towerPassive_" + i, new Animator(AssetLocation.Towers.getTowerPassiveGifFile(i)));
            }
            // Preload projectile animators
            for (int i = AssetLocation.Projectiles.getLowestInt(); i <= AssetLocation.Projectiles.getNumberOfIntDeclarations(); i++) {
                animators.put("projectile_" + i, new Animator(AssetLocation.Projectiles.getProjectileGifFile(i)));
            }
            // Preload building animators
            for (int i = AssetLocation.Buildings.getLowestInt(); i <= AssetLocation.Buildings.getNumberOfIntDeclarations(); i++) {
                animators.put("building_" + i, new Animator(AssetLocation.Buildings.getBuidingGifFile(i)));
            }
            // Preload icons
            for (int i = AssetLocation.Icons.getLowestInt(); i <= AssetLocation.Icons.getNumberOfIntDeclarations(); i++) {
                try {
                    icons.put("icon_" + i, ImageIO.read(AssetLocation.Icons.getIconImageFile(i)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            // PreLoad buttons
            for (int i = AssetLocation.Buttons.getLowestInt(); i <= AssetLocation.Buttons.getNumberOfIntDeclarations(); i++) {
                try {
                    icons.put("button_" + i, ImageIO.read(AssetLocation.Buttons.getButtonImageFile(i)));
                } catch (IOException e) {

                    throw new RuntimeException("Error loading button with index: " + i, e);
                }
            }
            // Preload sounds
            //
            //Deaths
            for (int i = AssetLocation.Sounds.Deaths.getLowestInt(); i <= AssetLocation.Sounds.Deaths.getNumberOfIntDeclarations(); i++) {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(AssetLocation.Sounds.Deaths.getSoundFile(i));
                    sounds.put("deaths_" + i, audioInputStream);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            //projectilesFired
            for (int i = AssetLocation.Sounds.ProjectileFired.getLowestInt(); i <= AssetLocation.Sounds.ProjectileFired.getNumberOfIntDeclarations(); i++) {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(AssetLocation.Sounds.ProjectileFired.getSoundFile(i));
                    sounds.put("projectileFired_" + i, audioInputStream);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
    }


    public Animator getAnimator(String key) {
        Animator animator = animators.get(key);
        if (animator == null) {
            throw new IllegalArgumentException("No Animator found for key: " + key);
        }
        return animator.clone();
    }

    public BufferedImage getIcon(String key) {
        BufferedImage icon = icons.get(key);
        if (icon == null) {
            throw new IllegalArgumentException("No Icon found for key: " + key);
        }
        return icon;
    }

    public AudioInputStream getSound(String key) {
        AudioInputStream sound = sounds.get(key);
        if (sound == null) {
            throw new IllegalArgumentException("No Sound found for key: " + key);
        }
        return sound;
    }
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
