import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;

public class KdTree {
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;        // the left/bottom sub-tree
        private Node rt;        // the right/top sub-tree
        public Node(Point2D p, RectHV rect) {
            this.p    = p;
            this.rect = rect;
        }
    }
    private Node root;
    private int size;
    public KdTree() {
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (contains(p)) return;
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        root = insert(root, p, rect, true);
    }
    private Node insert(Node node, Point2D p, RectHV rect, boolean vertical) {
        if (node == null) {
            size++;
            return new Node(p, rect);
        }
        RectHV newRect;
        if (vertical) {
            if (p.x() < node.p.x()) {
                newRect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax());
                node.lb = insert(node.lb, p, newRect, !vertical);
            } else {
                newRect = new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
                node.rt = insert(node.rt, p, newRect, !vertical);
            }
        } else {
            if (p.y() < node.p.y()) {
                newRect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y());
                node.lb = insert(node.lb, p, newRect, !vertical);
            } else {
                newRect = new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax());
                node.rt = insert(node.rt, p, newRect, !vertical);
            }
        }
        return node;
    }
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return get(root, p, true);
    }
    private boolean get(Node node, Point2D p, boolean vertical) {
        if (node == null) return false;
        if (p.equals(node.p)) return true;
        if (vertical) {
            if (p.x() < node.p.x()) return get(node.lb, p, !vertical);
            else                    return get(node.rt, p, !vertical);
        } else {
            if (p.y() < node.p.y()) return get(node.lb, p, !vertical);
            else                    return get(node.rt, p, !vertical);
        }
    }
    public void draw() {
        root.rect.draw();
        draw(root, true);
    }
    private void draw(Node node, boolean vertical) {
        if (node == null) return;
        // draw split line
        StdDraw.setPenRadius();
        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        // draw Point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();
        // draw children node
        draw(node.lb, !vertical);
        draw(node.rt, !vertical);
    }
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        range(rect, root, list);
        return list;
    }
    private void range(RectHV rect, Node node, ArrayList<Point2D> list) {
        if (node == null) return;
        if (node.rect.intersects(rect)) {
            if (rect.contains(node.p))
                list.add(node.p);
            range(rect, node.lb, list);
            range(rect, node.rt, list);
            // return;
        }
        // if (vertical) {
        //     if (rect.xmax() < node.p.x())
        //         range(rect, node.lb, list, !vertical);
        //     else
        //         range(rect, node.rt, list, !vertical);
        // } else {
        //     if (rect.ymax() < node.p.y())
        //         range(rect, node.lb, list, !vertical);
        //     else
        //         range(rect, node.rt, list, !vertical);
        // }
    }
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (root == null) return null;
        return nearest(root, p, null, true);
    }
    private Point2D nearest(Node node, Point2D p, Point2D min, boolean vertical) {
        if (node == null) return min;
        if (min == null)  min = node.p;
        else if (p.distanceTo(node.p) < p.distanceTo(min)) {
            min = node.p;
        }
        if (vertical) {
            if (p.x() < node.p.x()) {
                if (node.lb != null && node.lb.rect.distanceTo(p) < min.distanceTo(p))
                    min = nearest(node.lb, p, min, !vertical);
                if (node.rt != null && node.rt.rect.distanceTo(p) < min.distanceTo(p))
                    min = nearest(node.rt, p, min, !vertical);
            } else {
                if (node.rt != null && node.rt.rect.distanceTo(p) < min.distanceTo(p))
                    min = nearest(node.rt, p, min, !vertical);
                if (node.lb != null && node.lb.rect.distanceTo(p) < min.distanceTo(p))
                    min = nearest(node.lb, p, min, !vertical);
            }
        } else {
            if (p.y() < node.p.y()) {
                if (node.lb != null && node.lb.rect.distanceTo(p) < min.distanceTo(p))
                    min = nearest(node.lb, p, min, !vertical);
                if (node.rt != null && node.rt.rect.distanceTo(p) < min.distanceTo(p))
                    min = nearest(node.rt, p, min, !vertical);
            } else {
                if (node.rt != null && node.rt.rect.distanceTo(p) < min.distanceTo(p))
                    min = nearest(node.rt, p, min, !vertical);
                if (node.lb != null && node.lb.rect.distanceTo(p) < min.distanceTo(p))
                    min = nearest(node.lb, p, min, !vertical);
            }
        }
        return min;
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            System.out.println(kdtree.size());
        }
        in = new In(filename);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            System.out.println(p + " Contained?: " + kdtree.contains(p));
        }
    }
}
