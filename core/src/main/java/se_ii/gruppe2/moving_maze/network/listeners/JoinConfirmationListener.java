package se_ii.gruppe2.moving_maze.network.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.network.messages.in.JoinRequestConfirmation;

public class JoinConfirmationListener extends Listener {

    @Override
    public void received(Connection con, Object obj) {
        if(obj instanceof JoinRequestConfirmation) {
            JoinRequestConfirmation jrc = (JoinRequestConfirmation) obj;
            var game = MovingMazeGame.getGameInstance();
            game.setSessionKey(jrc.getSessionKey());
            game.getPlayer().setColor(jrc.getAssignedColor());
            Gdx.app.log("NetworkClient/JoinConfirmation", "Received join confirmation for session '" + jrc.getSessionKey() +
                    "', assigned color: " + jrc.getAssignedColor());
        }
    }
}
