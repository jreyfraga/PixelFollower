package com.njsoft.pixelfollower;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.njsoft.Screens.MainMenuScreen;

public class PixelFollower extends Game {
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public BitmapFont font;
	public Preferences prefs;
	private PixelFollower game;
	private MainMenuScreen mainMenuScreen;

	@Override
	public void create () {
		game = this;
		prefs = Gdx.app.getPreferences("My Preferences");
		font = new BitmapFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480,800);
		batch = new SpriteBatch();
		mainMenuScreen = new MainMenuScreen(this);
		this.setScreen(mainMenuScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public Texture createTexture(int width, int height, Color color) {
		Texture texture;
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, width, height);
		texture = new Texture(pixmap);
		pixmap.dispose();
		return texture;
	}

	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}


}
