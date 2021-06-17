package se_ii.gruppe2.moving_maze.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {

    private static AudioManager audioManagerInstance;
    private static Sound backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("audio/ambient.mp3"));
    private static Music backgroundMusic2 = Gdx.audio.newMusic(Gdx.files.internal("audio/silent-tears_bg_music.mp3"));

    private static Sound movingMaze = Gdx.audio.newSound(Gdx.files.internal("audio/"));
    private static Sound victory = Gdx.audio.newSound(Gdx.files.internal("audio/"));
    private static Sound movePlayer = Gdx.audio.newSound(Gdx.files.internal("audio/"));
    private static Sound joinGame = Gdx.audio.newSound(Gdx.files.internal("audio/"));
    private static Sound leaveGame = Gdx.audio.newSound(Gdx.files.internal("audio/"));
    private static Sound layCardDown = Gdx.audio.newSound(Gdx.files.internal("audio/"));
    private static Sound isYourTurn = Gdx.audio.newSound(Gdx.files.internal("audio/"));

    private static boolean isPlayingBackgroundMusic;

    private AudioManager() {
    }

    public static AudioManager getAudioManagerInstance() {
        if (audioManagerInstance == null) {
            audioManagerInstance = new AudioManager();
        }
        return audioManagerInstance;
    }

    private static Sound getBackgroundMusic() {
        return backgroundMusic;
    }

    public static void playBackgroundMusic() {
/*        if (!isPlayingBackgroundMusic){
            getBackgroundMusic().play(0.4f);
            getBackgroundMusic().loop();
            isPlayingBackgroundMusic = true;
        }*/
        if (!isPlayingBackgroundMusic) {
            backgroundMusic2.play();
            backgroundMusic2.setVolume(0.4f);
            backgroundMusic2.setLooping(true);
            isPlayingBackgroundMusic = true;
        }
    }

    public static void stopBackgroundMusic() {
//        getBackgroundMusic().stop();
        backgroundMusic2.stop();
        isPlayingBackgroundMusic = false;
    }

    public static void playMovingMaze() {
        movingMaze.play(0.4f);
    }

    public static void playVictory() {
        victory.play(0.4f);
    }

    public static void playMovePlayer() {
        movePlayer.play(0.4f);
    }

    public static void playJoinGame() {
        joinGame.play(0.4f);
    }

    public static void playLeaveGame() {
        leaveGame.play(0.4f);
    }

    public static void playLayCardDown() {
        layCardDown.play(0.4f);
    }

    public static void playIsYourTurn() {
        isYourTurn.play(0.4f);
    }

}



