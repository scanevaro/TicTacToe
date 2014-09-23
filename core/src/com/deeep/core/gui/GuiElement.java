package com.deeep.core.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 11/5/13
 * Time: 11:14 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class GuiElement implements CanvasElement {
    protected int x, y;
    protected float width, height;
    private float percentageWidth, percentageHeight;
    private boolean percentage = false;
    private int z = -1;
    private Rectangle hitBox;

    public GuiElement(int x, int y, boolean percentage, int width, int height) {
        this.x = x;
        this.y = y;
        this.percentage = percentage;
        if (!percentage) {
            this.width = width;
            this.height = height;
        } else {
            this.percentageWidth = width;
            this.percentageHeight = height;
            this.width = (Gdx.graphics.getWidth() * 0.01f) * percentageWidth;
            this.height = (Gdx.graphics.getHeight() * 0.01f) * percentageHeight;
        }
        hitBox = new Rectangle(x, y, this.width, this.height);
    }

    public void resize(int newWidth, int newHeight) {
        if (percentage) {
            this.width = (newWidth * 0.01f) * percentageWidth;
            this.height = (newHeight * 0.01f) * percentageHeight;
        }
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(int height) {
        hitBox.setHeight(height);
        this.height = height;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int newZ) {
        z = newZ;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public abstract void draw(SpriteBatch spriteBatch);
}
