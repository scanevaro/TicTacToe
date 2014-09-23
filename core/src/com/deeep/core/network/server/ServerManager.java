package com.deeep.core.network.server;

import com.deeep.core.entity.abstraction.Manager;
import com.deeep.core.network.mutual.packets.Packet;
import com.deeep.core.network.mutual.packets.PositionPacket;
import com.deeep.core.entity.abstraction.Entity;
import com.deeep.core.network.mutual.packets.EntityCreationPacket;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/30/13
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerManager extends Manager {
    private HashMap<Integer, Entity> integerEntityHashMap;
    private ArrayList<PositionPacket> movementChanges;
    private EntityIdManager entityIdManager;
    private BetterServer betterServer;

    public ServerManager(EntityIdManager entityIdManager, BetterServer betterServer) {
        integerEntityHashMap = new HashMap<Integer, Entity>();
        this.entityIdManager = entityIdManager;
        this.betterServer = betterServer;
    }

    @Override
    public void addEntity(Entity entity) {
        super.addInternalEntity(entity);
        entity.setId(entityIdManager.getId());
        entity.manager = this;
        integerEntityHashMap.put(entity.getId(), entity);
        betterServer.sendToAllTCP(entity.getPacket());
    }

    @Override
    protected void addInternalEntity(Entity entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void updateSingleEntity(Entity entity) {
        if(entity.isChanged()){
            Packet packet = entity.getSpecifics();
            if(packet!=null){
                betterServer.sendToAllTCP(packet);
            }
        }
    }

    @Override
    public void updateEntities(float deltaT) {
        movementChanges = getMap().getPositionPackets();
        if (movementChanges != null) {
            if (movementChanges.size() > 0) {
                for (PositionPacket positionPacket : movementChanges) {
                    betterServer.sendToAllTCP(positionPacket);
                    //TODO implement udp after testing
                }
            }
        }
    }

    public ArrayList<EntityCreationPacket> getEntityPackets() {
        ArrayList<EntityCreationPacket> entityCreationPackets = new ArrayList<EntityCreationPacket>();
        for (Entity entity : getEntities()) {
            entityCreationPackets.add(entity.getPacket());
        }
        return entityCreationPackets;
    }
}
