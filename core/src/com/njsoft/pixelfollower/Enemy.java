package com.njsoft.pixelfollower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;


class Enemy extends Actor {
    Rectangle rectangleLogic;
    int movX;
    int movY;
    private int width;
    private int height;
    private int speed;


    Enemy(OrthographicCamera camera) {
        Color[] colors;
        width = 10;
        height = 10;
        speed= 5;
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
        setColor(colors[rnd]);

        movX = (int) Math.round(Math.random() * speed);
        if (movX == 0) {
            rectangleLogic.setPosition(MathUtils.random(0, camera.viewportWidth), camera.viewportHeight);
            movY = speed;
        }
        else { //movY =0
            rectangleLogic.setPosition(camera.viewportWidth,MathUtils.random(0, camera.viewportHeight));
        }

    }

    void draw(Texture texture, Batch batch) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * 1);
        batch.draw(texture, rectangleLogic.getX(), rectangleLogic.getY(), rectangleLogic.getWidth(), rectangleLogic.getHeight());
    }

    void setPosition(OrthographicCamera camera, float x, float y) {
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