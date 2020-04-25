package com.njsoft.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.njsoft.pixelfollower.Enemy;
import com.njsoft.pixelfollower.Guy;
import com.njsoft.pixelfollower.PixelFollower;

public class GameScreen implements Screen {

    private final PixelFollower game;
    private Guy goodGuy;
    private long lastDropTime = 0;
    private Music music;
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
        game.font.draw(game.batch,"LEVEL: " + game.level, game.camera.viewportWidth/2-30, game.camera.viewportHeight/2);
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
        music.dispose();
    }

    private void drawEnemies() {
        //Enemies Add
        Enemy enemy;
        if (TimeUtils.nanoTime() - lastDropTime > delay || enemiesArray.size == 0 ){
            enemy = new Enemy(game);
            enemiesArray.add(enemy);
            lastDropTime = TimeUtils.nanoTime();
            game.score = game.score + 10;
        }
        //Enemies draw
        for (int iterator =0; iterator< enemiesArray.size; iterator++) {
            enemy = enemiesArray.get(iterator);
            enemiesArray.get(iterator).move();

            enemy.draw(texture,game.batch);
            if (enemy.rectangleLogic.overlaps(goodGuy.rectangleLogic)) {
                enemiesArray.clear();
                enemiesArray.truncate(1);
                saveScore(game.score);
                music.stop();
                game.setScreen(new EndGameScreen(game));
                dispose();

            }
        }
    }
    private void checkScore() {

        if (game.score == 600) {
            game.score = game.score + 10;
            game.level=2;
            dispose();
            game.setScreen(new NextLevelScreen(game));
        }
        if (game.score == 1000) {
            game.score = game.score + 10;
            game.level=3;
            dispose();
            game.setScreen(new NextLevelScreen(game));
        }
        if (game.score == 1400) {
            game.score = game.score + 10;
            game.level=4;
            dispose();
            game.setScreen(new NextLevelScreen(game));
        }
        if (game.score == 1700) {
            game.score = game.score + 10;
            game.level=5;
        }
        if (game.score == 2000) {
            game.score = game.score + 10;
            game.level=6;
        }
        if (game.score == 2200) {
            game.score = game.score + 10;
            game.level=7;
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
        game.font.draw(game.batch,"Score: " + game.score, game.camera.viewportWidth/2-30, game.camera.viewportHeight-15);
        game.font.draw(game.batch,"Record: " + game.prefs.getInteger("score", 0), game.camera.viewportWidth*3/4, game.camera.viewportHeight-15);
        checkScore();
    }
}
