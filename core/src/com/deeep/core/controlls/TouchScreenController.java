package com.deeep.core.controlls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.deeep.core.util.Logger;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/28/13
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class TouchScreenController {
    /** Arraylist containing all the regions registered */
    private ArrayList<Region> regions = new ArrayList<Region>();
    /** Vector containing the mouse position */
    private Vector2 mouseVector;

    /** Initialize the controller */
    public TouchScreenController() {
        mouseVector = new Vector2(-1, -1);
        Logger.getInstance().debug(this.getClass(), "Touchscreen controller initialized");
    }

    /**
     * Registers a new area. You can choose to trigger it on press, hold or release. see InputReactListener for more
     *
     * @param action the event that has to be fired upon trigger
     * @param x      position x
     * @param y      position y
     * @param width  the width of the hitbox
     * @param height the height of the hitbox
     * @param event  the trigger level
     * @see InputReactListener.Event
     */
    public void registerArea(InputReactListener action, int x, int y, int width, int height, InputReactListener.Event event) {
        regions.add(new Region(action, x, y, width, height, event));
    }

    /** Updates all the registered regions */
    public void update() {
        mouseVector.set(Gdx.input.getX(), Gdx.input.getY());
        for (Region region : regions) {
            region.update(mouseVector);
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        for (Region region : regions) {
            region.draw(spriteBatch);
        }
    }

    public class Region {
        private InputReactListener inputReactListener;
        private InputReactListener.Event event;
        private Rectangle hitBox;
        private boolean previousTouched = false;

        public Region(InputReactListener action, int x, int y, int width, int height, InputReactListener.Event event) {
            this.event = event;
            this.inputReactListener = action;
            this.hitBox = new Rectangle(x, y, width, height);
        }

        public void update(Vector2 mouseVector) {
            if (Gdx.input.isTouched()) {
                if (event == InputReactListener.Event.PRESSED) {
                    if (!previousTouched) {
                        if (hitBox.contains(mouseVector)) {
                            inputReactListener.inputReact();
                        }
                    }
                }
                if (event == InputReactListener.Event.HOLD) {
                    if (hitBox.contains(mouseVector)) {
                        inputReactListener.inputReact();
                    }
                }
                previousTouched = true;
            } else {
                if (event == InputReactListener.Event.RELEASED && previousTouched) {
                    inputReactListener.inputReact();
                }
                previousTouched = false;
            }
        }

        public void draw(SpriteBatch spriteBatch) {
            //TODO debug rendering
        }
    }
}
