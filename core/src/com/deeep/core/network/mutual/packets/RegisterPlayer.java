package com.deeep.core.network.mutual.packets;

import com.deeep.core.network.mutual.ConnectionOptions;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/29/13
 * Time: 9:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegisterPlayer extends Packet {
    public String name;
    public String gameId;
    public ConnectionOptions connectionOptions;

    @Override
    public String toString() {
        return name + " " + gameId + connectionOptions.toString();
    }
}
