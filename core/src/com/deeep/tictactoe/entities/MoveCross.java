package com.deeep.tictactoe.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.deeep.core.entity.abstraction.Entity;
import com.deeep.core.graphics.GameScreen;
import com.deeep.core.system.Constants;

/**
 * Created by Elmar on 9/24/2014.
 */
public class MoveCross extends Entity {

    public MoveCross() {
        this.x = -1;
        this.y = -1;
        this.mapX = -1;
        this.mapY = -1;
    }

    public MoveCross(int x, int y) {
        this.x = x;
        this.y = y;
        this.mapX = x;
        this.mapY = y;
    }

    @Override
    protected void loadAssets() {
        this.mapX = (int) this.getX();
        this.mapY = (int) this.getY();
    }

    @Override
    public void implementUpdate(float deltaT) {

    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        GameScreen.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        GameScreen.shapeRenderer.setColor(Color.GREEN);
        GameScreen.shapeRenderer.line(mapX * Constants.VIRTUAL_HEIGHT / 3,
                mapY * Constants.VIRTUAL_HEIGHT / 3 + Constants.VIRTUAL_HEIGHT / 3,
                mapX * Constants.VIRTUAL_HEIGHT / 3 + Constants.VIRTUAL_HEIGHT / 3,
                mapY * Constants.VIRTUAL_HEIGHT / 3
        );
        GameScreen.shapeRenderer.line(mapX * Constants.VIRTUAL_HEIGHT / 3,
                mapY * Constants.VIRTUAL_HEIGHT / 3,
                mapX * (Constants.VIRTUAL_HEIGHT / 3) + Constants.VIRTUAL_HEIGHT / 3,
                mapY * Constants.VIRTUAL_HEIGHT / 3 + Constants.VIRTUAL_HEIGHT / 3
        );
        GameScreen.shapeRenderer.end();
    }
}
