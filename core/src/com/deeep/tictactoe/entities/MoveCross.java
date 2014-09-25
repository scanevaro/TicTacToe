package com.deeep.tictactoe.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.deeep.core.entity.abstraction.Entity;

/**
 * Created by Elmar on 9/24/2014.
 */
public class MoveCross extends Entity{
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    public MoveCross(int x, int y) {
        this.mapX = x;
        this.mapY = y;
    }

    @Override
    protected void loadAssets() {

    }

    @Override
    public void implementUpdate(float deltaT) {

    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.end();
    }
}
