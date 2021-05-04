package se2.gruppe2.moving_maze.audio;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {

    private static AudioManager audioManager = new AudioManager() ;

    private  Sound backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("audio/ambient.mp3"));

    public static AudioManager getInstance(){
        return audioManager;
    }

    public Sound getBackgroundMusic() {
        return backgroundMusic;
    }



}



