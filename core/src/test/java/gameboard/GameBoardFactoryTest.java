package gameboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import se_ii.gruppe2.moving_maze.gameboard.GameBoard;
import se_ii.gruppe2.moving_maze.gameboard.GameBoardFactory;
import se_ii.gruppe2.moving_maze.tile.ITile;
import se_ii.gruppe2.moving_maze.tile.Tile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GameBoardFactoryTest {

    @Mock
    GameBoardFactory gb;

    @BeforeEach
    public void setup(){

    }

    @Test
    public void getRandomTileTest(){

    }
}
