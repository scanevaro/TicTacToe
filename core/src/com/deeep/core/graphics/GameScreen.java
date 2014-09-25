package com.deeep.core.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.deeep.core.entity.World;
import com.deeep.core.entity.abstraction.Manager;
import com.deeep.core.gui.Canvas;
import com.deeep.core.network.client.ClientLoop;
import com.deeep.core.network.mutual.PacketListener;
import com.deeep.core.network.mutual.packets.EntityCreationPacket;
import com.deeep.core.network.mutual.packets.PositionPacket;
import com.deeep.core.network.mutual.packets.ReceivedPacket;
import com.deeep.core.system.Constants;
import com.deeep.core.system.Core;
import com.deeep.core.util.Logger;


/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/29/13
 * Time: 10:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameScreen implements Screen {
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
     * The core system. Use this to switch screens
     */
    private Core core;
    /**
     * The world which should contain all the game play and entities
     */
    private World world;
    /**
     * The viewport for the game. Should handle all the resizing
     */
    private Rectangle viewport;
    private Canvas canvas;
    private ShapeRenderer shapeRenderer;

    /**
     * Constructor
     *
     * @param core the game Core
     */
    public GameScreen(Core core, ClientLoop clientLoop) {
        this.core = core;
        canvas = new Canvas((int) Constants.VIRTUAL_WIDTH, (int) Constants.VIRTUAL_HEIGHT);
        shapeRenderer = new ShapeRenderer();

        spriteBatch = new SpriteBatch(5);           //TODO tune this
        world = new World();

        cam = new OrthographicCamera(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT);
        cam.position.set(Constants.VIRTUAL_WIDTH / 2, Constants.VIRTUAL_HEIGHT / 2, 0);

        shapeRenderer.setProjectionMatrix(cam.combined);

        clientLoop.addListener(new PacketListener(EntityCreationPacket.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                System.out.println("CreationPacket received");
                System.out.println(receivedPacket);
                world.getEntityManager().addEntity((EntityCreationPacket) receivedPacket.containedPacket);
            }
        }));
        clientLoop.addListener(new PacketListener(PositionPacket.class, new PacketListener.PacketAction() {
            @Override
            public void action(ReceivedPacket receivedPacket) {
                world.getEntityManager().moveEntity((PositionPacket) receivedPacket.containedPacket);
            }
        }));
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        cam.update();
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(0, 0, Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT);
        shapeRenderer.end();
        update(delta);
        draw();
    }

    public Manager getManager() {
        return world.getEntityManager();
    }

    /**
     * All the updating should go in here
     *
     * @param deltaT The time in seconds since the last render
     */
    public void update(float deltaT) {
        canvas.update(deltaT);
        world.update(deltaT);
    }

    /**
     * Drawing happens here
     */
    public void draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for (int i = 0; i < 4; i++) {
            shapeRenderer.line(i * (Constants.VIRTUAL_HEIGHT / 3), 0, i * (Constants.VIRTUAL_HEIGHT / 3), Constants.VIRTUAL_HEIGHT);
            shapeRenderer.line(0, i * (Constants.VIRTUAL_HEIGHT / 3), Constants.VIRTUAL_HEIGHT, i * (Constants.VIRTUAL_HEIGHT / 3));
        }
        shapeRenderer.end();
        world.draw(spriteBatch);
        canvas.draw(spriteBatch);
    }

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
        //canvas.resize((int) viewport.width, (int) viewport.height);
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
        logger.debug(((Object) this).getClass(), "Disposing");
    }
}
