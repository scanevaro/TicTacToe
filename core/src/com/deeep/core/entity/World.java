package com.deeep.core.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.core.entity.manager.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/28/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class World {
    EntityManager entityManager;

    public World() {
        entityManager = new EntityManager();
    }

    public void draw(SpriteBatch spriteBatch) {
        entityManager.draw(spriteBatch);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void update(float deltaT) {
        entityManager.update(deltaT);
    }
}
