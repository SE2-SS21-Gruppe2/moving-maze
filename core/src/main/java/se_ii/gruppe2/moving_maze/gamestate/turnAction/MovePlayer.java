package se_ii.gruppe2.moving_maze.gamestate.turnAction;

import java.util.ArrayList;

import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.helperclasses.TextureLoader;
import se_ii.gruppe2.moving_maze.item.Position;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.tile.Tile;

public class MovePlayer implements TurnAction {

    MovingMazeGame game = MovingMazeGame.getGameInstance();
    private Player player;
    private ArrayList<Position> positionsToGO;
    private Position movePosition;
    private Position boardStart;


    @Override
    public void execute() {
        var playerInGameState = game.getGameState().getPlayerByName(game.getLocalPlayer().getName());
        var row = 0;
        var col = 0;
        for (var i = 0; i < 7; i++) {
            if (boardStart.getX() + (TextureLoader.TILE_EDGE_SIZE * row) < movePosition.getX()) {
                row++;
            }
            if (boardStart.getY() + (TextureLoader.TILE_EDGE_SIZE * col) < movePosition.getY()) {
                col++;
            }
        }
        playerInGameState.setPos(new Position(row, col));
        player.setPos(new Position(row, col));
        game.getGameState().completePhase();
        game.getClient().sendGameStateUpdate(game.getGameState());
    }

    @Override
    public boolean validate() {
        positionsToGO=new ArrayList<>();
        Tile[][] gb= game.getGameState().getBoard().getBoard();
        player=game.getGameState().getPlayerByName((game.getLocalPlayer().getName()));
        System.out.println("Y:"+ player.getPos().getY()+" X:"+player.getPos().getX());
        if(canMoveTop(gb,player.getPos().getY(), player.getPos().getX())|| canMoveRight(gb,player.getPos().getY(),player.getPos().getX())||canMoveBottom(gb,player.getPos().getY(),player.getPos().getX()) || canMoveLeft(gb,player.getPos().getY(),player.getPos().getX()) ){
            positionsToGO=possibleMoves(player.getPos(), gb,positionsToGO);
            return true;
        } else {
            game.getGameState().completePhase();
            game.getClient().sendGameStateUpdate(game.getGameState());
            return false;
        }
    }


    private static ArrayList<Position> possibleMoves(Position position, Tile[][] tiles, ArrayList<Position> positions) {
        for (Position pos : positions) {
            if (position.getX() == pos.getX() && position.getY() == pos.getY()) {
                return positions;
            }
        }
        int row = position.getY();
        int col = position.getX();
        positions.add(new Position(col, row));
        if (canMoveTop(tiles, row, col)) {
            possibleMoves(new Position(col, row + 1), tiles, positions);
        }
        if (canMoveRight(tiles, row, col)) {
            possibleMoves(new Position(col + 1, row), tiles, positions);
        }
        if (canMoveBottom(tiles, row, col)) {
            possibleMoves(new Position(col, row - 1), tiles, positions);
        }
        if (canMoveLeft(tiles, row, col)) {
            possibleMoves(new Position(col - 1, row), tiles, positions);
        }
        return positions;
    }


    private static boolean canMoveTop(Tile[][] tiles, int row, int col) {
        return tiles[row][col].isOpenTop() && row + 1 < tiles.length && tiles[row + 1][col].isOpenBottom();
    }

    private static boolean canMoveRight(Tile[][] tiles, int row, int col) {
        return tiles[row][col].isOpenRight() && col + 1 < tiles[row].length && tiles[row][col + 1].isOpenLeft();
    }

    private static boolean canMoveBottom(Tile[][] tiles, int row, int col) {
        return tiles[row][col].isOpenBottom() && row - 1 >= 0 && tiles[row - 1][col].isOpenTop();
    }

    private static boolean canMoveLeft(Tile[][] tiles, int row, int col) {
        return tiles[row][col].isOpenLeft() && col - 1 >= 0 && tiles[row][col - 1].isOpenRight();
    }

    public ArrayList<Position> getPositionsToGO() {
        return positionsToGO;
    }

    public void setMovePosition(Position position) {
        this.movePosition = position;
    }

    public void setBoardStart(Position position) {
        this.boardStart = position;
    }

}
