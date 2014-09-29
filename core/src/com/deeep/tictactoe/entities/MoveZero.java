package com.deeep.tictactoe.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.deeep.core.entity.abstraction.Entity;
import com.deeep.core.system.Constants;

/**
 * Created by Elmar on 9/24/2014.
 */
public class MoveZero extends Entity {
    private ShapeRenderer shapeRenderer;

    public MoveZero() {
        this.x = -1;
        this.y = -1;
        this.mapX = -1;
        this.mapY = -1;
    }

    public MoveZero(int x, int y) {
        this.x = x;
        this.y = y;
        this.mapX = x;
        this.mapY = y;
    }

    @Override
    protected void loadAssets() {
        shapeRenderer = new ShapeRenderer();
        this.mapX = (int) this.getX();
        this.mapY = (int) this.getY();
    }

    @Override
    public void implementUpdate(float deltaT) {

    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        if (mapX == -1)
            return;
        System.out.println("being drawn");
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.ellipse(mapX * Constants.VIRTUAL_HEIGHT / 3,
                mapY * Constants.VIRTUAL_HEIGHT / 3,
                Constants.VIRTUAL_HEIGHT / 3,
                Constants.VIRTUAL_HEIGHT / 3);
        shapeRenderer.end();
    }
}
