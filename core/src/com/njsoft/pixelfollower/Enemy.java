package com.njsoft.pixelfollower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Enemy extends Actor {
    public Rectangle rectangleLogic;
    private Integer speed;
    private PixelFollower game;
    private String typeOfMovement;
    private Integer circleDegrees;
    private float startXRound;
    private float startYRound;

    public Enemy(PixelFollower game) {
        this.game = game;
        int width = 10;
        int height = 10;
        circleDegrees = 0;
        rectangleLogic = new Rectangle();
        rectangleLogic.setSize(width, height);
        //Center of the enemies going round
        startXRound = 0;
        startYRound =  MathUtils.random(0, game.camera.viewportHeight);

        //Allocate the size of the array
        Color[] colors;
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
        //Movements

        List<String> ls=new ArrayList<String>();
        ls.add("Horizontal");
        ls.add("Vertical");
        ls.add("Two");
        ls.add("Round");

        //Speed aleatory.
        speed = (int) MathUtils.random(1,game.level+3);
        //Set a movement depending of the level.
        typeOfMovement = ls.get((int) MathUtils.random(0,game.level-1));

    }

    public void draw(Texture texture, Batch batch) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * 1);
        batch.draw(texture, rectangleLogic.getX(), rectangleLogic.getY(), rectangleLogic.getWidth(), rectangleLogic.getHeight());
    }

    public void move() {
        float x,y;
        x = rectangleLogic.getX();
        y = rectangleLogic.getY();

        if (y <= 0) { //limite pantalla
            y = game.camera.viewportHeight;
            x = MathUtils.random(0, game.camera.viewportWidth);
        }
        if (x <= 0) {
            x = game.camera.viewportWidth;
            y = MathUtils.random(0, game.camera.viewportHeight);
        }
        switch (typeOfMovement) {
            case "Horizontal":
                x = x- speed;
                break;
            case "Vertical":
                y = y- speed;
                break;
            case "Two":
                x = x- speed;
                y = y- speed;
                break;
            case "Round":
                circleDegrees = circleDegrees + speed/4;
                x = startXRound + (float) (400*Math.cos(circleDegrees*Math.PI/180));
                y = startYRound + (float) (400*Math.sin(circleDegrees*Math.PI/180));
        }
        rectangleLogic.setPosition(x,y);
    }


}