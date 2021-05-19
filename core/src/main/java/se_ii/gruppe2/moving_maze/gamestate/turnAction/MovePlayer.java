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

    public static ArrayList<Position> possibleMoves(Position position, Tile[][] tiles, ArrayList<Position> positions){
        for (Position pos: positions) {
            if(position.getX()==pos.getX() && position.getY()== pos.getY()){
                return positions;
            }
        }
        int x= position.getX();
        int y= position.getY();
        positions.add(new Position(x,y));
        if(tiles[x][y].isOpenTop() && x+1<tiles.length){
            if(tiles[x+1][y].isOpenBottom()){
                possibleMoves(new Position(x+1,y),tiles,positions);
            }
        }
        if(tiles[x][y].isOpenRight() && y+1<tiles[x].length) {
            if (tiles[x][y+1].isOpenLeft()){
                possibleMoves(new Position(x,y+1),tiles,positions);
            }
        }
        if(tiles[x][y].isOpenBottom() && x-1>=0){
            if(tiles[x-1][y].isOpenTop()){
                possibleMoves(new Position(x-1,y),tiles,positions);
            }
        }
        if(tiles[x][y].isOpenLeft() && y-1>=0){
            if(tiles[x][y-1].isOpenRight()) {
                possibleMoves(new Position(x,y-1),tiles,positions);
            }
        }
        return positions;
    }
}
