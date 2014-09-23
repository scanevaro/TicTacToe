package com.deeep.core.entity.types;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.core.entity.CollisionListener;
import com.deeep.core.entity.abstraction.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/28/13
 * Time: 6:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestEntity extends Entity {

    public TestEntity() {
        addCollisionListener(Wall.class, new CollisionListener() {
            @Override
            public void collide(Entity other, Entity own) {
                System.out.println("Wall?");
            }
        });
    }

    @Override
    protected void loadAssets() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void implementUpdate(float deltaT) {
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
