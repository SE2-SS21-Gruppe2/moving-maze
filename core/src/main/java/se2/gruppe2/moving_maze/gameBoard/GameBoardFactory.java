package se2.gruppe2.moving_maze.gameBoard;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import se2.gruppe2.moving_maze.item.Item;
import se2.gruppe2.moving_maze.item.Position;
import se2.gruppe2.moving_maze.tile.Tile;
import se2.gruppe2.moving_maze.tile.TileFactory;

import java.util.Random;


public class GameBoardFactory {

    /**
     * itemPath, Array of All Items
     * itemPathCounter: counter to iterate
     */
    private static final float[] possibleRotationAngles = {0, 90, 270, 180};
    private static String[] itemPaths = getFileList();
    private static int itemPathCounter= 0;
    private static Random random= new Random();
    private static int L,T,I;

    /**
     * Int L,T,I are responsible for how many Tile of a
     * Type are on the Board. The sum of I,T,L = 45. The
     * corner Parts are note included, because they are always
     * L Tiles.
     *
     * shuffleArray shuffles the itemPath, so every game items are newly organized.
     */
    public static GameBoard getStandardGameBoard(){
        GameBoard gb = new GameBoard();
        Tile[][] board = gb.getBoard();
        L=16;
        T=17;
        I=12;
        shuffleArray();
        buildBoard(L,T,I,board);
        return gb;
    }

    public static GameBoard getEasyGameBoard(){
        GameBoard gb = new GameBoard();
        Tile[][] board = gb.getBoard();
        L=10;
        T=25;
        I=10;
        shuffleArray();
        buildBoard(L,T,I,board);
        return gb;
    }


    /**
     *for, for: looks at every board
     * is Corner: when there is a Corner, It mast be a L tile.
     */
    private static void buildBoard(int L, int T, int I, Tile[][] board){
        boolean itemOnTile =false;
        int cornerRotation =0;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if (isCorner(i,j)){
                    board[i][j] = TileFactory.getLTile().applyRotation(possibleRotationAngles[cornerRotation++]);
                }
                else {
                    board[i][j]= getRandomTile();
                }
                if(itemOnTile){
                    board[i][j].setItem(buildItem(i,j));
                    itemOnTile=false;
                }
                else itemOnTile=true;
            }
        }
    }


    /**
     *Checks, if a Tile is a corner-element
     */
    private static boolean isCorner(int x , int y){
        if (x==0 || x==6){
            if(y==0 || y==6){
                return true;
            }
        }
        return false;
    }

    /**
     *turns the tile to a random degree
     */
    private static float getRandomRotationAngle() {
        return possibleRotationAngles[random.nextInt(possibleRotationAngles.length)];
    }

    /**
     *Gets all components to create an item
     */
    private static Item buildItem(int x, int y){
        Position position = new Position();
        position.setPosition(x, y);
        String path= itemPaths[itemPathCounter++];
        return new Item(path, position,false);
    }

    /**
     *LibGDX has the FileHandel Class. With this class you can give a Pathname. When u call then the
     *.list function, you get a List off all Files in the Folder, that were selectet.
     * List element if android:I/System.out: items/(image Element)
     * List element if desktop: android/assets/items/(image Element)
     *
     * The second for makes the item instances game per game random.
     */
    private static String[] getFileList(){
        FileHandle handle;
        if(Gdx.app.getType() == Application.ApplicationType.Android){
            handle= Gdx.files.internal("items");
        }
        else{
            handle= Gdx.files.internal("android/assets/items");
        }
        String[] fileNames= new String[handle.list().length];
        int i=0;
        for (FileHandle file : handle.list()) {
            fileNames[i]=file.toString();
            i++;
        }

        return fileNames;
    }

    private static void shuffleArray(){
        for (int j = 0; j < itemPaths.length; j++) {
            int swapIndex= random.nextInt(itemPaths.length);
            String temp = itemPaths[swapIndex];
            itemPaths[swapIndex]= itemPaths[j];
            itemPaths[j]=temp;
        }
    }


    /**
     *L, T, I: Tells how many Tiles  are left.
     * When a Tile is empty, it can't be placed on the board anymore.
     * Therefore a new Random number is needed (while)
     */
    private static Tile getRandomTile(){
        while (true){
            int randomTile= random.nextInt(3);
            if(randomTile==0 && L!=0){
                L--;
                return TileFactory.getLTile().applyRotation(getRandomRotationAngle());
            }
            else if(randomTile==1 && T != 0){
                T--;
               return TileFactory.getTTile().applyRotation(getRandomRotationAngle());
            }
            else if (randomTile==2 && I != 0) {
                I--;
                return TileFactory.getITile().applyRotation(getRandomRotationAngle());

            }
        }
    }

}
