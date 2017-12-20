import java.util.Iterator;

/**
 * PSBruteForce is a Point collection that provides brute force
 * nearest neighbor searching using red-black tree.
 */
public class PSBruteForce<Value> implements PointSearch<Value> {

    private RedBlackBST<Point, Value> tree;
    private Point maxPoint, minPoint;

    // constructor makes empty collection
    public PSBruteForce() {
        this.tree = new RedBlackBST<>();
    }

    // add the given Point to KDTree
    public void put(Point p, Value v) {
        this.setMinAndMax(p);
        this.tree.put(p, v);
    }

    public Value get(Point p) {
        return (this.tree.contains(p)) ? this.tree.get(p) : null;
    }

    // returns whether the tree contains the point or not
    public boolean contains(Point p) {
        return this.tree.contains(p);
    }

    // return an iterable of all points in collection using Red-Black tree methods
    public Iterable<Point> points() {
        return this.tree.keys();
    }

    // return the Point that is closest to the given Point
    public Point nearest(Point p) {
        if(isEmpty()) throw new IllegalArgumentException("Tree is empty, cannot run nearest() method.");

        //set new point with impossibly large x and y. All values in the tree should be smaller
        Point toReturn = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
        for(Point q : this.points()) {
            if(q.dist(p) < toReturn.dist(p)) toReturn = q;
        }
        return toReturn;
    }

    // return the Value associated to the Point that is closest to the given Point
    public Value getNearest(Point p) {
        if(isEmpty()) throw new IllegalArgumentException("Tree is empty, cannot run getNearest() method.");

        return this.tree.get(this.nearest(p)); // runs nearest point method to get value from tree;
    }

    // return the min and max for all Points in collection.
    // The min-max pair will form a bounding box for all Points.
    // if KDTree is empty, return null.
    public Point min() { return (isEmpty()) ? null : minPoint; }
    public Point max() { return (isEmpty()) ? null : maxPoint; }

    /**
     * Function to set the absolute minimum and maximum values
     *
     * @param p point to compare
     */
    private void setMinAndMax(Point p) {
        //Empty case
        if (isEmpty()) {
            minPoint = new Point(p.x(), p.y()); //default value
            maxPoint = new Point(p.x(), p.y()); //default value
        } else {
            //All other cases, check values
            if (p.x() < minPoint.x()) minPoint = new Point(p.x(), minPoint.y()); // (new x, old y)
            if (p.y() < minPoint.y()) minPoint = new Point(minPoint.x(), p.y()); // (old x, new y)
            if (p.x() > maxPoint.x()) maxPoint = new Point(p.x(), maxPoint.y()); // (new x, old y)
            if (p.y() > maxPoint.y()) maxPoint = new Point(maxPoint.x(), p.y()); // (old x, new y)
        }
    }

    /**
     * Returns an iterable of the k-nearest points to point p;
     * @param p point to compare to
     * @param k number of points to include
     * @return iterable of k-nearest points
     */
    public Iterable<Point> nearest(Point p, int k) {
        MaxPQ<PointDist> mPQ = new MaxPQ<>();
        for(Point q : this.points()) {
            mPQ.insert(new PointDist(q, q.dist(p)));
            if(mPQ.size() > k) mPQ.delMax();
        }
        Stack<Point> toReturn = new Stack<>();
        for(PointDist i : mPQ) {
            toReturn.push(i.p());
        }
        return toReturn;
    }

    public Iterable<Partition> partitions() { return null; } //Silly Red-Black tree, you can't keep track of partitions!

    // return the number of Points in KDTree
    public int size() { return this.tree.size(); }

    // return whether the KDTree is empty
    public boolean isEmpty() { return this.size() == 0; }

    // place your timing code or unit testing here
    public static void main(String[] args) {
        PSBruteForce<Integer> p = new PSBruteForce<>();
        p.put(new Point(1.5, 1.5), 0);
        p.put(new Point(2.5, 2.5), 1);
        p.put(new Point(3.5, 3.5), 2);
        p.put(new Point(4.5, 4.5), 3);
        p.put(new Point(5.5, 5.5), 4);
        p.put(new Point(6.5, 6.5), 5);
        p.put(new Point(7.5, 7.5), 6);
        p.put(new Point(5.0, 5.0), 7);
        //StdOut.println(p.nearest(new Point(-500, -500)));
        Iterable<Point> i = p.nearest(new Point(4.9, 5.3), 5);
        for(Point j : i) StdOut.println(j);
    }
}
