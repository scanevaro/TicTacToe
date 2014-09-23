package com.deeep.core.network.server;

import com.deeep.core.network.mutual.packets.Packet;
import com.deeep.core.network.mutual.packets.RegisterPlayer;
import com.deeep.core.util.Logger;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/30/13
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerListener extends Listener {
    private AbstractServer abstractServer;

    public ServerListener(AbstractServer abstractServer) {
        this.abstractServer = abstractServer;
    }

    public void received(Connection connection, Object object) {
        if (object instanceof RegisterPlayer) {
            Logger.getInstance().debug(this.getClass(), object);
            abstractServer.packetReceived(connection, (Packet) object);
        } else if (object instanceof Packet) {
            abstractServer.packetReceived(connection, (Packet) object);
        }
    }
}
