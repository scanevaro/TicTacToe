package com.deeep.core.entity.manager;

import com.deeep.core.entity.types.EntityTypes;
import com.deeep.core.network.mutual.packets.PositionPacket;
import com.deeep.core.entity.abstraction.Entity;
import com.deeep.core.entity.abstraction.Manager;
import com.deeep.core.network.mutual.packets.EntityCreationPacket;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/28/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityManager extends Manager {
    private HashMap<Integer, Entity> integerEntityHashMap;

    public EntityManager() {
        integerEntityHashMap = new HashMap<Integer, Entity>();
    }

    @Override
    protected void updateSingleEntity(Entity entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateEntities(float deltaT) {
        //Should be handled on the server
    }


    public void addEntity(EntityCreationPacket entityCreation) {
        //TODO add the type
        Entity entity = null;
        try {
            entity = EntityTypes.getEntity(entityCreation.type).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        entity.clientCreated();
        entity.setPosition(entityCreation.x, entityCreation.y);
        integerEntityHashMap.put(entityCreation.id, entity);
        super.addEntity(entity);
    }

    public void moveEntity(PositionPacket positionPacket) {
        integerEntityHashMap.get(positionPacket.id).setPosition(positionPacket.x, positionPacket.y);
        integerEntityHashMap.get(positionPacket.id).setDirection(positionPacket.direction);
    }
}
