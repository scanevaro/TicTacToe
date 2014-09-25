package com.deeep.tictactoe;

import com.deeep.core.network.server.ServerLoop;
import com.deeep.core.system.Core;

/**
 * Created by Elmar on 9/25/2014.
 */
public class Game extends Core {
    private ServerGame serverGame;

    public Game(boolean android, boolean host) {
        super(android, host);
    }

    @Override
    public void onHost(ServerLoop serverLoop) {
        serverGame = new ServerGame(serverLoop);
    }

    @Override
    public void onJoin() {
        //switch to the game screen

    }

    @Override
    public void onUpdate(float deltaTime) {
        //update servergame for example

    }
}
