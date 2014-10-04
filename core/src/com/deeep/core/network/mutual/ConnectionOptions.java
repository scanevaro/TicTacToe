package com.deeep.core.network.mutual;

/**
 * Created by Elmar on 10/2/2014.
 */
public class ConnectionOptions {
    /**
     * Whether the connections should have a reserved playing slot
     */
    public boolean participator;

    public String toString() {
        return (participator) ? " participating" : " observing";
    }
}
