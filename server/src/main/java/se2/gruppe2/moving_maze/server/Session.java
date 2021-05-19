package se2.gruppe2.moving_maze.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;
import se2.gruppe2.moving_maze.gameState.GameStateHandler;
import se2.gruppe2.moving_maze.player.Player;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private static final int MAX_PLAYERS = 4;
    private String key;
    private HashMap<Player, Connection> players;
    private GameStateHandler state;

    public Session(String key) {
        this.key = key;
        players = new HashMap<>();
        // TODO: use a static INIT function to initialize the game-state
        state = new GameStateHandler();
        state.setSessionCode(this.key);
    }

    /**
     * Add player to session
     * @param player to add
     * @throws IllegalStateException in case the amount of players is already maxed out
     */
    public void addPlayer(Player player, Connection con) {
        if(players.size() < MAX_PLAYERS) {
            players.put(player, con);
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Send the current session state to all stored players.
     */
    public void sendStateToPlayers() {

        Player currentPlayer;
        Connection currentConnection;
        for(Map.Entry<Player, Connection> entry : players.entrySet()) {

            currentPlayer = entry.getKey();
            currentConnection = entry.getValue();

            if(currentPlayer != null && currentConnection != null) {
                Log.info("(" + key + ") Sending gamestate update to player '" + currentPlayer.getName() + "'");
                currentConnection.sendTCP(state);
            } else {
                Log.info("(" + key + ") Player or connection == NULL; not distributing gamestate update");
            }
        }

        Log.info("(" + key + ") State update finished");
    }

    // GETTER & SETTER
    public String getKey() {
        return key;
    }

    public HashMap<Player, Connection> getPlayers() {
        return players;
    }

    public GameStateHandler getState() {
        return state;
    }

    public void setState(GameStateHandler state) {
        this.state = state;
    }

}
