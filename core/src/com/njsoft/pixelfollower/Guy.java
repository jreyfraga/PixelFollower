package com.njsoft.pixelfollower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

#Perro

public class Guy extends Actor {
    public Rectangle rectangleLogic;
    private Texture texture;
    private Color rectangleColor;

    public Guy(float x, float y, float width, float height, Color color) {

        rectangleLogic = new Rectangle();
        rectangleLogic.setPosition(x,y);
        rectangleLogic.setSize(width,height);
        createTexture((int)width, (int)height, color);
        rectangleColor = color;

    }

    private void createTexture(int width, int height, Color color) {
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
        //rectangleLogic.setY(x);
        Vector3 touchPos = new Vector3();
        touchPos.set(x, y, 0);
        camera.unproject(touchPos);
        rectangleLogic.setX(touchPos.x);
        rectangleLogic.setY(touchPos.y);
    }

}
