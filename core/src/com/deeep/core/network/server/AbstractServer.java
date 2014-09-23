package com.deeep.core.network.server;

import com.deeep.core.network.mutual.packets.Packet;
import com.deeep.core.network.mutual.Network;
import com.deeep.core.network.mutual.PlayerConnection;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/29/13
 * Time: 9:30 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractServer extends Server {

    public AbstractServer() {
        super();
        Network.register(this);
        addListener(new ServerListener(this));
    }

    @Override
    protected Connection newConnection() {
        return new PlayerConnection();
    }

    public abstract void packetReceived(Connection connection, Packet packet);

    public boolean startListening() {
        try {
            bind(Network.TCP_PORT, Network.UDP_PORT);
            start();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
