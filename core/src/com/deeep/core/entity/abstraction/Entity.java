package com.deeep.core.entity.abstraction;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.core.entity.CollisionListener;
import com.deeep.core.entity.Direction;
import com.deeep.core.entity.types.EntityTypes;
import com.deeep.core.network.mutual.packets.Packet;
import com.deeep.core.entity.Map;
import com.deeep.core.network.mutual.packets.EntityCreationPacket;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/28/13
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Entity extends Element {
    public Manager manager;
    protected Direction previousDirection = Direction.NORTH;
    protected Direction direction = Direction.NORTH;
    protected int id;
    private boolean changed = true;
    private ArrayList<Collide> collisionListeners;
    private int previousMapX, previousMapY;
    protected int mapX, mapY;
    private Map map;
    private int type = -1;

    public Entity() {
        this.collisionListeners = new ArrayList<Collide>();
        if (type == -1) {
            type = EntityTypes.getType(this.getClass());
        }
    }

    protected abstract void loadAssets();

    public void clientCreated() {
        loadAssets();
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void addCollisionListener(Class<? extends Entity> sensitivity, CollisionListener collisionListener) {
        collisionListeners.add(new Collide(collisionListener, sensitivity));
    }

    public void collide(ArrayList<Entity> entities) {
        for (Entity entity : entities) {
            for (Collide collide : collisionListeners) {
                if (collide.getSensitivity().isInstance(entity)) {
                    collide.getCollisionListener().collide(entity, this);
                }
            }
        }
    }

    public void update(float deltaT) {
        implementUpdate(deltaT);
        mapX = (int) x;
        mapY = (int) y;
        if (mapX != previousMapX || mapY != previousMapY) {
            map.move(this, previousMapX, previousMapY);
            previousMapX = mapX;
            previousMapY = mapY;
        }
    }

    public abstract void implementUpdate(float deltaT);

    public abstract void draw(SpriteBatch spriteBatch);

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return type + " : " + id + " [" + mapX + ", " + mapY + "]";
    }

    public EntityCreationPacket getPacket() {
        EntityCreationPacket entityCreationPacket = new EntityCreationPacket();
        entityCreationPacket.id = id;
        entityCreationPacket.type = type;
        entityCreationPacket.x = (int) x;
        entityCreationPacket.y = (int) y;
        return entityCreationPacket;
    }

    public boolean isChanged() {
        boolean temp = changed;
        changed = false;
        return temp;
    }

    public void change() {
        changed = true;
    }

    public int getDirection() {
        return direction.getValue();
    }

    public void setDirection(int direction) {
        this.direction = this.direction.setDirection(direction);
    }

    public int getType() {
        return type;
    }

    /**
     * Some entity Specifics. Override this method if the entity has any specific data to send on change
     *
     * @return
     */
    public Packet getSpecifics() {
        return null;
    }

    public class Collide {
        private CollisionListener collisionListener;
        private Class<? extends Entity> sensitivity;

        public Collide(CollisionListener collisionListener, Class<? extends Entity> sensitivity) {
            this.collisionListener = collisionListener;
            this.sensitivity = sensitivity;
        }

        public CollisionListener getCollisionListener() {
            return collisionListener;
        }

        public Class<? extends Entity> getSensitivity() {
            return sensitivity;
        }
    }
}
