package se_ii.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.audio.AudioManager;
import se_ii.gruppe2.moving_maze.audio.AudioNetworkManager;
import se_ii.gruppe2.moving_maze.gameboard.GameBoard;
import se_ii.gruppe2.moving_maze.gameboard.GameBoardFactory;
import se_ii.gruppe2.moving_maze.gamestate.GamePhaseType;
import se_ii.gruppe2.moving_maze.gamestate.turnAction.InsertTile;
import se_ii.gruppe2.moving_maze.gamestate.turnAction.MovePlayer;
import se_ii.gruppe2.moving_maze.gamestate.turnAction.TreasurePickupAction;
import se_ii.gruppe2.moving_maze.helperclasses.MyShapeRenderer;
import se_ii.gruppe2.moving_maze.helperclasses.RotationResetter;
import se_ii.gruppe2.moving_maze.helperclasses.TextureLoader;
import se_ii.gruppe2.moving_maze.helperclasses.TextureType;
import se_ii.gruppe2.moving_maze.item.ItemLogical;
import se_ii.gruppe2.moving_maze.item.Position;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.player.PlayerColorMapper;
import se_ii.gruppe2.moving_maze.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private final MovingMazeGame game;
    private final SpriteBatch batch;
    private boolean newExtraTile;
    private OrthographicCamera camera;
    private Player player;
    private Stage stagePlayerMovement;
    private Stage stageMenuButton;
    private Stage stageExtraTile;
    private Stage stagePlayerList;
    private Stage stageYourTurn;
    private Stage stageInsertPosition;
    private ArrayList<Position> localPlayerMoves;
    private boolean canMove = false;
    private boolean canInsert = false;
    public static boolean tileJustRotated = false;

    private MyShapeRenderer myShapeRenderer;

    private Table playerTable;

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

    private Texture boardframe;
    private Texture tileframe;
    private Texture cardStack;
    private Image cardStackImage;
    private float getBoardFrameX;
    private float getBoardFrameY;
    private float getTileFrameX;
    private float getTileFrameY;

    private Label phaseLabel;

    private Label localPlayerLabel;
    private Label player1Label;
    private Label player2Label;
    private Label player3Label;

    private Label localPlayerCardsLabel;
    private Label player1CardsLabel;
    private Label player2CardsLabel;
    private Label player3CardsLabel;

    private Player localPlayer;
    private Player player1;
    private Player player2;
    private Player player3;

    private Skin skin;
    private float scalingFactor;
    private boolean firstCall;

    private TextButton menuButton;
    private Dialog dialogMenu;
    private boolean yourTurnShown;

    private Texture yourTurnTexture;


    public GameScreen(final MovingMazeGame game) {
        this.game = game;
        this.batch = game.getBatch();

        stagePlayerMovement = new Stage();
        stageMenuButton = new Stage();
        stageExtraTile = new Stage();
        stagePlayerList = new Stage();
        stageYourTurn = new Stage();
        stageInsertPosition = new Stage();

        myShapeRenderer = new MyShapeRenderer();
        firstCall = true;
        yourTurnShown = false;

        camera = MovingMazeGame.getStandardizedCamera();

        boardframe = getScaledImage("ui/boardframe.PNG", 0.62f);
        tileframe = getScaledImage("ui/tileframe.png", 0.1f);
        cardStack = new Texture(Gdx.files.internal("gameboard/cardstack.png"));
        yourTurnTexture = getScaledImage("ui/yourturn.png", 0.4f);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    @Override
    public void show() {
        player = game.getLocalPlayer();

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stagePlayerMovement);
        inputMultiplexer.addProcessor(stageMenuButton);
        inputMultiplexer.addProcessor(stageExtraTile);
        inputMultiplexer.addProcessor(stagePlayerList);
        inputMultiplexer.addProcessor(stageYourTurn);
        inputMultiplexer.addProcessor(stageInsertPosition);

        Gdx.input.setInputProcessor(inputMultiplexer);

        stagePlayerList.clear();
        stageExtraTile.clear();
        stageMenuButton.clear();
        stagePlayerMovement.clear();
        stageYourTurn.clear();
        stageInsertPosition.clear();

        player1 = null;
        player2 = null;
        player3 = null;
        localPlayer = null;

        scalingFactor = Gdx.graphics.getWidth() / 1280f;

        setUpMenuButton();
        setUpYourTurnStage();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            Gdx.app.log("recreateBoard", "Recreating gameboard");
            recreateGameBoard();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            game.getGameState().completePhase();
            game.getClient().sendGameStateUpdate(game.getGameState());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            game.getGameState().getPlayerByName(game.getLocalPlayer().getName()).nextCard();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            game.getClient().gameWin(game.getSessionKey(), game.getLocalPlayer());
        }

        //play network sounds
        playNetworkSounds();

        //handle cheat
        if (Gdx.input.isKeyJustPressed(Input.Keys.VOLUME_DOWN)) {
            Player player = game.getGameState().getPlayerByName(game.getLocalPlayer().getName());
            if (!player.getCheatFunction().getCheated()) {
                game.getGameState().getPlayerByName(game.getLocalPlayer().getName()).getCheatFunction().setCheatCurrentMove(true);
            } else {
                Gdx.app.log("cheat", "not possible to activate cheat! You have already activated it once");
            }
            Gdx.app.log("cheat", "cheat activated for " + game.getLocalPlayer().getName());
        }

        //handle cheat report
        if (Gdx.input.isKeyJustPressed(Input.Keys.VOLUME_UP)) {
            if (game.getGameState().getPreviousPlayer() != null) {
                Player caller = game.getGameState().getPlayerByName(game.getLocalPlayer().getName());
                Player cheater = game.getGameState().getPlayerByName(game.getGameState().getPreviousPlayer().getName());

                if (cheater != null && cheater.getCheatFunction().isLaidCardDownPreviousMove()) {
                    boolean cheatDetected = caller.getCheatFunction().markCheater(caller, cheater);
                    Gdx.app.log("cheat/report", "cheat report activated for " + cheater.getName() + " from caller " +
                            caller.getName() + " Status cheat detected: " + cheatDetected);

                } else {
                    Gdx.app.log("cheat/report", "Player " + cheater.getName() + " didn't lay down any card!");
                }
            } else {
                Gdx.app.log("cheat/bug", "no previous Player found!");
            }
        }

        batch.begin();
        batch.draw(TextureLoader.getSpriteByTexturePath("ui/bg_moss.jpeg", TextureType.BACKGROUND).getTexture(), 0f, 0f);
        batch.draw(tileframe, getTileFrameX - tileframe.getWidth() / 5f, getTileFrameY - tileframe.getHeight() / 5f);
        batch.draw(boardframe, (int) getBoardFrameX, (int) getBoardFrameY);


        drawCardToScreen(batch);
        drawGameBoard(batch);
        batch.end();
        
        if (!firstCall) {
            drawPlayerColorShapes();
        }
        updatePlayerTable();
        stagePlayerMovement.draw();
        stagePlayerList.draw();
        stageInsertPosition.draw();
        stageExtraTile.draw();
        stageMenuButton.draw();

        if (game.getGameState().isMyTurn(game.getLocalPlayer()) && game.getGameState().getGamePhase() == GamePhaseType.INSERT_TILE && yourTurnShown == false){
            stageYourTurn.draw();
        }


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
        dispose();
    }

    @Override
    public void dispose() {
    }


    /**
     * updates the player table with the names and the players' remaining cards
     */
    private void updatePlayerTable() {

        List<Player> players = game.getGameState().getPlayers();
        playerTable = new Table();
        playerTable.setSize(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() / 3f);
        playerTable.defaults().padTop(30f).maxHeight(50f * scalingFactor);

        var indexOfLocalPlayer = 0;
        var i = 0;

        for (Player player : players) {
            if (game.getLocalPlayer().getName().equals(player.getName())) {
                indexOfLocalPlayer = players.indexOf(player);
            }
        }

        if (players.size() != 0) {
            localPlayer = players.get((indexOfLocalPlayer + i) % players.size());
            localPlayerLabel = new Label(localPlayer.getName(), skin);
            localPlayerLabel.setFontScale(2.0f * scalingFactor);
            localPlayerLabel.setAlignment(Align.left);
            playerTable.add(localPlayerLabel).align(Align.left).expandX().fillX();

            var cardstackimage = new Image(cardStack);
            playerTable.add(cardstackimage).right().maxWidth(50f * scalingFactor);

            localPlayerCardsLabel = new Label(String.valueOf(localPlayer.getCardsToFind().size() + 1), skin);
            localPlayerCardsLabel.setFontScale(2.0f * scalingFactor);
            localPlayerCardsLabel.setAlignment(Align.left);
            playerTable.add(localPlayerCardsLabel).align(Align.left).padLeft(10f);

            playerTable.row().maxHeight(50f * scalingFactor);
        }
        if (players.size() > 1) {
            player1 = players.get((indexOfLocalPlayer + (++i)) % players.size());
            player1Label = new Label(player1.getName(), skin);
            player1Label.setFontScale(2.0f * scalingFactor);
            player1Label.setAlignment(Align.left);
            playerTable.add(player1Label).align(Align.left).expandX().fillX();

            var cardstackimage = new Image(cardStack);
            playerTable.add(cardstackimage).right().maxWidth(50f * scalingFactor);

            player1CardsLabel = new Label(String.valueOf(player1.getCardsToFind().size() + 1), skin);
            player1CardsLabel.setFontScale(2.0f * scalingFactor);
            player1CardsLabel.setAlignment(Align.left);
            playerTable.add(player1CardsLabel).align(Align.left).padLeft(10f);

            playerTable.row().maxHeight(50f * scalingFactor);
        }
        if (players.size() > 2) {
            player2 = players.get((indexOfLocalPlayer + (++i)) % players.size());
            player2Label = new Label(player2.getName(), skin);
            player2Label.setFontScale(2.0f * scalingFactor);
            player2Label.setAlignment(Align.left);
            playerTable.add(player2Label).align(Align.left).expandX().fillX();

            var cardstackimage = new Image(cardStack);
            playerTable.add(cardstackimage).right().maxWidth(50f * scalingFactor);

            player2CardsLabel = new Label(String.valueOf(player2.getCardsToFind().size() + 1), skin);
            player2CardsLabel.setFontScale(2.0f * scalingFactor);
            player2CardsLabel.setAlignment(Align.left);
            playerTable.add(player2CardsLabel).align(Align.left).padLeft(10f);

            playerTable.row().maxHeight(50f * scalingFactor);
        }
        if (players.size() > 3) {
            player3 = players.get((indexOfLocalPlayer + (++i)) % players.size());
            player3Label = new Label(player3.getName(), skin);
            player3Label.setFontScale(2.0f * scalingFactor);
            player3Label.setAlignment(Align.left);
            playerTable.add(player3Label).align(Align.left).expandX().fillX();

            var cardstackimage = new Image(cardStack);
            playerTable.add(cardstackimage).right().maxWidth(50f * scalingFactor);

            player3CardsLabel = new Label(String.valueOf(player3.getCardsToFind().size() + 1), skin);
            player3CardsLabel.setFontScale(2.0f * scalingFactor);
            player3CardsLabel.setAlignment(Align.left);
            playerTable.add(player3CardsLabel).align(Align.left).padLeft(10f);

        }

        playerTable.setPosition(50f * scalingFactor, Gdx.graphics.getHeight() - playerTable.getHeight() - 120f * scalingFactor);
        stagePlayerList.clear();
        stagePlayerList.addActor(playerTable);
        firstCall = false;

        phaseLabel = new Label(game.getGameState().getGamePhaseText(), skin);
        phaseLabel.setFontScale(1.5f * scalingFactor);
        phaseLabel.setAlignment(Align.center);
        phaseLabel.setSize(Gdx.graphics.getWidth() / 4, phaseLabel.getHeight());
        phaseLabel.setPosition(Gdx.graphics.getWidth() / 3 + 50f * scalingFactor - phaseLabel.getWidth(), Gdx.graphics.getHeight() - 50f * scalingFactor - phaseLabel.getHeight());
        stagePlayerList.addActor(phaseLabel);

    }

    /**
     * draws the rounded rectangles around the player names
     */
    private void drawPlayerColorShapes() {

        float offsetX = 10f * scalingFactor;
        float offsetY = 4f * scalingFactor;
        myShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (localPlayer != null) {
            myShapeRenderer.setColor(PlayerColorMapper.getColorValue(localPlayer.getColor()));
            if (game.getGameState().isMyTurn(localPlayer)) {
                myShapeRenderer.roundedRect(playerTable.getX() - 2.5f * offsetX, playerTable.getY() + localPlayerLabel.getY() - 2.0f * offsetY, playerTable.getWidth() + 5.0f * offsetX, localPlayerLabel.getHeight() + 4.0f * offsetY, 10);
            } else {
                myShapeRenderer.roundedRect(playerTable.getX() - offsetX, playerTable.getY() + localPlayerLabel.getY() - offsetY, playerTable.getWidth() + 2.0f * offsetX, localPlayerLabel.getHeight() + 2.0f * offsetY, 10);
            }
        }
        if (player1 != null) {
            myShapeRenderer.setColor(PlayerColorMapper.getColorValue(player1.getColor()));
            if (game.getGameState().isMyTurn(player1)) {
                myShapeRenderer.roundedRect(playerTable.getX() - 2.5f * offsetX, playerTable.getY() + player1Label.getY() - 2.0f * offsetY, playerTable.getWidth() + 5.0f * offsetX, player1Label.getHeight() + 4.0f * offsetY, 10);
            } else {
                myShapeRenderer.roundedRect(playerTable.getX() - offsetX, playerTable.getY() + player1Label.getY() - offsetY, playerTable.getWidth() + 2.0f * offsetX, player1Label.getHeight() + 2.0f * offsetY, 10);
            }
        }
        if (player2 != null) {
            myShapeRenderer.setColor(PlayerColorMapper.getColorValue(player2.getColor()));
            if (game.getGameState().isMyTurn(player2)) {
                myShapeRenderer.roundedRect(playerTable.getX() - 2.5f * offsetX, playerTable.getY() + player2Label.getY() - 2.0f * offsetY, playerTable.getWidth() + 5.0f * offsetX, player2Label.getHeight() + 4.0f * offsetY, 10);
            } else {
                myShapeRenderer.roundedRect(playerTable.getX() - offsetX, playerTable.getY() + player2Label.getY() - offsetY, playerTable.getWidth() + 2.0f * offsetX, player2Label.getHeight() + 2.0f * offsetY, 10);
            }
        }
        if (player3 != null) {
            myShapeRenderer.setColor(PlayerColorMapper.getColorValue(player3.getColor()));
            if (game.getGameState().isMyTurn(player3)) {
                myShapeRenderer.roundedRect(playerTable.getX() - 2.5f * offsetX, playerTable.getY() + player3Label.getY() - 2.0f * offsetY, playerTable.getWidth() + 5.0f * offsetX, player3Label.getHeight() + 4.0f * offsetY, 10);
            } else {
                myShapeRenderer.roundedRect(playerTable.getX() - offsetX, playerTable.getY() + player3Label.getY() - offsetY, playerTable.getWidth() + 2.0f * offsetX, player3Label.getHeight() + 2.0f * offsetY, 10);
            }
        }
        if (phaseLabel != null) {
            myShapeRenderer.setColor(0.5f, 0.5f, 0.5f, 0.85f);
            myShapeRenderer.roundedRect(phaseLabel.getX() - 0.5f * offsetX, phaseLabel.getY() - 2.0f * offsetX, phaseLabel.getWidth() + 1.0f * offsetX, phaseLabel.getHeight() + 4.0f * offsetX, 10);
        }

        myShapeRenderer.end();

    }


    /**
     * set up the menu button and the associated confirm dialog
     */
    private void setUpMenuButton() {
        menuButton = new TextButton("Menu", skin);
        menuButton.getLabel().setFontScale(1.75f * scalingFactor);
        Container<TextButton> backButtonContainer = new Container<>(menuButton);
        backButtonContainer.setTransform(true);
        backButtonContainer.size(100 * scalingFactor, 50f * scalingFactor);
        backButtonContainer.setPosition(80f * scalingFactor, Gdx.graphics.getHeight() - 57f * scalingFactor - backButtonContainer.getHeight());
        stageMenuButton.addActor(backButtonContainer);

        dialogMenu = new Dialog("Quit Game", skin, "dialog") {
            public void result(Object obj) {
                if (obj.equals(true)) {
                    game.setScreen(game.getMainMenuScreen());
                    game.getClient().leaveSession(game.getLocalPlayer(), game.getSessionKey());
                    game.setSessionKey("------");
                    game.setInGame(false);
                } else {
                    dialogMenu.remove();
                }
            }
        };
        dialogMenu.text("Are you sure you want to quit?");
        dialogMenu.button("Yes", true); //sends "true" as the result
        dialogMenu.button("No", false); //sends "false" as the result
        dialogMenu.scaleBy(2.0f);
        dialogMenu.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 3);
        dialogMenu.setPosition(Gdx.graphics.getWidth() / 3.0f, Gdx.graphics.getHeight() / 3.0f);
        dialogMenu.pack();

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioManager.playButtonClick();
                stageMenuButton.addActor(dialogMenu);
            }
        });
    }

    private void setUpYourTurnStage(){
        var unfocusButton = new Button(skin);
        unfocusButton.setColor(0,0,0,0.75f);
        unfocusButton.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        unfocusButton.setPosition(0,0);
        stageYourTurn.addActor(unfocusButton);

        var yourTurnImage = new Image(yourTurnTexture);
        yourTurnImage.setPosition(Gdx.graphics.getWidth()/2.0f - yourTurnImage.getWidth()/2.0f, Gdx.graphics.getHeight()/2.0f - yourTurnImage.getHeight()/2.0f);
        stageYourTurn.addActor(yourTurnImage);

        unfocusButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                yourTurnShown = true;
                canInsert = true;
            }
        });

        yourTurnImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                yourTurnShown = true;
                canInsert = true;
            }
        });

    }


    /**
     * Calculates the start-coordinates for a gameboard with respect to the aspect-ratio.
     */
    private Position getStartCoordinates() {

        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();

        if (aspectRatio <= 19f / 9f && aspectRatio >= 16f / 9f) {
            getBoardFrameX = Gdx.graphics.getWidth() / 100 * 45 - 45;
            getBoardFrameY = (Gdx.graphics.getHeight() - boardframe.getHeight()) / 1.75f - 5f;
            return new Position(Gdx.graphics.getWidth() / 100 * 49, Gdx.graphics.getHeight() / 100 * 5);
        } else if (aspectRatio == 4f / 3f) {
            getBoardFrameX = Gdx.graphics.getWidth() / 100 * 30 - 30;
            getBoardFrameY = (Gdx.graphics.getHeight() - boardframe.getHeight()) / 1.75f;
            return new Position(Gdx.graphics.getWidth() / 100 * 35, Gdx.graphics.getHeight() / 90 * 10);
        } else if (aspectRatio == 1f) {
            getBoardFrameX = Gdx.graphics.getWidth() / 100;
            getBoardFrameY = Gdx.graphics.getHeight() / 100;
            return new Position(Gdx.graphics.getWidth() / 100, Gdx.graphics.getHeight() / 100);

        } else {
            return new Position(0, 0);
        }

    }

    public void recreateGameBoard() {
        game.getGameState().setBoard(GameBoardFactory.getStandardGameBoard("Original"));
        game.getClient().sendGameStateUpdate(game.getGameState());
    }

    /**
     * Draws a visual representation of a logical gameboard onto the screen.
     *
     * @param batch TODO: refactor
     */
    private void drawGameBoard(SpriteBatch batch) {
        Tile[][] tl = game.getGameState().getBoard().getBoard();
        Position initPos = getStartCoordinates();

        float curX = initPos.getX();
        float curY = initPos.getY();

        for (var y = 0; y < tl.length; y++) {
            for (var x = 0; x < tl[y].length; x++) {
                currentTile = tl[y][x];
                currentSprite = TextureLoader.getSpriteByTexturePath(currentTile.getTexturePath(), TextureType.TILE);
                currentItem = currentTile.getItem();

                currentSprite.setPosition(curX, curY);
                currentSprite.setRotation(currentTile.getRotationDegrees());
                currentSprite.draw(batch);

                // render item
                if (currentItem != null) {
                    currentSprite = TextureLoader.getSpriteByTexturePath(currentItem.getTexturePath(), TextureType.ITEM);
                    currentSprite.setPosition(curX + TextureLoader.TILE_EDGE_SIZE / 4f, curY + TextureLoader.TILE_EDGE_SIZE / 4f);
                    currentSprite.draw(batch);
                }

                // render players
                currentPlayersOnTile = game.getGameState().playersOnTile(y, x);

                if (currentPlayersOnTile.size() != 0) {
                    for (Player p : currentPlayersOnTile) {
                        positionBuffer = PlayerColorMapper.getOffsetByColor(p.getColor());
                        currentSprite = TextureLoader.getSpriteByTexturePath(p.getTexturePath(), TextureType.PLAYER);
                        currentSprite.setPosition(curX + TextureLoader.TILE_EDGE_SIZE / 4f + positionBuffer.getX(), curY + TextureLoader.TILE_EDGE_SIZE / 4f + positionBuffer.getY());
                        currentSprite.draw(batch);
                    }
                }

                curX += TextureLoader.TILE_EDGE_SIZE;
            }
            curX = initPos.getX();
            curY += TextureLoader.TILE_EDGE_SIZE;
        }

        // check for rotation of accelerometer
        if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer) && game.getPreferences().getBoolean("rotateWithSensorOn", true)) {
            if (Gdx.input.getAccelerometerY() > 4.0 && !tileJustRotated) {
                game.getGameState().getExtraTile().rotateCounterClockwise();
                newExtraTile = true;
                tileJustRotated = true;
                new RotationResetter(2500).start();
            }

            if (Gdx.input.getAccelerometerY() < -4.0 && !tileJustRotated) {
                game.getGameState().getExtraTile().rotateClockwise();
                newExtraTile = true;
                tileJustRotated = true;
                new RotationResetter(2500).start();
            }
        }

        if (isNewExtraTile()) {
            updateExtraTile();
        }

        if (game.getGameState().getGamePhase() == GamePhaseType.INSERT_TILE && game.getGameState().isMyTurn(game.getLocalPlayer()) && canInsert){
            updateInsertPosition();
        }

        if (game.getGameState().getGamePhase() == GamePhaseType.MOVE_PLAYER && game.getGameState().isMyTurn(game.getLocalPlayer()) && canMove) {
            updatePlayerMovement(initPos.getX(), initPos.getY());
        }
    }

    private void updateInsertPosition() {
        stageInsertPosition.clear();
        Texture texture = TextureLoader.getTileTextureOverlay();
        for (int i = 0; i < game.getGameState().getBoard().getBoard().length; i++){
            for (int j = 0; j < game.getGameState().getBoard().getBoard().length; j++){
                var pos = new InsertTile(new Vector2(i,j));
                if (pos.validate()){
                    Image image = new Image(texture);
                    image.setPosition(getStartCoordinates().getX() + i * TextureLoader.TILE_EDGE_SIZE, getStartCoordinates().getY() + j * TextureLoader.TILE_EDGE_SIZE);
                    //image.setOrigin(i * TextureLoader.TILE_EDGE_SIZE, j * TextureLoader.TILE_EDGE_SIZE);
                    stageInsertPosition.addActor(image);
                }
            }
        }
        canInsert = false;
    }


    public void updatePlayerMovement(float colStart, float rowStart) {
        stagePlayerMovement.clear();
        MovePlayer movePlayer = new MovePlayer();
        if (movePlayer.validate() && movePlayer.getPositionsToGO().size() > 1) {
            localPlayerMoves = movePlayer.getPositionsToGO();
            Texture texture = TextureLoader.getTileTextureOverlay();
            for (Position pos : localPlayerMoves) {
                float xT = colStart + (TextureLoader.TILE_EDGE_SIZE * (pos.getX()));
                float yT = rowStart + (TextureLoader.TILE_EDGE_SIZE * (pos.getY()));
                Image image = new Image(texture);
                image.setPosition(xT, yT);
                image.setOrigin(xT, yT);
                image.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent inputEvent, float x, float y) {
                        game.getGameState().getAudioNetwork().setPlayPlayerMovement(true);
                        localPlayerMoves.clear();
                        movePlayer.setBoardStart(getStartCoordinates());
                        movePlayer.setMovePosition(new Position((int) image.getOriginX(), (int) image.getOriginY()));
                        movePlayer.execute();
                        stagePlayerMovement.clear();

                        new TreasurePickupAction().execute();
                    }
                });
                //image.draw(batch,1f);
                stagePlayerMovement.addActor(image);
            }
        }
        canMove = false;
    }

    private void drawCardToScreen(SpriteBatch batch) {
        cardSprite = TextureLoader.getSpriteByTexturePath("gameboard/card.png", TextureType.CARD);
        cardSprite.setPosition(50f * scalingFactor, 10f * scalingFactor);
        cardSprite.setScale(0.7f);
        cardSprite.draw(batch);

        playerBuffer = game.getGameState().getPlayerByName(game.getLocalPlayer().getName());
        itemBuffer = playerBuffer != null ? playerBuffer.getCurrentCard() : null;
        if (itemBuffer != null) {
            currentSprite = TextureLoader.getSpriteByTexturePath(itemBuffer.getTexturePath(), TextureType.ITEM);
            currentSprite.setCenterX(cardSprite.getX() + cardSprite.getWidth() / 2f);
            currentSprite.setCenterY(cardSprite.getY() + cardSprite.getHeight() / 2f);
            currentSprite.draw(batch);
        }
    }

    /**
     * updates the current extra tile and handles the inserting into the gameboard
     */
    public void updateExtraTile() {
        stageExtraTile.clear();
        currentExtraTile = game.getGameState().getExtraTile();
        Texture layeredTexture;

        if (currentExtraTile != null) {

            layeredTexture = TextureLoader.getLayeredTexture(currentExtraTile.getTexturePath(), null);

            if (currentExtraTile.getItem() != null) {
                currentExtraTileItem = currentExtraTile.getItem();
                layeredTexture = TextureLoader.getLayeredTexture(currentExtraTile.getTexturePath(), currentExtraTileItem.getTexturePath());
            }

            extraTileImage = new Image(layeredTexture);
            extraTileImage.setOrigin(extraTileImage.getWidth() / 2f, extraTileImage.getHeight() / 2f);
            extraTileImage.setPosition(cardSprite.getX() + cardSprite.getWidth() + 80f, cardSprite.getY() + cardSprite.getHeight() / 2 - extraTileImage.getHeight());
            extraTileImage.setRotation(currentExtraTile.getRotationDegrees());

            getTileFrameX = cardSprite.getX() + cardSprite.getWidth() + 80f;
            getTileFrameY = cardSprite.getY() + cardSprite.getHeight() / 2 - extraTileImage.getHeight();

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
                            game.getGameState().getAudioNetwork().setPlayLabyrinthMovement(true);
                            insert.execute();
                            canMove = true;
                            stageInsertPosition.clear();
                        } else {
                            extraTileImage.setPosition(cardSprite.getX() + cardSprite.getWidth() + 80f, cardSprite.getY() + cardSprite.getHeight() / 2 - extraTileImage.getHeight());
                        }
                    }
                });

                extraTileImage.addListener(new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        AudioManager.playRotateTile();
                        extraTileImage.rotateBy(90);
                        game.getGameState().getExtraTile().rotateCounterClockwise();
                    }
                });
            }
            stageExtraTile.addActor(extraTileImage);
            setNewExtraTile(false);
            yourTurnShown = false;
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
        float baseScalingFactor = (originalBg.getWidth() * 1.0f) / (camera.viewportWidth);
        float scalingFactor = baseScalingFactor / percentOfScreen;

        var scaledBg = new Pixmap((int) (originalBg.getWidth() / scalingFactor),
                (int) (originalBg.getHeight() / scalingFactor),
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
     *
     * @return true if it is my turn
     */
    private boolean isMyTurn() {
        return game.getGameState().isMyTurn(game.getLocalPlayer());
    }

    /**
     * Gets the active game phase
     *
     * @return current game phase
     */
    private GamePhaseType gamePhase() {
        return game.getGameState().getGamePhase();
    }

    /**
     * Plays the sound which instruction is sent over the network
     */
    private void playNetworkSounds() {
        AudioNetworkManager audio = game.getGameState().getAudioNetwork();
        if (audio.isPlayPlayerMovement()) {
            AudioManager.playMovePlayer();
            audio.setPlayPlayerMovement(false);
        }
        if (audio.isPlayLabyrinthMovement()) {
            AudioManager.playMovingMaze();
            audio.setPlayLabyrinthMovement(false);
        }
        if (audio.isPlayLayCardDown()) {
            AudioManager.playLayCardDown();
            audio.setPlayLayCardDown(false);
        }

    }

}