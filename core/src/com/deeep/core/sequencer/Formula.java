package com.deeep.core.sequencer;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/2/13
 * Time: 8:08 AM
 */
public abstract class Formula {
    protected float period, endValue, startValue;
    protected float value;

    /**
     * @param period   the period to reach the end value in
     * @param endValue the final to reach value
     */
    protected Formula(float period, float endValue) {
        this.period = period;
        this.endValue = endValue;
        this.startValue = value = 0;
    }

    /**
     * This function calculates the new value given the state time, and then returns the new value based on the
     * formula used.
     *
     * @param stateTime start time
     * @return f(x)
     */
    public float getValue(float stateTime) {
        return value = calculate(stateTime);
    }

    public void setStartValue(float startValue) {
        this.startValue = startValue;
    }

    /**
     * This function should contain the whole formula in a f(x) = x notation
     *
     * @param stateTime the time the function is at
     * @return f(x)
     */
    protected abstract float calculate(float stateTime);

    /**
     * Use this function to initialize all the slopes and constants
     *
     * @param startValue the value to start off
     */
    public abstract void initialize(float startValue);
}
