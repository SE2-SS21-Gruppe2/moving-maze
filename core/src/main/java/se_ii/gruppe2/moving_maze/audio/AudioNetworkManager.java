package se_ii.gruppe2.moving_maze.audio;


public class AudioNetworkManager {
    private static AudioNetworkManager audioNetwork;

    //play sounds
    private boolean playPlayerMovement;
    private boolean playLabyrinthMovement;
    private boolean playLayCardDown;

    private AudioNetworkManager() {
        playPlayerMovement = false;
        playLabyrinthMovement = false;
        playLayCardDown = false;

    }

    public static AudioNetworkManager getInstance() {
        if (audioNetwork == null) {
            audioNetwork = new AudioNetworkManager();
        }
        return audioNetwork;
    }


    //Getters and Setters
    public boolean isPlayPlayerMovement() {
        return playPlayerMovement;
    }

    public void setPlayPlayerMovement(boolean playPlayerMovement) {
        this.playPlayerMovement = playPlayerMovement;
    }

    public boolean isPlayLabyrinthMovement() {
        return playLabyrinthMovement;
    }

    public void setPlayLabyrinthMovement(boolean playLabyrinthMovement) {
        this.playLabyrinthMovement = playLabyrinthMovement;
    }

    public boolean isPlayLayCardDown() {
        return playLayCardDown;
    }

    public void setPlayLayCardDown(boolean playLayCardDown) {
        this.playLayCardDown = playLayCardDown;
    }
}
