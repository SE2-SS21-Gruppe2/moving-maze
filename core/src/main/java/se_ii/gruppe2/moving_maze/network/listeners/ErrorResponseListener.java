package se_ii.gruppe2.moving_maze.network.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import se_ii.gruppe2.moving_maze.network.messages.in.RequestProcessError;

public class ErrorResponseListener extends Listener {
    @Override
    public void received(Connection con, Object obj) {
        if(obj instanceof RequestProcessError) {
            RequestProcessError rpe = (RequestProcessError) obj;
            Gdx.app.error("NetworkClient/request-error", "[" + rpe.getCategory() + "] " + rpe.getMessage());
        }
    }
}
