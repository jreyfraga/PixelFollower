package com.njsoft.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.njsoft.pixelfollower.Enemy;
import com.njsoft.pixelfollower.Guy;
import com.njsoft.pixelfollower.PixelFollower;

public class GameScreen implements Screen {

    private final PixelFollower game;
    private Guy goodGuy;
    private Integer score;
    private Music music;
    private long lastDropTime;
    private Array<Enemy> enemiesArray;
    private long delay;
    private Texture topBarTexture;
    private Color topBarColor;
    private Texture texture;

    GameScreen(PixelFollower game) {
        this.game = game;
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.play();
        Color color = new Color(0, 0, 1, 1);
        goodGuy = new Guy(game.camera.viewportWidth/2,20,30,30, color);
        enemiesArray = new Array<>();
        topBarColor = new Color(Color.PINK);
        topBarTexture = game.createTexture((int) game.camera.viewportWidth,20,topBarColor);
        texture = game.createTexture(10,10, new Color(1,1,1,1));
        lastDropTime = 0;
        score = 0;
        delay = 1000000000;
    }

    @Override
    public void render (float delta) {

        game.batch.setProjectionMatrix(game.camera.combined);
        game.camera.update();

        Gdx.gl.glClearColor(30/255f, 30/255f, 30/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        if (Gdx.input.isTouched()) {
            goodGuy.setPosition(game.camera, Gdx.input.getX(),Gdx.input.getY());
        }
        goodGuy.draw(game.batch,1);
        drawTopBar();
        drawEnemies();
        game.batch.end();
    }

    @Override
    public void show() {

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
    private void drawEnemies() {
        //Enemies Add
        Enemy enemy;
        if (TimeUtils.nanoTime() - lastDropTime > delay || enemiesArray.size == 0 ){
            enemy = new Enemy(game.camera);
            enemiesArray.add(enemy);
            lastDropTime = TimeUtils.nanoTime();
            score = score + 10;
        }
        //Enemies draw
        for (int iterator =0; iterator< enemiesArray.size; iterator++) {
            enemy = enemiesArray.get(iterator);
            enemiesArray.get(iterator).setPosition(game.camera, enemy.rectangleLogic.getX()- enemy.movX, enemy.rectangleLogic.getY() - enemy.movY);

            enemy.draw(texture,game.batch);
            if (enemy.rectangleLogic.overlaps(goodGuy.rectangleLogic)) {
                enemiesArray.clear();
                enemiesArray.truncate(1);
                saveScore(score);
                music.stop();
                game.setScreen(new EndGameScreen(game,score));
                score=0;
                dispose();

            }
        }
    }
    private void checkScore() {
        BitmapFont message = new BitmapFont();
        //message.getData().setScale(2);
        message.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        if (100 <= score && score <= 110) {
            message.draw(game.batch,"GREAT!!", 5,game.camera.viewportHeight-15);
        }
        if (200 <= score && score <= 210) {
            message.draw(game.batch,"MASTER!!", 5,game.camera.viewportHeight-15);
        }
        if (300 <= score && score <= 310) {
            message.draw(game.batch,"ULTRA!!", 5,game.camera.viewportHeight-15);
        }
        if (400 <= score && score <= 410) {
            message.draw(game.batch,"UNBREAKABLE!!", 5,game.camera.viewportHeight-15);
        }
        if (500 <= score && score <= 510) {
            message.draw(game.batch,"GOD!!", 5,game.camera.viewportHeight-15);
        }
        if (600 <= score && score <= 610) {
            message.draw(game.batch,"STOP THIS!!", 5,game.camera.viewportHeight-15);
        }
        if (700 <= score && score <= 710) {
            message.draw(game.batch,"MADNESS!!", 5,game.camera.viewportHeight-15);
        }
    }
    private void saveScore(int score) {
        if (score > game.prefs.getInteger("score", 0)) {
            game.prefs.putInteger("score", score);
            game.prefs.flush();
        }

    }

    private void drawTopBar() {
        game.batch.setColor(topBarColor);
        game.batch.draw(topBarTexture, 0, game.camera.viewportHeight-40, game.camera.viewportWidth, 40);
        game.font.draw(game.batch,"Score: " + score, game.camera.viewportWidth/2-30, game.camera.viewportHeight-15);
        game.font.draw(game.batch,"Record: " + game.prefs.getInteger("score", 0), game.camera.viewportWidth*3/4, game.camera.viewportHeight-15);
        checkScore();
    }
}
