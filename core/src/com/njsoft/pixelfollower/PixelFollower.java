package com.njsoft.pixelfollower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class PixelFollower extends Game {
	private SpriteBatch batch;
	private Guy goodGuy;
	private Color color;
	public OrthographicCamera camera;
    private Enemy enemy;
	private BitmapFont font;
	public int score;
	private long lastDropTime;
	private Array<Enemy> enemiesArray;
	private long delay;

	@Override
	public void create () {
		font = new BitmapFont();
		color =  new Color(0,0,1,1);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480,800);


		batch = new SpriteBatch();
		goodGuy = new Guy(camera.viewportWidth/2,20,30,30, color);
        //enemy = new Enemy(MathUtils.random(0, camera.viewportWidth),camera.viewportHeight,10,10, new Color(1,0,0,1));
		enemiesArray = new Array<Enemy>();
		lastDropTime = 0;
		score = 0;
		delay = 1000000000;
	}

	@Override
	public void render () {

		batch.setProjectionMatrix(camera.combined);

		camera.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		if (Gdx.input.isTouched()) {
			goodGuy.setPosition(camera, Gdx.input.getX(),Gdx.input.getY());
		}
        goodGuy.draw(batch,1);
		drawEnemies();
        font.draw(batch,"Score: " + score, camera.viewportWidth/2-30, camera.viewportHeight-20);
		checkScore();
        batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	private void drawEnemies() {
		//Enemies draw
		if (TimeUtils.nanoTime() - lastDropTime > delay || enemiesArray.size == 0 ){
			enemy = new Enemy(camera);
			enemiesArray.add(enemy);
			lastDropTime = TimeUtils.nanoTime();
			score=score+10;
		}

		for (int iterator =0; iterator< enemiesArray.size; iterator++) {
			enemy = enemiesArray.get(iterator);
			enemiesArray.get(iterator).setPosition(camera, enemy.rectangleLogic.getX()-enemy.movX, enemy.rectangleLogic.getY() - enemy.movY);
			enemy.draw(batch, 1);
			if (enemy.rectangleLogic.overlaps(goodGuy.rectangleLogic)) {
				enemiesArray.clear();
				score=0;
			}
		}

	}
	private void checkScore() {
		BitmapFont message = new BitmapFont();
		message.getData().setScale(3);
		message.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		if (100 <= score && score <= 110) {
			message.draw(batch,"GREAT!!", 5,camera.viewportHeight-10);
		}
		if (200 <= score && score <= 210) {
			message.draw(batch,"MASTER!!", 5,camera.viewportHeight-10);
		}
		if (300 <= score && score <= 310) {
			message.draw(batch,"ULTRA!!", 5,camera.viewportHeight-10);
		}
		if (400 <= score && score <= 410) {
			message.draw(batch,"UNBREAKABLE!!", 5,camera.viewportHeight-10);
		}
		if (500 <= score && score <= 510) {
			message.draw(batch,"GOD!!", 5,camera.viewportHeight-10);
		}
		if (600 <= score && score <= 610) {
			message.draw(batch,"STOP THIS!!", 5,camera.viewportHeight-10);
		}
		if (700 <= score && score <= 710) {
			message.draw(batch,"MADNESS!!", 5,camera.viewportHeight-10);
		}
	}
}
