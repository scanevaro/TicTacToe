package com.deeep.core.network.mutual.packets;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/29/13
 * Time: 9:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegisterPlayer extends Packet {
    public String name;
    public int skinId;

    @Override
    public String toString() {
        return name + " " + skinId;
    }
}
