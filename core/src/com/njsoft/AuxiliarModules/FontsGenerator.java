package com.njsoft.AuxiliarModules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontsGenerator {

    private BitmapFont titleFont;
    private BitmapFont textFont;


    public FontsGenerator() {
        FileHandle fontFile = Gdx.files.internal("Fonts/FreeSans.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        textFont = generator.generateFont(parameter);
        parameter.size = 32;
        titleFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public BitmapFont getTitleFont() {
        return titleFont;
    }

    public BitmapFont getTextFont() {
        return textFont;
    }

}

