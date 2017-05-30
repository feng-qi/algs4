import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        possibilities = new double[trials];
        this.n = n;
        for (int i = 0; i < trials; i++) {
            possibilities[i] = monteCarloSimulate(n);
        }
    }
    public double mean() {
        return StdStats.mean(possibilities);
    }
    public double stddev() {
        return StdStats.stddev(possibilities);
    }
    public double confidenceLo() {
        return mean() - 1.96 * stddev()/Math.sqrt(n);
    }
    public double confidenceHi() {
        return mean() + 1.96 * stddev()/Math.sqrt(n);
    }

    // private Percolation p;
    private double monteCarloSimulate(int n) {
        Percolation p = new Percolation(n);
        // outer:
        while (!p.percolates()) {
            // for (int i = 1; i <= n; i++) {
            //     if (p.isFull(n, i)) break outer;
            // }
            int row, col;
            do {
                row = StdRandom.uniform(n) + 1;
                col = StdRandom.uniform(n) + 1;
                // System.out.println(row + "\t" + col);
            } while (p.isOpen(row, col));
            // System.out.println("selected: " + row + "\t" + col);
            p.open(row, col);
        }
        // System.out.println("one trial ----------------------------------------");
        return (double)p.numberOfOpenSites() / (n*n);
    }
    private double[] possibilities;
    private int n;

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]); // Trials
        PercolationStats stat = new PercolationStats(n, T);
        StdOut.printf("mean                    = %f\n", stat.mean());
        StdOut.printf("stddev                  = %f\n", stat.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", stat.confidenceLo(), stat.confidenceHi());
    }
}
