package se_ii.gruppe2.moving_maze.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se_ii.gruppe2.moving_maze.gamestate.GameStateHandler;
import se_ii.gruppe2.moving_maze.network.messages.in.RequestProcessError;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.server.Session;
import se_ii.gruppe2.moving_maze.server.SessionManager;

import java.util.ArrayList;

/**
 * Distributes gamestate-updates amongst all clients in a certain session.
 */
public class GameStateUpdateHandler extends Listener {

    @Override
    public void received(Connection con, Object obj) {

        if(obj instanceof GameStateHandler) {
            GameStateHandler state = (GameStateHandler) obj;
            Log.info("Received updated gamestate from " + con.getRemoteAddressTCP().getAddress().toString());

            Session affectedSession = updateSessionByState(state);

            if(affectedSession != null) {
                affectedSession.sendStateToPlayers();
            } else {
                Log.warn("Not able to process gamestate update; session '" + state.getSessionCode() + "' not found");
                con.sendTCP(new RequestProcessError("GameState update", "Failed to process gamestate update because the session could not be found"));
            }

        }

    }

    /**
     * Updates a sessions internal gamestate
     * @param gsh new gamestate that also contains the affected sessioncode
     * @return the found, updated session or null (if session not found)
     */
    public Session updateSessionByState(GameStateHandler gsh) {

        Session se = gsh != null ? SessionManager.getSessionByKey(gsh.getSessionCode()) : null;

        if(se != null) {
            se.setState(gsh);
        }

        // preserve player-data if in devmode for proper testing
        if(se != null && se.getKey().equals("DEVGME")) {
            se.syncSessionPlayersWithState();
        }

        return se;
    }

}
