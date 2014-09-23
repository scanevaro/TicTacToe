package com.deeep.core.network.mutual.packets;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/31/13
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class ControlPacket extends Packet {
    public ArrayList<Integer> pressedKeys = new ArrayList<Integer>();

    public String toString() {
        String string = "ControlPacket: ";
        for (Integer integer : pressedKeys) {
            string += integer + ",";
        }
        return string;
    }
}
