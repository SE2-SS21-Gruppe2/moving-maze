package se2.gruppe2.moving_maze.gameBoard;

import se2.gruppe2.moving_maze.tile.Tile;

public class GameBoard {
    public static final int TILES_PER_EDGE = 7;
    Tile[][] board;

    public GameBoard() {
        board = new Tile[TILES_PER_EDGE][TILES_PER_EDGE];
    }


    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public Tile[][] getBoard() {
        return this.board;
    }

}
