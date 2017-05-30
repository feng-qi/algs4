import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Iterator;

public class PointSET {
    private TreeSet<Point2D> set;
    public PointSET () {
        set = new TreeSet<Point2D>();
    }
    public boolean isEmpty() {
        return set.isEmpty();
    }
    public int size() {
        return set.size();
    }
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        set.add(p);
    }
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return set.contains(p);
    }
    public void draw() {
        for (Point2D p : set) {
            StdDraw.point(p.x(), p.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        return new Range(rect);
    }
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (set.isEmpty()) return null;
        Point2D pmin;
        double  dist;
        Iterator<Point2D> it = set.iterator();
        // if (it.hasNext())
            pmin = it.next();
        dist = p.distanceTo(pmin);
        while (it.hasNext()) {
            Point2D ptmp = it.next();
            double  dtmp = p.distanceTo(ptmp);
            if (dtmp < dist) {
                pmin = ptmp;
                dist = dtmp;
            }
        }
        return pmin;
    }
    private class Range implements Iterable<Point2D> {
        ArrayList<Point2D> list;
        // RectHV rect;
        public Range(RectHV rect) {
            list = new ArrayList<Point2D>();
            // this.rect = rect;
            for (Point2D p : set) {
                if (rect.contains(p))
                    list.add(p);
            }
        }
        public Iterator<Point2D> iterator() {
            return list.iterator();
        }
        // private class Itor implements Iterator<Point2D> {
        //     public boolean hasNext() {}
        // }
    }
}
