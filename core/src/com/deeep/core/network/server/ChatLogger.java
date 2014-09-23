package com.deeep.core.network.server;

import com.deeep.core.util.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/31/13
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChatLogger {
    public ChatLogger() {
    }

    public void logText(String text) {
        Logger.getInstance().debug(this.getClass(), "Server log: " + text);
    }
}
