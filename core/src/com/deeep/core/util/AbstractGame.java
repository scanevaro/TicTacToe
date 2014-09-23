package com.deeep.core.util;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/29/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractGame implements ApplicationListener {
    /** Logger instances */
    protected Logger logger = Logger.getInstance();
    /** The screen to work with */
    private Screen screen;

    /**
     * Called when the {@link com.badlogic.gdx.Application} is resized. This can happen at any point during a non-paused state but will never happen
     * before a call to {@link #create()}.
     *
     * @param width  the new width in pixels
     * @param height the new height in pixels
     */
    @Override
    public void resize(int width, int height) {
        logger.system(this.getClass(), "Resize to: " + width + " x " + height);
        if (screen != null) screen.resize(width, height);
    }

    /** Called when the {@link com.badlogic.gdx.Application} should render itself. */
    @Override
    public void render() {
        render(Gdx.graphics.getDeltaTime());
        if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
    }

    /**
     * Called when the {@link com.badlogic.gdx.Application} is paused. An Application is paused before it is destroyed, when a user pressed the Home
     * button on Android or an incoming call happend. On the desktop this will only be called immediately before {@link #dispose()}
     * is called.
     */
    @Override
    public void pause() {
        logger.system(this.getClass(), "Paused");
        if (screen != null) screen.pause();
    }

    /**
     * Called when the {@link com.badlogic.gdx.Application} is resumed from a paused state. On Android this happens when the activity gets focus
     * again. On the desktop this method will never be called.
     */
    @Override
    public void resume() {
        logger.system(this.getClass(), "Resumed");
        if (screen != null) screen.resume();
    }

    /** Called when the {@link com.badlogic.gdx.Application} is destroyed. Preceded by a call to {@link #pause()}. */
    @Override
    public void dispose() {
        logger.system(this.getClass(), "Disposing");
        if (screen != null) screen.dispose();
    }

    /** @return the currently active {@link com.badlogic.gdx.Screen}. */
    public Screen getScreen() {
        return screen;
    }

    /**
     * Sets the current screen. {@link com.badlogic.gdx.Screen#hide()} is called on any old screen, and {@link com.badlogic.gdx.Screen#show()} is called on the new
     * screen, if any.
     *
     * @param screen may be {@code null}
     */
    public void setScreen(Screen screen) {
        if (this.screen != null) this.screen.hide();
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    /**
     * Draw background in here
     *
     * @param deltaTime time that has passed
     */
    public abstract void render(float deltaTime);

}
