package se_ii.gruppe2.moving_maze.network.messages.out;

import se_ii.gruppe2.moving_maze.gameboard.GameBoard;

/**
 * Sent to the server by a game host in order to signal that a game can be started.
 * In addition to the session-key, a generated board and the chosen name of the session-creator is submitted.
 */
public class InitGameStart {
    private String key;
    private GameBoard board;
    private String finalHostName;

    public InitGameStart() {
    }

    public InitGameStart(String key, GameBoard board, String finalHostName) {
        this.key = key;
        this.board = board;
        this.finalHostName = finalHostName;
    }

    // GETTER & SETTER
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public GameBoard getBoard() {
        return board;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    public String getFinalHostName() {
        return finalHostName;
    }

    public void setFinalHostName(String finalHostName) {
        this.finalHostName = finalHostName;
    }
}
