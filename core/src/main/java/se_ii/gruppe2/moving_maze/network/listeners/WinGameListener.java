package se_ii.gruppe2.moving_maze.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import se_ii.gruppe2.moving_maze.network.messages.in.WinGameConformation;

public class WinGameListener extends Listener {

    @Override
    public void received(Connection con, Object obj) {
        if(obj instanceof WinGameConformation){

        }
    }
}
