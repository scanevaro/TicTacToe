package com.deeep.core.network.mutual;

import com.deeep.core.network.mutual.packets.*;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/29/13
 * Time: 9:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Network {
    static public final int TCP_PORT = 12345;
    static public final int UDP_PORT = 23456;

    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Packet.class);
        kryo.register(PositionPacket.class);
        kryo.register(RegisterPlayer.class);
        kryo.register(ConnectionOptions.class);
        kryo.register(ChatPacket.class);
        kryo.register(ControlPacket.class);
        kryo.register(EntityCreationPacket.class);
        kryo.register(ArrayList.class);
        kryo.register(PingPacket.class);
        kryo.register(TouchPacket.class);
        kryo.register(WinPacket.class);
    }
}
