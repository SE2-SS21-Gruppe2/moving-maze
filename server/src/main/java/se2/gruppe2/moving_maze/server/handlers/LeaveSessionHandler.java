package se2.gruppe2.moving_maze.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se2.gruppe2.moving_maze.network.messages.out.LeaveSessionRequest;
import se2.gruppe2.moving_maze.server.Session;
import se2.gruppe2.moving_maze.server.SessionManager;

public class LeaveSessionHandler extends Listener {

    @Override
    public void received(Connection con, Object obj) {
        if (obj instanceof LeaveSessionRequest) {
            LeaveSessionRequest lsr = (LeaveSessionRequest) obj;

            if (SessionManager.sessionExists(lsr.getSessionKey())){
                Session se = SessionManager.getSessionByKey(lsr.getSessionKey());
                se.removePlayer(lsr.getPlayer(), con);
                Log.info("Player '" + lsr.getPlayer().getName() + "' removed from '" + lsr.getSessionKey() +
                        "'. This session now has " + SessionManager.getSessionByKey(lsr.getSessionKey()).getNumberOfPlayersInSession() + " connected players.");
            }
        }
    }
}
