package se2.gruppe2.moving_maze.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se2.gruppe2.moving_maze.network.messages.out.CloseSessionRequest;
import se2.gruppe2.moving_maze.server.SessionManager;

public class CloseSessionHandler extends Listener {

    @Override
    public void received(Connection con, Object obj) {
        if (obj instanceof CloseSessionRequest){
            CloseSessionRequest csr = (CloseSessionRequest) obj;
            Log.info("Received request to close session '" + csr.getSessionKey() + "'");
            SessionManager.closeSession(csr.getSessionKey());
        }
    }
}
