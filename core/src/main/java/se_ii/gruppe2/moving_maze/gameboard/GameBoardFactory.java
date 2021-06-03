package se_ii.gruppe2.moving_maze.gameboard;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import se_ii.gruppe2.moving_maze.item.ItemFactory;
import se_ii.gruppe2.moving_maze.item.ItemLogical;
import se_ii.gruppe2.moving_maze.item.Position;
import se_ii.gruppe2.moving_maze.tile.Tile;
import se_ii.gruppe2.moving_maze.tile.TileFactory;

import java.security.SecureRandom;

public class GameBoardFactory {

    /**
     * itemPath, Array of All Items
     * itemPathCounter: counter to iterate
     */
    private static final float[] possibleRotationAngles = {0, 90, 270, 180};
    private static final ItemLogical[] items = ItemFactory.itemArray();
    private static int itemPathCounter= 0;
    private static final SecureRandom  random= new SecureRandom();
    private static int amountOfLTiles;
    private static int amountOfTTiles;
    private static int amountOfITiles;

    // static utility class - prevent instantiation
    private GameBoardFactory() {}

    /**
     * Int L,T,I are responsible for how many Tile of a
     * Type are on the Board. The sum of I,T,L = 46
     * (7x7=49 - 4 corners + 1 extra tile).
     * The corner Parts are note included, because they are always
     * L Tiles.
     *
     * shuffleArray shuffles the itemPath, so every game items are newly organized.
     */
    public static GameBoard getStandardGameBoard(){
        var gb = new GameBoard();
        Tile[][] board = gb.getBoard();
        amountOfLTiles =16;
        amountOfTTiles =18;
        amountOfITiles =12;
        buildBoard(board);
        gb.setExtraTile(getRandomTile());
        return gb;
    }

    public static GameBoard getEasyGameBoard(){
        var gb = new GameBoard();
        Tile[][] board = gb.getBoard();
        amountOfLTiles =10;
        amountOfTTiles =26;
        amountOfITiles =10;
        buildBoard(board);
        gb.setExtraTile(getRandomTile());
        return gb;
    }


    /**
     *for, for: looks at every board
     * is Corner: when there is a Corner, It mast be a L tile.
     */
    private static void buildBoard(Tile[][] board){
        var itemOnTile =false;
        var cornerRotation =0;
        for(var i = 0; i < board.length; i++) {
            for(var j = 0; j < board[i].length; j++) {
                if (isCorner(i,j)){
                    board[i][j] = TileFactory.getLTile().applyRotation(possibleRotationAngles[cornerRotation++]);
                }
                else {
                    board[i][j]= getRandomTile();
                }
                if(itemOnTile){
                    Position position= new Position(i,j);
                    items[itemPathCounter].setPosition(position);
                    board[i][j].setItem(items[itemPathCounter++]);
                    itemOnTile=false;
                }
                else itemOnTile=true;
            }
        }

        itemPathCounter = 0;

        // Overwrite the corner-tiles with the corresponding start-tiles
        setStartTiles(board);
    }


    /**
     *Checks, if a Tile is a corner-element
     */
    private static boolean isCorner(int x , int y){
        return (x == 0 || x == 6) && (y == 0 || y == 6);
    }

    /**
     *turns the tile to a random degree
     */
    private static float getRandomRotationAngle() {
        return possibleRotationAngles[random.nextInt(possibleRotationAngles.length)];
    }


    /**
     *L, T, I: Tells how many Tiles  are left.
     * When a Tile is empty, it can't be placed on the board anymore.
     * Therefore a new Random number is needed (while)
     */
   private static Tile getRandomTile(){
        while (true){
            var randomTile= random.nextInt(3);
            if(randomTile==0 && amountOfLTiles !=0){
                amountOfLTiles--;
                return TileFactory.getLTile().applyRotation(getRandomRotationAngle());
            }
            else if(randomTile==1 && amountOfTTiles != 0){
                amountOfTTiles--;
               return TileFactory.getTTile().applyRotation(getRandomRotationAngle());
            }
            else if (randomTile==2 && amountOfITiles != 0) {
                amountOfITiles--;
                return TileFactory.getITile().applyRotation(getRandomRotationAngle());

            }
        }
    }


    /**
     * Set start-tiles on the giveb gameboard.
     */
    private static void setStartTiles(Tile[][] boardTiles) {

        // upper left
        boardTiles[GameBoard.TILES_PER_EDGE-1][0] = TileFactory.getYellowStartTile().applyRotation(270f);

        // upper right
        boardTiles[GameBoard.TILES_PER_EDGE-1][GameBoard.TILES_PER_EDGE-1] = TileFactory.getGreenStartTile().applyRotation(180f);

        // lower right
        boardTiles[0][GameBoard.TILES_PER_EDGE-1] = TileFactory.getRedStartTile().applyRotation(90f);

        // lower left
        boardTiles[0][0] = TileFactory.getBlueStartTile();

    }

}
