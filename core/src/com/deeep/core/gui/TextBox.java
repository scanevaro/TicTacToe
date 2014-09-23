package com.deeep.core.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 11/13/13
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class TextBox extends GuiElement {
    public final int OFFSET = 1;
    protected ShapeRenderer shapeRenderer;
    protected BitmapFont bitmapFont;
    private float transparency = 1;
    private Color color1, color2;

    public TextBox(int x, int y, boolean percentage, int width, int lines) {
        super(x, y, percentage, width, 0);
        shapeRenderer = new ShapeRenderer();
        color1 = Color.BLACK;
        color2 = Color.GRAY;
        setTransparency(0.4f);
        bitmapFont = new BitmapFont();
        bitmapFont.setScale(1f, 1f);
        setHeight((int) (bitmapFont.getLineHeight() * lines));
    }

    public void setTransparency(float transparency) {
        this.transparency = transparency;
        color1.set(color1.r, color1.g, color1.b, transparency);
        color2.set(color2.r, color2.g, color2.b, transparency);
    }

    @Override
    public void update(float deltaT, boolean pressed, Vector2 mouseVector) {
        if (getHitBox().contains(mouseVector)) {
            setTransparency(0.4f);
        } else {
            setTransparency(0.3f);
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_COLOR, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color1);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.setColor(color2);
        shapeRenderer.rect(x + OFFSET, y + OFFSET, width - OFFSET * 2, height - OFFSET * 2);
        shapeRenderer.end();
        shapeRenderer.setColor(Color.BLACK);
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public float getHeight() {
        return height + OFFSET * 2;
    }
}
