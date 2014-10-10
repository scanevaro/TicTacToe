package com.deeep.core.util;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.deeep.core.graphics.Assets;
import com.deeep.core.screens.SplashScreen;
import com.deeep.core.system.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/29/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Game implements ApplicationListener {
    private OrthographicCamera orthographicCamera;
    private Rectangle viewport;
    private SpriteBatch spriteBatch;
    private Logger logger = Logger.getInstance();
    private Screen screen;

    @Override
    public void create() {
        Assets.getAssets().load();

        orthographicCamera = new OrthographicCamera(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT);
        orthographicCamera.position.set(Constants.VIRTUAL_WIDTH / 2, Constants.VIRTUAL_HEIGHT / 2, 0);

        spriteBatch = new SpriteBatch();

        viewport = new Rectangle();

        setScreen(new SplashScreen());

    }

    /**
     * Called when the {@link com.badlogic.gdx.Application} should render itself.
     */
    @Override
    public void render() {
        orthographicCamera.update();

        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.

        spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
    }

    /**
     * Called when the {@link com.badlogic.gdx.Application} is resized. This can happen at any point during a non-paused state but will never happen
     * before a call to {@link #create()}.
     *
     * @param width  the new width in pixels
     * @param height the new height in pixels
     */
    @Override
    public void resize(int width, int height) {
        logger.system(((Object) this).getClass(), "Resize to: " + width + " x " + height);
        if (screen != null) screen.resize(width, height);

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

        viewport.set(crop.x, crop.y, w, h);
    }

    /**
     * Called when the {@link com.badlogic.gdx.Application} is paused. An Application is paused before it is destroyed, when a user pressed the Home
     * button on Android or an incoming call happend. On the desktop this will only be called immediately before {@link #dispose()}
     * is called.
     */
    @Override
    public void pause() {
        logger.system(((Object) this).getClass(), "Paused");
        if (screen != null) screen.pause();
    }

    /**
     * Called when the {@link com.badlogic.gdx.Application} is resumed from a paused state. On Android this happens when the activity gets focus
     * again. On the desktop this method will never be called.
     */
    @Override
    public void resume() {
        logger.system(((Object) this).getClass(), "Resumed");
        if (screen != null) screen.resume();
    }

    /**
     * Called when the {@link com.badlogic.gdx.Application} is destroyed. Preceded by a call to {@link #pause()}.
     */
    @Override
    public void dispose() {
        logger.system(((Object) this).getClass(), "Disposing");
        Assets.getAssets().dispose();
        if (screen != null) screen.dispose();
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

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}