import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Merge;

public class BruteCollinearPoints {
    private LineSegment[] segments = new LineSegment[100];
    private int n = 0;
    public BruteCollinearPoints(Point[] points) {
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
        for (int i = 0; i < points.length-3; i++) {
            for (int j = i+1; j < points.length-2; j++) {
                for (int k = j+1; k < points.length-1; k++) {
                    for (int l = k+1; l < points.length; l++) {
                        addIfIsCollinear(points[i], points[j], points[k], points[l]);
                    }
                }
            }
        }
    }
    private void addIfIsCollinear(Point p1, Point p2, Point p3, Point p4) {
        Point[] points = new Point[4];
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        points[3] = p4;
        Merge.sort(points);
        double slope1 = points[0].slopeTo(points[1]);
        double slope2 = points[0].slopeTo(points[2]);
        double slope3 = points[0].slopeTo(points[3]);
        if (slope1 == slope2 && slope1 == slope3) {
            if (n == segments.length) resize();
            segments[n++] = new LineSegment(points[0], points[3]);
        }
    }
    private void resize() {
        LineSegment[] tmp = new LineSegment[segments.length * 2];
        for (int i = 0; i < segments.length; i++) {
            tmp[i] = segments[i];
        }
    }
    public int numberOfSegments() {
        return n;
    }
    public LineSegment[] segments() {
        LineSegment[] ret = new LineSegment[n];
        for (int i = 0; i < n; i++) {
            ret[i] = segments[i];
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
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
