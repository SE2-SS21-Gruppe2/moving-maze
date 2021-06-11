package se_ii.gruppe2.moving_maze.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import java.util.Iterator;
import java.util.Map;

import javax.swing.text.html.HTMLDocument;

import se_ii.gruppe2.moving_maze.network.messages.in.WinGameConformation;
import se_ii.gruppe2.moving_maze.network.messages.out.WinGameRequest;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.server.Session;
import se_ii.gruppe2.moving_maze.server.SessionManager;

public class WinGameHandler extends Listener {

    @Override
    public void received(Connection con, Object obj){
        if(obj instanceof WinGameRequest){
            WinGameRequest win= (WinGameRequest) obj;
            if(true/*win.getPlayer().getCardsToFind().size()==0*/){
                WinGameConformation winConf= new WinGameConformation(win.getPlayer());
                var session= SessionManager.getSessionByKey(win.getSessionCode());
                Connection connection;
                for(Map.Entry<Player,Connection> entry: session.getPlayers().entrySet()){
                    connection= entry.getValue();
                    connection.sendTCP(winConf);
                }
                SessionManager.closeSession(win.getSessionCode());
            }

        }
    }
}
