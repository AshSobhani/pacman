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

    SoundController() {
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

    public void playPacIntro() {
        pacIntro.start();
        pacIntro.setMicrosecondPosition(0);
    }

    public void playPacStart() {
        pacStart.start();
        pacStart.setMicrosecondPosition(0);
    }

    public void playPacChomp() {
        pacChomp.start();
        pacChomp.setMicrosecondPosition(0);
    }

    public void playPacDeath() {
        pacDeath.start();
        pacDeath.setMicrosecondPosition(0);
    }
}
