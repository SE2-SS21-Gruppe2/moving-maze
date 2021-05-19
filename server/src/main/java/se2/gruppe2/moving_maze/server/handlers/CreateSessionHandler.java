package se2.gruppe2.moving_maze.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se2.gruppe2.moving_maze.network.messages.in.CreateSessionRequestConfirmation;
import se2.gruppe2.moving_maze.network.messages.in.JoinRequestConfirmation;
import se2.gruppe2.moving_maze.network.messages.in.RequestProcessError;
import se2.gruppe2.moving_maze.network.messages.out.CreateSessionRequest;
import se2.gruppe2.moving_maze.player.Player;
import se2.gruppe2.moving_maze.server.Session;
import se2.gruppe2.moving_maze.server.SessionManager;

public class CreateSessionHandler extends Listener {

    @Override
    public void received(Connection con, Object obj) {
        if (obj instanceof CreateSessionRequest) {
            CreateSessionRequest csr = (CreateSessionRequest) obj;
            Log.info("Received request to create new session");

            Session newSession;
            int tryCounter = 5;
            do {
                newSession = SessionManager.createRandomSession();
                tryCounter--;
            } while( newSession == null && tryCounter != 0);

            if (newSession != null) {
                Log.info("New session created with session key '" + newSession.getKey() + "'");
                con.sendTCP(new CreateSessionRequestConfirmation(newSession.getKey()));

                boolean joinSuccess = processJoinRequest(csr.getPlayer(), con, newSession.getKey());

                if(joinSuccess) {
                    Log.info("Player '" + csr.getPlayer().getName() + "' added to '" + newSession.getKey() + "'. This session now has " + SessionManager.getSessionByKey(newSession.getKey()).getNumberOfPlayersInSession() + " connected players.");
                    con.sendTCP(new JoinRequestConfirmation(newSession.getKey()));
                } else {
                    String message = "Unable to join session '" + newSession.getKey() + "' because MAX_PLAYER count reached. Rejecting player '" + csr.getPlayer().getName() + "'";
                    Log.info(message);
                    con.sendTCP(new RequestProcessError("JoinSessionHandler", message));
                }

            }
            else {
                String message = "Unable to create new session";
                Log.info(message);
                con.sendTCP(new RequestProcessError("CreateSessionHandler", message));
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
