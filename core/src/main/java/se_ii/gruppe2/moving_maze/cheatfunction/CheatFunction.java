package se_ii.gruppe2.moving_maze.cheatfunction;

import se_ii.gruppe2.moving_maze.gamestate.GameStateHandler;
import se_ii.gruppe2.moving_maze.player.Player;

public class CheatFunction {
    private boolean cheated;
    private boolean cheatedCurrentMove;
    private boolean cheatDetected;

    //Constructor
    public CheatFunction() {
        cheated = false;
        cheatDetected = false;
        cheatedCurrentMove = false;
    }

    //Getter
    public boolean getCheated() {
        return cheated;
    }

    public boolean getCheatDetected() {
        return cheatDetected;
    }

    //Setter
    public void setCheated(boolean cheated) {
        cheated = cheated;
    }

    public void setCheatDetected(boolean cheatDetected) {
        cheatDetected = cheatDetected;
    }

    /**
     * Player can mark a cheater once
     * @param player who probably cheated
     * @return true if handled
     */
    public boolean markCheater(Player player, GameStateHandler gsh) {
        if (!gsh.getCurrentPlayerOnTurn().getCheatFunction().getCheatDetected()) {
            gsh.getCurrentPlayerOnTurn().getCheatFunction().setCheated(true);
            if (player.getCheatFunction().cheatedCurrentMove) {
                //cheat detected and current player is punished
            } else {
                //wrong cheat detect and caller is punished
            }

        }
        //TODO handling on mark Cheater
        return false;
    }

    /**
     * Player can cheat once and say he is on the tile of his card
     * @return true if handled
     */
    public boolean activateCheat(GameStateHandler gsh) {
        //TODO player can activate the cheat once
        return false;
    }

}
