package com.njsoft.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.njsoft.pixelfollower.PixelFollower;

public class NextLevelScreen implements Screen {

    private final PixelFollower game;
    private Stage stage;

    NextLevelScreen(PixelFollower game)
    {
        game.beginLevelScore = game.score;
        this.game = game;
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
        TextButton playButton = new TextButton("Continue!", game.skin);
        TextButton menuButton = new TextButton("Main Menu", game.skin);

        playButton.setColor( new Color(1/255f, 120/255f, 216/255f, 1));
        menuButton.setColor(new Color(Color.GRAY));
        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
               game.setScreen(new GameScreen(game));
            }
        });
        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.level = 1;
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Label gameOverLabel = new Label("Level "+game.level + " completed", game.skin);
        gameOverLabel.setAlignment(Align.center);
        gameOverLabel.setFontScale(3);

        //Add buttons to table

        mainTable.add(gameOverLabel).width(300).height(60).space(10).padTop(50).fillX();
        mainTable.row();
        mainTable.add(playButton).width(350).height(60).space(30).padTop(30);
        mainTable.row();
        mainTable.add(menuButton).width(350).height(60).space(30).padTop(5);
        mainTable.row();

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
        stage.dispose();
    }
}