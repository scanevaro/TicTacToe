package com.deeep.core.network.mutual.packets;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 11/1/13
 * Time: 10:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class EntityCreationPacket extends Packet {
    //-1 for server, other for other clients
    public int owner = -1;
    public int x, y;
    public int id;
    public int type;

    @Override
    public String toString() {
        return "entity: " + type + " with id: " + id + " from: " + owner + " created at: " + x + ", " + y;
    }
}
