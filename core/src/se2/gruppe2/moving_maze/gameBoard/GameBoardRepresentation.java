package se2.gruppe2.moving_maze.gameBoard;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se2.gruppe2.moving_maze.tile.TileLogical;
import se2.gruppe2.moving_maze.tile.TileRepresentation;

public class GameBoardRepresentation {
    private GameBoardLogical logicalGameBoard;
    private TileRepresentation[][] gameBoardRepresentation;

    // initial position of the first tile
    private float origin_x, origin_y;

    public GameBoardRepresentation(GameBoardLogical logicalGameBoard) {
        this.logicalGameBoard = logicalGameBoard;
        gameBoardRepresentation = getRepresentationFromLogicalBoard(this.logicalGameBoard);
        origin_x = 0;
        origin_y = 0;
    }

    public void draw(SpriteBatch batch) {
        float current_x = origin_x;
        float current_y = origin_y;

        for(int i = 0; i < gameBoardRepresentation.length; i++) {
            for(int j = 0; j < gameBoardRepresentation[i].length; j++) {
                gameBoardRepresentation[i][j].draw(batch, current_x, current_y);
                current_x += TileLogical.tileEdgeSize;
            }
            current_y += TileLogical.tileEdgeSize;
            current_x = origin_x;
        }
    }

    /**
     * Generates a board that can be represented on the screen from a logical board
     * @param logicalGameBoard to use as base
     * @return the board-representation containing representation-tiles
     */
    private TileRepresentation[][] getRepresentationFromLogicalBoard(GameBoardLogical logicalGameBoard) {
        TileLogical[][] logicalBoard = logicalGameBoard.getBoard();
        TileRepresentation[][] representation = new TileRepresentation[logicalBoard.length][logicalBoard[0].length];

        for(int i = 0; i <= logicalBoard.length; i++) {
            for(int j = 0; j <= logicalBoard[i].length; j++) {
                representation[i][j] = new TileRepresentation(logicalBoard[i][j]);
            }
        }

        return representation;
    }

    // GETTER & SETTER
    public void setStartCoordinates(float x, float y) {
        this.origin_x = x;
        this.origin_y = y;
    }

    public float getOrigin_x() {
        return origin_x;
    }

    public float getOrigin_y() {
        return origin_y;
    }
}
