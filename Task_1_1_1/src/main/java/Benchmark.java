import java.util.Arrays;
import java.util.Random;

/**
 * Benchmark times HeapSort with different n.
 * Prints CSV: n, time, time/(n*log2 n).
 */

public class Benchmark {

    /** If algorithm works O(n log n), last column is a ~const.
     */

    public static void main(String[] args) {
        int[] sizes = {500_000, 1_000_000, 10_000_000};
        int warmup = 3;
        int runs = 5;
        Random rnd = new Random();

        System.out.println("n,time_ns,time/(n*log2(n))");

        for (int n : sizes) {
            int[] base = new int[n];
            for (int i = 0; i < n; i++) {
                base[i] = rnd.nextInt();
            }

            // skipping JIT
            for (int w = 0; w < warmup; w++) {
                HeapSort.sort(base.clone());
            }

            long[] times = new long[runs];
            for (int r = 0; r < runs; r++) {
                int[] copy = base.clone();
                long start = System.nanoTime();
                HeapSort.sort(copy);
                times[r] = System.nanoTime() - start;
            }
            Arrays.sort(times);
            long median = times[runs / 2];

            double nlogn = n * (Math.log(n) / Math.log(2));
            System.out.printf("%d,%d,%.6f%n", n, median, median / nlogn);
        }
    }
}