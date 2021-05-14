package se2.gruppe2.moving_maze.network;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.esotericsoftware.kryo.Kryo;
import se2.gruppe2.moving_maze.gameBoard.GameBoardLogical;
import se2.gruppe2.moving_maze.gameState.ChatMessage;
import se2.gruppe2.moving_maze.gameState.GameState;
import se2.gruppe2.moving_maze.gameState.GameStateHandler;
import se2.gruppe2.moving_maze.item.Card;
import se2.gruppe2.moving_maze.item.ItemLogical;
import se2.gruppe2.moving_maze.item.Position;
import se2.gruppe2.moving_maze.network.messages.in.RequestProcessError;
import se2.gruppe2.moving_maze.network.messages.out.JoinRequest;
import se2.gruppe2.moving_maze.player.Player;
import se2.gruppe2.moving_maze.player.PlayerRole;
import se2.gruppe2.moving_maze.tile.ITile;
import se2.gruppe2.moving_maze.tile.LTile;
import se2.gruppe2.moving_maze.tile.TTile;
import se2.gruppe2.moving_maze.tile.TileLogical;

import java.util.Stack;

public class Registry {
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
        kryo.register(GameBoardLogical.class);
        kryo.register(TileLogical.class);
        kryo.register(LTile.class);
        kryo.register(TTile.class);
        kryo.register(ITile.class);
        kryo.register(TileLogical[].class);
        kryo.register(TileLogical[][].class);
        // TODO: separate sprites from logical representation to reduce sending overhead
        kryo.register(Sprite.class);
        kryo.register(Texture.class);
        kryo.register(Color.class);
        kryo.register(Pixmap.class);
        kryo.register(PixmapTextureData.class);
    }

}
