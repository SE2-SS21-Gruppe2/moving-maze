package se_ii.gruppe2.moving_maze.network.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import se2.gruppe2.moving_maze.MovingMazeGame;
import se2.gruppe2.moving_maze.network.messages.in.UpdateConnectedPlayersConfirmation;

public class UpdateConnectedPlayersListener extends Listener {

    @Override
    public void received(Connection con, Object obj) {
        if(obj instanceof UpdateConnectedPlayersConfirmation) {
            UpdateConnectedPlayersConfirmation ucpc = (UpdateConnectedPlayersConfirmation) obj;
            var game = MovingMazeGame.getGameInstance();
            game.setConnectedPlayers(ucpc.getConnectedPlayers());
            Gdx.app.log("NetworkClient/UpdateConnectedPlayersConfirmation", "Received update for connected players.");
        }
    }
}
