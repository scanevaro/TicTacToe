package com.deeep.core.network.mutual.packets;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/29/13
 * Time: 9:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class PositionPacket extends Packet {
    public int id;
    public int x, y;
    public int direction;

    public String toString() {
        return id + " : [ " + x + ", " + y + " ] @ " + direction + ";";
    }


}
