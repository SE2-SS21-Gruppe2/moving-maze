package se_ii.gruppe2.moving_maze.gamestate;

import com.badlogic.gdx.math.Vector2;
import se_ii.gruppe2.moving_maze.gameboard.GameBoard;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.tile.Tile;

import java.util.ArrayList;

public class GameStateHandler {
    private String sessionCode;
    private ArrayList<Player> players;
    private Player currentPlayerOnTurn;
    private GameBoard board;
    private GameState gameState;
    private ChatMessage[] chat;
    private Vector2 lastInsertPosition;

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

    public Tile getExtraTile() {
        return board.getExtraTile();
    }

    public void setExtraTile(Tile extraTile) {
        board.setExtraTile(extraTile);
    }

    public Vector2 getLastInsertPosition() {
        return lastInsertPosition;
    }

    public void setLastInsertPosition(Vector2 lastInsertPosition) {
        this.lastInsertPosition = lastInsertPosition;
    }
}
