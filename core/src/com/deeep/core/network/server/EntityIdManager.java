package com.deeep.core.network.server;

import com.deeep.core.util.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/31/13
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class EntityIdManager {
    private final int maxEntities;
    private int currentEntity = 0;

    public EntityIdManager(int maxEntities) {
        this.maxEntities = maxEntities;
    }

    public int getId() {
        if (currentEntity > maxEntities) {
            Logger.getInstance().error(this.getClass(), "Too many entities!!!");
        }
        return currentEntity++;
    }

    public void reset() {
        currentEntity = 0;
    }
}
