package com.deeep.core.entity.types;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.core.entity.CollisionListener;
import com.deeep.core.entity.abstraction.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/29/13
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Wall extends Entity {
    public Wall(int x, int y) {
        super();
        setPosition(x, y);
        addCollisionListener(TestEntity.class, new CollisionListener() {
            @Override
            public void collide(Entity other, Entity own) {
                System.out.println("ENTITYYY@!?");
            }
        });
    }

    public Wall() {
        setPosition(5, 5);
        addCollisionListener(TestEntity.class, new CollisionListener() {
            @Override
            public void collide(Entity other, Entity own) {
                System.out.println("ENTITYYY@!?");
            }
        });
    }

    @Override
    protected void loadAssets() {
        //TODO load assets
    }

    @Override
    public void implementUpdate(float deltaT) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
