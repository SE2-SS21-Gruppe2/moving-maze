package se_ii.gruppe2.moving_maze.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import se_ii.gruppe2.moving_maze.network.messages.in.RequestProcessError;
import se_ii.gruppe2.moving_maze.network.messages.out.InitGameStart;
import se_ii.gruppe2.moving_maze.server.Session;
import se_ii.gruppe2.moving_maze.server.SessionManager;

public class InitGameHandler extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof InitGameStart) {
            InitGameStart igs = (InitGameStart) object;

            Session s = SessionManager.getSessionByKey(igs.getKey());

            if(s != null) {
                Log.info("Session '" + igs.getKey() + "' found, initializing game");
                s.updateLobbyHostName(igs.getFinalHostName());
                s.getState().setBoard(igs.getBoard());
                s.getState().initGamePhase();
                s.randomizePlayerTurns();
                s.getState().updatePlayerOnTurn();
                s.initializeItems(igs.getItemsToDistribute(), igs.getItemsPerPlayer());
                s.sendStateToPlayers();
            } else {
                Log.error("Error while trying to start game - session not found");
                connection.sendTCP(new RequestProcessError("InitGame", "Could not start game because no session corresponding to key '" + igs.getKey() + "' has been found."));
            }
        }
    }
}
