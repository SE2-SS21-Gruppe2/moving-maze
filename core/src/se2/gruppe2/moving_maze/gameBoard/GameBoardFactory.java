package se2.gruppe2.moving_maze.gameBoard;

import se2.gruppe2.moving_maze.tile.Tile;
import se2.gruppe2.moving_maze.tile.TileFactory;

public class GameBoardFactory {

    public static GameBoard getLOnlyBoard() {
        GameBoard gb = new GameBoard();

        Tile[][] board = gb.getBoard();

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = TileFactory.getLTile();
            }
        }

        return gb;
    }

    public static GameBoard getStandardGameBoard(){
        return new GameBoard();
    }

    public static GameBoard getEasyGameBoard(){
        return new GameBoard();
    }
}
