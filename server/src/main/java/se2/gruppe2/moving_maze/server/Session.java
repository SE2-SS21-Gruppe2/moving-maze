package se2.gruppe2.moving_maze.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;
import se2.gruppe2.moving_maze.gameState.GameStateHandler;
import se2.gruppe2.moving_maze.network.messages.in.UpdateConnectedPlayersConfirmation;
import se2.gruppe2.moving_maze.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Session {
    private static final int MAX_PLAYERS = 4;
    private String key;
    private HashMap<Player, Connection> players;
    private HashMap<Player, Connection> lobbyHost;
    private GameStateHandler state;

    public Session(String key) {
        this.key = key;
        players = new HashMap<>();
        lobbyHost = new HashMap<>();
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
            sendConnectedPlayersToHost();
        } else {
            throw new IllegalStateException();
        }
    }

    private void sendConnectedPlayersToHost() {
        ArrayList<String> connectedPlayers = new ArrayList<>();
        if (!players.isEmpty() && !lobbyHost.isEmpty()){
            for (Map.Entry<Player,Connection> entry : players.entrySet()){
                if ( !lobbyHost.containsKey(entry.getKey())){
                    connectedPlayers.add(entry.getKey().getName());
                }
            }
            lobbyHost.entrySet().iterator().next().getValue().sendTCP(new UpdateConnectedPlayersConfirmation(connectedPlayers));
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

    public int getNumberOfPlayersInSession() {
        int num = 0;
        for (Map.Entry<Player, Connection> p : players.entrySet()) {
            num++;
        }
        return num;
    }

    public HashMap<Player, Connection> getLobbyHost() {
        return lobbyHost;
    }

    public void setLobbyHost(Player player, Connection con) {
        lobbyHost.put(player, con);
    }

    public void removePlayer(Player player, Connection con) {


        Player p;
        Iterator itr = players.keySet().iterator();
        while (itr.hasNext()){
            p = (Player)itr.next();
            if (p.getName().equals(player.getName()) && con.equals(players.get(p))){
                itr.remove();
                break;
            }
        }
        sendConnectedPlayersToHost();
    }
}
