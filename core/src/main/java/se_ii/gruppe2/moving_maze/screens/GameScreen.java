package se_ii.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.ScreenUtils;
import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.gameboard.GameBoardFactory;
import se_ii.gruppe2.moving_maze.gamestate.GamePhaseType;
import se_ii.gruppe2.moving_maze.gamestate.turnAction.InsertTile;
import se_ii.gruppe2.moving_maze.gamestate.turnAction.MovePlayer;
import se_ii.gruppe2.moving_maze.helperclasses.RotationResetter;
import se_ii.gruppe2.moving_maze.helperclasses.TextureLoader;
import se_ii.gruppe2.moving_maze.helperclasses.TextureType;
import se_ii.gruppe2.moving_maze.item.ItemLogical;
import se_ii.gruppe2.moving_maze.item.Position;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.player.PlayerColorMapper;
import se_ii.gruppe2.moving_maze.tile.Tile;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private final MovingMazeGame game;
    private final SpriteBatch batch;
    private boolean newExtraTile;
    private OrthographicCamera camera;
    private Player player;
    private Stage stage;
    private ArrayList<Position> localPlayerMoves;
    private boolean canMove=false;
    public static boolean tileJustRotated = false;

    private Stage stageTable;

    private Table table;



    // Buffer-variables used for rendering
    Sprite currentSprite;
    Sprite cardSprite;
    Tile currentTile;
    Tile currentExtraTile;
    ItemLogical currentItem;
    ItemLogical currentExtraTileItem;
    ArrayList<Player> currentPlayersOnTile = new ArrayList<>();
    Player playerBuffer;
    ItemLogical itemBuffer;
    Position positionBuffer;


    Image extraTileImage;

    // background
    private Texture bgImageTexture;
    private TextureRegion bgTextureRegion;

    private Texture boardframe ;
    private Texture tileframe ;
    private float getBoardFrameX;
    private float getBoardFrameY;
    private float getTileFrameX;
    private float getTileFrameY;

    private Label player1Label;
    private Label player2Label;
    private Label player3Label;

    private Skin skin;


    public GameScreen(final MovingMazeGame game) {
        this.game = game;
        this.batch = game.getBatch();

        stage = new Stage();


        camera = MovingMazeGame.getStandardizedCamera();

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // instantiate textures for background
        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg"));
        bgTextureRegion = new TextureRegion(bgImageTexture);

        boardframe = getScaledImage("ui/boardframe.PNG",0.7f);
        tileframe = getScaledImage("ui/tileframe.png",0.1f);

        table = new Table();
        stageTable = new Stage();
        stageTable.getCamera().position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 0);

        Gdx.input.setInputProcessor(stageTable);

        initTable();
        setCoordinatesTable();

        stageTable.addActor(table);


    }

    @Override
    public void show() {
        player = game.getLocalPlayer();
        Gdx.input.setInputProcessor(stage);

    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        batch.setProjectionMatrix(camera.combined);

        if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            Gdx.app.log("recreateBoard", "Recreating gameboard");
            recreateGameBoard();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            game.getGameState().completePhase();
            game.getClient().sendGameStateUpdate(game.getGameState());
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            game.getGameState().getPlayerByName(game.getLocalPlayer().getName()).nextCard();
        }

        batch.begin();
        batch.draw(bgTextureRegion, 0, 0);

        batch.draw(tileframe, getTileFrameX - tileframe.getWidth()/5 ,getTileFrameY - tileframe.getHeight()/5);


        batch.draw(boardframe, (int)getBoardFrameX,(int)getBoardFrameY);

        drawCardToScreen(batch);
        drawGameBoard(batch);
        stage.draw();
        stageTable.draw();
        //game.getFont().draw(batch, "PLAYER: " + player.getName() + " | " + player.getColor().toString(), 70f, Gdx.graphics.getHeight()-100f);
        //game.getFont().draw(batch, "COLOR: " +  player.getColor().toString(), 70f, Gdx.graphics.getHeight()-160f);
        //game.getFont().draw(batch, "GAME PHASE: " + game.getGameState().getGamePhase().toString() + " | " + game.getGameState().getCurrentPlayerOnTurn().getName(), 70f, Gdx.graphics.getHeight()-220f);
        //game.getFont().draw(batch, "CARDS FOUND: " + player.getCardsFound().size(), 70f, Gdx.graphics.getHeight()-280f);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // lifecycle function
    }

    @Override
    public void pause() {
        // lifecycle function
    }

    @Override
    public void resume() {
        // lifecycle function
    }

    @Override
    public void hide() {
        // lifecycle function
    }

    @Override
    public void dispose() {
        // lifecycle function
    }

    private void initTable(){

        Label localplayer = new Label("Martin",skin);
        Label cardsfound0 = new Label(0 + " / " + 3,skin);

        Label extra = new Label("S",skin);


        player1Label = new Label("",skin);
        player2Label = new Label("",skin);
        player3Label = new Label("",skin);

        table.add(localplayer).size(50,30);
        table.add(cardsfound0).size(50,30);

        if (currentPlayersOnTile.size() != 0 ){
            if ( currentPlayersOnTile.size() == 1){

            player1Label = new Label(currentPlayersOnTile.get(0).getName(),skin);
            table.add(player1Label);
            Label cardsfound1 = new Label(currentPlayersOnTile.get(0).getCardsFound().toString(),skin);
            table.add(cardsfound1);
            table.add(extra);

            }else if (currentPlayersOnTile.size() == 2){

            table.row();

            player1Label = new Label(currentPlayersOnTile.get(0).getName(),skin);
            table.add(player1Label);
            Label cardsfound1 = new Label(currentPlayersOnTile.get(0).getCardsFound().toString(),skin);
            table.add(cardsfound1);
            table.add(extra);

            table.row();

            player2Label = new Label(currentPlayersOnTile.get(1).getName(),skin);
            table.add(player2Label);
            Label cardsfound2 = new Label(currentPlayersOnTile.get(1).getCardsFound().toString(),skin);
            table.add(cardsfound2);
            table.add(extra);


            }else {

            player1Label = new Label(currentPlayersOnTile.get(0).getName(),skin);
            table.add(player1Label);
            Label cardsfound1 = new Label(currentPlayersOnTile.get(0).getCardsFound().toString(),skin);
            table.add(cardsfound1);
            table.add(extra);

            table.row();

            player2Label = new Label(currentPlayersOnTile.get(1).getName(),skin);
            table.add(player2Label);
            Label cardsfound2 = new Label(currentPlayersOnTile.get(1).getCardsFound().toString(),skin);
            table.add(cardsfound2);
            table.add(extra);

            table.row();

            player3Label = new Label(currentPlayersOnTile.get(2).getName(),skin);
            table.add(player3Label);
            Label cardsfound3 = new Label(currentPlayersOnTile.get(2).getCardsFound().toString(),skin);
            table.add(cardsfound3);
            table.add(extra);
            }
        }



    }

    private void setCoordinatesTable(){

        table.setPosition(   Gdx.graphics.getWidth()/100 * 10,Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/100 * 5);


    }

    /**
     * Calculates the start-coordinates for a gameboard with respect to the aspect-ratio.
     */
    private Position getStartCoordinates(){

        float aspectRatio=(float) Gdx.graphics.getWidth()/(float) Gdx.graphics.getHeight();

        if(aspectRatio<= 19f/9f && aspectRatio>= 16f/9f){
            getBoardFrameX = Gdx.graphics.getWidth()/100 * 40 - 40 ;
            getBoardFrameY = (Gdx.graphics.getHeight() - boardframe.getHeight())/1.75f;
            return new Position(Gdx.graphics.getWidth()/100 * 49, Gdx.graphics.getHeight()/100 * 5);
        }
        else if(aspectRatio==4f/3f){
            getBoardFrameX = Gdx.graphics.getWidth()/100 * 30 - 30;
            getBoardFrameY = (Gdx.graphics.getHeight() - boardframe.getHeight())/1.75f;
            return new Position(Gdx.graphics.getWidth()/100 * 35 , Gdx.graphics.getHeight()/90*10);
        }
        else if(aspectRatio==1f){
            getBoardFrameX = Gdx.graphics.getWidth()/100 ;
            getBoardFrameY = Gdx.graphics.getHeight()/100 ;
            return new Position(Gdx.graphics.getWidth()/100, Gdx.graphics.getHeight()/100);

        } else {
            return new Position(0,0);
        }

    }

    public void recreateGameBoard() {
        game.getGameState().setBoard(GameBoardFactory.getStandardGameBoard());
        game.getClient().sendGameStateUpdate(game.getGameState());
    }

    /**
     * Draws a visual representation of a logical gameboard onto the screen.
     * @param batch
     * TODO: refactor
     */
    private void drawGameBoard(SpriteBatch batch) {
        Tile[][] tl = game.getGameState().getBoard().getBoard();
        Position initPos = getStartCoordinates();

        float curX = initPos.getX();
        float curY = initPos.getY();

        for(var y = 0; y < tl.length; y++) {
            for(var x = 0; x < tl[y].length; x++) {
                currentTile = tl[y][x];
                currentSprite = TextureLoader.getSpriteByTexturePath(currentTile.getTexturePath(), TextureType.TILE);
                currentItem = currentTile.getItem();

                currentSprite.setPosition(curX, curY);
                currentSprite.setRotation(currentTile.getRotationDegrees());
                currentSprite.draw(batch);

                // render item
                if(currentItem != null) {
                    currentSprite = TextureLoader.getSpriteByTexturePath(currentItem.getTexturePath(), TextureType.ITEM);
                    currentSprite.setPosition(curX+TextureLoader.TILE_EDGE_SIZE /4f, curY + TextureLoader.TILE_EDGE_SIZE /4f);
                    currentSprite.draw(batch);
                }

                // render players
                currentPlayersOnTile = game.getGameState().playersOnTile(y, x);

                if(currentPlayersOnTile.size() != 0) {
                    for(Player p : currentPlayersOnTile) {
                        positionBuffer = PlayerColorMapper.getOffsetByColor(p.getColor());
                        currentSprite = TextureLoader.getSpriteByTexturePath(p.getTexturePath(), TextureType.PLAYER);
                        currentSprite.setPosition(curX+TextureLoader.TILE_EDGE_SIZE/4f + positionBuffer.getX(), curY+TextureLoader.TILE_EDGE_SIZE/4f + positionBuffer.getY());
                        currentSprite.draw(batch);
                    }
                }

                curX += TextureLoader.TILE_EDGE_SIZE;
            }
            curX = initPos.getX();
            curY += TextureLoader.TILE_EDGE_SIZE;
        }

        // check for rotation of accelerometer
        if(Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer) && OptionScreen.rotateTileByGyro()) {
            Gdx.app.log("sensor/accelerom", "X: " + Gdx.input.getAccelerometerX() +
                    " | Y: " + Gdx.input.getAccelerometerY() + " | Z: " + Gdx.input.getAccelerometerZ());
            if (Gdx.input.getAccelerometerY() > 4.0 && !tileJustRotated) {
                Gdx.app.log("sensor/accelerom", "Triggered tile rotation positive!");
                game.getGameState().getExtraTile().rotateCounterClockwise();
                newExtraTile = true;
                tileJustRotated = true;
                new RotationResetter(2500).start();
            }

            if (Gdx.input.getAccelerometerY() < -4.0 && !tileJustRotated) {
                Gdx.app.log("sensor/accelerom", "Triggered tile rotation negative!");
                game.getGameState().getExtraTile().rotateClockwise();
                newExtraTile = true;
                tileJustRotated = true;
                new RotationResetter(2500).start();
            }
        }

        if (isNewExtraTile()){
            updateExtraTile();
        }
        if(canMove && game.getGameState().getGamePhase()== GamePhaseType.MOVE_PLAYER){
            updatePlayerMovement(initPos.getX(),initPos.getY());
        }
    }



    public void updatePlayerMovement(float colStart, float rowStart){
        stage.clear();
        MovePlayer movePlayer= new MovePlayer();
        if (movePlayer.validate() && movePlayer.getPositionsToGO().size()>1){
            localPlayerMoves=movePlayer.getPositionsToGO();
            Texture texture=TextureLoader.getTileTextureOverlay();
            for(Position pos: localPlayerMoves){
                float xT= colStart + (TextureLoader.TILE_EDGE_SIZE*(pos.getX()));
                float yT= rowStart+ (TextureLoader.TILE_EDGE_SIZE*(pos.getY()));
                Image image = new Image(texture);
                image.setPosition(xT,yT);
                image.setOrigin(xT,yT);
                image.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent inputEvent,float x,float y){
                        localPlayerMoves.clear();
                        movePlayer.setBoardStart(getStartCoordinates());
                        movePlayer.setMovePosition(new Position((int)image.getOriginX(),(int) image.getOriginY() ));
                        movePlayer.execute();
                        stage.clear();
                    }
                });
                canMove=false;
                image.draw(batch,1f);
                stage.addActor(image);
            }
        }

    }

    private void drawCardToScreen(SpriteBatch batch) {
        cardSprite = TextureLoader.getSpriteByTexturePath("gameboard/card.png", TextureType.CARD);
        cardSprite.setPosition(Gdx.graphics.getWidth()/100 * 7, Gdx.graphics.getHeight()/100 * 5);
        cardSprite.setScale(0.7f);
        cardSprite.draw(batch);

        playerBuffer = game.getGameState().getPlayerByName(game.getLocalPlayer().getName());
        itemBuffer = playerBuffer != null ? playerBuffer.getCurrentCard() : null;
        if(itemBuffer != null) {
            currentSprite = TextureLoader.getSpriteByTexturePath(itemBuffer.getTexturePath(), TextureType.ITEM);
            currentSprite.setCenterX(cardSprite.getX() + cardSprite.getWidth()/2f);
            currentSprite.setCenterY(cardSprite.getY() + cardSprite.getHeight()/2f);
            currentSprite.draw(batch);
        }
    }

    public void updateExtraTile(){
        for (Actor actor : stage.getActors()){
            if (actor.getName() == extraTileImage.getName()){
                actor.remove();
            }
        }
        currentExtraTile = game.getGameState().getExtraTile();
        Texture layeredTexture;

        if (currentExtraTile != null){

            layeredTexture = TextureLoader.getLayeredTexture(currentExtraTile.getTexturePath(), null);

            if (currentExtraTile.getItem() != null){
                currentExtraTileItem = currentExtraTile.getItem();
                layeredTexture = TextureLoader.getLayeredTexture(currentExtraTile.getTexturePath(), currentExtraTileItem.getTexturePath());
            }

            extraTileImage = new Image(layeredTexture);
            extraTileImage.setOrigin(extraTileImage.getWidth()/2f, extraTileImage.getHeight()/2f);
            extraTileImage.setPosition(cardSprite.getX() + cardSprite.getWidth() + 80f, cardSprite.getY() + cardSprite.getHeight()/2 - extraTileImage.getHeight());
            extraTileImage.setRotation(currentExtraTile.getRotationDegrees());

            getTileFrameX = cardSprite.getX() + cardSprite.getWidth() + 80f ;
            getTileFrameY = cardSprite.getY() + cardSprite.getHeight()/2 - extraTileImage.getHeight();

            if (isMyTurn() && gamePhase() == GamePhaseType.INSERT_TILE) {

                extraTileImage.addListener(new DragListener() {

                    @Override
                    public void drag(InputEvent event, float x, float y, int pointer) {
                        var dir = new Vector2();
                        var offset = new Vector2();
                        if (extraTileImage.getRotation() == 0) {
                            dir.x = x;
                            dir.y = y;
                            offset.x = 0 - extraTileImage.getWidth() / 2f;
                            offset.y = 0 - extraTileImage.getHeight() / 2f;
                        } else if (extraTileImage.getRotation() == 90f) {
                            dir.x = -y;
                            dir.y = x;
                            offset.x = extraTileImage.getHeight() / 2f;
                            offset.y = 0 - extraTileImage.getWidth() / 2f;
                        } else if (extraTileImage.getRotation() == 180f) {
                            dir.x = -x;
                            dir.y = -y;
                            offset.x = extraTileImage.getWidth() / 2f;
                            offset.y = extraTileImage.getHeight() / 2f;
                        } else if (extraTileImage.getRotation() == 270f) {
                            dir.x = y;
                            dir.y = -x;
                            offset.x = 0 - extraTileImage.getHeight() / 2f;
                            offset.y = extraTileImage.getWidth() / 2f;
                        }
                        extraTileImage.moveBy(dir.x + offset.x, dir.y + offset.y);
                    }

                    @Override
                    public void dragStop(InputEvent event, float x, float y, int pointer) {
                        var insertSuccess = false;
                        Position initPos = getStartCoordinates();
                        var inputPosition = new Vector2(0, 0);
                        inputPosition.x = (int) Math.floor((Gdx.input.getX() - initPos.getX()) / TextureLoader.TILE_EDGE_SIZE);
                        inputPosition.y = (int) Math.floor((Gdx.graphics.getHeight() - Gdx.input.getY() - initPos.getY()) / TextureLoader.TILE_EDGE_SIZE);

                        var insert = new InsertTile(inputPosition);

                        insertSuccess = insert.validate();

                        if (insertSuccess) {
                            insert.execute();
                            canMove=true;
                        } else {
                            extraTileImage.setPosition(cardSprite.getX() + cardSprite.getWidth() + 80f, cardSprite.getY() + cardSprite.getHeight()/2 - extraTileImage.getHeight());
                        }
                    }
                });

                extraTileImage.addListener(new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        extraTileImage.rotateBy(90);
                        game.getGameState().getExtraTile().rotateClockwise();
                    }
                });
            }
            stage.addActor(extraTileImage);
            setNewExtraTile(false);
        }

    }

    public boolean isNewExtraTile() {
        return newExtraTile;
    }

    public void setNewExtraTile(boolean newExtraTile) {
        this.newExtraTile = newExtraTile;
    }


    private Texture getScaledImage(String path, float percentOfScreen) {
        var originalBg = new Pixmap(Gdx.files.internal(path));

        // determine how much the picture has to be scaled in order to fit the screen width exactly
        float baseScalingFactor = (originalBg.getWidth()*1.0f) / (camera.viewportWidth);
        float scalingFactor = baseScalingFactor / percentOfScreen;

        var scaledBg = new Pixmap((int) (originalBg.getWidth()/scalingFactor),
                (int) (originalBg.getHeight()/scalingFactor),
                originalBg.getFormat());

        // scale by "redrawing" the original pixmap into the smaller pixmap
        scaledBg.drawPixmap(originalBg,
                0, 0, originalBg.getWidth(), originalBg.getHeight(),
                0, 0, scaledBg.getWidth(), scaledBg.getHeight());

        var scaledBgTexture = new Texture(scaledBg);

        originalBg.dispose();
        scaledBg.dispose();

        return scaledBgTexture;
    }

    /**
     * Checks if it is the local player's turn
     * @return true if it is my turn
     */
    private boolean isMyTurn(){
        return game.getGameState().isMyTurn(game.getLocalPlayer());
    }

    /**
     * Gets the active game phase
     * @return current game phase
     */
    private GamePhaseType gamePhase(){
        return game.getGameState().getGamePhase();
    }

}