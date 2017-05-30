import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            rq.enqueue(item);
        }
        int n = Integer.parseInt(args[0]);
        Iterator<String> itr = rq.iterator();
        for (int i = 0; itr.hasNext() && i < n; i++) {
            StdOut.println(itr.next());
        }
    }
}
