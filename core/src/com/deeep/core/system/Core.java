package com.deeep.core.system;

import com.badlogic.gdx.Gdx;
import com.deeep.core.controlls.NetworkTouchController;
import com.deeep.core.graphics.Assets;
import com.deeep.core.graphics.SplashScreen;
import com.deeep.core.network.client.ClientLoop;
import com.deeep.core.network.mutual.PacketListener;
import com.deeep.core.network.mutual.packets.*;
import com.deeep.core.network.server.ServerLoop;
import com.deeep.core.util.AbstractGame;
import com.deeep.core.util.Logger;
import com.deeep.core.util.UpdateAble;

import java.util.ArrayList;

/**
 * This class is the entry point to the game
 */
public abstract class Core extends AbstractGame {
    public static boolean android = false;
    private ArrayList<UpdateAble> updateListeners = new ArrayList<UpdateAble>();
    private ServerLoop serverLoop;
    private ClientLoop clientLoop;
    private NetworkTouchController networkTouchController;
    private boolean host;

    public Core(boolean android, boolean host) {
        this.host = host;
        Core.android = android;
    }

    /**
     * Called when the {@link com.badlogic.gdx.Application} is first created.
     */
    @Override
    public void create() {
        Assets.getAssets().load();
        if (this.host) {
            serverLoop = new ServerLoop();
            serverLoop.start();
            onHost(serverLoop);
            connect("127.0.0.1");
            onJoin();
        } else {
//            connect("192.168.2.13");
            connect("127.0.0.1");
            onJoin();
        }

        setClientListeners();
//        setScreen(new GameScreen(this, clientLoop));
        setScreen(new SplashScreen(this));
    }

    public abstract void onHost(ServerLoop serverLoop);

    public abstract void onJoin();

    public abstract void onUpdate(float deltaTime);

    protected void connect(String ip) {
        clientLoop = new ClientLoop();
        networkTouchController = new NetworkTouchController(clientLoop);
        clientLoop.connect(ip);
    }

    private void setClientListeners() {
        clientLoop.addListener(new PacketListener(ChatPacket.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                Logger.getInstance().debug(this.getClass(), "Received from server: " + ((ChatPacket) receivedPacket.containedPacket).text);
            }
        }));
        clientLoop.addListener(new PacketListener(PingPacket.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                long ping = (System.nanoTime() - ((PingPacket) receivedPacket.containedPacket).time) / 1000;
                Logger.getInstance().debug(this.getClass(), "Ping: " + ping);
            }
        }));
        clientLoop.addListener(new PacketListener(TouchPacket.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                Logger.getInstance().debug(Core.class, "Touched: " + receivedPacket.containedPacket.toString());
            }
        }));
        clientLoop.addListener(new PacketListener(WinPacket.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                clientLoop.winPacket = (WinPacket) receivedPacket.containedPacket;
            }
        }));
    }

    public void addUpdateListener(UpdateAble updateAble) {
        updateListeners.add(updateAble);
    }

    /**
     * This will get rid of all the assets to prevent a memory leak
     */
    @Override
    public void dispose() {
        super.dispose();
        Assets.getAssets().dispose();
    }

    /**
     * This should in the future render a background
     */
    @Override
    public void render(float deltaTime) {
        try {
            Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond());
            if (host) {
                serverLoop.update(deltaTime);
                clientLoop.update(deltaTime);
                networkTouchController.update(deltaTime);
            } else {
                clientLoop.update(deltaTime);
                networkTouchController.update(deltaTime);
            }
            for (UpdateAble updateAble : updateListeners) {
                updateAble.update(deltaTime);
            }
            onUpdate(deltaTime);
        } catch (ArrayIndexOutOfBoundsException e) {
            dispose();
            Gdx.app.exit();
        }
    }
}
