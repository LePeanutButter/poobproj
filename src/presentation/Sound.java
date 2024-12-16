package presentation;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * The Sound class provides functionality to manage and control audio playback. 
 * It supports playing, pausing, resuming, stopping, and looping audio files using the Java Sound API.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 , 2024
 */
public class Sound {
    private Clip clip;
    private long clipPosition;

    /**
     * Constructs a new Sound object and loads an audio file from the specified path.
     * 
     * @param pathname The file path to the audio file.
     * @throws RuntimeException If there is an issue loading the audio file.
     */
    public Sound(String pathname) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(pathname));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException _) {
        }
    }

    /**
     * Starts looping the audio indefinitely.
     * If the clip is not initialized, this method does nothing.
     */
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Starts or resumes playback of the audio clip from its current position.
     * If the clip is not initialized, this method does nothing.
     */
    public void startClip() {
        if (clip != null) {
            clip.start();
        }
    }

    /**
     * Stops the playback of the audio clip.
     * If the clip is not initialized, this method does nothing.
     */
    public void stopClip() {
        if (clip != null) {
            clip.stop();
        }
    }

    /**
     * Pauses the audio clip, saving its current playback position.
     * If the clip is not initialized or not currently playing, this method does nothing.
     */
    public void pauseClip() {
        if (clip != null && clip.isRunning()) {
            clipPosition = clip.getMicrosecondPosition();
            clip.stop();
        }
    }

    /**
     * Resumes the audio clip from its paused position. Optionally loops the audio clip.
     * 
     * @param loop true to loop the audio clip after resuming; false to play once.
     * If the clip is not initialized, this method does nothing.
     */
    public void resumeClip(boolean loop) {
        if (clip != null) {
            clip.setMicrosecondPosition(clipPosition);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.start();
        }
    }
}
