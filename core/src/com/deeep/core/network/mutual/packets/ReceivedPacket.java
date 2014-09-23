package com.deeep.core.network.mutual.packets;

import com.esotericsoftware.kryonet.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/30/13
 * Time: 10:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReceivedPacket {
    public Packet containedPacket;
    public Connection connection;

    public String toString() {
        return connection + "\n" + containedPacket.toString();
    }
}
