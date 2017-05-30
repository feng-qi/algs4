import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n;              // number of elements on deque
    private Node first;         // beginning of deque
    private Node last;          // end of deque

    public Deque() {
        first = null;
        last  = null;
        n = 0;
    }
    public boolean isEmpty() {
        return n == 0;
    }
    public int size() {
        return n;
    }
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (isEmpty())
            last = first;
        else
            oldFirst.prev = first;
        n++;
    }
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        last.next = null;
        if (n == 0)
            first = last;
        else
            oldLast.next = last;
        n++;
    }
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty())
            last = null;
        else
            first.prev = null;
        return item;
    }
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item;
        last = last.prev;
        n--;
        if (isEmpty())
            first = null;
        else
            last.next = null;
        return item;
    }
    public Iterator<Item> iterator() {
        return new DoubleListIterator();
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    private class DoubleListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
