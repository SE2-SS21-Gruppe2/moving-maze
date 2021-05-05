package se2.gruppe2.moving_maze.gameBoard;

import com.badlogic.gdx.math.MathUtils;

import se2.gruppe2.moving_maze.item.Position;
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
        GameBoard gb = new GameBoard();

        Tile[][] board = gb.getBoard();
        int L=20;
        int T=17;
        int I=12;

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if (isCorner(i,j)){
                    board[i][j] = TileFactory.getLTile();
                    L--;
                }
                else {
                    int randomTile= MathUtils.random.nextInt(3);
                    if(randomTile==0 && L!=0){
                        board[i][j] = TileFactory.getLTile();
                        L--;
                    }
                    else if(randomTile==1 && T != 0){
                        board[i][j] = TileFactory.getTTile();
                        T--;
                    }
                    else {
                        board[i][j] = TileFactory.getITile();
                        I--;
                    }
                }
            }
        }
        return gb;
    }

    public static GameBoard getEasyGameBoard(){
        return new GameBoard();
    }

    private static boolean isCorner(int x , int y){
        if (x==0 || x==6){
            if(y==0 || y==6){
                return true;
            }
        }
        return false;
    }
}
