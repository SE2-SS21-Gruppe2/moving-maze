package se_ii.gruppe2.moving_maze.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import se_ii.gruppe2.moving_maze.network.messages.out.WinGameRequest;

public class WinGameHandler extends Listener {

    @Override
    public void received(Connection con, Object obj){
        if(obj instanceof WinGameRequest){
            WinGameRequest win= (WinGameRequest) obj;
            Log.info("Win Player Name:"+win.getPlayer());
        }
    }
}
