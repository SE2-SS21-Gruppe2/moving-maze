package se_ii.gruppe2.moving_maze.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {

    private static AudioManager audioManagerInstance;
    private static Sound backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("audio/ambient.mp3"));
    private static boolean isPlayingBackgroundMusic;

    private AudioManager(){ }

    public static AudioManager getAudioManagerInstance(){
        if(audioManagerInstance == null) {
            audioManagerInstance = new AudioManager();
        }
        return audioManagerInstance;
    }

    private static Sound getBackgroundMusic() {
        return backgroundMusic;
    }

    public static void playBackgroundMusic(){
        if (!isPlayingBackgroundMusic){
            getBackgroundMusic().play(0.4f);
            getBackgroundMusic().loop();
            isPlayingBackgroundMusic = true;
        }
    }

    public static void stopBackgroundMusic(){
        getBackgroundMusic().stop();
        isPlayingBackgroundMusic = false;
    }
}



