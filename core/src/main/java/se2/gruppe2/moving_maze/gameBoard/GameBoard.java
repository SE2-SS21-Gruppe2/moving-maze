package se2.gruppe2.moving_maze.gameBoard;

import se2.gruppe2.moving_maze.tile.Tile;

public class GameBoard {
    public static int tilesPerEdge = 7;
    Tile[][] board;

    public GameBoard() {
        board = new Tile[tilesPerEdge][tilesPerEdge];
    }

    public void setTileOnBoard(int x, int y, Tile tile) {
        if(x > tilesPerEdge || y > tilesPerEdge)
            throw new IllegalArgumentException("Invalid indexes");

        board[x][y] = tile;
    }

    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public Tile[][] getBoard() {
        return this.board;
    }

}
