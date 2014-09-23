package com.deeep.core.sequencer;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/1/13
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Sequence {
    /** The current value of the sequence, this will be adjusted */
    private float value = 0;
    /** The total amount of lifetime for this sequence, it will be finished after the life time */
    private float lifeTime = 0;
    /** The current stateTime */
    private float stateTime = 0;
    /** To reset the lifetime with */
    private float initialLifeTime = 0;
    /** The formula to calculate the new value with */
    private Formula formula;

    /**
     * Constructor for each sequence for now. TODO add more algorithms Exponential and Sinus
     * if ending value is -1, it wont change over the course
     *
     * @param formula the formula to use
     */
    public Sequence(Formula formula) {
        this.formula = formula;
        this.initialLifeTime = formula.period;
    }

    /**
     * This function should be called once you want to start using the sequence. It will reset all the
     * values that have to be reset, and it will calculate the delta values.
     *
     * @param startValue the value it's starting with
     */
    public void activate(float startValue) {
        this.value = startValue;
        stateTime = 0;
        formula.initialize(startValue);
        lifeTime = initialLifeTime;
    }

    /** @return the current value */
    public float getValue() {
        return value;
    }

    /**
     * Updates the life time, and calculates the new value
     *
     * @param deltaT time that has passed
     */
    public void update(float deltaT) {
        stateTime += deltaT;
        lifeTime -= deltaT;
        if (lifeTime > 0) {
            value = formula.getValue(stateTime);
        }
    }

    /**
     * if the sequence is finished
     *
     * @return true if the lifetime is smaller or equal to zero, false otherwise
     */
    public boolean isFinished() {
        return lifeTime <= 0;
    }
}
