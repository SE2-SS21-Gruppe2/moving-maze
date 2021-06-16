package se_ii.gruppe2.moving_maze.helperclasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import se_ii.gruppe2.moving_maze.audio.AudioManager;

public class Styles {

    private static final String SKINPATH = "ui/uiskin.json";

    private Skin skin;
    private Label.LabelStyle labelStyle;

    public Styles(){

        skin = new Skin(Gdx.files.internal(SKINPATH));

        var myFontTexture = new Texture(Gdx.files.internal("ui/nunito.png"));
        myFontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("ui/nunito.fnt"), new TextureRegion(myFontTexture), false);
        labelStyle = new Label.LabelStyle(myFont, Color.WHITE);

    }

    public Skin getSkin() {
        return skin;
    }

    public Label.LabelStyle getLabelStyle() {
        return labelStyle;
    }
}
