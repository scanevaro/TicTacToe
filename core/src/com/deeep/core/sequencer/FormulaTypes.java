package com.deeep.core.sequencer;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/2/13
 * Time: 8:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class FormulaTypes {
    /** TODO  Y = ab^X */
    public static class Exponential extends Formula {
        private float steepness;
        private float valueChange;

        public Exponential(float period, float endValue, float steepness) {
            super(period, endValue);
            this.steepness = steepness;
        }

        @Override
        protected float calculate(float stateTime) {
            float tempTime = stateTime / this.period;
            return (float) ((Math.pow(steepness, tempTime) / steepness) * valueChange + startValue);
        }

        @Override
        public void initialize(float startValue) {
            valueChange = endValue - startValue;
        }
    }

    public static class Parabola extends Formula {

        /**
         * @param period   the period to reach the end value in
         * @param endValue the final to reach value
         */
        protected Parabola(float period, float endValue, float m) {
            super(period, endValue);
        }

        /**
         * This function should contain the whole formula in a f(x) = x notation
         *
         * @param stateTime the time the function is at
         * @return f(x)
         */
        @Override
        protected float calculate(float stateTime) {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

        /**
         * Use this function to initialize all the slopes and constants
         *
         * @param startValue the value to start off
         */
        @Override
        public void initialize(float startValue) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    /** Y = aX + b */
    public static class Linear extends Formula {
        private float a = 0;
        private float b = 0;

        /**
         * multiply the value with a pre calculated slope
         *
         * @param period   the period to reach the end value in
         * @param endValue the final to reach value
         */
        public Linear(float period, float endValue) {
            super(period, endValue);
        }

        @Override
        protected float calculate(float stateTime) {
            return a * stateTime + b;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void initialize(float startValue) {
            b = startValue;
            a = (endValue - startValue) / period;
        }
    }

    /** Y = b */
    public static class Sleep extends Formula {
        /**
         * Sleep method. The value wont change
         *
         * @param period the period to sleep for
         */
        public Sleep(float period) {
            super(period, -1);
        }

        @Override
        protected float calculate(float stateTime) {
            return value;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void initialize(float startValue) {
            this.value = startValue;
        }
    }
}
