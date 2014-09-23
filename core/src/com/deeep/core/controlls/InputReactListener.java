package com.deeep.core.controlls;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/18/13
 * Time: 1:40 AM
 * This interface contains the trigger levels and the inputReact() function.
 * This interface is used in combination with the Controller class
 */
public interface InputReactListener {
    /** The function that will be executed upon event */
    public void inputReact();

    /** The trigger levels */
    static enum Event {
        PRESSED(0), RELEASED(1), HOLD(2);
        int number;

        Event(int number) {
            this.number = number;
        }
    }
}
