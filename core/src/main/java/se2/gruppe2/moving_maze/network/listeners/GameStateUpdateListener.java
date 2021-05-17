package se2.gruppe2.moving_maze.network.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import se2.gruppe2.moving_maze.MovingMazeGame;
import se2.gruppe2.moving_maze.gameState.GameStateHandler;

public class GameStateUpdateListener extends Listener {
    @Override
    public void received(Connection con, Object obj) {
        if(obj instanceof GameStateHandler) {
            MovingMazeGame game = MovingMazeGame.getGameInstance();
            GameStateHandler gsh = (GameStateHandler) obj;
            Gdx.app.log("NetworkClient/gamestate-update", "Gamestate update received!");
            if(isValidState(gsh)) {
                game.gameScreen.updatedFromServer = true;
                game.setGameState(gsh);
                Gdx.app.log("NetworkClient/gamestate-update", "Received gamestate set and representation updated");
            } else {
                Gdx.app.error("NetworkClient/gamestate-update", "Received invalid gamestate; not updating internal representation");
            }
        }
    }

    private boolean isValidState(GameStateHandler gsh) {
        return gsh != null && gsh.getBoard() != null;
    }
}
