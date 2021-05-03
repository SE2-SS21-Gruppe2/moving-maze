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

    public boolean submitUpdateGameState(){
        return false;
    }
    public void receiveUpdateGameState(){}

    public void initGame(){}
    public void finishGame(){}

}
