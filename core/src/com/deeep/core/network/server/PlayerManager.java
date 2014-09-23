package com.deeep.core.network.server;

import com.deeep.core.network.mutual.packets.ControlPacket;
import com.deeep.core.entity.abstraction.Manager;
import com.deeep.core.network.mutual.packets.ReceivedPacket;
import com.esotericsoftware.kryonet.Connection;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/31/13
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerManager {
    private ArrayList<Player> players;
    private HashMap<Connection, Player> playerConnections;
    private Manager manager;

    public PlayerManager(Manager manager) {
        playerConnections = new HashMap<Connection, Player>();
        players = new ArrayList<Player>(3);
        this.manager = manager;
    }

    public void addPlayer(Connection connection) {
        Player player = new Player(connection, manager);
        players.add(player);
        playerConnections.put(connection, player);
    }

    public void changeState(ReceivedPacket receivedPacket) {
        playerConnections.get(receivedPacket.connection).getPlayerControlHandler().updateKeyState((ControlPacket) receivedPacket.containedPacket);
    }

    public Player getPlayer(Connection connection) {
        return playerConnections.get(connection);
    }

    public void update(float deltaT) {
        for (Player player : players) {
            player.update(deltaT);
        }
    }
}
