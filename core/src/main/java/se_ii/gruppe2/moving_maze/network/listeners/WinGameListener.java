package se_ii.gruppe2.moving_maze.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.network.messages.in.WinGameConformation;
import se_ii.gruppe2.moving_maze.screens.VictoryScreen;

public class WinGameListener extends Listener {

    @Override
    public void received(Connection con, Object obj) {
        if(obj instanceof WinGameConformation){
            WinGameConformation win= (WinGameConformation) obj;
            var game= MovingMazeGame.getGameInstance();
            game.setScreen(game.getVictoryScreen());
            VictoryScreen.setWiningPlayer(win.getPlayer());

        }
    }
}
