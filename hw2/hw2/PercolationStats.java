package hw2;

import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import org.junit.Assert;

import java.util.Map;
import java.util.Random;

public class PercolationStats {
    private int N;
    private int T;
    private PercolationFactory pf;
    private double[] thresholds;


    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be greater than 0.");
        }
        if(T <= 0) {
            throw new IllegalArgumentException("T should be greater than 0.");
        }
        this.N = N;
        this.T = T;
        this.pf = pf;
        this.thresholds = new double[T];

        simulate(T);
    }

    private void simulate(int T) {
        for(int i = 0; i < T; i++) {
            Percolation expr = pf.make(N);
            while(!expr.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                expr.open(row, col);
            }
            double threshold = (double) expr.numberOfOpenSites() / (N * N);
            thresholds[i] = threshold;
        }
    }

    /**
     * Return sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /*
    * Return sample standard deviation of percolation thresshold
    * */
    public double stddev() {
       return StdStats.stddev(thresholds);
    }


    /*
    * Return low endpoint of 95% confidence interval
    * */
    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(T));
    }

    /*
    * Return high endpoint of 95% confidence interval
    * */
    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(T));
    }
}
