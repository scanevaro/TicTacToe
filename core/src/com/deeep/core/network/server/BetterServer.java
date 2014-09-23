package com.deeep.core.network.server;

import com.deeep.core.network.mutual.packets.Packet;
import com.deeep.core.network.mutual.packets.ReceivedPacket;
import com.esotericsoftware.kryonet.Connection;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/30/13
 * Time: 10:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class BetterServer extends AbstractServer {
    private LinkedList<ReceivedPacket> packets = new LinkedList<ReceivedPacket>();

    public AbstractServer getServer() {
        return this;
    }

    @Override
    public void packetReceived(Connection connection, Packet packet) {
        ReceivedPacket receivedPacket = new ReceivedPacket();
        receivedPacket.containedPacket = packet;
        receivedPacket.connection = connection;
        packets.add(receivedPacket);
    }

    public ReceivedPacket getReceivedPacket() {
        return packets.poll();
    }

    public int getSize() {
        return packets.size();
    }
}
