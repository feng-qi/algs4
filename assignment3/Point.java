import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        if (y < that.y) return -1;
        else if (y > that.y) return 1;
        else if (x < that.x) return -1;
        else if (x > that.x) return 1;
        else return 0;
    }
    public double slopeTo(Point that) {
        // return ((double)that.y - y) / ((double)that.x - x);
        if (x == that.x && y == that.y) {
            return Double.NEGATIVE_INFINITY;
        } else if (y == that.y) {
            return 0.0;
        } else if (x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else {
            return ((double)that.y - y) / ((double)that.x - x);
        }
    }
    public Comparator<Point> slopeOrder() {
        return new cmptor();
    }
    private class cmptor implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            // return slopeTo(p1) < slopeTo(p2) ? 1 : -1;
            return Double.compare(slopeTo(p1), slopeTo(p2));
        }
    }
    public static void main(String[] args) {
        Point p = new Point(177, 412);
        Point q = new Point(177, 229);
        Point r = new Point(177, 89);
        System.out.println(p.slopeTo(q));
        System.out.println(p.slopeOrder().compare(q, q));
        System.out.println(p.slopeOrder().compare(q, r));
        System.out.println(p.slopeOrder().compare(r, q));
    }
}
