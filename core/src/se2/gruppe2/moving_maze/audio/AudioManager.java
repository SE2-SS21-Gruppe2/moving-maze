package se2.gruppe2.moving_maze.audio;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {

    private static AudioManager audioManager = new AudioManager() ;

    private  Sound backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("audio/ambient.mp3"));
    private  Music pressButton;
    private  Music rotateTile;
    private  Music placeTile;
    private  Music winSound;


    private AudioManager(){ }

    public static AudioManager getInstance(){
        return audioManager;
    }

    public Sound getBackgroundMusic() {
        return backgroundMusic;
    }

    public void setBackgroundMusic(Sound backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }

    public Music getPressButton() {
        return pressButton;
    }

    public void setPressButton(Music pressButton) {
        this.pressButton = pressButton;
    }

    public  Music getRotateTile() {
        return rotateTile;
    }

    public void setRotateTile(Music rotateTile) {
        this.rotateTile = rotateTile;
    }

    public Music getPlaceTile() {
        return placeTile;
    }

    public void setPlaceTile(Music placeTile) {
        this.placeTile = placeTile;
    }

    public Music getWinSound() {
        return winSound;
    }

    public void setWinSound(Music winSound) {
        this.winSound = winSound;
    }


}



