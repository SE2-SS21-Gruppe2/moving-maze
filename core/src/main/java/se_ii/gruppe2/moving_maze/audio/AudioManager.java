

package se_ii.gruppe2.moving_maze.audio;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.audio.Music;
        import com.badlogic.gdx.audio.Sound;

public class AudioManager {

    final private static Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/silent-tears_bg_music.mp3"));
    private static boolean isPlayingBackgroundMusic;

    final private static Sound movingMaze = Gdx.audio.newSound(Gdx.files.internal("audio/moving_maze.mp3"));
    final private static Sound victory = Gdx.audio.newSound(Gdx.files.internal("audio/victory.mp3"));
    final private static Sound movePlayer = Gdx.audio.newSound(Gdx.files.internal("audio/move_player.mp3"));
    final private static Sound joinGame = Gdx.audio.newSound(Gdx.files.internal("audio/silent-tears_bg_music.mp3"));
    final private static Sound leaveGame = Gdx.audio.newSound(Gdx.files.internal("audio/silent-tears_bg_music.mp3"));
    final private static Sound layCardDown = Gdx.audio.newSound(Gdx.files.internal("audio/lay_card_down.mp3"));
    final private static Sound rotateTile = Gdx.audio.newSound(Gdx.files.internal("audio/rotate_tile.mp3"));
    final private static Sound cheatDetected = Gdx.audio.newSound(Gdx.files.internal("audio/silent-tears_bg_music.mp3"));
    final private static Sound noCheatDetected = Gdx.audio.newSound(Gdx.files.internal("audio/silent-tears_bg_music.mp3"));

    private AudioManager(){ }

    private static Music getBackgroundMusic() {
        return backgroundMusic;
    }

    public static void playBackgroundMusic(){
        if (!isPlayingBackgroundMusic){
            getBackgroundMusic().play();
            getBackgroundMusic().setVolume(0.4f);
            getBackgroundMusic().setLooping(true);
            isPlayingBackgroundMusic = true;
        }
    }

    public static void stopBackgroundMusic(){
        getBackgroundMusic().stop();
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

    public static void playRotateTile() {
        rotateTile.play(0.4f);
    }

    public static void playCheatDetected() {
        cheatDetected.play(0.4f);
    }

    public static void playNoCheatDetected() {
        noCheatDetected.play(0.4f);
    }
}