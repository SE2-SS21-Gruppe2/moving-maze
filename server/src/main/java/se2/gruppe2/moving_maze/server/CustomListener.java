package se2.gruppe2.moving_maze.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class CustomListener extends Listener {

    @Override
    public void received(Connection con, Object obj) {
        System.out.println("Received a request!");
        con.sendTCP("thanks for the request buddy");
    }

}
