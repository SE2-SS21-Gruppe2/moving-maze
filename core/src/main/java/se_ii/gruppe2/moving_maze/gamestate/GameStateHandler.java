package se_ii.gruppe2.moving_maze.gamestate;

import se_ii.gruppe2.moving_maze.gameboard.GameBoard;
import se_ii.gruppe2.moving_maze.player.Player;

public class GameStateHandler {
    private String sessionCode;
    private Player player;
    private Player currentPlayerOnTurn;
    private GameBoard board;
    private GameState gameState;
    private ChatMessage[] chat;

    // GETTER & SETTER
    public GameBoard getBoard() {
        return board;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    public Player getCurrentPlayerOnTurn() {
        return currentPlayerOnTurn;
    }
}
