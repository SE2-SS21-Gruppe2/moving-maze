package se_ii.gruppe2.moving_maze.network.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.gamestate.GameStateHandler;

public class GameStateUpdateListener extends Listener {
    private final String logTag = "NetworkClient/gamestate-update";

    @Override
    public void received(Connection con, Object obj) {
        if(obj instanceof GameStateHandler) {
            var game = MovingMazeGame.getGameInstance();
            GameStateHandler gsh = (GameStateHandler) obj;
            Gdx.app.log(logTag, "Gamestate update received!");
            if(isValidState(gsh)) {
                game.setGameState(gsh);
                Gdx.app.log(logTag, "Received gamestate set and representation updated");
            } else {
                Gdx.app.error(logTag, "Received invalid gamestate; not updating internal representation");
            }
        }
    }

    private boolean isValidState(GameStateHandler gsh) {
        return gsh != null && gsh.getBoard() != null;
    }
}
