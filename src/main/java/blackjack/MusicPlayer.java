package blackjack;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Handles background music for the BlackJack game using the Java Sound API
 */
public class MusicPlayer {
    private Clip audioClip;
    private boolean isPlaying = false;
    private float volume = 0.5f; // Default volume (0.0 to 1.0)
    private String musicFilePath;

    /**
     * Constructor initializes the music player with the specified music file
     *
     * @param musicFilePath Path to the music file to be played (WAV format recommended)
     */
    public MusicPlayer(String musicFilePath) {
        this.musicFilePath = musicFilePath;
        try {
            // Check if file exists
            File musicFile = new File(musicFilePath);
            if (!musicFile.exists()) {
                System.err.println("Music file not found: " + musicFilePath);
                return;
            }

            initializeClip(musicFile);
        } catch (Exception e) {
            System.err.println("Error loading music file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Initialize the audio clip from the music file
     */
    private void initializeClip(File musicFile) {
        try {
            // Get AudioInputStream
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);

            // Get a clip resource
            audioClip = AudioSystem.getClip();

            // Open audio clip and load samples from the audio input stream
            audioClip.open(audioInputStream);

            // Set up looping
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);

            // Set initial volume
            setVolume(volume);

            // Add listener to handle when clip is done
            audioClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && isPlaying) {
                    // If the clip has stopped but should be playing, restart it
                    audioClip.setFramePosition(0);
                    audioClip.start();
                }
            });

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error initializing audio clip: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Start playing the music
     */
    public void play() {
        if (audioClip != null && !isPlaying) {
            audioClip.setFramePosition(0);
            audioClip.start();
            isPlaying = true;
        }
    }

    /**
     * Pause the currently playing music
     */
    public void pause() {
        if (audioClip != null && isPlaying) {
            audioClip.stop();
            isPlaying = false;
        }
    }

    /**
     * Stop the music completely
     */
    public void stop() {
        if (audioClip != null) {
            audioClip.stop();
            audioClip.setFramePosition(0);
            isPlaying = false;
        }
    }

    /**
     * Adjust the volume of the music
     *
     * @param volume Value between 0.0 (mute) and 1.0 (max volume)
     */
    public void setVolume(float volume) {
        if (audioClip != null) {
            this.volume = volume;
            try {
                FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
                // Convert linear scale (0.0 to 1.0) to decibel scale
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                // Ensure we don't exceed the range
                dB = Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), dB));
                gainControl.setValue(dB);
            } catch (Exception e) {
                System.err.println("Error setting volume: " + e.getMessage());
            }
        }
    }

    /**
     * Check if music is currently playing
     *
     * @return true if music is playing, false otherwise
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Release resources when the application is closing
     */
    public void dispose() {
        if (audioClip != null) {
            audioClip.stop();
            audioClip.close();
        }
    }

    /**
     * Restart the player with the same file
     */
    public void restart() {
        if (audioClip != null) {
            audioClip.close();
        }
        initializeClip(new File(musicFilePath));
        if (isPlaying) {
            play();
        }
    }
}