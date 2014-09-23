package com.deeep.core.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 11/5/13
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChatBox extends TextBox {
    private ArrayList<Text> containedText;


    public ChatBox(int x, int y, boolean percentage, int width, int lines) {
        super(x, y, percentage, width, lines);

        containedText = new ArrayList<Text>();

    }

    public void addText(String username, Color color, String text) {
        containedText.add(0, new Text(text, username, color));
    }

    public void setText(String text) {

    }

    public void clear() {
        containedText.clear();
    }

    @Override
    public void update(float deltaT, boolean pressed, Vector2 mouseVector) {
        super.update(deltaT, pressed, mouseVector);

    }


    @Override
    public void draw(SpriteBatch spriteBatch) {
        super.draw(spriteBatch);
        spriteBatch.begin();
        float tempY = y + height;
        for (int i = containedText.size() - 1; i > -1; i--) {
            containedText.get(i).draw(x, tempY, width, bitmapFont, spriteBatch);
            tempY -= containedText.get(i).getHeight(width, bitmapFont) * (bitmapFont.getLineHeight()) * 1 + 5;
        }
        spriteBatch.end();
    }

    public void addCharacter(char character) {
        if (containedText.size() == 0) {
            containedText.add(new Text(character + "", "", Color.WHITE));
        } else {
            containedText.get(containedText.size() - 1).text += character;
        }
    }

    @Override
    public void resize(int newWidth, int newHeight) {

    }

    public class Text {
        private String text;
        private String name;
        private Color color;

        public Text(String text, String name, Color color) {
            this.text = text;
            this.name = name;
            this.color = color;
        }

        public float getHeight(float width, BitmapFont bitmapFont) {
            return bitmapFont.getWrappedBounds("MMMMMM" + text, width).height / bitmapFont.getLineHeight();
        }

        public void draw(int x, float y, float width, BitmapFont bitmapFont, SpriteBatch spriteBatch) {
            bitmapFont.setColor(color);
            bitmapFont.draw(spriteBatch, name, x, y);
            bitmapFont.setColor(Color.WHITE);
            bitmapFont.drawWrapped(spriteBatch, text, bitmapFont.getBounds("MMMMMM").width, y, width - (bitmapFont.getBounds("MMMMMM").width));
        }
    }
}
