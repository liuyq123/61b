package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;
    // the sum of periods we have passed
    private int passedPeriod;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
        passedPeriod = 0;
    }

    @Override
    public double next() {
        state++;
        if ((state - passedPeriod) % period == 0) {
            passedPeriod += period;
            period *= factor;
        }
        return normalize(state - passedPeriod);
    }

    private double normalize(int s) {
        return (double) (s * 2) / (period - 1) - 1;
    }
}
