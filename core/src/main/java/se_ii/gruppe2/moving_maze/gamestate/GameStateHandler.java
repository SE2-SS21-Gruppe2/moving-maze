package se_ii.gruppe2.moving_maze.gamestate;

import com.badlogic.gdx.math.Vector2;
import se_ii.gruppe2.moving_maze.audio.AudioNetworkManager;
import se_ii.gruppe2.moving_maze.gameboard.GameBoard;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.tile.Tile;

import java.util.ArrayList;

public class GameStateHandler {
    private String sessionCode;
    private ArrayList<Player> players;
    private Player currentPlayerOnTurn;
    private Player previousPlayer;
    private int playerOnTurnIndex;
    private GameBoard board;
    private GamePhase gamePhase;
    private ChatMessage[] chat;
    private Vector2 lastInsertPosition;
    private AudioNetworkManager audioNetwork;

    public GameStateHandler() {
        players = new ArrayList<>();
        audioNetwork = AudioNetworkManager.getInstance();

    }

    /**
     * Returns the player object(s) whose position(s) are matching with the given x and y coordinates.
     * The coordinates have to be seen as index of a Tile on the gameboard.
     *
     * @param y -index (row) of the Tile in the gameboard-grid
     * @param x -index (column) of the Tile in the gameboard-grid
     * @return player that are on the tile
     */
    public ArrayList<Player> playersOnTile(int y, int x) {
        ArrayList<Player> foundPlayers = new ArrayList<>();

        for (Player p : players) {
            if (p.getPos().getX() == x && p.getPos().getY() == y) {
                foundPlayers.add(p);
            }
        }

        return foundPlayers;
    }

    /**
     * Can be called when a player finished its turn and the next player should be chosen.
     *
     * @return the chosen player
     */
    public Player updatePlayerOnTurn() {
        if (players.size() == 0)
            throw new IllegalStateException("Cannot update next player if no players are in the session");

        if (currentPlayerOnTurn == null) {
            playerOnTurnIndex = 0;
            currentPlayerOnTurn = players.get(playerOnTurnIndex);
        } else {
            previousPlayer = currentPlayerOnTurn;
            currentPlayerOnTurn = players.get(++playerOnTurnIndex % (players.size()));
        }
        currentPlayerOnTurn.getCheatFunction().setLaidCardDownPreviousMove(false);
        return currentPlayerOnTurn;
    }


    /**
     * Initializes the game phase when the game is started
     */
    public void initGamePhase() {
        gamePhase = new GamePhase();
    }

    public void completePhase() {
        GamePhaseType nextPhase = gamePhase.nextPhase();

        if (nextPhase == GamePhaseType.END_TURN) {
            updatePlayerOnTurn();
            gamePhase.nextPhase();
        }
    }

    /**
     * get the Player by his name
     *
     * @param name of the player
     * @return Player if name exists. Otherwise return null
     */
    public Player getPlayerByName(String name) {

        for (Player p : players) {
            if (p.getName().equals(name))
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

    public Player getPreviousPlayer() {
        return this.previousPlayer;
    }

    public GamePhaseType getGamePhase() {
        return gamePhase.getPhaseType();
    }

    public boolean isMyTurn(Player player) {
        return players.get(playerOnTurnIndex % players.size()).getId() == player.getId();
    }

    public AudioNetworkManager getAudioNetwork() {
        return audioNetwork;
    }

    public void setAudioNetwork(AudioNetworkManager audioNetwork) {
        this.audioNetwork = audioNetwork;
    }

    /**
     * Can be called to get the current game phase in plain text
     * @return current game phase as plain text
     */
    public String getGamePhaseText() {
        switch(this.gamePhase.getPhaseType()){
            case INSERT_TILE: return "Extra-Teil einsetzen";
            case MOVE_PLAYER: return "Spielfigur bewegen";
            //case DRAW_CARD: return "Karte ziehen";
            case PICKUP_ITEM: return "Symbol aufnehmen";
            default: return "";
        }
    }
}
