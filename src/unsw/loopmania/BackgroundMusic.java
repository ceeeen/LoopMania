package unsw.loopmania;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * This class modifies the background music of the game depending on which scene
 * we are currently on.
 */
public class BackgroundMusic {

    private String menuMusic;
    private String gameMusic;
    private Clip clip;

    public BackgroundMusic() {
        menuMusic = "src/music/Jukebox.wav";
        gameMusic = "src/music/battleMusic.wav";

    }

    /**
     * Plays the music file. Stops old clip file before playing new clip
     * 
     * @param file
     */
    public void play(String file) {

        if (clip != null) {
            clip.stop();
        }
        File musicPath = new File(file);
        try {
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip music = AudioSystem.getClip();
                music.open(audioInput);
                music.start();
                music.loop(Clip.LOOP_CONTINUOUSLY);
                clip = music;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getmenuMusic() {
        return this.menuMusic;
    }

    public String getgameMusic() {
        return this.gameMusic;
    }
}
