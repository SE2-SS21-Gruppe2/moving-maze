package se_ii.gruppe2.moving_maze.gamestate;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.minlog.Log;
import se_ii.gruppe2.moving_maze.gameboard.GameBoard;
import se_ii.gruppe2.moving_maze.item.ItemLogical;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.tile.Tile;

import java.util.ArrayList;

public class GameStateHandler {
    private String sessionCode;
    private ArrayList<Player> players;
    private Player currentPlayerOnTurn;
    private int playerOnTurnIndex;
    private GameBoard board;
    private GamePhase gamePhase;
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
            currentPlayerOnTurn = players.get(++playerOnTurnIndex % (players.size()));
        }

        return currentPlayerOnTurn;
    }


    /**
     * Initializes the game phase when the game is started
     */
    public void initGamePhase(){
        gamePhase = new GamePhase();
    }

    public void completePhase(){
        GamePhaseType nextPhase = gamePhase.nextPhase();
        if (nextPhase == GamePhaseType.END_TURN){
            updatePlayerOnTurn();
            gamePhase.nextPhase();
        }
    }

    public Player getPlayerByName(String name) {

        for(Player p : players) {
            if(p.getName().equals(name))
                return p;
        }

        return null;
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

    public Player getCurrentPlayerOnTurn() {
        return this.currentPlayerOnTurn;
    }

    public GamePhaseType getGamePhase() {
        return gamePhase.getPhaseType();
    }

    public boolean isMyTurn(Player player){
        if (players.get(playerOnTurnIndex % players.size()).getId() == player.getId()){
            return true;
        } else {
            return false;
        }
    }
}
