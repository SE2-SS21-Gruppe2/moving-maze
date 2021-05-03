package se2.gruppe2.moving_maze.gameState;

import se2.gruppe2.moving_maze.gameState.turnAction.TurnAction;
import se2.gruppe2.moving_maze.player.Player;

public class Turn {
    private int sequenceNumber;
    private Player player;
    private TurnAction[] executeAction;
    private TurnAction[] availableActions;

    public boolean executeAction(TurnAction action){
        return false;
    }
}
