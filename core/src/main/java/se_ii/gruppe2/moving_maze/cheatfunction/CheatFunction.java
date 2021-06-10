package se_ii.gruppe2.moving_maze.cheatfunction;

import se_ii.gruppe2.moving_maze.player.Player;

public class CheatFunction {
    private boolean cheated;
    private boolean cheatDetected;

    //Constructor
    public CheatFunction() {
        cheated = false;
        cheatDetected = false;
    }

    //Getter
    public boolean isCheated() {
        return cheated;
    }

    public boolean isCheatDetected() {
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
    public boolean markCheater(Player player) {
        //TODO handling on mark Cheater
        return false;
    }

    /**
     * Player can cheat once and say he is on the tile of his card
     * @return true if handled
     */
    public boolean activateCheat() {
        //TODO player can activate the cheat once
        return false;
    }

}
