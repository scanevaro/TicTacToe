package com.deeep.core.network.mutual.packets;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 11/22/13
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class TailSpecificPacket extends Packet {
    public boolean corner = false;
    public boolean end = false;
    public int direction = 0;
}
