package se_ii.gruppe2.moving_maze.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se_ii.gruppe2.moving_maze.network.messages.in.JoinRequestConfirmation;
import se_ii.gruppe2.moving_maze.network.messages.in.RequestProcessError;
import se_ii.gruppe2.moving_maze.network.messages.out.JoinRequest;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.player.PlayerColor;
import se_ii.gruppe2.moving_maze.server.Session;
import se_ii.gruppe2.moving_maze.server.SessionManager;

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
                PlayerColor assignedColor = processJoinRequest(jr.getPlayer(), con, jr.getSessionKey());

                if(assignedColor != null) {
                    Log.info("Player '" + jr.getPlayer().getName() + "' added to '" + jr.getSessionKey() + "'");
                    JoinRequestConfirmation jrc = new JoinRequestConfirmation(jr.getSessionKey(), assignedColor);
                    con.sendTCP(jrc);
                    SessionManager.logResponse(jrc);
                } else {
                    String message = "Unable to join session '" + jr.getSessionKey() + "' because MAX_PLAYER count reached. Rejecting player '" + jr.getPlayer().getName() + "'";
                    Log.info(message);
                    RequestProcessError rpe = new RequestProcessError("JoinSessionHandler", message);
                    con.sendTCP(rpe);
                    SessionManager.logResponse(rpe);
                }

            } else {
                Log.info("Session not found: " + jr.getSessionKey());
                RequestProcessError rpe = new RequestProcessError("JoinSessionHandler", "Session '" + jr.getSessionKey() + "' could not be found");
                con.sendTCP(rpe);
                SessionManager.logResponse(rpe);
            }
        }
    }

    public PlayerColor processJoinRequest(Player pl, Connection con, String key) {
        try {
            Session se = SessionManager.getSessionByKey(key);
            return se.addPlayer(pl, con);
        } catch(IllegalStateException ise) {
            Log.error("Failed to join session '" + key + "': MAX_PLAYER limit reached");
            return null;
        }
    }

}
