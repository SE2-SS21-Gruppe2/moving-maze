package se2.gruppe2.moving_maze.gameBoard;

import se2.gruppe2.moving_maze.tile.Tile;

public class GameBoard {
    public static final int tilesPerEdge = 7;
    Tile[][] board;

    public GameBoard() {
        board = new Tile[tilesPerEdge][tilesPerEdge];
    }


    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public Tile[][] getBoard() {
        return this.board;
    }

}
