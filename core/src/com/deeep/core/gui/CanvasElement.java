package com.deeep.core.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 11/5/13
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CanvasElement {
    public void update(float deltaT, boolean pressed, Vector2 mouseVector);

    public void draw(SpriteBatch spriteBatch);

    public void resize(int newX, int newY);
}
