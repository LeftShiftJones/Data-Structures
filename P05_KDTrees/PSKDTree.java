import java.util.Stack;

/**
 * PSKDTree is a Point collection that provides nearest neighbor searching using
 * 2d tree
 */
public class PSKDTree<Value> implements PointSearch<Value> {

    /**
     * Root of tree
     */
    private Node root;

    /**
     * Size of tree
     */
    private int size;

    /**
     * Absolute minimum and maximum
     */
    private Point minPoint, maxPoint;

    /**
     * private class for Node
     */
    private class Node {
        Point p;
        Value v;
        Node left, right;
        Partition.Direction dir;

        /**
         * Constructor for Node class
         */
        public Node(Point p, Value v, Partition.Direction d) {
            this.p      = p;
            this.v      = v;
            this.dir    = d;
            setMinAndMax(p);
            ++size;
        }
    }

    /**
     * Constructor instantiates an empty KD Tree
     */
    public PSKDTree() {
        this.size = 0;
    }

    /**
     * Add nodes to the KD Tree
     *
     * @param p Point to add
     * @param v Value to assign to point
     */
    public void put(Point p, Value v) {
        validPoint(p);
        if (isEmpty()) {
            root = new Node(p, v, Partition.Direction.DOWNUP);
            return;
        }

        Node current = root;
        while (current != null) {
            //case where point already exists, update value
            if (current.p.equals(p)) {
                current.v = v; // set new value
                break; // break loop
            }

            //Check direction
            if (current.dir == Partition.Direction.DOWNUP) {
                //Check which side the node needs to go on
                if (p.x() < current.p.x()) {
                    //Check if it's null
                    if (current.left == null) {
                        //if so, make it a new Node
                        current.left = new Node(p, v, Partition.Direction.LEFTRIGHT);
                        break; //item is put, break loop
                    } else {
                        current = current.left;
                    }
                } else {
                    if (current.right == null) {
                        current.right = new Node(p, v, Partition.Direction.LEFTRIGHT);
                        break; //item is put, break loop
                    } else {
                        current = current.right;
                    }
                }
            } else {
                if (p.y() < current.p.y()) {
                    if (current.left == null) {
                        current.left = new Node(p, v, Partition.Direction.DOWNUP);
                        break; //item is put, break loop
                    } else {
                        current = current.left;
                    }
                } else {
                    if (current.right == null) {
                        current.right = new Node(p, v, Partition.Direction.DOWNUP);
                        break; //item is put, break loop
                    } else {
                        current = current.right;
                    }
                }
            }
        }
    }

    /**
     * @return All nodes in the tree
     */
    public Iterable<Point> points() {
        Queue<Point> toReturn = new Queue<>();
        traverse(root, toReturn);
        return toReturn;
    }

    /**
     * Return an iterable of all partitions
     *
     * @return partitions iterable object
     */
    public Iterable<Partition> partitions() {
        validate();
        Stack <Partition> partitions = new Stack<>(); //re-initialize stack
        findPartitions(root, minPoint, maxPoint, partitions);
        return partitions;
    }

    /**
     * Recursive method to add partitions to the graph
     * DOES NOT COMPLETELY WORK, STILL SOME BUGS
     *
     * @param n   Node to add partition to
     * @param min minimum value to get x and y from
     * @param max maximum value to get x and y from
     */
    private void findPartitions(Node n, Point min, Point max, Stack<Partition> partitions) {
        if (n == null) return;
        Point p0, p1;
        Partition p;

        //set values of partition based on direction
        if (n.dir == Partition.Direction.DOWNUP) {
            p0    = new Point(n.p.x(), min.y());
            p1    = new Point(n.p.x(), max.y());
            p = new Partition(p0, p1, n.dir);
        } else {
            p0    = new Point(min.x(), n.p.y());
            p1    = new Point(max.x(), n.p.y());
            p = new Partition(p0, p1, n.dir);
        }

        //add to partition stack and recurse through children
        partitions.push(p);
        findPartitions(n.left, min, p0, partitions);
        findPartitions(n.right, p0, max, partitions);
    }

    /**
     * Finds the nearest node, used for nearest value and point methods
     * Uses the same logic as the put method
     *
     * @param p node to find nearest neighbor
     * @return nearest node
     */
    private Node findNode(Point p) {
        validate();
        validPoint(p);
        Node current = root;
        //runs until current is the node we're looking for
        //  or it goes to a null node
        while (true) {
            if (current.p.equals(p)) return current; //return current if we're at the point
            //Check direction...
            if (current.dir == Partition.Direction.DOWNUP) {
                //Check if we need to go left or right...
                if (p.x() < current.p.x()) {
                    //Check if we're going to hit a null node
                    if(current.left == null) break;
                    //Otherwise, we'll continue the loop from this point
                    else current = current.left;
                }
                else {
                    if(current.right == null) break;
                    else current = current.right;
                }
            } else { //For case where current is a left-right node
                if (p.y() < current.p.y()) {
                    if(current.right == null) break;
                    else current = current.right;
                }
                else {
                    if(current.right == null) break;
                    else current = current.right;
                }
            }
        }
        return current;
    }

    /**
     * Uses nearestNode helper method to find the closest node
     *
     * @param p
     * @return
     */
    public Point nearest(Point p) {
        //get nearest with a k of 1
        Iterable<Point> i = nearest(p, 1);
        //set and return the given point
        Point ptr = new Point(0, 0);
        for (Point j : i) ptr = j;
        return ptr;
    }

