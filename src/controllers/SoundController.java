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

public class SoundController {

    private static SoundController instance;
    private Clip soundEffectClip;
    private Clip bgMusicClip;
    private FloatControl bgMusicVolumeControl;
    private FloatControl soundEffectVolumeControl;

    private float backgroundVolume = 1.0f; // default volume, range 0.0 (muted) to 1.0 (max)
    private float soundEffectVolume = 1.0f; // default volume, range 0.0 (muted) to 1.0 (max)
    private final int MAX_CLIPS = 15; // maximale gleichzeitige soundeffecte (nicht hintergrund)
    private String currentBgMusic = null;
    private Map<Clip, String> clipSoundMap = new HashMap<>(); // Track which sound is loaded in each clip
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
    private void preLoadSounds() {
        int instancesPerSound = 1;

        // Get all the sound keys from AssetController
        Set<String> soundKeys = AssetController.getInstance().getSoundKeys();

        for (String soundKey : soundKeys) {
            preloadSound(soundKey, instancesPerSound);
        }
    }

    public void preloadSound(String soundEffect, int instances) {
        ArrayList<Clip> clips = new ArrayList<>();
        try {
            AudioInputStream originalStream = AssetController.getInstance().getSound(soundEffect);
            byte[] audioBytes = originalStream.readAllBytes();  // Read the entire stream once
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

    public void playSoundEffect(String soundEffect) {
        ArrayList<Clip> clips = preloadedClips.get(soundEffect);
        if (clips != null) {
            for (Clip clip : clips) {
                if (!clip.isActive()) {
                    soundThreadPool.execute(() -> {
                        clip.setFramePosition(0);
                        clip.start();
                    });
                    return;  // Exit the method after scheduling one of the clips to play
                }
            }
        }
    }

    public void playBackgroundMusic(String soundEffect) {
        // Check if the requested background music is already playing
        if (currentBgMusic != null && currentBgMusic.equals(soundEffect) && bgMusicClip != null && bgMusicClip.isRunning()) {
            return; // If it's already playing, do nothing
        }

        // If there's another background music playing, stop it
        if (bgMusicClip != null) {
            bgMusicClip.stop();
            bgMusicClip.close(); // Release the resources associated with the clip
        }

        new Thread(() -> {
            try {
                AudioInputStream audioInputStream = AssetController.getInstance().getSound(soundEffect);
                bgMusicClip = AudioSystem.getClip();
                bgMusicClip.open(audioInputStream);

                bgMusicVolumeControl = (FloatControl) bgMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolumeForClip(bgMusicVolumeControl, backgroundVolume);

                bgMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
                currentBgMusic = soundEffect; // Update the currently playing background music ID

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
    public void setBackgroundVolume(float volume) {
        this.backgroundVolume = Math.max(0.0f, Math.min(volume, 1.0f)); // Ensure volume is between 0 and 1

        // Apply to currently active clips
        if (bgMusicVolumeControl != null) {
            setVolumeForClip(bgMusicVolumeControl, this.backgroundVolume);
        }
        if (soundEffectVolumeControl != null) {
            setVolumeForClip(soundEffectVolumeControl, this.backgroundVolume);
        }
    }

    public void setSoundEffectVolume(float volume) {
        this.soundEffectVolume = Math.max(0.0f, Math.min(volume, 1.0f)); // Ensure volume is between 0 and 1

        // Apply to all preloaded clips
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