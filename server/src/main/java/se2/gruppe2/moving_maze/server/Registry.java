package se2.gruppe2.moving_maze.server;

import com.esotericsoftware.kryo.Kryo;
import se2.gruppe2.moving_maze.network.messages.JoinRequest;
import se2.gruppe2.moving_maze.player.Player;
import se2.gruppe2.moving_maze.player.PlayerRole;

import java.util.ArrayList;
import java.util.Stack;

public class Registry {
    /**
     * Registers all classes that are known to the registry
     * @param kryo to registrate the classes on
     */
    public static void registerClassesOnKryo(Kryo kryo) {
        /*
        kryo.register(JoinRequest.class);
        kryo.register(Player.class);
        kryo.register(String.class);
        kryo.register(Stack.class);
        kryo.register(PlayerRole.class);
        */
        kryo.register(String.class);
    }

}
