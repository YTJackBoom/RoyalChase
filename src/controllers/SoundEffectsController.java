package controllers;

import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;

public class SoundEffectsController {

    private static SoundEffectsController instance;
    private Clip soundEffectClip;
    private Clip bgMusicClip;
    private FloatControl bgMusicVolumeControl;
    private FloatControl soundEffectVolumeControl;

    private float volume = 1.0f; // default volume, range 0.0 (muted) to 1.0 (max)

    private ArrayList<Clip> clipPool = new ArrayList<>();
    private final int MAX_CLIPS = 10; // maximale gleichzeitige soundeffecte (nicht hintergrund)
    private int currentBgMusic = -1;

    private SoundEffectsController() {
        initializeClipPool();
    }
    private void initializeClipPool() {
         for (int i = 0; i < MAX_CLIPS; i++) {
             try {
                 Clip clip = AudioSystem.getClip();
                 clipPool.add(clip);
             } catch (Exception ex) {
                 ex.printStackTrace();
             }
         }
     }

    public static SoundEffectsController getInstance() {
        if (instance == null) {
            instance = new SoundEffectsController();
        }
        return instance;
    }
    private Clip getAvailableClip() {
        for (Clip clip : clipPool) {
            if (!clip.isActive()) {
                return clip;
            }
        }
        return null;
    }
    public void playSoundEffect(int soundEffect) {
        Clip clip = getAvailableClip();
        if (clip != null) {
            new Thread(() -> {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(helpers.variables.Sounds.getSoundFile(soundEffect));
                    clip.open(audioInputStream);
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        }
    }


    public void playBackgroundMusic(int soundEffect) {
        // Check if the requested background music is already playing
        if (currentBgMusic != -1 && currentBgMusic == soundEffect && bgMusicClip != null && bgMusicClip.isRunning()) {
            return; // If it's already playing, do nothing
        }

        // If there's another background music playing, stop it
        if (bgMusicClip != null) {
            bgMusicClip.stop();
            bgMusicClip.close(); // Release the resources associated with the clip
        }

        new Thread(() -> {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(helpers.variables.Sounds.getSoundFile(soundEffect));
                bgMusicClip = AudioSystem.getClip();
                bgMusicClip.open(audioInputStream);

                bgMusicVolumeControl = (FloatControl) bgMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolumeForClip(bgMusicVolumeControl, volume);

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

    public void pauseSoundEffect() {
        if (soundEffectClip != null && soundEffectClip.isRunning()) {
            soundEffectClip.stop();
        }
    }

    public void resumeSoundEffect() {
        if (soundEffectClip != null && !soundEffectClip.isRunning()) {
            soundEffectClip.start();
        }
    }

    public void setVolume(float volume) {
        this.volume = Math.max(0.0f, Math.min(volume, 1.0f)); // Ensure volume is between 0 and 1

        // Apply to currently active clips
        if (bgMusicVolumeControl != null) {
            setVolumeForClip(bgMusicVolumeControl, this.volume);
        }
        if (soundEffectVolumeControl != null) {
            setVolumeForClip(soundEffectVolumeControl, this.volume);
        }
    }

    private void setVolumeForClip(FloatControl control, float volume) {
        float min = control.getMinimum();
        float max = control.getMaximum();
        control.setValue(min + (max - min) * volume);
    }
}