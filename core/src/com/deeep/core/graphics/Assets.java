package com.deeep.core.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.deeep.core.util.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/28/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Assets {
    /**
     * instance for singleton
     */
    private static Assets assets;
    /**
     * Just a check to be sure that the assets aren't loaded multiple times
     */
    private static boolean loaded = false;
    /**
     * The atlases containing all the images
     */
    private TextureAtlas textureAtlas;

    /**
     * Skin for menus and buttons
     */
    private Skin skin;

    /**
     * Logo for SplashScreen
     */
    private Texture logo;

    /**
     * Logger instance
     */
    private Logger logger = Logger.getInstance();

    /**
     * Simple singleton
     *
     * @return assets instance
     */
    public static Assets getAssets() {
        if (assets == null) {
            assets = new Assets();
        }
        return assets;
    }

    /**
     * function to load everything. Nothing special. TODO add more resources here( sound music etc)
     */
    public void load() {
        if (!loaded) {
            textureAtlas = new TextureAtlas(Gdx.files.internal("images/TextureAtlas.txt"));
            skin = new Skin(Gdx.files.internal("data/uiskin.json"));
            logo = new Texture(Gdx.files.internal("data/logo.png"));
            logger.system(Assets.class, "All assets have been loaded");
            loaded = true;
        }
    }

    /**
     * Returns an texture region based on the name given. Get images using this function!
     *
     * @param name see TextureAtlas.txt
     * @return the texture region connected to the name
     */
    public TextureRegion getRegion(String name) {
        TextureRegion textureRegion = textureAtlas.findRegion(name);
        if (textureRegion == null) {
            logger.error(Assets.class, "Unkown texture requested: " + name);
        }
        return textureAtlas.findRegion(name);
    }

    public Skin getSkin() {
        return skin;
    }

    public Texture getLogo() {
        return logo;
    }

    public void dispose() {
        textureAtlas.dispose();
        skin.dispose();
    }
}