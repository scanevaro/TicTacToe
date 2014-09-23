package com.deeep.core.network.client;

import com.deeep.core.network.mutual.Network;
import com.deeep.core.network.mutual.packets.Packet;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/29/13
 * Time: 9:30 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractClient {
    private Client client;

    public AbstractClient() {
        client = new Client();
        client.start();
        Network.register(client);
        client.addListener(new Listener() {
            public void connected(Connection connection) {
                onConnect(connection);
            }

            public void received(Connection connection, Object object) {
                if (object instanceof Packet) {                  //Game packet
                    packetReceived(connection, (Packet) object);
                }
            }
        });
    }

    public void addListener(Listener listener) {
        client.addListener(listener);
    }

    public abstract void packetReceived(Connection connection, Packet packet);

    public void sendUdp(Packet packet) {
        client.sendUDP(packet);
    }

    public void sendTcp(Packet packet) {
        client.sendTCP(packet);
    }

    public abstract void onConnect(Connection connection);

    public boolean connect(String host) {
        try {
            client.connect(5000, host, Network.TCP_PORT, Network.UDP_PORT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void disconnect() {
        client.close();
    }
}
