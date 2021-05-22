package se_ii.gruppe2.moving_maze.gamestate.turnAction;

import java.util.ArrayList;

import se_ii.gruppe2.moving_maze.item.Position;
import se_ii.gruppe2.moving_maze.tile.Tile;

public class MovePlayer implements TurnAction {


    @Override
    public void execute() {

    }

    @Override
    public boolean validate() {
        return false;
    }



    private static ArrayList<Position> possibleMoves(Position position, Tile[][] tiles, ArrayList<Position> positions){
        for (Position pos: positions) {
            if(position.getX()==pos.getX() && position.getY()== pos.getY()){
                return positions;
            }
        }
        int x= position.getX();
        int y= position.getY();
        positions.add(new Position(x,y));
        if(canMoveTop(tiles,x,y)){
            possibleMoves(new Position(x+1,y),tiles,positions);
        }
        if(canMoveRight(tiles,x,y)) {
            possibleMoves(new Position(x,y+1),tiles,positions);
        }
        if(canMoveBottom(tiles,x,y)){
            possibleMoves(new Position(x-1,y),tiles,positions);
        }
        if(canMoveLeft(tiles,x,y)){
            possibleMoves(new Position(x,y-1),tiles,positions);
        }

        return positions;
    }


    private static boolean canMoveTop(Tile[][] tiles, int x,int y){
        return tiles[x][y].isOpenTop() && x+1<tiles.length && tiles[x+1][y].isOpenBottom();
    }
    private static boolean canMoveRight(Tile[][] tiles, int x,int y){
        return tiles[x][y].isOpenRight() && y+1<tiles[x].length && tiles[x][y+1].isOpenLeft();
    }
    private static boolean canMoveBottom(Tile[][] tiles, int x,int y){
        return tiles[x][y].isOpenBottom() && x-1>=0 && tiles[x-1][y].isOpenTop();
    }
    private static boolean canMoveLeft(Tile[][] tiles, int x,int y){
        return tiles[x][y].isOpenLeft() && y-1>=0 && tiles[x][y-1].isOpenRight();
    }




}
