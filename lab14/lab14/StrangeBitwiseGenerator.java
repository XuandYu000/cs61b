package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator{
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        this.state = 0;
    }

    @Override
    public double next() {
        state += 1;
        int weridState = state & (state >>> 3) & (state >> 8) % period;
        return normalize(weridState);
    }

    private double normalize(int x) {
        return (double) x * 2 / period - 1;
    }
}
