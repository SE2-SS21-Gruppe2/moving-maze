package se2.gruppe2.moving_maze.server;

import se2.gruppe2.moving_maze.player.Player;

import java.util.ArrayList;

public class Session {
    private String key;
    private ArrayList<Player> players;

    public Session(String key) {
        this.key = key;
        players = new ArrayList<>();
    }

    /**
     * Add player to session
     * @param player to add
     * @return true if adding was successful, false if session was already full
     */
    public boolean addPlayer(Player player) {
        if(players.size() < 4) {
            players.add(player);
            return true;
        } else {
            return false;
        }
    }
}
