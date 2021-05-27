package se_ii.gruppe2.moving_maze.gamestate;

public class GamePhase {
    private int phaseCounter;
    private final GamePhaseType[] gamePhases;

    public GamePhase(){
        this.phaseCounter = 0;
        gamePhases = GamePhaseType.values();
    }

    public GamePhaseType nextPhase(){
        return gamePhases[++phaseCounter % gamePhases.length];
    }

    public GamePhaseType getPhaseType(){
        return gamePhases[phaseCounter%gamePhases.length];
    }
}
