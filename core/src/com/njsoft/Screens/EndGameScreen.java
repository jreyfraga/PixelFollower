package com.njsoft.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.njsoft.pixelfollower.PixelFollower;

/**
 * Created by jaime on 10/05/15.
 */
public class EndGameScreen implements Screen {

    PixelFollower game;
    TextButtonStyle style; //** Button properties **//
    TextButton buttonPlay;
    private Stage stage;
    int score;

    public EndGameScreen(PixelFollower game, int score) {
        this.game = game;
        this.score = score;
        style = new TextButtonStyle();
        style.font = game.font;
        buttonPlay = new TextButton("Play Again",style);
        stage = new Stage();        //** window is stage **//
        stage.clear();
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(30/255f, 30/255f, 30/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        buttonPlay.setPosition(20, 550); //** Button location **//
        buttonPlay.setHeight(100); //** Button Height **//
        buttonPlay.setWidth(600); //** Button Width **//
        game.font.draw(game.batch, score + " Score!", 20, 650);
        game.font.draw(game.batch, "Game Over", 20, 600);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
                game.setScreen(game.getMainMenuScreen());
            }
        });
        stage.addActor(buttonPlay);

    }

    @Override
    public void render(float delta) {
        stage.act();
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
        stage.dispose();
    }
}
