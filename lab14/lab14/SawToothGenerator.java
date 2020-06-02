package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    @Override
    public double next() {
        state++;
        return normalize(state % period);
    }

    private int normalize(int s) {
        return (s * 2) / (period - 1) - 1;
    }
}
