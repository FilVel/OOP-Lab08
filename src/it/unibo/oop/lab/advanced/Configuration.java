package it.unibo.oop.lab.advanced;

public final class Configuration {

    private static final int DEF_MAX = 100;
    private final int max; 
    private static final int DEF_MIN = 0;
    private final int min;
    private static final int DEF_ATTEMPTS = 10;
    private final int attempts;

    private Configuration(final int max, final int min, final int attempts) {
        this.max = max;
        this.min = min;
        this.attempts = attempts;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public int getAttempts() {
        return attempts;
    }

    public boolean isConsistent() {
        return max > min && attempts > 0;
    }

    public static class NewConfiguration {

        private boolean usedAlready;
        private int max = DEF_MAX;
        private int min = DEF_MIN;
        private int attempts = DEF_ATTEMPTS;

        /**
         * 
         * @param newMin
         */
        public void setMin(final int newMin) {
            this.min = newMin;
        }

        /**
         * 
         * @param newMax
         */
        public void setMax(final int newMax) {
            this.max = newMax;
        }

        /**
         * 
         * @param newAttempts
         */
        public void setAttempts(final int newAttempts) {
            this.attempts = newAttempts;
        }

        public final Configuration newConfig() {
            if (!usedAlready) {
                usedAlready = true;
                return new Configuration(max, min, attempts);
            } else {
                throw new IllegalStateException("You can change the configuration only once");
            }
        }
    }

}
