package com.deeep.core.network.client;

import com.deeep.core.network.mutual.PacketListener;
import com.deeep.core.network.mutual.packets.Packet;
import com.deeep.core.network.mutual.packets.PingPacket;
import com.deeep.core.network.mutual.packets.ReceivedPacket;
import com.deeep.core.network.mutual.packets.WinPacket;
import com.deeep.core.util.Logger;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/31/13
 * Time: 11:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientLoop {
    private BetterClient betterClient;
    private ArrayList<PacketListener> packetListeners;
    private float pingTimer = 0;
    public WinPacket winPacket;

    public ClientLoop() {
        betterClient = new BetterClient();
        packetListeners = new ArrayList<PacketListener>();
    }

    public void connect(String host) {
        betterClient.connect(host);
    }

    public void update(float deltaT) {
        pingTimer += deltaT;
        if (pingTimer >= 10) {
            PingPacket pingPacket = new PingPacket();
            pingPacket.time = System.nanoTime();
            betterClient.sendTcp(pingPacket);
            pingTimer -= 10;
        }
        if (betterClient.getSize() > 0) {
            for (int i = 0, l = betterClient.getSize(); i < l; i++) {
                actPacket(betterClient.getReceivedPacket());
            }
        }
    }

    public void actPacket(ReceivedPacket receivedPacket) {
        if (receivedPacket != null) {
            for (PacketListener packetListener : packetListeners) {
                if (packetListener.listener.isInstance(receivedPacket.containedPacket)) {
                    packetListener.packetAction.action(receivedPacket);
                }
            }
        }
    }

    public BetterClient getClient() {
        return betterClient;
    }

    public void addListener(PacketListener packetListener) {
        packetListeners.add(packetListener);
    }

    public void removeListener(Class<? extends Packet> claz) {
        for (PacketListener packetListener : packetListeners) {
            if (packetListener.listener.isInstance(claz)) {
                packetListeners.remove(packetListener);
                return;
            }
        }
        Logger.getInstance().warn(this.getClass(), "No listener found with class: " + claz);
    }
}
