package se_ii.gruppe2.moving_maze.network.messages.out;

import se_ii.gruppe2.moving_maze.gameboard.GameBoard;

/**
 * Sent to the server by a game host in order to signal that a game can be started.
 * In addition to the session-key, a generated board is sent.
 */
public class InitGameStart {
    private String key;
    private GameBoard board;

    public InitGameStart() {
    }

    public InitGameStart(String key, GameBoard board) {
        this.key = key;
        this.board = board;
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
}
