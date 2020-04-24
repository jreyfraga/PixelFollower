package com.njsoft.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.njsoft.pixelfollower.PixelFollower;

public class MainMenuScreen implements Screen {
    private final PixelFollower game;
    private Stage stage;
    private Image image;

    public MainMenuScreen(PixelFollower game)
    {
        this.game = game;
        Texture texture = new Texture(Gdx.files.internal("icon.png"));
        image = new Image(texture);
        stage = new Stage(new StretchViewport(480,800),game.batch);

    }

    @Override
    public void show() {
        //Stage should control input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.top();

        //Create buttons
        TextButton playButton = new TextButton("Play", game.skin);
        TextButton optionsButton = new TextButton("Options", game.skin);
        TextButton exitButton = new TextButton("Exit", game.skin);

        playButton.setColor( new Color(1/255f, 120/255f, 216/255f, 1));
        optionsButton.setColor(new Color(Color.PINK));
        exitButton.setColor(new Color(Color.GRAY));
        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen(game));
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        Label titleLabel = new Label("Pixel Follower", game.skin);
        titleLabel.setAlignment(Align.center);
        titleLabel.setFontScale(2);
        //Add buttons to table
        mainTable.add(image).width(300).height(300).space(20).padTop(40);
        mainTable.row();
        mainTable.add(titleLabel).width(300).height(60).space(10).fillX();
        mainTable.row();
        mainTable.add(playButton).width(350).height(60).space(30).padTop(30);
        mainTable.row();
        mainTable.add(optionsButton).width(350).height(60).space(30);
        mainTable.row();
        mainTable.add(exitButton).width(350).height(60).space(30);

        //Add table to stage
        stage.addActor(mainTable);

    }

    @Override
    public void render(float delta) {
        game.batch.setProjectionMatrix(game.camera.combined);

        game.camera.update();
        Gdx.gl.glClearColor(30/255f, 30/255f, 30/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
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
}