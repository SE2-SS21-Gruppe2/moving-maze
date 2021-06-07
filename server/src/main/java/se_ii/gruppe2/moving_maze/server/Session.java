package se_ii.gruppe2.moving_maze.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;
import se_ii.gruppe2.moving_maze.gamestate.GameStateHandler;
import se_ii.gruppe2.moving_maze.item.ItemLogical;
import se_ii.gruppe2.moving_maze.network.messages.in.UpdateConnectedPlayersConfirmation;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.player.PlayerColor;

import java.util.*;

public class Session {
    public static final int MAX_PLAYERS = 4;
    private String key;
    private HashMap<Player, Connection> players;
    private HashMap<Player, Connection> lobbyHost;
    private GameStateHandler state;
    private Stack<PlayerColor> availableColors;

    public static final String LOBBY_HOST_TMP_NAME = "temp_SessionCreator";

    public Session(String key) {
        this.key = key;
        players = new HashMap<>();
        lobbyHost = new HashMap<>();
        state = new GameStateHandler();
        state.setSessionCode(this.key);
        initColors();
    }

    /**
     * Add player to session and return the color that has been assigned
     * @param player to add
     * @throws IllegalStateException in case the amount of players is already maxed out
     */
    public PlayerColor addPlayer(Player player, Connection con) {
        if(players.size() < MAX_PLAYERS) {
            player.setColor(availableColors.pop());
            players.put(player, con);
            state.addPlayer(player);
            sendConnectedPlayersToHost();
            Log.info("Assigned player the color " + player.getColor().toString());
            return player.getColor();
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Updates the name of the lobby-host in the respective gamestate object.
     * @param name
     */
    public void updateLobbyHostName(String name) {

        for(Player p : state.getPlayers()) {
            if(p.getName().equals(LOBBY_HOST_TMP_NAME)) {
                p.setName(name);
                Log.info("Updated player-name of lobbyhost to '" + name + "'");
            }
        }

    }

    /**
     * Shuffles the player-array to randomize turn-orders
     */
    public void randomizePlayerTurns() {
        Collections.shuffle(state.getPlayers());
        Log.info("Randomized order of players: ");
        int counter = 1;
        for(Player p : state.getPlayers()) {
            Log.info(p.getName());
        }
    }

    /**
     * Distribute the client-generated list of items to the players
     * TODO: check if spread evenly
     * @param items to distribute
     */
    public void initializeItems(ArrayList<ItemLogical> items) {
        Log.info("Assigning " + items.size() + " items to players");

        // distribute items
        int playerIdx = 0;
        Player currentPlayer;
        while(items.size() > 0) {
            currentPlayer = getState().getPlayers().get(playerIdx);
            currentPlayer.getCardsToFind().push(items.get(0));
            items.remove(0);
            playerIdx = ++playerIdx%getState().getPlayers().size();
        }

        // set the first item for each player to be the top of the stack
        for(Player p : getState().getPlayers()) {
            p.setCurrentCard(p.getCardsToFind().pop());
        }
    }

    /**
     * Update the host with all players that have been added to the session.
     */
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

    /**
     * Initialize the available colors
     */
    private void initColors() {
        availableColors = new Stack<>();
        availableColors.add(PlayerColor.GREEN);
        availableColors.add(PlayerColor.RED);
        availableColors.add(PlayerColor.BLUE);
        availableColors.add(PlayerColor.YELLOW);
    }

    /**
     * Resets and synchronizes the players in the managed state with the player stored on session-level.
     */
    public void syncSessionPlayersWithState() {
        this.state.getPlayers().clear();

        for(Map.Entry<Player, Connection> entry : players.entrySet()) {
            state.addPlayer(entry.getKey());
        }
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
