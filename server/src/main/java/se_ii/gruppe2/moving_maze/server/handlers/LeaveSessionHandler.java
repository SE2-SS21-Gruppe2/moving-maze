package se_ii.gruppe2.moving_maze.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se_ii.gruppe2.moving_maze.network.messages.out.LeaveSessionRequest;
import se_ii.gruppe2.moving_maze.server.SessionManager;

public class LeaveSessionHandler extends Listener {

    @Override
    public void received(Connection con, Object obj) {
        if (obj instanceof LeaveSessionRequest) {
            LeaveSessionRequest lsr = (LeaveSessionRequest) obj;

            if (SessionManager.sessionExists(lsr.getSessionKey())){
                var se = SessionManager.getSessionByKey(lsr.getSessionKey());
                se.removePlayer(lsr.getPlayer(), con);
                Log.info("Player '" + lsr.getPlayer().getName() + "' removed from '" + lsr.getSessionKey() +
                        "'. This session now has " + SessionManager.getSessionByKey(lsr.getSessionKey()).getNumberOfPlayersInSession() + " connected players.");
            }
        }
    }
}
