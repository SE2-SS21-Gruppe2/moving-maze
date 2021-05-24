package gameboard;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Method;
import java.util.Random;

import se_ii.gruppe2.moving_maze.gameboard.GameBoard;
import se_ii.gruppe2.moving_maze.gameboard.GameBoardFactory;
import se_ii.gruppe2.moving_maze.tile.Tile;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameBoardFactoryTest {

    private static GameBoard gb;
    private static GameBoardFactory gameBoardFactory;



    @BeforeEach
    public void setup(){
    }

    @Test
    public void isCornerTest() throws Exception {
        Method isCornerMethod= GameBoardFactory.class.getDeclaredMethod("isCorner", int.class, int.class);
        isCornerMethod.setAccessible(true);
        boolean corner00= (boolean) isCornerMethod.invoke(gameBoardFactory,0,0);
        boolean corner06=(boolean) isCornerMethod.invoke(gameBoardFactory,0,6);
        boolean corner60=(boolean) isCornerMethod.invoke(gameBoardFactory,6,0);
        boolean corner66=(boolean) isCornerMethod.invoke(gameBoardFactory,6,6);
        assertEquals(true,corner00);
        assertEquals(true,corner06);
        assertEquals(true,corner60);
        assertEquals(true,corner66);
    }

}
