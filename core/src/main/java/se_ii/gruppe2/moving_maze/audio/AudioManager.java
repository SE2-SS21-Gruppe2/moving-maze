package se_ii.gruppe2.moving_maze.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import se_ii.gruppe2.moving_maze.MovingMazeGame;

public class AudioManager {

    private static AudioManager audioManagerInstance;
    private Sound backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("audio/ambient.mp3"));
    private static boolean isPlayingBackgroundMusic;

    private AudioManager(){ }

    public static AudioManager getAudioManagerInstance(){
        if(audioManagerInstance == null) {
            audioManagerInstance = new AudioManager();
        }
        return audioManagerInstance;
    }

    private Sound getBackgroundMusic() {
        return backgroundMusic;
    }

    public void playBackgroundMusic(){
        if (!isPlayingBackgroundMusic){
            getBackgroundMusic().play(0.4f);
            getBackgroundMusic().loop();
            isPlayingBackgroundMusic = true;
        }
    }

    public void stopBackgroundMusic(){
        getBackgroundMusic().stop();
        isPlayingBackgroundMusic = false;
    }
}



