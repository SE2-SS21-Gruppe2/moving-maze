package se_ii.gruppe2.moving_maze.cheatfunction;

import com.badlogic.gdx.Gdx;
import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.item.ItemLogical;
import se_ii.gruppe2.moving_maze.item.Position;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.player.PlayerColorMapper;
import se_ii.gruppe2.moving_maze.tile.Tile;

public class CheatFunction {
    private boolean cheated;
    private boolean cheatCurrentMove;
    private boolean cheatDetected;
    private boolean laidCardDownPreviousMove;

    //Constructor
    public CheatFunction() {
        cheated = false;
        cheatDetected = false;
        cheatCurrentMove = false;
        laidCardDownPreviousMove = false;
    }


    /**
     * Player can mark a cheater once
     *
     * @param cheater who probably cheated
     * @param caller  who called a cheater
     * @return true if handled
     */
    public boolean markCheater(Player caller, Player cheater) {
        Gdx.app.log("name/cheater", "Cheater: " + cheater.getName() + " Caller: " + caller.getName());
        MovingMazeGame game = MovingMazeGame.getGameInstance();
        //check if already reported a cheater
        if (!caller.getCheatFunction().getCheatDetected()) {
            Gdx.app.log("cheat/report", "Caller has not reported anyone yet. Checking if cheater cheated.");
            caller.getCheatFunction().setCheatDetected(true);

            Position p = cheater.getPos();
            Tile t = game.getGameState().getBoard().getBoard()[p.getY()][p.getX()];
            Gdx.app.log("DEBUG", "cheater has cards in found " + cheater.getCardsFound().size());


            ItemLogical cardCheck = cheater.getCardsFound().firstElement();
            if (cardCheck != null && !cardCheck.equals(t.getItem())) {
                Gdx.app.log("cheat/report", "cardCheck: " + cardCheck.getName() + ", " + t.getItem().getName());
                Gdx.app.log("cheat/report", "Cheat detected! Cheater will be punished ...");
                //cheat detected and cheater is punished
                cheater.getCardsToFind().push(cheater.getCurrentCard());
                cheater.setCurrentCard(cardCheck);
                cheater.getCardsToFind().push(caller.getCurrentCard());
                caller.nextCard();

                cheater.setPos(PlayerColorMapper.getInitialPositionByColor(cheater.getColor()));

                //update GameState over server
                game.getClient().sendGameStateUpdate(game.getGameState());

                return true;
            } else {
                Gdx.app.log("DEBUG", "cheater has NO cards in found " + cheater.getCardsFound().size());
                Gdx.app.log("cheat/report", "Cheat not detected! Caller will be punished ...");
                //wrong cheat detected and caller is punished

                caller.getCardsToFind().push(caller.getCurrentCard());
                caller.setCurrentCard(cheater.getCardsFound().pop());
                cheater.getCardsFound().push(caller.getCurrentCard());

                caller.setPos(PlayerColorMapper.getInitialPositionByColor(caller.getColor()));

                //update GameState over server
                game.getClient().sendGameStateUpdate(game.getGameState());

            }

        }

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
            Player cheater = game.getGameState().getPlayerByName(game.getGameState().getCurrentPlayerOnTurn().getName());
            cheater.nextCard();
            cheater.getCheatFunction().setLaidCardDownPreviousMove(true);
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

    public boolean isLaidCardDownPreviousMove() {
        return laidCardDownPreviousMove;
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

    public void setLaidCardDownPreviousMove(boolean laidCardDownPreviousMove) {
        this.laidCardDownPreviousMove = laidCardDownPreviousMove;
    }
}
