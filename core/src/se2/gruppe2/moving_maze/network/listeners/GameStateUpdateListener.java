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
            GameStateHandler gsh = (GameStateHandler) obj;
            Gdx.app.log("NetworkClient/gamestate-update", "Gamestate update received!");
            // TODO: uncomment
            //MovingMazeGame.getGameInstance().setGameState(gsh);
            //Gdx.app.log("NetworkClient/gamestate-update", "Received gamestate set");
        }
    }
}
