package com.deeep.core.sequencer;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/1/13
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Sequencer {
    /** This array list holds all the sequences it will iterate over before the sequencer is finished */
    private ArrayList<Sequence> sequences = new ArrayList<Sequence>();
    /** The value of the sequence in use */
    private float value = 0;
    /** The index of the sequence it's currently using */
    private int index = -1;
    /** If the sequencer has to restart after the last sequence */
    private boolean repeat = false;

    /**
     * simple constructor. Not sure what else to do here
     *
     * @param repeat If the sequencer has to restart after the last sequence
     */
    public Sequencer(boolean repeat) {
        this.repeat = repeat;
    }

    /**
     * Adds a new sequence to the arraylist and thus to the sequencer
     *
     * @param sequence the sequence to add
     */
    public void addSequence(Sequence sequence) {
        sequences.add(sequence);
        sequence.activate(sequences.size() - 1);
        index = (index == -1) ? 0 : index;
    }

    public void setStartValue(float value) {

    }

    public void startSingleSequence(float startValue, Sequence sequence) {
        clearSequence();
        sequences.add(sequence);
        sequence.activate(startValue);
        index = 0;
    }

    public void start(float value) {
        index = 0;
        sequences.get(0).activate(value);
    }

    /** TODO figure out when to actually use this */
    public void clearSequence() {
        sequences.clear();
        index = -1;
    }

    /**
     * Updates the current state of the sequencer. Increments the index if the current sequence is done
     *
     * @param deltaT time that has passed
     */
    public void update(float deltaT) {
        if (index != -1) {
            value = sequences.get(index).getValue();
            sequences.get(index).update(deltaT);
            if (sequences.get(index).isFinished()) {
                if (repeat) {
                    index = (index < sequences.size() - 1) ? index + 1 : 0;
                    sequences.get(index).activate(value);
                } else {
                    index = (index < sequences.size() - 1) ? index + 1 : -1;
                }
            }
        }
    }

    /**
     * Returns the value of the current sequencer
     *
     * @return value
     */
    public float getValue() {
        return value;
    }

    /**
     * Returns if the sequencer is finished and thus can be recycled
     *
     * @return true if done, false otherwise
     */
    public boolean isFinished() {
        if (index == -1) {
            return true;
        }
        return false;
    }

    public int getIndex() {
        return index;
    }
}
