package com.njsoft.pixelfollower;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class PixelFollower extends Game {
	private SpriteBatch batch;
	private Guy goodGuy;
	private OrthographicCamera camera;
	private int score;
	private BitmapFont font;
	private Music music;
	private long lastDropTime;
	private Array<Enemy> enemiesArray;
	private long delay;
	private Preferences prefs;
	private Texture topBarTexture;
	private Color topBarColor;
	private Texture texture;

	@Override
	public void create () {
		prefs = Gdx.app.getPreferences("My Preferences");
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.play();
		font = new BitmapFont();
		Color color = new Color(0, 0, 1, 1);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480,800);
		batch = new SpriteBatch();
		goodGuy = new Guy(camera.viewportWidth/2,20,30,30, color);
		enemiesArray = new Array<Enemy>();
		topBarColor = new Color(Color.PINK);
		topBarTexture = createTexture((int) camera.viewportWidth,20,topBarColor);
		texture = createTexture(10,10, new Color(1,1,1,1));
		lastDropTime = 0;
		score = 0;
		delay = 1000000000;
	}

	@Override
	public void render () {

		batch.setProjectionMatrix(camera.combined);
		camera.update();

		Gdx.gl.glClearColor(51/255f, 51/255f, 51/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		if (Gdx.input.isTouched()) {
			goodGuy.setPosition(camera, Gdx.input.getX(),Gdx.input.getY());
		}
        goodGuy.draw(batch,1);
		drawEnemies();
        drawTopBar();
        batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	private void drawEnemies() {
		//Enemies Add
		Enemy enemy;
		if (TimeUtils.nanoTime() - lastDropTime > delay || enemiesArray.size == 0 ){
				enemy = new Enemy(camera);
				enemiesArray.add(enemy);
				lastDropTime = TimeUtils.nanoTime();
				score = score + 10;
		}
		//Enemies draw
		for (int iterator =0; iterator< enemiesArray.size; iterator++) {
			enemy = enemiesArray.get(iterator);
			enemiesArray.get(iterator).setPosition(camera, enemy.rectangleLogic.getX()- enemy.movX, enemy.rectangleLogic.getY() - enemy.movY);

			enemy.draw(texture,batch);
			if (enemy.rectangleLogic.overlaps(goodGuy.rectangleLogic)) {
				enemiesArray.clear();
				enemiesArray.truncate(1);
				saveScore(score);
				score=0;
				music.stop();
				music.play();
			}
		}
	}
	private void checkScore() {
		BitmapFont message = new BitmapFont();
		//message.getData().setScale(2);
		message.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		if (100 <= score && score <= 110) {
			message.draw(batch,"GREAT!!", 5,camera.viewportHeight-15);
		}
		if (200 <= score && score <= 210) {
			message.draw(batch,"MASTER!!", 5,camera.viewportHeight-15);
		}
		if (300 <= score && score <= 310) {
			message.draw(batch,"ULTRA!!", 5,camera.viewportHeight-15);
		}
		if (400 <= score && score <= 410) {
			message.draw(batch,"UNBREAKABLE!!", 5,camera.viewportHeight-15);
		}
		if (500 <= score && score <= 510) {
			message.draw(batch,"GOD!!", 5,camera.viewportHeight-15);
		}
		if (600 <= score && score <= 610) {
			message.draw(batch,"STOP THIS!!", 5,camera.viewportHeight-15);
		}
		if (700 <= score && score <= 710) {
			message.draw(batch,"MADNESS!!", 5,camera.viewportHeight-15);
		}
	}
	private void saveScore(int score) {
		if (score > prefs.getInteger("score", 0)) {
			prefs.putInteger("score", score);
			prefs.flush();
		}

	}

	private void drawTopBar() {
		batch.setColor(topBarColor);
		batch.draw(topBarTexture, 0, camera.viewportHeight-40, camera.viewportWidth, 40);
		font.draw(batch,"Score: " + score, camera.viewportWidth/2-30, camera.viewportHeight-15);
		font.draw(batch,"Record: " + prefs.getInteger("score", 0), camera.viewportWidth*3/4, camera.viewportHeight-15);
		checkScore();
	}

	private Texture createTexture(int width, int height,Color color) {
		Texture texture;
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, width, height);
		texture = new Texture(pixmap);
		pixmap.dispose();
		return texture;
	}
}
