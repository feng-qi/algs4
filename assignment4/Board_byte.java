import edu.princeton.cs.algs4.In;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board_byte {
    private final byte[][] blocks;
    private final int dimension;
    private int blankRow;
    private int blankCol;
    private boolean didManhattan = false;
    private int manhattanValue;

    public Board(int[][] blocks) {
        dimension = blocks.length;
        this.blocks = new byte[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
    }
    public int dimension() { return dimension; }
    public int hamming() {
        int pos = 1, ret = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != pos) {
                    ret++;
                }
                pos++;
            }
        }
        return ret;
    }
    public int manhattan() {
        if (didManhattan) return manhattanValue;
        int row, col, ret = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != 0) {
                    row = (blocks[i][j] - 1) / dimension;
                    col = blocks[i][j] - 1 - row * dimension;
                    ret += Math.abs(i-row) + Math.abs(j-col);
                }
            }
        }
        manhattanValue = ret;
        didManhattan = true;
        return manhattanValue;
    }
    public boolean isGoal() {
        int pos = 1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != pos) {
                    return false;
                }
                pos++;
            }
        }
        if (blocks[dimension-1][dimension-1] != 0) return false;
        return true;
    }
    public Board twin() {
        int i, j = 0;
        outer:
        for (i = 0; i < dimension; i++) {
            for (j = 0 ; j < dimension; j++) {
                if (blocks[i][j] == 0) break outer;
            }
        }
        int ii = (i+1) % dimension;
        int jj = (j+1) % dimension;
        Board ret = new Board(this.blocks);
        // if      (blocks[0][0] == 0) {i = 1; j = 2;}
        // else if (blocks[0][1] == 0) {i = 0; j = 2;}
        // else                        {i = 0; j = 1;}
        int tmp           = ret.blocks[i][jj];
        ret.blocks[i][jj] = ret.blocks[ii][j];
        ret.blocks[ii][j] = tmp;
        return ret;
    }
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension != that.dimension) return false;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    private class Neighbors implements Iterable<Board> {
        private Board[] neighbors = new Board[4];
        private int n;
        private Neighbors() {
            n = 0;
            int i = 3;
            if (blankRow-1 < 0) { neighbors[i--] = null; }
            else {
                exch(blocks, blankRow, blankCol, blankRow-1, blankCol);
                neighbors[n++] = new Board(blocks);
                exch(blocks, blankRow, blankCol, blankRow-1, blankCol);
            }
            if (blankRow+1 == dimension) { neighbors[i--] = null; }
            else {
                exch(blocks, blankRow, blankCol, blankRow+1, blankCol);
                neighbors[n++] = new Board(blocks);
                exch(blocks, blankRow, blankCol, blankRow+1, blankCol);
            }
            if (blankCol-1 < 0) { neighbors[i--] = null; }
            else {
                exch(blocks, blankRow, blankCol-1, blankRow, blankCol);
                neighbors[n++] = new Board(blocks);
                exch(blocks, blankRow, blankCol-1, blankRow, blankCol);
            }
            if (blankCol+1 == dimension) { neighbors[i--] = null; }
            else {
                exch(blocks, blankRow, blankCol+1, blankRow, blankCol);
                neighbors[n++] = new Board(blocks);
                exch(blocks, blankRow, blankCol+1, blankRow, blankCol);
            }
        }
        private class NeiIterator implements Iterator<Board> {
            int i = 0;
            public boolean hasNext() {
                return i != n;
            }
            public Board next() {
                if (!hasNext()) throw new NoSuchElementException();
                return neighbors[i++];
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
        public Iterator<Board> iterator() {
            return new NeiIterator();
        }
    }
    private void exch(int[][] blocks, int r1, int c1, int r2, int c2) {
        int tmp = blocks[r1][c1];
        blocks[r1][c1] = blocks[r2][c2];
        blocks[r2][c2] = tmp;
    }
    public Iterable<Board> neighbors() {
        return new Neighbors();
    }
    public String toString() {
        StringBuilder strbld = new StringBuilder();
        strbld.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                strbld.append(String.format("%2d ", blocks[i][j]));
            }
            strbld.append('\n');
        }
        return strbld.toString();
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.println(initial.manhattan());
    }
}
