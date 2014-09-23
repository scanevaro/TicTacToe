package com.deeep.core.controlls;

import com.badlogic.gdx.Gdx;
import com.deeep.core.util.Logger;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/29/13
 * Time: 1:20 PM
 * This class makes prototyping easy. Just register a key and give the key a trigger level and
 * a trigger event. No need anymore for any external variables.
 * Can use with any framework.
 */
public class Controller {
    /** Arraylist containing all the keys registered */
    private ArrayList<RegisteredKey> registeredKeys = new ArrayList<RegisteredKey>();

    /** Not too much here ehh */
    public Controller() {
        Logger.getInstance().debug(this.getClass(), "Controller initialized");
    }

    /** Updates all the registered keys */
    public void update() {
        for (int i = 0, l = registeredKeys.size(); i < l; i++) {
            registeredKeys.get(i).update();
        }
    }

    /**
     * Registers a new key. You can choose to trigger it on press, hold or release. see InputReactListener for more
     *
     * @param key                the key id you want to register
     * @param inputReactListener the event that has to be fired upon trigger
     * @param trigger            the trigger level
     * @see InputReactListener.Event
     */
    public void registerKey(int key, InputReactListener inputReactListener, InputReactListener.Event trigger) {
        registeredKeys.add(new RegisteredKey(key, inputReactListener, trigger));
    }

    /** Class holding information about the registered key */
    class RegisteredKey {
        /** if the key was previously pressed */
        boolean pressed = false;
        /** The key id */
        int key;
        /** the event that has to be fired upon trigger */
        InputReactListener inputReactListener;
        /** The trigger level */
        InputReactListener.Event triggerEvent;

        /**
         * @param key                the key id you want to register
         * @param inputReactListener the event that has to be fired upon trigger
         * @param trigger            the trigger level
         * @see InputReactListener.Event
         */
        RegisteredKey(int key, InputReactListener inputReactListener, InputReactListener.Event trigger) {
            this.key = key;
            this.inputReactListener = inputReactListener;
            this.triggerEvent = trigger;
        }

        /** Updates and checks for the state */
        public void update() {
            if (Gdx.input.isKeyPressed(key)) {
                if (triggerEvent == InputReactListener.Event.HOLD) {
                    inputReactListener.inputReact();
                }
            }
            if (Gdx.input.isKeyPressed(key) && !pressed) {
                if (triggerEvent == InputReactListener.Event.PRESSED) {
                    inputReactListener.inputReact();
                }
                pressed = true;
            } else if (!Gdx.input.isKeyPressed(key) && pressed) {
                if (triggerEvent == InputReactListener.Event.RELEASED) {
                    inputReactListener.inputReact();
                }
                pressed = false;
            }
        }
    }
}
