package lab14;

public class StrangeBitwiseGenerator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    @Override
    public double next() {
        state++;
        int weirdState = state & (state >>> 3) % period;
        return normalize(weirdState);
    }

    private double normalize(int s) {
        return (double) (s * 2) / (period - 1) - 1;
    }
}
