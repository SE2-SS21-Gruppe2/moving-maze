package se2.gruppe2.moving_maze.gameBoard;

import com.badlogic.gdx.math.MathUtils;

import se2.gruppe2.moving_maze.item.Item;
import se2.gruppe2.moving_maze.item.Position;
import se2.gruppe2.moving_maze.tile.Tile;
import se2.gruppe2.moving_maze.tile.TileFactory;

import java.util.Random;

public class GameBoardFactory {

    private static final float[] possibleRotationAngles = {0, 90, 180, 270};

    public static GameBoard getLOnlyBoard() {
        GameBoard gb = new GameBoard();

        Tile[][] board = gb.getBoard();

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = TileFactory.getLTile().applyRotation(getRandomRotationAngle());
            }
        }

        return gb;
    }

    public static GameBoard getStandardGameBoard(){
        GameBoard gb = new GameBoard();
        Tile[][] board = gb.getBoard();
        int L=16;
        int T=17;
        int I=12;
        buildBoard(L,T,I,board);
        return gb;
    }

    public static GameBoard getEasyGameBoard(){
        return new GameBoard();
    }


    //TODO: Set different items Images.
    private static void buildBoard(int L, int T, int I, Tile[][] board){
        boolean itemOnTile =false;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if (isCorner(i,j)){
                    board[i][j] = TileFactory.getLTile();
                }
                else {
                    int randomTile= MathUtils.random.nextInt(3);
                    if(randomTile==0 && L!=0){
                        board[i][j] = TileFactory.getLTile().applyRotation(getRandomRotationAngle());
                        L--;
                    }
                    else if(randomTile==1 && T != 0){
                        board[i][j] = TileFactory.getTTile().applyRotation(getRandomRotationAngle());
                        T--;
                    }
                    else {
                        board[i][j] = TileFactory.getITile().applyRotation(getRandomRotationAngle());
                        I--;
                    }
                }
                if(itemOnTile){
                    board[i][j].setItem(buildItem(i,j));
                    itemOnTile=false;
                }
                else itemOnTile=true;
            }
        }
    }


    private static boolean isCorner(int x , int y){
        if (x==0 || x==6){
            if(y==0 || y==6){
                return true;
            }
        }
        return false;
    }

    private static float getRandomRotationAngle() {
        Random random = new Random();
        return possibleRotationAngles[random.nextInt(possibleRotationAngles.length)];
    }

    private static Item buildItem(int x, int y){
        Position position = new Position();
        position.setPosition(x, y);
        return new Item("items/NicosTestItem.jpg", position,false);
    }









}
