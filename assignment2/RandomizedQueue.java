import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int n;
    private int first;
    private int last;
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last  = 0;
    }
    public boolean isEmpty() {
        return n == 0;
    }
    public int size() {
        return n;
    }
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            temp[i] = q[i];     // Randomized, no need to keep the order.
        q = temp;
        first = 0;
        last  = n;
    }
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (n == q.length) resize(2 * q.length);
        q[last++] = item;
        if (last == q.length) last = 0;
        n++;
    }
    private int getIndex() {
        return (first + StdRandom.uniform(n)) % q.length;
    }
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = getIndex();
        Item ret = q[index];
        if (index != first) {
            q[index] = q[first];
        }
        q[first] = null;
        if (++first == q.length)
            first = 0;
        return ret;
    }
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return q[getIndex()];
    }
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    private class ArrayIterator implements Iterator<Item> {
        private Item[] itr_array = (Item[])new Object[n];
        private int index = 0;
        private ArrayIterator() {
            int j = first;
            for (int i = 0; i < n; i++) {
                itr_array[i] = q[j++];
                j = (j == n) ? 0 : j;
            }
            StdRandom.shuffle(itr_array);
        }
        public boolean hasNext() {
            return index != n;
        }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            return itr_array[index++];
        }
    }
}
