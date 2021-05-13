package se2.gruppe2.moving_maze.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se2.gruppe2.moving_maze.gameState.GameStateHandler;
import se2.gruppe2.moving_maze.network.messages.in.RequestProcessError;
import se2.gruppe2.moving_maze.network.messages.out.JoinRequest;
import se2.gruppe2.moving_maze.player.Player;
import se2.gruppe2.moving_maze.server.Session;
import se2.gruppe2.moving_maze.server.SessionManager;

/**
 * Invoked when a Client tries to join a session.
 * Tries to add the client to the session and send the gamestate or, in case of failure, return an error message.
 */
public class JoinSessionHandler extends Listener {

    @Override
    public void received(Connection con, Object obj) {
        if(obj instanceof JoinRequest) {
            JoinRequest jr = (JoinRequest) obj;
            Log.info("Received request to join session '" + jr.getSessionKey() + "' from " + con.getRemoteAddressTCP().getAddress().toString());

            Session target = SessionManager.getSessionByKey(jr.getSessionKey());
            if(target != null) {
                GameStateHandler response = processJoinRequest(jr.getPlayer(), target);

                if(response != null) {
                    Log.info("Player '" + jr.getPlayer().getName() + "' added to '" + jr.getSessionKey() + "'");
                    con.sendTCP(response);
                } else {
                    String message = "Unable to join session '" + jr.getSessionKey() + "' because MAX_PLAYER count reached. Rejecting player '" + jr.getPlayer().getName() + "'";
                    Log.info(message);
                    con.sendTCP(new RequestProcessError("JoinSessionHandler", message));
                }

            } else {
                Log.info("Session not found: " + jr.getSessionKey());
                con.sendTCP(new RequestProcessError("JoinSessionHandler", "Session '" + jr.getSessionKey() + "' could not be found"));
            }
        }
    }

    public GameStateHandler processJoinRequest(Player pl, Session se) {
        try {
            se.addPlayer(pl);
            return se.getState();
        } catch(IllegalStateException ise) {
            Log.error("Failed to join session '" + se.getKey() + "': MAX_PLAYER limit reached");
            return null;
        }
    }

}
