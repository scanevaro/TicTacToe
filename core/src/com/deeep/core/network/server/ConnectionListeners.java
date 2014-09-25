package com.deeep.core.network.server;

import com.esotericsoftware.kryonet.Connection;

/**
 * Created by Elmar on 9/25/2014.
 */
public class ConnectionListeners {
    public ConnectionInterface connection;
    public DisconnectionInterface disconnection;

    public interface ConnectionInterface {
        public void onConnect(Connection connection);
    }

    public interface DisconnectionInterface {
        public void onDisconnect(Connection connection);
    }
}
