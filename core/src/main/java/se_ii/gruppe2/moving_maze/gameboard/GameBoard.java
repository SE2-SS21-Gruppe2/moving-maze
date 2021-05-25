package se_ii.gruppe2.moving_maze.gameboard;

import se_ii.gruppe2.moving_maze.tile.Tile;

public class GameBoard {
    public static final int TILES_PER_EDGE = 7;
    Tile[][] board;
    Tile extraTile;

    public GameBoard() {
        board = new Tile[TILES_PER_EDGE][TILES_PER_EDGE];
        extraTile = null;
    }


    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public Tile[][] getBoard() {
        return this.board;
    }

    public Tile getExtraTile() {
        return extraTile;
    }

    public void setExtraTile(Tile extraTile) {
        this.extraTile = extraTile;
    }
}
