package com.deeep.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.deeep.core.entity.abstraction.Entity;
import com.deeep.core.system.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/30/13
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class Camera {
    /** The singleton instance */
    private static Camera ourInstance = new Camera();
    /** The frustum width and height. These are the blocks showed on screen */
    private final float FRUSTUM_WIDTH = Constants.VIRTUAL_WIDTH / (Constants.BLOCK_SIZE * Constants.SCALE);
    private final float FRUSTUM_HEIGHT = Constants.VIRTUAL_HEIGHT / (Constants.BLOCK_SIZE * Constants.SCALE);
    /** The previous x and y of the focus */
    private float previousFocusY = 0;
    private float previousFocusX = 0;
    /** The amount the camera focus should deviate from the focus entity */
    private float offsetX;
    private float offsetY;
    /** The current position of the camera */
    private float x;
    private float y;
    /** 2 points to make the boundary box */
    private Vector3 point1;
    private Vector3 point2;
    /** The boundary box itself */
    private BoundingBox boundingBox;
    /** If the camera is in the hud */
    private boolean inHud = false;
    /** The camera controlling the viewing */
    private OrthographicCamera gameCam;
    /** The camera for the hud stuff */
    private OrthographicCamera hudCam;
    /** The reference to the frustum */
    private Frustum frustum;
    private Entity focus;

    /** Default constructor */
    private Camera() {
        boundingBox = new BoundingBox();
        point1 = new Vector3(0, 0, 0);
        point2 = new Vector3(1, 1, 0);
        hudCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameCam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        gameCam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
    }

    public static Camera getInstance() {
        if (ourInstance == null)
            ourInstance = new Camera();
        return ourInstance;
    }

    public OrthographicCamera getGameCam() {
        return gameCam;
    }

    public OrthographicCamera getHudCam() {
        return hudCam;
    }

    /**
     * Sets the focus on an entity
     *
     * @param focus the entity to focus
     */
    public void setFocus(Entity focus, float offsetX, float offsetY) {
        this.focus = focus;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    /**
     * sets the frustum instance
     *
     * @param frustum instance
     */
    public void setFrustum(Frustum frustum) {
        this.frustum = frustum;
    }

    /**
     * update method, will follow the focus entity. TODO put some work
     *
     * @param deltaT delta time
     */
    public void update(float deltaT) {
        gameCam.update();
        hudCam.update();
        gameCam.position.set(x, y, 0);
        hudCam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
        if (focus != null) {
            //TODO set x and y
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    /**
     * This function will check if the designated object is in vision or not. This will save a lot of
     * processing power if we don't draw objects which are outside the vision
     *
     * @param x      the x of the object we want to check
     * @param y      the y of the object we want to check
     * @param width  the width of the object
     * @param height the height of the object
     * @return true if no frustum exists, or if the object is within vision. false otherwise
     */
    public boolean inVision(float x, float y, float width, float height) {
        if (inHud)
            return true;
        point1.set(x, y, 0);
        point2.set(x + width, y + height, 0);
        boundingBox.set(point1, point2);
        if (frustum == null)        //no frustum to begin with
            return true;
        if (frustum.boundsInFrustum(boundingBox))
            return true;
        return false;
    }

    /** switches to the HUD TODO do something here */
    public void switchToHud() {
        this.inHud = true;
    }

    public void switchToGame() {
        this.inHud = false;
    }

    public boolean isInHud() {
        return inHud;
    }

    /**
     * Returns the x vector of the touch coordinated. It's relative to the worlds unit system
     *
     * @return x value of touch in floats
     */
    public float getTouchUnitX() {
        return x + (float) Gdx.input.getX() / (Constants.BLOCK_SIZE * Constants.SCALE) - (Constants.VIRTUAL_WIDTH / (Constants.BLOCK_SIZE * Constants.SCALE) / 2);
    }

    public int getTouchPixelX() {
        return Gdx.input.getX();
    }

    /**
     * Returns the y vector of the touch coordinated. It's relative to the worlds unit system
     *
     * @return y value of touch in floats
     */
    public float getTouchUnitY() {
        return y + (Gdx.graphics.getHeight() - (float) Gdx.input.getY()) / (Constants.BLOCK_SIZE * Constants.SCALE) - (Constants.VIRTUAL_HEIGHT / (Constants.BLOCK_SIZE * Constants.SCALE) / 2);
    }

    public int getTouchPixelY() {
        return Gdx.input.getY();
    }
}
