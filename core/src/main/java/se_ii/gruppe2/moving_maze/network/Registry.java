package se_ii.gruppe2.moving_maze.network;

import com.esotericsoftware.kryo.Kryo;
import se_ii.gruppe2.moving_maze.gameBoard.GameBoard;
import se_ii.gruppe2.moving_maze.gameState.ChatMessage;
import se_ii.gruppe2.moving_maze.gameState.GameState;
import se_ii.gruppe2.moving_maze.gameState.GameStateHandler;
import se_ii.gruppe2.moving_maze.item.Card;
import se_ii.gruppe2.moving_maze.item.ItemLogical;
import se_ii.gruppe2.moving_maze.item.Position;
import se_ii.gruppe2.moving_maze.network.messages.in.JoinRequestConfirmation;
import se_ii.gruppe2.moving_maze.network.messages.in.RequestProcessError;
import se_ii.gruppe2.moving_maze.network.messages.out.JoinRequest;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.player.PlayerRole;
import se_ii.gruppe2.moving_maze.tile.ITile;
import se_ii.gruppe2.moving_maze.tile.LTile;
import se_ii.gruppe2.moving_maze.tile.TTile;
import se_ii.gruppe2.moving_maze.tile.Tile;

import java.util.Stack;

public class Registry {

    // utility class - prevent instantiation
    private Registry() {}

    /**
     * Registers all classes that are known to the registry
     * @param kryo to registrate the classes on
     */
    public static void registerClassesOnKryo(Kryo kryo) {
        kryo.register(String.class);
        kryo.register(Stack.class);
        kryo.register(JoinRequest.class);
        kryo.register(Player.class);
        kryo.register(Position.class);
        kryo.register(Card.class);
        kryo.register(PlayerRole.class);
        kryo.register(ItemLogical.class);
        kryo.register(RequestProcessError.class);
        kryo.register(GameStateHandler.class);
        kryo.register(GameState.class);
        kryo.register(ChatMessage.class);
        kryo.register(GameBoard.class);
        kryo.register(Tile.class);
        kryo.register(LTile.class);
        kryo.register(TTile.class);
        kryo.register(ITile.class);
        kryo.register(Tile[].class);
        kryo.register(Tile[][].class);
        kryo.register(JoinRequestConfirmation.class);
    }

}
