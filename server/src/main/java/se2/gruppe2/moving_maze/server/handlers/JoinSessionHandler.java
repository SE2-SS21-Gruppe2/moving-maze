package se2.gruppe2.moving_maze.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se2.gruppe2.moving_maze.gameState.GameStateHandler;
import se2.gruppe2.moving_maze.network.messages.in.JoinRequestConfirmation;
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

            if(SessionManager.sessionExists(jr.getSessionKey())) {
                boolean joinSuccess = processJoinRequest(jr.getPlayer(), con, jr.getSessionKey());

                if(joinSuccess) {
                    Log.info("Player '" + jr.getPlayer().getName() + "' added to '" + jr.getSessionKey() + "'. This session now has " + SessionManager.getSessionByKey(jr.getSessionKey()).getNumberOfPlayersInSession() + " connected players.");
                    con.sendTCP(new JoinRequestConfirmation(jr.getSessionKey()));
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

    public boolean processJoinRequest(Player pl, Connection con, String key) {
        try {
            Session se = SessionManager.getSessionByKey(key);
            se.addPlayer(pl, con);
            return true;
        } catch(IllegalStateException ise) {
            Log.error("Failed to join session '" + key + "': MAX_PLAYER limit reached");
            return false;
        }
    }

}
