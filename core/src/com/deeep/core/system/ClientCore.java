package com.deeep.core.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.core.controlls.NetworkTouchController;
import com.deeep.core.entity.World;
import com.deeep.core.gui.Canvas;
import com.deeep.core.network.client.ClientLoop;
import com.deeep.core.network.mutual.PacketListener;
import com.deeep.core.network.mutual.packets.*;
import com.deeep.core.util.Game;
import com.deeep.core.util.Logger;
import com.deeep.core.util.UpdateAble;

import java.util.ArrayList;

/**
 * Created by Elmar on 9/26/2014.
 */
public abstract class ClientCore implements Screen {
    /**
     * Main instance of the game
     */
    private Game game;
    /**
     * Updates. TODO Hook the server in here?
     */
    private ArrayList<UpdateAble> updateListeners = new ArrayList<UpdateAble>();
    /**
     * Object handling all the netcode
     */
    protected ClientLoop clientLoop;
    /**
     * Touch controller. TODO should this really be here?
     */
    private NetworkTouchController networkTouchController;
    /**
     * The spritebatch to draw everything to
     */
    private SpriteBatch spriteBatch;
    /**
     * Logger instance to log all events to. Please don't use system.out.print
     */
    private Logger logger = Logger.getInstance();
    /**
     * The world which should contain all the game play and entities
     */
    private World world;
    /**
     * Canvas handles the UI
     */
    private Canvas canvas;

    public ClientCore(String ip, String gameId, String name) {
        this.game = (Game) Gdx.app.getApplicationListener();
        this.spriteBatch = game.getSpriteBatch();

        //TODO return something when it goes wrong
        clientLoop = new ClientLoop(gameId, name);
        networkTouchController = new NetworkTouchController(clientLoop);
        connect(ip);

        clientLoop.addListener(new PacketListener(EntityCreationPacket.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                world.getEntityManager().addEntity((EntityCreationPacket) receivedPacket.containedPacket);
            }
        }));
        clientLoop.addListener(new PacketListener(PositionPacket.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                world.getEntityManager().moveEntity((PositionPacket) receivedPacket.containedPacket);
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
                Logger.getInstance().debug(this.getClass(), "Touched: " + receivedPacket.containedPacket.toString());
            }
        }));

    }

    public abstract void onGDXLoad();

    protected void connect(String ip) {
        clientLoop.connect(ip);
    }

    @Override
    public void render(float deltaTime) {
        clientLoop.update(deltaTime);
        networkTouchController.update(deltaTime);
        clientUpdate(deltaTime);
        world.update(deltaTime);
        canvas.update(deltaTime);

        world.draw(spriteBatch);
        canvas.draw(spriteBatch);
        clientDraw(spriteBatch);

        for (UpdateAble updateAble : updateListeners) {
            updateAble.update(deltaTime);
        }
    }

    public abstract void clientUpdate(float deltaTime);

    public abstract void clientDraw(SpriteBatch spriteBatch);

    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
    }
}
