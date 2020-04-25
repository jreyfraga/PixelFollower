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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.njsoft.Screens.MainMenuScreen;

public class PixelFollower extends Game {
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public BitmapFont font;
	public Preferences prefs;
	private PixelFollower game;
	public Integer score = 0;
	public Integer beginLevelScore = 0;
	private TextureAtlas atlas;
	public Skin skin;
	public Integer level;

	@Override
	public void create () {
		game = this;
		prefs = Gdx.app.getPreferences("My Preferences");
		font = new BitmapFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480,800);
		batch = new SpriteBatch();
		MainMenuScreen mainMenuScreen = new MainMenuScreen(this);
		atlas = new TextureAtlas("skin/skin.atlas");
		skin = new Skin(Gdx.files.internal("skin/skin.json"), atlas);
		level = 1;
		this.setScreen(mainMenuScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
        skin.dispose();
        atlas.dispose();
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

}
