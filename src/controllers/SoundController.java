package controllers;

import helpers.AssetLocation;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Singelton-Controller Klasse für die Sounds
 */
public class SoundController {

    private static SoundController instance;
    private Clip soundEffectClip;
    private Clip bgMusicClip;
    private FloatControl bgMusicVolumeControl;
    private FloatControl soundEffectVolumeControl;

    private float backgroundVolume = 1.0f; // hintegrund Lautstärke(0.0(aus) bis 1.0(max))
    private float soundEffectVolume = 1.0f;
    private final int MAX_CLIPS = 15; // maximale gleichzeitige soundeffecte (ohne hintergrund)
    private String currentBgMusic = null;
//    private Map<Clip, String> clipSoundMap = new HashMap<>(); // Track which sound is loaded in each clip
    private Map<String, ArrayList<Clip>> preloadedClips = new HashMap<>();
    private ExecutorService soundThreadPool = Executors.newFixedThreadPool(MAX_CLIPS);



    private SoundController() {
        preLoadSounds();
    }

    public static SoundController getInstance() {
        if (instance == null) {
            instance = new SoundController();
        }
        return instance;
    }

    /**
     * Methode um alle Soundeffecte in den Arbeitsspeicher zu laden
     */
    private void preLoadSounds() {
        int instancesPerSound = 1; //Jeder ton kann nur einmal gleichzeitig abgespielt werden

        Set<String> soundKeys = AssetController.getInstance().getSoundKeys();

        for (String soundKey : soundKeys) {
            preloadSound(soundKey, instancesPerSound);
        }
    }

    /**
     * Methode um einen Sound in den Arbeitsspeicher zu laden
     * @param soundEffect Identifier des Sounds
     * @param instances Anzahl der Instanzen, die geladen werden sollen
     */
    private void preloadSound(String soundEffect, int instances) {
        ArrayList<Clip> clips = new ArrayList<>();
        try {
            AudioInputStream originalStream = AssetController.getInstance().getSound(soundEffect);
            byte[] audioBytes = originalStream.readAllBytes();
            for (int i = 0; i < instances; i++) {
                    try {
                        AudioInputStream copyStream = new AudioInputStream(new ByteArrayInputStream(audioBytes), originalStream.getFormat(), audioBytes.length / originalStream.getFormat().getFrameSize());
                        Clip clip = AudioSystem.getClip();
                        clip.open(copyStream);
                        clips.add(clip);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
        preloadedClips.put(soundEffect, clips);
    }

    /**
     * Methode zum abspielen eines Soundeffects
     * @param soundEffect Identifier des Sounds
     */
    public void playSoundEffect(String soundEffect) {
        ArrayList<Clip> clips = preloadedClips.get(soundEffect);
        if (clips != null) {
            for (Clip clip : clips) {
                if (!clip.isActive()) {
                    soundThreadPool.execute(() -> { // Spielen des sounds in einem eigenen Thread, threath pool limitiert auf 15 Threaths
                        clip.setFramePosition(0);
                        clip.start();
                    });
                    return;
                }
            }
        }
    }

    /**
     * Methode zum abspielen von hintergrundMusik
     * @param soundEffect^ Identifier des Sounds
     */
    public void playBackgroundMusic(String soundEffect) {
        if (currentBgMusic != null && currentBgMusic.equals(soundEffect) && bgMusicClip != null && bgMusicClip.isRunning()) { // falls der Sound bereits läuft wird nichts gemacht
            return;
        }

        // falls ein anderer Sound läuft, wird dieser gestoppt
        if (bgMusicClip != null) {
            bgMusicClip.stop();
            bgMusicClip.close();
        }

        new Thread(() -> { //startet einen Threth für die hintergrundMusik
            try {
                AudioInputStream audioInputStream = AssetController.getInstance().getSound(soundEffect);
                bgMusicClip = AudioSystem.getClip();
                bgMusicClip.open(audioInputStream);

                bgMusicVolumeControl = (FloatControl) bgMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolumeForClip(bgMusicVolumeControl, backgroundVolume);

                bgMusicClip.loop(Clip.LOOP_CONTINUOUSLY);// spielt den Sound in einer Schleife ab
                currentBgMusic = soundEffect;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public void pauseBackgroundMusic() {
        if (bgMusicClip != null && bgMusicClip.isRunning()) {
            bgMusicClip.stop();
        }
    }

    public void resumeBackgroundMusic() {
        if (bgMusicClip != null && !bgMusicClip.isRunning()) {
            bgMusicClip.start();
        }
    }

    /**
     * Methode zum setzen der Lautstärke für die hintergrundMusik
     * @param volume Lautstärke(0.0(aus) bis 1.0(max))
     */
    public void setBackgroundVolume(float volume) {
        this.backgroundVolume = Math.max(0.0f, Math.min(volume, 1.0f));

        if (bgMusicVolumeControl != null) {
            setVolumeForClip(bgMusicVolumeControl, this.backgroundVolume);
        }
        if (soundEffectVolumeControl != null) {
            setVolumeForClip(soundEffectVolumeControl, this.backgroundVolume);
        }
    }

    /**
     * Methode zum setzen der Lautstärke für alle Soundeffects
     * @param volume Lautstärke(0.0(aus) bis 1.0(max))
     */
    public void setSoundEffectVolume(float volume) {
        this.soundEffectVolume = Math.max(0.0f, Math.min(volume, 1.0f));

        for (ArrayList<Clip> clips : preloadedClips.values()) {
            for (Clip clip : clips) {
                FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolumeForClip(control, this.soundEffectVolume);
            }
        }
    }


    private void setVolumeForClip(FloatControl control, float volume) {
        float min = control.getMinimum();
        float max = control.getMaximum();
        control.setValue(min + (max - min) * volume);
    }
}