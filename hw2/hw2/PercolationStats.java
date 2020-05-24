package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int[] result; /* store the number if open sites of each experiment */
    private int totalSites;
    int numTest;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        result = new int[T];
        totalSites = N * N;
        numTest = T;

        for (int i = 0; i < T; i++) {
            Percolation percolation = pf.make(N);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                percolation.open(row, col);
            }
            int numOpenSites = percolation.numberofOpenSites();
            result[i] = numOpenSites;
        }
    }

    public double mean() {
        return StdStats.mean(result) / totalSites;
    }

    public double stddev() {
        return StdStats.stddev(result) / totalSites;
    }

    public double confidenceLow() {
        double mean = mean();
        double sd = stddev();
        double low = mean - 1.96 * sd / Math.sqrt(numTest);
    }

    public double confidenceHigh() {
        double mean = mean();
        double sd = stddev();
        double low = mean + 1.96 * sd / Math.sqrt(numTest);
    }
}
