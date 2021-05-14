package se2.gruppe2.moving_maze.gameBoard;

import se2.gruppe2.moving_maze.tile.TileLogical;

public class GameBoardLogical {
    public static int tilesPerEdge = 7;
    TileLogical[][] board;

    public GameBoardLogical() {
        board = new TileLogical[tilesPerEdge][tilesPerEdge];
    }

    public void setTileOnBoard(int x, int y, TileLogical tile) {
        if(x > tilesPerEdge || y > tilesPerEdge)
            throw new IllegalArgumentException("Invalid indexes");

        board[x][y] = tile;
    }

    public void setBoard(TileLogical[][] board) {
        this.board = board;
    }

    public TileLogical[][] getBoard() {
        return this.board;
    }

}
