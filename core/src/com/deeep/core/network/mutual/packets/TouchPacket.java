package com.deeep.core.network.mutual.packets;

/**
 * Created by scanevaro on 24/09/2014.
 */
public class TouchPacket extends Packet {

    public boolean touch;
    public float x;
    public float y;

    public TouchPacket() {
        touch = false;
        x = 0;
        y = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TouchPacket) {
            TouchPacket packet = (TouchPacket) obj;
            if (touch == packet.touch && x == packet.x && y == packet.y)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]" + " " + touch;
    }
}
