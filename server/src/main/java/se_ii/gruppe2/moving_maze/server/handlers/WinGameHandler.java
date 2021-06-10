package se_ii.gruppe2.moving_maze.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import se_ii.gruppe2.moving_maze.network.messages.in.WinGameConformation;
import se_ii.gruppe2.moving_maze.network.messages.out.WinGameRequest;
import se_ii.gruppe2.moving_maze.server.SessionManager;

public class WinGameHandler extends Listener {

    @Override
    public void received(Connection con, Object obj){
        if(obj instanceof WinGameRequest){
            WinGameRequest win= (WinGameRequest) obj;
            if(true/*win.getPlayer().getCardsToFind().size()==0*/){
                WinGameConformation winConf= new WinGameConformation(win.getPlayer());
                con.sendTCP(winConf);
                SessionManager.closeSession(win.getSessionCode());
            }

        }
    }
}
