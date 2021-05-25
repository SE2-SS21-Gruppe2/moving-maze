package se_ii.gruppe2.moving_maze.gamestate;

import se_ii.gruppe2.moving_maze.gameboard.GameBoard;
import se_ii.gruppe2.moving_maze.player.Player;

import java.util.ArrayList;

public class GameStateHandler {
    private String sessionCode;
    private ArrayList<Player> players;
    private Player currentPlayerOnTurn;
    private int playerOnTurnIndex;
    private GameBoard board;
    private GameState gameState;
    private ChatMessage[] chat;

    public GameStateHandler() {
        players = new ArrayList<>();
    }

    /**
     * Returns the player object(s) whose position(s) are matching with the given x and y coordinates.
     * The coordinates have to be seen as index of a Tile on the gameboard.
     * @param y -index (row) of the Tile in the gameboard-grid
     * @param x -index (column) of the Tile in the gameboard-grid
     * @return player that are on the tile
     */
    public ArrayList<Player> playersOnTile(int y, int x) {
        ArrayList<Player> foundPlayers = new ArrayList<>();

        for(Player p : players) {
            if(p.getPos().getX() == x && p.getPos().getY() == y) {
                foundPlayers.add(p);
            }
        }

        return  foundPlayers;
    }

    /**
     * Can be called when a player finished its turn and the next player should be chosen.
     * @return the chosen player
     */
    public Player updatePlayerOnTurn() {
        if(players.size() == 0) throw new IllegalStateException("Cannot update next player if no players are in the session");

        if(currentPlayerOnTurn == null) {
            playerOnTurnIndex = 0;
            currentPlayerOnTurn = players.get(playerOnTurnIndex);
        } else {
            currentPlayerOnTurn = players.get(++playerOnTurnIndex % (players.size()-1));
        }

        return currentPlayerOnTurn;
    }

    // GETTER & SETTER
    public GameBoard getBoard() {
        return board;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    public void addPlayer(Player p) {
        this.players.add(p);
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public Player getCurrentPlayerOnTurn() {
        return this.currentPlayerOnTurn;
    }
}
