package se_ii.gruppe2.moving_maze.gamestate;

import se_ii.gruppe2.moving_maze.gamestate.turnAction.TurnAction;
import se_ii.gruppe2.moving_maze.player.Player;

public class Turn {
    private int sequenceNumber;
    private Player player;
    private TurnAction[] executeAction;
    private TurnAction[] availableActions;

    public boolean executeAction(TurnAction action){
        return false;
    }
}
