package com.njsoft.pixelfollower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;


public class Enemy extends Actor {
    public Rectangle rectangleLogic;
    public int movX;
    public int movY;
    private int width;
    private int height;
    private Texture texture;
    private Color rectangleColor;
    private Color[] colors;

    Enemy(OrthographicCamera camera) {
        width = 10;
        height = 10;
        rectangleLogic = new Rectangle();
        rectangleLogic.setSize(width,height);
        //Allocate the size of the array
        colors = new Color[5];

        //Initialize the values of the array
        colors[0] = new Color(Color.GREEN);
        colors[1] = new Color(Color.YELLOW);
        colors[2] = new Color(Color.BLUE);
        colors[3] = new Color(Color.RED);
        colors[4] = new Color(Color.WHITE);
        //Color aleatorio
        int rnd = new Random().nextInt(colors.length);
        rectangleColor = colors[rnd];

        movX = (int) Math.round(Math.random() * 3);
        if (movX == 0) {
            rectangleLogic.setPosition(MathUtils.random(0, camera.viewportWidth), camera.viewportHeight);
            movY = 3;
        }
        else { //movY =0
            rectangleLogic.setPosition(camera.viewportWidth,MathUtils.random(0, camera.viewportHeight));
        }

        createTexture(rectangleColor);
    }

    private void createTexture(Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, width, height);
        texture = new Texture(pixmap);
        pixmap.dispose();

    }

    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(rectangleColor.r, rectangleColor.g, rectangleColor.b, rectangleColor.a * parentAlpha);
        batch.draw(texture, rectangleLogic.getX(), rectangleLogic.getY(), rectangleLogic.getWidth(), rectangleLogic.getHeight());
    }

    public void setPosition(OrthographicCamera camera,float x, float y) {
        rectangleLogic.setPosition(x,y);
        if (y < 0) { //limite pantalla
            rectangleLogic.setY(camera.viewportHeight);
            rectangleLogic.setX(MathUtils.random(0, camera.viewportWidth));
        }
        if (x < 0) {
            rectangleLogic.setX(camera.viewportWidth);
            rectangleLogic.setY(MathUtils.random(0, camera.viewportHeight));
        }
    }
}