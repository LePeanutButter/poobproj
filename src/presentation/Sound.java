package presentation;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private Clip clip;

    public Sound(boolean loop, String pathname) {
        AudioInputStream audioStream = null;
        try {
            audioStream = AudioSystem.getAudioInputStream(new File(pathname));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException _) {
        }
    }

    public void stopClip() {
        if (clip != null) {
            clip.stop();
        }
    }
}
