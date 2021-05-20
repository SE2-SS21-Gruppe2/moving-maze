package se2.gruppe2.moving_maze.network.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import se2.gruppe2.moving_maze.MovingMazeGame;
import se2.gruppe2.moving_maze.network.messages.in.CreateSessionRequestConfirmation;

public class CreateSessionConfirmationListener extends Listener {

    @Override
    public void received(Connection con, Object obj) {
        if (obj instanceof CreateSessionRequestConfirmation) {
            CreateSessionRequestConfirmation csrc = (CreateSessionRequestConfirmation) obj;
            Gdx.app.log("NetworkClient/CreateSessionRequestConfirmation", "Created Session with session key '" + csrc.getSessionKey() + "'");
            var game = MovingMazeGame.getGameInstance();
            game.setSessionKey(csrc.getSessionKey());
        }
    }

}
