import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("negative parameter");
        int size = n * n;
        side = n;
        wquf = new WeightedQuickUnionUF(size + 2);
        bOpen = new boolean[size + 2];
        for (int i = 0; i < size+2; i++)
            bOpen[i] = false;
        top = size;
        bot = size + 1;
        bOpen[top] = true;
        bOpen[bot] = true;
    }
    public void open(int row, int col) {
        if (row <= 0 || row > side)
            throw new IndexOutOfBoundsException("row index out of bounds");
        if (col <= 0 || col > side)
            throw new IndexOutOfBoundsException("col index out of bounds");
        int index = getIndex(row, col);
        bOpen[index] = true;
        openSiteCount++;
        if (index <= getIndex(1, side)) {
            wquf.union(top, index);
            // return;
        }
        if (index > getIndex(side-1, side) && index <= getIndex(side, side)) {
            wquf.union(bot, index);
            // return;
        }
        if (row-1 > 0 && isOpen(row-1, col)) wquf.union(index, getIndex(row-1, col));
        if (row+1 <= side && isOpen(row+1, col)) wquf.union(index, getIndex(row+1, col));
        if (col-1 > 0 && isOpen(row, col-1))
            wquf.union(index, getIndex(row, col-1));
        if (col+1 <= side && isOpen(row, col+1))
            wquf.union(index, getIndex(row, col+1));
    }
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > side)
            throw new IndexOutOfBoundsException("row index out of bounds");
        if (col <= 0 || col > side)
            throw new IndexOutOfBoundsException("col index out of bounds");
        return bOpen[getIndex(row, col)];
    }
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > side)
            throw new IndexOutOfBoundsException("row index out of bounds");
        if (col <= 0 || col > side)
            throw new IndexOutOfBoundsException("col index out of bounds");
        if (!isOpen(row, col))
            return false;
        return wquf.connected(top, getIndex(row, col));
    }
    public int numberOfOpenSites() {
        return openSiteCount;
    }
    public boolean percolates() {
        return wquf.connected(top, bot);
    }

    private int getIndex(int row, int col) {
        if (row < 0 || row > side) throw new IndexOutOfBoundsException();
        if (col < 0 || col > side) throw new IndexOutOfBoundsException();
        return side * (row - 1) + col - 1;
    }

    private WeightedQuickUnionUF wquf;
    private boolean[] bOpen;
    private int side;
    private int openSiteCount = 0;
    private int top;
    private int bot;

    public static void main(String[] args) {
        int n = 3;
        Percolation p = new Percolation(n);
        p.open(1, 1);
        p.open(3, 1);
        p.open(3, 3);
        p.open(2, 3);
        p.open(2, 1);
        p.open(1, 2);
        p.open(1, 3);
        p.open(3, 2);
        p.open(2, 2);
        System.out.println("p.isOpen(3, 1)" + p.isOpen(3, 1));
        System.out.println("p.isFull(3, 1)" + p.isFull(3, 1));
        System.out.println("p.percolates(): " + p.percolates());
        System.out.println("p.numberOfOpenSites(): " + p.numberOfOpenSites());
        System.out.println("one trial ----------------------------------------");
    }
}
