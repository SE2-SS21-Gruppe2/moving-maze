package se2.gruppe2.moving_maze.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se2.gruppe2.moving_maze.network.messages.out.JoinRequest;

public class JoinSessionHandler extends Listener {
    @Override
    public void received(Connection con, Object obj) {
        if(obj instanceof JoinRequest) {
            JoinRequest jr = (JoinRequest) obj;
            Log.info("Received request to join session '" + jr.getSessionCode() + "' from " + con.getRemoteAddressTCP().getAddress().toString());
        }
    }
}
