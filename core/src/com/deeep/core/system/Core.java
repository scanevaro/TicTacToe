package com.deeep.core.system;

import com.badlogic.gdx.Gdx;
import com.deeep.core.graphics.GameScreen;
import com.deeep.core.network.client.ClientLoop;
import com.deeep.core.network.mutual.PacketListener;
import com.deeep.core.network.mutual.packets.ChatPacket;
import com.deeep.core.util.Camera;
import com.deeep.core.util.Logger;
import com.deeep.core.util.UpdateAble;
import com.deeep.core.controlls.NetworkController;
import com.deeep.core.graphics.Assets;
import com.deeep.core.network.mutual.packets.PingPacket;
import com.deeep.core.network.mutual.packets.ReceivedPacket;
import com.deeep.core.network.server.ServerLoop;
import com.deeep.core.util.AbstractGame;

import java.util.ArrayList;

/** This class is the entry point to the game */
public class Core extends AbstractGame {
    public static boolean android = false;
    private ArrayList<UpdateAble> updateListeners = new ArrayList<UpdateAble>();
    private ServerLoop serverLoop;
    private ClientLoop clientLoop;
    private NetworkController networkController;
    private boolean host;

    public Core(boolean android, boolean host) {
        this.host = host;
        Core.android = android;
    }

    /** Called when the {@link com.badlogic.gdx.Application} is first created. */
    @Override
    public void create() {
        Assets.getAssets().load();
        if (this.host) {
            serverLoop = new ServerLoop();
            serverLoop.start();
        } else {
            clientLoop = new ClientLoop();
            networkController = new NetworkController(clientLoop);
            clientLoop.connect("192.168.2.13");
            //clientLoop.connect("127.0.0.1");
            //clientLoop.connect("181.164.23.215");
            clientLoop.addListener(new PacketListener(ChatPacket.class, new PacketListener.PacketAction() {
                @Override
                public void action(ReceivedPacket receivedPacket) {
                    Logger.getInstance().debug(this.getClass(), "Received from server: " + ((ChatPacket) receivedPacket.containedPacket).text);
                }
            }));
            clientLoop.addListener(new PacketListener(PingPacket.class, new PacketListener.PacketAction() {
                @Override
                public void action(ReceivedPacket receivedPacket) {
                    long ping = System.nanoTime() - ((PingPacket) receivedPacket.containedPacket).time;
                    Logger.getInstance().debug(this.getClass(), "Ping: " + ping);
                }
            }));
            setScreen(new GameScreen(this, clientLoop));
        }
    }

    public void addUpdateListener(UpdateAble updateAble) {
        updateListeners.add(updateAble);
    }

    /** This will get rid of all the assets to prevent a memory leak */
    @Override
    public void dispose() {
        super.dispose();
        Assets.getAssets().dispose();
    }

    /** This should in the future render a background */
    @Override
    public void render(float deltaTime) {
        try {
            Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond());
            if (host) {
                serverLoop.update(deltaTime);
            } else {
                //Camera.getInstance().update(deltaTime);
                clientLoop.update(deltaTime);
                networkController.update(deltaTime);
            }
            for (UpdateAble updateAble : updateListeners) {
                updateAble.update(deltaTime);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            dispose();
            Gdx.app.exit();
        }
    }
}
