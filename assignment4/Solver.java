import java.util.Iterator;
import java.util.Comparator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ResizingArrayStack;

public class Solver {
    private int moves = -1;     // -1 if unsolvable.
    private Board[] solution = null; // null if unsolvable.

    private class Node {
        private Board board;
        private int   moves;
        private Node  prev;
        public Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            this.prev  = prev;
        }
    }

    public Solver (Board initial) {
        if (initial == null) throw new NullPointerException();

        MinPQ<Node> pq = new MinPQ<Node>(new Comptor());
        Node min;
        pq.insert(new Node(initial, 0, null));

        Board mirror = initial.twin();
        MinPQ<Node> pq_mirror = new MinPQ<Node>(new Comptor());
        Node min_mirror;
        pq_mirror.insert(new Node(mirror, 0, null));

        min = pq.delMin();
        min_mirror = pq_mirror.delMin();
        boolean flip = true;
        while (!min.board.isGoal() && !min_mirror.board.isGoal()) {
            if (flip) {
                Iterator<Board> iter = min.board.neighbors().iterator();
                while (iter.hasNext()) {
                    Board b = iter.next();
                    if (min.prev == null || !b.equals(min.prev.board))
                        pq.insert(new Node(b, min.moves+1, min));
                }
                min = pq.delMin();
            } else {
                Iterator<Board> iter = min_mirror.board.neighbors().iterator();
                while (iter.hasNext()) {
                    Board b = iter.next();
                    if (min_mirror.prev == null || !b.equals(min_mirror.prev.board))
                        pq_mirror.insert(new Node(b, min_mirror.moves+1, min_mirror));
                }
                min_mirror = pq_mirror.delMin();
            }
            flip = !flip;
        }

        if (min.board.isGoal()) {
            ResizingArrayStack<Board> stack = new ResizingArrayStack<Board>();
            while (min != null) {
                stack.push(min.board);
                min = min.prev;
            }
            moves = stack.size() - 1;
            solution = new Board[moves+1];
            for (int i = 0; i < solution.length; i++) {
                solution[i] = stack.pop();
            }
        }
    }
    public boolean isSolvable() {
        return moves == -1 ? false : true;
    }
    public int moves() {
        return moves;
    }
    private class Comptor implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            int manhattan1 = n1.board.manhattan() + n1.moves;
            int manhattan2 = n2.board.manhattan() + n2.moves;
            if (manhattan1 < manhattan2) return -1;
            else if (manhattan1 > manhattan2) return 1;
            else return 0;
        }
    }
    private class SolutionIterable implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return new SolutionIterator();
        }
        private class SolutionIterator implements Iterator<Board> {
            private int i;
            public boolean hasNext() {
                return i != solution.length;
            }
            public Board next() {
                if (!hasNext()) throw new NoSuchElementException();
                return solution[i++];
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return new SolutionIterable();
    }
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

