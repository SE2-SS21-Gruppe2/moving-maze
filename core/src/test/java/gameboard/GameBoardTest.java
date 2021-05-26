package gameboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import se_ii.gruppe2.moving_maze.gameboard.GameBoard;
import static org.junit.jupiter.api.Assertions.assertEquals;
import se_ii.gruppe2.moving_maze.tile.LTile;


public class GameBoardTest {

    LTile[][] tiles;

    @BeforeEach
    public void setup(){
        tiles= new LTile[7][7];
    }

    @Test
    public void egeSizeTest(){
        GameBoard gb= new GameBoard();
        for(int i = 0; i<tiles.length; i++){
            assertEquals(tiles[i].length,gb.getBoard()[i].length);
        }
    }


}
