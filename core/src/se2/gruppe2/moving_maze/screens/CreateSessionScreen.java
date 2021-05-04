package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import se2.gruppe2.moving_maze.MovingMazeGame;
import se2.gruppe2.moving_maze.HelperClasses.MyShapeRenderer;

import java.awt.*;

public class CreateSessionScreen implements Screen {

    final MovingMazeGame game;
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    MyShapeRenderer myShapeRenderer;
    Stage stage;
    Skin skin;

    String headingText;
    TextField txfName;

    // textures and views
    Texture bgImageTexture;
    TextureRegion bgTextureRegion;
    Texture heading;
    BitmapFont headingFont;
    Texture startImageTexture;

    // measures
    float screenHeight;
    float screenWidth;
    float rowHeight;
    float colWidth;

    public CreateSessionScreen(final MovingMazeGame game) {
        this.game = game;
        camera = MovingMazeGame.getStandardizedCamera();

        // instantiate textures
        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_nomoss.jpeg"));
        bgTextureRegion = new TextureRegion(bgImageTexture);

        startImageTexture = new Texture(Gdx.files.internal("ui/start.png"));

        shapeRenderer = new ShapeRenderer();
        myShapeRenderer = new MyShapeRenderer();

        screenHeight = camera.viewportHeight;
        screenWidth = camera.viewportWidth;
        rowHeight = camera.viewportHeight / 15f;
        colWidth = camera.viewportWidth / 25;


        heading = new Texture(Gdx.files.internal("ui/nunito.png"));
        heading.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        headingFont = new BitmapFont(Gdx.files.internal("ui/nunito.fnt"), new TextureRegion(heading), false);
        headingFont.getData().setScale(2f);
        headingText = "Create Lobby";

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        txfName = new TextField("Martin", skin);
        TextField.TextFieldStyle textFieldStyle = skin.get(TextField.TextFieldStyle.class);
        textFieldStyle.font.getData().scale(1.0f);
        txfName.setStyle(textFieldStyle);
        //txfName.setPosition(0, 0);
        //txfName.setSize(9f * colWidth, 2f * rowHeight);
        //stage.addActor(txfName);


        // ---------- Table Layout ----------

        // --- Left Table ---
        Table left_table = new Table();
        left_table.defaults().pad(10F);

        Label nameLabel = new Label("Name:", skin);
        nameLabel.setAlignment(Align.left);
        nameLabel.setFontScale(2.0f);
        left_table.add(nameLabel).padLeft(20);

        txfName.setAlignment(Align.left);
        left_table.add(txfName).width(Gdx.graphics.getWidth()/3).height(100).expand();


        // --- Right Table
        Table right_table = new Table();
        right_table.add(new Label("RIGHT TABLE", skin));



        // --- 1st Row ---
        Table table = new Table();
        table.defaults().pad(10F);
        table.setFillParent(true);
        table.padBottom(100.0f);

        Label lblCreateLobbyHeading = new Label("CREATE LOBBY", new Label.LabelStyle(headingFont, Color.WHITE));
        lblCreateLobbyHeading.setFontScale(2.0f);
        lblCreateLobbyHeading.setAlignment(Align.center);
        table.add(lblCreateLobbyHeading).colspan(2).fillX();

        // --- 2nd Row ---
        table.row();

        table.add(left_table).expand().fill();
        table.add(right_table).expand().fill();

        // ---------- End Tables ----------

        stage.addActor(table);

        // Debugging
        stage.setDebugAll(false);
         table.debug();
        // left_table.debug();
        // right_table.debug();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.batch.setProjectionMatrix(camera.combined);


        game.batch.begin();
        game.batch.draw(bgTextureRegion, 0, 0);
        //game.batch.draw(startImageTexture, colWidth * 18.5f, 0);
        //headingFont.draw(game.batch, "Create Lobby", getPositionOffset(headingFont, headingText), 14.0f*rowHeight);
        game.batch.end();


        /**
         Gdx.gl.glEnable(GL20.GL_BLEND);
         Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
         myShapeRenderer.setProjectionMatrix(camera.combined);
         myShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
         myShapeRenderer.setColor(0.5f,0.5f,0.5f,0.8f);
         // name
         myShapeRenderer.roundedRect(1.0f*colWidth,9.0f*rowHeight,11.0f*colWidth,2.5f*rowHeight,10);
         // lobby settings
         myShapeRenderer.roundedRect(1.0f*colWidth,1.0f*rowHeight,11.0f*colWidth,7.5f*rowHeight,10);
         // joining players
         myShapeRenderer.roundedRect(13.0f*colWidth,3.5f*rowHeight,11.0f*colWidth,8f*rowHeight,10);
         // game code
         myShapeRenderer.roundedRect(13.0f*colWidth,1.0f*rowHeight,6.0f*colWidth,2.0f*rowHeight,10);
         myShapeRenderer.end();
         Gdx.gl.glDisable(GL20.GL_BLEND);

         myShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
         // joining players
         myShapeRenderer.setColor(0.2f, 0.8f, 0.2f, 1);
         myShapeRenderer.roundedRect(13.5f*colWidth, 4.0f*rowHeight, 10.0f*colWidth, 2.0f*rowHeight, 10);
         myShapeRenderer.setColor(0.8f, 0.2f, 0.2f, 1);
         myShapeRenderer.roundedRect(13.5f*colWidth, 6.5f*rowHeight, 10.0f*colWidth, 2.0f*rowHeight, 10);
         myShapeRenderer.setColor(0.2f, 0.2f, 0.8f, 1);
         myShapeRenderer.roundedRect(13.5f*colWidth, 9.0f*rowHeight, 10.0f*colWidth, 2.0f*rowHeight, 10);
         myShapeRenderer.setColor(0.8f, 0.8f, 0.2f, 1);
         myShapeRenderer.circle(10.75f*colWidth, 10.25f*rowHeight, 2*colWidth/3);
         myShapeRenderer.end();
         **/

        game.batch.begin();
        stage.draw();
        game.batch.end();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private float getPositionOffset(BitmapFont bitmapFont, String value) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(bitmapFont, value);
        return (camera.viewportWidth / 2) - glyphLayout.width / 2;
    }
}