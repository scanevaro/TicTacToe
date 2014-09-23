package com.deeep.core.util;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.core.system.Core;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/28/13
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractScreen implements Screen {
    /** Core reference to switch the screen */
    private Core core;
    /** Spritebatch to draw everything on */
    private SpriteBatch spriteBatch;

    /**
     * Constructor
     *
     * @param core reference to the core
     */
    public AbstractScreen(Core core) {
        this.core = core;
        this.spriteBatch = new SpriteBatch();
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        update(delta);
        spriteBatch.begin();
        draw(spriteBatch);
        spriteBatch.end();
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Core getCore() {
        return core;
    }

    public abstract void draw(SpriteBatch spriteBatch);

    public abstract void update(float deltaT);

    /** @see com.badlogic.gdx.ApplicationListener#resize(int, int) */
    @Override
    public void resize(int width, int height) {

    }

    /** Called when this screen becomes the current screen for a {@link com.badlogic.gdx.Game}. */
    @Override
    public void show() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /** Called when this screen is no longer the current screen for a {@link com.badlogic.gdx.Game}. */
    @Override
    public void hide() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /** @see com.badlogic.gdx.ApplicationListener#pause() */
    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /** @see com.badlogic.gdx.ApplicationListener#resume() */
    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /** Called when this screen should release all resources. */
    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
