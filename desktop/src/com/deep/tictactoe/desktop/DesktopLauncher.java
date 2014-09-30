package com.deep.tictactoe.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.deeep.tictactoe.ClientGame;
import com.deeep.tictactoe.ServerGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 600;
        config.height = 360;
        ServerGame serverGame = new ServerGame();
        serverGame.start();
        new LwjglApplication(new ClientGame("127.0.0.1"), config);
    }
}
