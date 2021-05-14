package se2.gruppe2.moving_maze.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se2.gruppe2.moving_maze.gameState.GameStateHandler;

/**
 * Distributes gamestate-updates amongst all clients in a certain session.
 */
public class GameStateUpdateHandler extends Listener {

    @Override
    public void received(Connection con, Object obj) {

        if(obj instanceof GameStateHandler) {
            // GameStateHandler state = (GameStateHandler) obj;
            Log.info("Received updated gamestate from " + con.getRemoteAddressTCP().getAddress().toString());
            // TODO: Distribute updates amongst clients
        }

    }

}
