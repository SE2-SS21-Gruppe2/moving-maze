package se2.gruppe2.moving_maze.gameBoard;

import se2.gruppe2.moving_maze.tile.Tile;

public class GameBoard {
    public static int tilesPerEdge = 7;
    public static final float gameBoardSize = tilesPerEdge * Tile.tileEdgeSize;
    Tile[][] board;

    // initial position of the first tile
    private float x, y;

    public GameBoard() {
        board = new Tile[tilesPerEdge][tilesPerEdge];
    }


    public void setStartCoordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setBoard(Tile[][] board) {
        this.board = board;
    }

    public Tile[][] getBoard() {
        return this.board;
    }

    private void calcStartCoordinates(){

    }
}
