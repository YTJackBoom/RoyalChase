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

public class AssetController {
    private static AssetController instance;

    private Map<String, Animator> animators;
    private Map<String, BufferedImage> icons;
    private Map<String, Clip> sounds;

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
        for (int i = AssetLocation.Sounds.getLowestInt(); i <= AssetLocation.Sounds.getNumberOfIntDeclarations(); i++) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(AssetLocation.Sounds.getSoundFile(i));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                sounds.put("sound_" + i, clip);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Animator getAnimator(String key) {
        return animators.get(key).clone();
    }
    public BufferedImage getIcon(String key) {
        return icons.get(key);
    }
    public Clip getSound(String key) {
        return sounds.get(key);
    }


    public static synchronized AssetController getInstance() {
        if (instance == null) {
            instance = new AssetController();
        }
        return instance;
    }
}
