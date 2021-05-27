package se_ii.gruppe2.moving_maze.network;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import se_ii.gruppe2.moving_maze.gamestate.GamePhase;
import se_ii.gruppe2.moving_maze.network.messages.in.CreateSessionRequestConfirmation;
import se_ii.gruppe2.moving_maze.network.messages.in.UpdateConnectedPlayersConfirmation;
import se_ii.gruppe2.moving_maze.network.messages.out.*;
import se_ii.gruppe2.moving_maze.gameboard.GameBoard;
import se_ii.gruppe2.moving_maze.gamestate.ChatMessage;
import se_ii.gruppe2.moving_maze.gamestate.GamePhaseType;
import se_ii.gruppe2.moving_maze.gamestate.GameStateHandler;
import se_ii.gruppe2.moving_maze.item.Card;
import se_ii.gruppe2.moving_maze.item.ItemLogical;
import se_ii.gruppe2.moving_maze.item.Position;
import se_ii.gruppe2.moving_maze.network.messages.in.JoinRequestConfirmation;
import se_ii.gruppe2.moving_maze.network.messages.in.RequestProcessError;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.player.PlayerColor;
import se_ii.gruppe2.moving_maze.player.PlayerRole;
import se_ii.gruppe2.moving_maze.tile.*;

import java.util.ArrayList;
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
        kryo.register(GamePhaseType.class);
        kryo.register(GamePhaseType[].class);
        kryo.register(ChatMessage.class);
        kryo.register(GameBoard.class);
        kryo.register(Tile.class);
        kryo.register(LTile.class);
        kryo.register(TTile.class);
        kryo.register(ITile.class);
        kryo.register(Tile[].class);
        kryo.register(Tile[][].class);
        kryo.register(JoinRequestConfirmation.class);
        kryo.register(CreateSessionRequest.class);
        kryo.register(CreateSessionRequestConfirmation.class);
        kryo.register(CloseSessionRequest.class);
        kryo.register(UpdateConnectedPlayersConfirmation.class);
        kryo.register(LeaveSessionRequest.class);
        kryo.register(ArrayList.class);
        kryo.register(PlayerColor.class);
        kryo.register(PlayerColor[].class);
        kryo.register(StartTile.class);
        kryo.register(InitGameStart.class);
        kryo.register(Vector2.class);
        kryo.register(GamePhase.class);
    }

}
