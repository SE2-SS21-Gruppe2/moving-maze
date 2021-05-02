package se2.gruppe2.moving_maze.audio;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {

    private static AudioManager audioManager = new AudioManager() ;

    private static Sound backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("audio/ambient.mp3"));
    private static Music pressButton;
    private static Music rotateTile;
    private static Music placeTile;
    private static Music winSound;


    private AudioManager(){

    }

    public static AudioManager getInstance(){
        return audioManager;
    }


    public void playBackgroundMusic(){
        backgroundMusic.play(0.2f);
    }

    public void stopBackgroundMusic(){
        backgroundMusic.stop();
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public void setAudioManager(AudioManager audioManager) {
        AudioManager.audioManager = audioManager;
    }

    public Sound getBackgroundMusic() {
        return backgroundMusic;
    }

    public void setBackgroundMusic(Sound backgroundMusic) {
        AudioManager.backgroundMusic = backgroundMusic;
    }

    public Music getPressButton() {
        return pressButton;
    }

    public void setPressButton(Music pressButton) {
        AudioManager.pressButton = pressButton;
    }

    public  Music getRotateTile() {
        return rotateTile;
    }

    public void setRotateTile(Music rotateTile) {
        AudioManager.rotateTile = rotateTile;
    }

    public Music getPlaceTile() {
        return placeTile;
    }

    public void setPlaceTile(Music placeTile) {
        AudioManager.placeTile = placeTile;
    }

    public Music getWinSound() {
        return winSound;
    }

    public void setWinSound(Music winSound) {
        AudioManager.winSound = winSound;
    }


}



