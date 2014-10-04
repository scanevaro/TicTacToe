package com.deeep.core.network.client;

import com.deeep.core.network.mutual.ConnectionOptions;
import com.deeep.core.network.mutual.packets.Packet;
import com.deeep.core.network.mutual.packets.RegisterPlayer;
import com.deeep.core.network.mutual.packets.ReceivedPacket;
import com.deeep.core.util.Logger;
import com.esotericsoftware.kryonet.Connection;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/30/13
 * Time: 10:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class BetterClient extends AbstractClient {
    private ConcurrentLinkedQueue<ReceivedPacket> packets = new ConcurrentLinkedQueue<ReceivedPacket>();
    private String gameId;
    private String name;
    private ConnectionOptions connectionOptions;

    public BetterClient(String gameId, String name) {
        connectionOptions = new ConnectionOptions();
        this.gameId = gameId;
        this.name = name;
    }

    public void setConnectionOptions(ConnectionOptions connectionOptions) {
        this.connectionOptions = new ConnectionOptions();
    }

    public AbstractClient getClient() {
        return this;
    }

    @Override
    public void packetReceived(Connection connection, Packet packet) {
        ReceivedPacket receivedPacket = new ReceivedPacket();
        receivedPacket.containedPacket = packet;
        receivedPacket.connection = connection;
        packets.add(receivedPacket);
    }

    public int getSize() {
        return packets.size();
    }

    @Override
    public void onConnect(Connection connection) {
        Logger.getInstance().system(this.getClass(), "Connected with id: " + connection.getID() + "  successfully to: " + connection.getRemoteAddressTCP());
        RegisterPlayer registerPlayer = new RegisterPlayer();
        registerPlayer.gameId = gameId;
        registerPlayer.name = name;
        registerPlayer.connectionOptions = connectionOptions;
        connection.sendTCP(registerPlayer);
    }

    public ReceivedPacket getReceivedPacket() {
        return packets.poll();
    }
}
