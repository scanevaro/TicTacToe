package com.deeep.core.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.deeep.core.controlls.NetworkTouchController;
import com.deeep.core.entity.World;
import com.deeep.core.graphics.Assets;
import com.deeep.core.gui.Canvas;
import com.deeep.core.network.client.ClientLoop;
import com.deeep.core.network.mutual.PacketListener;
import com.deeep.core.network.mutual.packets.*;
import com.deeep.core.network.server.ServerLoop;
import com.deeep.core.util.AbstractGame;
import com.deeep.core.util.Logger;
import com.deeep.core.util.UpdateAble;

import java.util.ArrayList;

/**
 * Created by Elmar on 9/26/2014.
 */
public abstract class ClientCore extends AbstractGame implements Screen {
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
     * The camera controlling the viewing
     */
    private OrthographicCamera cam;
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
     * The viewport for the game. Should handle all the resizing
     */
    private Rectangle viewport;
    private Canvas canvas;

    public ClientCore(String ip) {
        viewport = new Rectangle(0,0,0,0);
        canvas = new Canvas((int) Constants.VIRTUAL_WIDTH, (int) Constants.VIRTUAL_HEIGHT);

        spriteBatch = new SpriteBatch(5);           //TODO tune this
        world = new World();

        cam = new OrthographicCamera(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT);
        cam.position.set(Constants.VIRTUAL_WIDTH / 2, Constants.VIRTUAL_HEIGHT / 2, 0);

        //TODO return something when it goes wrong
        clientLoop = new ClientLoop();
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

    protected void connect(String ip) {
        clientLoop.connect(ip);
    }


    /**
     * This should in the future render a background
     */
    @Override
    public void render(float deltaTime) {
        System.out.println("Client: " );
        clientLoop.update(deltaTime);
        networkTouchController.update(deltaTime);
        clientUpdate(deltaTime);

        cam.update();
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
        spriteBatch.setProjectionMatrix(cam.combined);
        world.draw(spriteBatch);
        canvas.draw(spriteBatch);
        clientDraw(spriteBatch);

        for (UpdateAble updateAble : updateListeners) {
            updateAble.update(deltaTime);
        }
    }
    public abstract void clientUpdate(float deltaTime);

    public abstract void clientDraw(SpriteBatch spriteBatch);


    /**
     * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / (float) height;
        float scale;

        Vector2 crop = new Vector2(0f, 0f);
        if (aspectRatio > Constants.VIRTUAL_ASPECT) {
            scale = (float) height / Constants.VIRTUAL_HEIGHT;
            crop.x = (width - Constants.VIRTUAL_WIDTH * scale) / 2f;
        } else if (aspectRatio < Constants.VIRTUAL_ASPECT) {
            scale = (float) width / Constants.VIRTUAL_WIDTH;
            crop.y = (height - Constants.VIRTUAL_HEIGHT * scale) / 2f;
        } else {
            scale = (float) width / Constants.VIRTUAL_WIDTH;
        }

        float w = Constants.VIRTUAL_WIDTH * scale;
        float h = Constants.VIRTUAL_HEIGHT * scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);
        canvas.resize((int) viewport.width, (int) viewport.height);
    }

    /**
     * Called when this screen becomes the current screen for a {@link com.badlogic.gdx.Game}.
     */
    @Override
    public void show() {
        logger.debug(((Object) this).getClass(), "Showing");
    }

    /**
     * Called when this screen is no longer the current screen for a {@link com.badlogic.gdx.Game}.
     */
    @Override
    public void hide() {
    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#pause()
     */
    @Override
    public void pause() {
    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#resume()
     */
    @Override
    public void resume() {
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        super.dispose();
        Assets.getAssets().dispose();
    }
}
