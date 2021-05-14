package se2.gruppe2.moving_maze.gameState;

import se2.gruppe2.moving_maze.gameBoard.GameBoardLogical;
import se2.gruppe2.moving_maze.player.Player;

public class GameStateHandler {
    private String sessionCode;
    private Player player;
    private Player currentPlayerOnTurn;
    private GameBoardLogical board;
    private GameState gameState;
    private ChatMessage[] chat;

    public boolean submitUpdateGameState(){
        return false;
    }
    public void receiveUpdateGameState(){}

    public void initGame(){}
    public void finishGame(){}

    public GameBoardLogical getBoard() {
        return board;
    }

    public void setBoard(GameBoardLogical board) {
        this.board = board;
    }
}
