import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int  noOfExp;
    private final double[] percFractions;
    private double mean;
    private double stdDev;
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("row index i out of bounds");
        noOfExp = trials;
        percFractions = new double[noOfExp];
        for (int i = 1; i <= noOfExp; i++) {
            Percolation testPerc = new Percolation(n);
            while (!testPerc.percolates()) {
                int randRow = StdRandom.uniform(1, n+1);
                int randCol = StdRandom.uniform(1, n+1);
                if (!testPerc.isOpen(randRow, randCol)) {
                    testPerc.open(randRow, randCol);
                }
            }

            percFractions[i - 1] =  testPerc.numberOfOpenSites() * 1.0 / (n * n) * 1.0;
        }
        mean = StdStats.mean(percFractions);
        stdDev = StdStats.stddev(percFractions);
        confidenceLo = mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(noOfExp));
        confidenceHi = mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(noOfExp));
    }
    public double mean()                          // sample mean of percolation threshold
    {
        return mean;
    }
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return stdDev;
    }
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return confidenceLo;
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return confidenceHi;
    }

    public static void main(String[] args) {
        int testGridSize = Integer.parseInt(args[0]);
        int noOfTrials = Integer.parseInt(args[1]);

        PercolationStats testRun = new PercolationStats(testGridSize, noOfTrials);

        System.out.println("mean                    = " + testRun.mean());
        System.out.println("stdev                   = " + testRun.stddev());
        System.out.println("95% confidence interval = [" + testRun.confidenceLo() + ", " + testRun.confidenceHi()+"]");
    }
}