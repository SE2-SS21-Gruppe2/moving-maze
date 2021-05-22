package gamestate.turneAction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

import se_ii.gruppe2.moving_maze.gameboard.GameBoardFactory;
import se_ii.gruppe2.moving_maze.gamestate.turnAction.MovePlayer;
import se_ii.gruppe2.moving_maze.item.Position;
import se_ii.gruppe2.moving_maze.tile.ITile;
import se_ii.gruppe2.moving_maze.tile.LTile;
import se_ii.gruppe2.moving_maze.tile.TTile;
import se_ii.gruppe2.moving_maze.tile.Tile;

class MovePlayerTest {

    public static Tile[][] gameBoard;
    public static MovePlayer movePlayer;
    public static Method possibleMoves;
    public static ArrayList<Position> moves;
    public static Position position;

    @BeforeEach
    void setup() throws Exception{
        possibleMoves= MovePlayer.class.getDeclaredMethod("possibleMoves", Position.class, Tile[][].class, ArrayList.class);
        possibleMoves.setAccessible(true);
        position= new Position();
    }

    @Test
    void openBorderTest() throws Exception{
        gameBoard= new Tile[2][2];
        gameBoard[0][0]= new LTile();
        gameBoard[0][1]=new TTile().applyRotation(180f);
        gameBoard[1][0]= new TTile().applyRotation(180f);
        gameBoard[1][1]= new LTile();
        moves=new ArrayList<>();
        position.setPosition(0,0);
        moves=(ArrayList<Position>) possibleMoves.invoke(movePlayer,position,gameBoard,moves);
        assertEquals(3,moves.size());
        assertEquals(0,moves.get(0).getX());
        assertEquals(0,moves.get(0).getY());

        assertEquals(1,moves.get(1).getX());
        assertEquals(0,moves.get(1).getY());

        assertEquals(0,moves.get(2).getX());
        assertEquals(1,moves.get(2).getY());

        assertEquals(3,moves.size());
    }

    @Test
    void circle () throws Exception{
        gameBoard= new Tile[2][2];
        gameBoard[0][0]= new LTile();
        gameBoard[0][1]=new TTile().applyRotation(270f);
        gameBoard[1][0]= new TTile().applyRotation(90f);
        gameBoard[1][1]= new LTile().applyRotation(180f);;
        moves=new ArrayList<>();
        position.setPosition(0,0);
        moves=(ArrayList<Position>) possibleMoves.invoke(movePlayer,position,gameBoard,moves);
        System.out.println(moves.get(3));
        assertEquals(4,moves.size());

        assertEquals(0,moves.get(0).getX());
        assertEquals(0,moves.get(0).getY());

        assertEquals(1,moves.get(1).getX());
        assertEquals(0,moves.get(1).getY());

        assertEquals(1,moves.get(2).getX());
        assertEquals(1,moves.get(2).getY());

        assertEquals(0,moves.get(3).getX());
        assertEquals(1,moves.get(3).getY());


    }

    @Test
    void noFieldToGo() throws Exception{
        gameBoard= new Tile[2][2];
        gameBoard[1][1]= new LTile();
        gameBoard[1][0]=new TTile().applyRotation(270f);
        gameBoard[0][1]= new TTile().applyRotation(90f);
        gameBoard[0][0]= new LTile().applyRotation(180f);;
        moves=new ArrayList<>();
        position.setPosition(0,0);
        moves=(ArrayList<Position>) possibleMoves.invoke(movePlayer,position,gameBoard,moves);
        assertEquals(1,moves.size());
        assertEquals(0,moves.get(0).getX());
        assertEquals(0,moves.get(0).getY());
    }

    @Test
    void statementCover () throws Exception{
        gameBoard= new Tile[2][2];
        gameBoard[1][1]= new ITile();
        gameBoard[1][0]=new ITile().applyRotation(90f);
        gameBoard[0][1]=new ITile().applyRotation(90f);
        gameBoard[0][0]=  new ITile();
        moves=new ArrayList<>();
        position.setPosition(0,1);
        moves=(ArrayList<Position>) possibleMoves.invoke(movePlayer,position,gameBoard,moves);
        assertEquals(1,moves.size());
        assertEquals(0,moves.get(0).getX());
        assertEquals(1,moves.get(0).getY());

        position.setPosition(1,1);
        moves=(ArrayList<Position>) possibleMoves.invoke(movePlayer,position,gameBoard,moves);
        assertEquals(1,moves.get(1).getX());
        assertEquals(1,moves.get(1).getY());

        position.setPosition(1,0);
        moves=(ArrayList<Position>) possibleMoves.invoke(movePlayer,position,gameBoard,moves);
        assertEquals(1,moves.get(2).getX());
        assertEquals(0,moves.get(2).getY());


    }

}
