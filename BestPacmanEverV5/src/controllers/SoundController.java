package controllers;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SoundController {
    private int toggle;
    private Clip pacIntro;
    private Clip pacStart;
    private Clip pacChomp;
    private Clip pacDeath;

    /**
     * I have made a sound controller to safely keep all the audio directories in one place
     * I have also wrapped it in a Exception to ensure that program would fail gracefully if th that were case
     */
    public SoundController() {
        try {
            pacIntro = AudioSystem.getClip();
            pacIntro.open(AudioSystem.getAudioInputStream(new File("BestPacmanEverV5/src/resources/sounds/pacIntro.wav")));

            pacStart = AudioSystem.getClip();
            pacStart.open(AudioSystem.getAudioInputStream(new File("BestPacmanEverV5/src/resources/sounds/pacStart.wav")));

            pacChomp = AudioSystem.getClip();
            pacChomp.open(AudioSystem.getAudioInputStream(new File("BestPacmanEverV5/src/resources/sounds/pacChomp.wav")));

            pacDeath = AudioSystem.getClip();
            pacDeath.open(AudioSystem.getAudioInputStream(new File("BestPacmanEverV5/src/resources/sounds/pacDeath.wav")));
        } catch (Exception Error) {
            System.out.println("Audio File Failed To Load");
        }
    }

    /**
     * Added some of the standard Pacman sounds and reset them so that they can be played again
     * This will play when Pacman is opened up
     */
    public void playPacIntro() {
        pacIntro.start();
        pacIntro.setMicrosecondPosition(0);
    }

    /**
     * This sound will be played at the start of a game
     */
    public void playPacStart() {
        pacStart.start();
        pacStart.setMicrosecondPosition(0);
    }

    /**
     * This is Pacmans eating sound when he's having the cookies
     */
    public void playPacChomp() {
        pacChomp.start();
        pacChomp.setMicrosecondPosition(0);
    }

    /**
     * When the user dies this sound will play and reset ready for the next death
     */
    public void playPacDeath() {
        pacDeath.start();
        pacDeath.setMicrosecondPosition(0);
    }
}
