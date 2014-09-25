package com.deeep.tictactoe;

import com.deeep.core.entity.types.EntityTypes;
import com.deeep.core.network.mutual.packets.Packet;
import com.deeep.tictactoe.entities.MoveCross;
import com.deeep.tictactoe.entities.MoveZero;

/**
 * Created by Elmar on 9/25/2014.
 */
public class Specifics {
    private static Specifics specifics;

    static {
        if (specifics == null) {
            specifics = new Specifics();
        }
    }

    private static void registerPackets() {

    }

    private static void registerEntities() {
        EntityTypes.registerEntity(MoveCross.class);
        EntityTypes.registerEntity(MoveZero.class);
    }

    public Specifics() {

    }

    public static void setSpecifics() {
    }
}
