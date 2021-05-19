package se2.gruppe2.moving_maze.gameState;

import se2.gruppe2.moving_maze.gameBoard.GameBoard;
import se2.gruppe2.moving_maze.player.Player;

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
}
