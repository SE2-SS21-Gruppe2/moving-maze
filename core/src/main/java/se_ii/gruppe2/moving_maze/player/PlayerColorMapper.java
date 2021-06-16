package se_ii.gruppe2.moving_maze.player;

import com.badlogic.gdx.Game;
import se_ii.gruppe2.moving_maze.gameboard.GameBoard;
import se_ii.gruppe2.moving_maze.helperclasses.TextureLoader;
import se_ii.gruppe2.moving_maze.item.Position;

public class PlayerColorMapper {

    private PlayerColorMapper() { }

    /** Delivers the correct texture path for a player depending on its PlayerColor
     * @param color to map
     * @return texture pth for the texture corresponding to the Playercolor
     */
    public static String getTexturePathByPlayerColor(PlayerColor color) {
        switch (color) {
            case RED:
                return "gameboard/pawn_red.png";

            case YELLOW:
                return "gameboard/pawn_yellow.png";

            case GREEN:
                return "gameboard/pawn_green.png";

            case BLUE:
                return "gameboard/pawn_blue.png";

            default:
                return null;
        }
    }

    /**
     * Returns initial coordinates in form of a Position-object for each color.
     * @param color to check
     * @return initial position
     */
    public static Position getInitialPositionByColor(PlayerColor color) {
        // TODO: also use this in the GameBoard factory
        Position pos;

        switch(color) {
            case RED:
                // lower right corner
                pos = new Position(GameBoard.TILES_PER_EDGE-1, 0);
                break;

            case BLUE:
                // lower left corner
                pos = new Position(0, 0);
                break;

            case GREEN:
                // upper right corner
                pos = new Position(GameBoard.TILES_PER_EDGE-1, GameBoard.TILES_PER_EDGE-1);
                break;

            case YELLOW:
                // upper left corner
                pos = new Position(0, GameBoard.TILES_PER_EDGE-1);
                break;

            default:
                pos = null;
        }

        return pos;
    }

    /**
     * get the offset by a color, so that 2 pawns are not drawn on top of each other
     * @param color of the player
     * @return the Position of the pawn by the color
     */
    public static Position getOffsetByColor(PlayerColor color) {
        int offsetFactor = 8;
        switch (color) {
            case RED:
                return new Position(TextureLoader.TILE_EDGE_SIZE/offsetFactor, -TextureLoader.TILE_EDGE_SIZE/offsetFactor);

            case YELLOW:
                return new Position(-TextureLoader.TILE_EDGE_SIZE/offsetFactor, TextureLoader.TILE_EDGE_SIZE/offsetFactor);

            case GREEN:
                return new Position(TextureLoader.TILE_EDGE_SIZE/offsetFactor, TextureLoader.TILE_EDGE_SIZE/offsetFactor);

            case BLUE:
                return new Position(-TextureLoader.TILE_EDGE_SIZE/offsetFactor, -TextureLoader.TILE_EDGE_SIZE/offsetFactor);

            default:
                return null;
        }
    }
}