package com.deeep.core.entity.types;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.core.entity.abstraction.Entity;

/**
 * Created by Elmar on 9/24/2014.
 */
public class MoveZero extends Entity {
    public MoveZero(int x, int y) {
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

    }
}
