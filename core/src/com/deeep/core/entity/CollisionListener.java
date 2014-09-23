package com.deeep.core.entity;

import com.deeep.core.entity.abstraction.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/29/13
 * Time: 8:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CollisionListener {
    public void collide(Entity other, Entity own);
}
