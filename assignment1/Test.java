import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.UF;

public class Test {
    public static void main(String[] args) {
        int i = 0;
        outer:
        while (true) {
            while (true) {
                if (i == 10) {
                    System.out.println("i = " + i);
                    break outer;
                }
                else
                    i++;
            }
        }

        // System.out.println("args[1]: " + args[1]);

        // int N = StdIn.readInt();
        // UF uf = new UF(N);
        // while (!StdIn.isEmpty()) {
        //     int p = StdIn.readInt() - 1;
        //     int q = StdIn.readInt() - 1;
        //     if (!uf.connected(p, q)) {
        //         uf.union(p, q);
        //         StdOut.println(p + " " + q);
        //     }
        // }
    }
}