    /**
     * Method calls a recursive function to find the nearest k-nodes to a desired point
     * (also used to find single nearest)
     *
     * @param p
     * @param k
     * @return
     */
    public Iterable<Point> nearest(Point p, int k) {
        validate();
        validPoint(p);
        if (k < 1) return null;
        //set up
        validate();
        MaxPQ<PointDist> nearestPQ = new MaxPQ<>();

        //run recursion
        traverse(root, k, p, nearestPQ);

        //create iterable
        Stack<Point> toReturn = new Stack<>();
        for (PointDist i : nearestPQ) {
            toReturn.push(i.p());
        }
        return toReturn;
    }

    /**
     * Checks the MaxPQ to see if it exceeds the mandated size. Deletes the min if true;
     *
     * @param s size to reduce to
     */
    private void checkPQSize(int s, MaxPQ<PointDist> nearestPQ) {
        if (nearestPQ.size() > s) nearestPQ.delMax();
    }

    /**
     * Recursive method to traverse the KD Tree and find the nearest node
     *
     * @param x   Node to examine
     * @param max Maximum number of values that can be placed in the maxPQ
     * @param p   Point to search for
     */
    private void traverse(Node x, int max, Point p, MaxPQ<PointDist> nearestPQ) {
        if (x == null) return;
        nearestPQ.insert(new PointDist(x.p, x.p.distSquared(p)));
        checkPQSize(max, nearestPQ);

        //now check children
        //Check direction...
        if (x.dir == Partition.Direction.DOWNUP) {
            //Check if we need to go left or right...
            if (p.x() < x.p.x()) {
                //Go that direction...
                traverse(x.left, max, p, nearestPQ);
                //Check if we should go the other direction...
                if (Math.pow(Math.abs(x.p.x() - p.x()), 2) < nearestPQ.max().d() || nearestPQ.size() < max) {
                    traverse(x.right, max, p, nearestPQ);
                }
            } else {
                traverse(x.right, max, p, nearestPQ);
                if (Math.pow(Math.abs(x.p.x() - p.x()), 2) < nearestPQ.max().d() || nearestPQ.size() < max) {
                    traverse(x.left, max, p, nearestPQ);
                }
            }
        } else {
            if (p.y() < x.p.y()) {
                traverse(x.left, max, p, nearestPQ);
                if (Math.pow(Math.abs(x.p.y() - p.y()), 2) < nearestPQ.max().d() || nearestPQ.size() < max) {
                    traverse(x.right, max, p, nearestPQ);
                }
            } else {
                traverse(x.right, max, p, nearestPQ);
                if (Math.pow(Math.abs(x.p.y() - p.y()), 2) < nearestPQ.max().d() || nearestPQ.size() < max) {
                    traverse(x.left, max, p, nearestPQ);
                }
            }
        }
    }

    private void traverse(Node n, Queue<Point> q) {
        if(n == null) return;
        traverse(n.left, q);
        q.enqueue(n.p);
        traverse(n.right, q);
    }

    /**
     * Simple get method that uses the nearestNode method to determine if the given point exists
     *
     * @param p Point to search for
     * @return Value at Node if it exists
     */
    public Value get(Point p) {
        validate();
        validPoint(p);
        Node toReturn = findNode(p);
        if (toReturn.p.equals(p)) return toReturn.v;
        return null;
    }

    /**
     * Simple boolean check to see if a node at point P exists
     *
     * @param p Point to search for
     * @return Whether or not the tree contains this point
     */
    public boolean contains(Point p) {
        validate();
        validPoint(p);
        if (findNode(p).p.equals(p)) return true;
        return false;
    }

    /**
     * Gets the value at the node nearest to point p
     *
     * @param p Point to look near
     * @return value at closest point
     */
    public Value getNearest(Point p) {
        validate();
        validPoint(p);
        return get(nearest(p));
    }

    /**
     * @return absolute minimum point of the graph
     */
    public Point min() {
        return (isEmpty()) ? null : minPoint;
    }

    /**
     * @return absolute maximum point of the graph
     */
    public Point max() {
        return (isEmpty()) ? null : maxPoint;
    }

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
            //All other cases, check each value
            this.minPoint = Point.min(p, minPoint);
            this.maxPoint = Point.max(p, maxPoint);
        }
    }

    /**
     * Get Size of tree
     *
     * @return Size of the tree
     */
    public int size() {
        return this.size;
    }

    /**
     * Check if tree is empty
     *
     * @return Whether or not tree is empty
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Simple method to validate whether the tree is empty or not
     * Throws an exception if it is empty
     */
    private void validate() {
        if (isEmpty()) throw new IllegalArgumentException("KD Tree is Empty");
    }

    /**
     * Helper method checks to see that the point entered is valid.
     * @param p Pointer to validate
     */
    private void validPoint(Point p) {
        if(p == null) throw new NullPointerException("Point invalid.");
    }

    // place your timing code or unit testing here
    public static void main(String[] args) {

        PSKDTree<Character> t = new PSKDTree<>();

        t.put(new Point(0.8, 0.9), 'A');
        t.put(new Point(0.5, 0.4), 'B');
        t.put(new Point(0.2, 0.6), 'C');
        t.put(new Point(0.3, 0.1), 'D');
        t.put(new Point(0.9, 0.4), 'E');

        StdOut.println(t.points());
        StdOut.println(t.nearest(new Point(0.75, 0.2)));
        StdOut.println(t.getNearest(new Point(0.75, 0.2)));

    }

}
