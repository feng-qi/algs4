import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Merge;

public class FastCollinearPoints {
    private Point[] pairs = new Point[100];
    private int n = 0;
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        for (Point p : points) {
            if (p == null) throw new NullPointerException();
        }
        for (int i = 0; i < points.length-1; i++) {
            for (int j = i+1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
        Merge.sort(points);
        for (int i = 0; i < points.length; i++) {
            addIfHasCollinear(points, i);
        }
        assert(n%2 == 0);
    }
    private void resize() {
        assert(n%2 == 0);
        Point[] tmp = new Point[pairs.length * 2];
        for (int i = 0; i < pairs.length; i++) {
            tmp[i] = pairs[i];
        }
        pairs = tmp;
    }
    private class PointAndSlope implements Comparable<PointAndSlope> {
        private Point point;
        private double slope;
        public int compareTo(PointAndSlope that) {
            if (slope < that.slope) return -1;
            else if (slope > that.slope) return 1;
            else return point.compareTo(that.point);
        }
    }
    private void addIfHasCollinear(Point[] points, int p) {
        PointAndSlope ps[] = new PointAndSlope[points.length];
        for (int i = 0; i < ps.length; i++) {
            PointAndSlope tmp = new PointAndSlope();
            tmp.point = points[i];
            tmp.slope = points[p].slopeTo(points[i]);
            ps[i] = tmp;
        }
        Merge.sort(ps);
        outer:
        for (int i = 0, j = i+1; i < ps.length; j = i+1) {
            while (j < ps.length && ps[j].slope == ps[i].slope) j++;
            if (j-i >= 3) {
                Point min, max;
                int k;
                min = (ps[i].point.compareTo(points[p]) < 0) ? ps[i].point : points[p];
                max = (ps[j-1].point.compareTo(points[p]) > 0) ? ps[j-1].point : points[p];
                if (n == 0) {
                    pairs[n++] = min; pairs[n++] = max;
                    i = j;
                    continue;
                }
                for (k = 0; k < n; k+=2) {
                    if (pairs[k].compareTo(min) == 0 && pairs[k+1].compareTo(max) == 0)
                        break outer;
                }
                if (k == n) {
                    if (n == pairs.length) resize();
                    pairs[n++] = min; pairs[n++] = max;
                }
                i = j;
            } else {
                i++;
            }
        }
    }
    public int numberOfSegments() {
        return n / 2;
    }
    public LineSegment[] segments() {
        assert(n%2 == 0);
        LineSegment[] ret = new LineSegment[n/2];
        for (int i = 0; i < n; i+=2) {
            ret[i/2] = new LineSegment(pairs[i], pairs[i+1]);
        }
        return ret;
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 32768);
        // StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        //     p.draw();
        // }
        // StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            // segment.draw();
        }
        // StdDraw.show();
    }
}
