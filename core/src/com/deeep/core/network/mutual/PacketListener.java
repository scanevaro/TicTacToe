package com.deeep.core.network.mutual;

import com.deeep.core.network.mutual.packets.Packet;
import com.deeep.core.network.mutual.packets.ReceivedPacket;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/31/13
 * Time: 11:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class PacketListener {
    public Class<? extends Packet> listener;
    public PacketAction packetAction;

    public PacketListener(Class<? extends Packet> listener, PacketAction packetAction) {
        this.listener = listener;
        this.packetAction = packetAction;
    }

    public interface PacketAction {
        public void action(ReceivedPacket receivedPacket);
    }

    public String toString(){
        return "Listener: " + listener.getClass() + " action: " + packetAction;
    }
}
