package se_ii.gruppe2.moving_maze.cheatfunction;

import com.badlogic.gdx.Gdx;
import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.gamestate.GameStateHandler;
import se_ii.gruppe2.moving_maze.player.Player;

public class CheatFunction {
    private boolean cheated;
    private boolean cheatCurrentMove;
    private boolean cheatDetected;

    //Constructor
    public CheatFunction() {
        cheated = false;
        cheatDetected = false;
        cheatCurrentMove = false;
    }


    /**
     * Player can mark a cheater once
     *
     * @param playerReported who probably cheated
     * @param playerCaller   who called a cheater
     * @return true if handled
     */
    public boolean markCheater(Player playerReported, Player playerCaller) {
        MovingMazeGame game = MovingMazeGame.getGameInstance();
        //check if already reported a cheater
        if (!game.getGameState().getCurrentPlayerOnTurn().getCheatFunction().getCheatDetected()) {
            game.getGameState().getCurrentPlayerOnTurn().getCheatFunction().setCheatDetected(true);
            if (playerReported.getCheatFunction().cheatCurrentMove) {
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
     *
     * @return true if cheated
     */
    public boolean activateCheat() {
        if (!getCheated()) {
            //next card and send to server
            setCheated(true);
            MovingMazeGame game = MovingMazeGame.getGameInstance();
            Player local = game.getGameState().getPlayerByName(game.getLocalPlayer().getName());
            local.nextCard();
            Gdx.app.log("cheat/debug", "Cheat activated and successful! Updating card ...");

            return true;
        } else {
            //notification, that no cheat available
            Gdx.app.log("cheat/debug", "Cheat NOT activated. Already cheated ...");
            return false;
        }
    }


    //Getter
    public boolean getCheated() {
        return cheated;
    }

    public boolean getCheatDetected() {
        return cheatDetected;
    }

    public boolean isCheatCurrentMove() {
        return cheatCurrentMove;
    }


    //Setter
    public void setCheated(boolean cheated) {
        this.cheated = cheated;
    }

    public void setCheatDetected(boolean cheatDetected) {
        this.cheatDetected = cheatDetected;
    }

    public void setCheatCurrentMove(boolean cheatCurrentMove) {
        this.cheatCurrentMove = cheatCurrentMove;
    }

}
