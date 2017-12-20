//import java.util.Queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * HollywoodGraph loads movie and actor data from a file, generates
 * a graph out of it, and then allows questions to be asked about
 * the data, such as:
 * - How many connected components exist?
 * - How many actors are connected to a particular actor?
 * - What is the average distance between all actors connected
 * to a particular actor?
 * - What is the length of the shortest path between two actors?
 */
public class HollywoodGraph {
    private Graph actorGraph;
    private CCBFS components;
    private String[] keys;
    private RedBlackBST<String, Integer> hollywoodTree;
    private int numActors;

    /////////////////////////////////////////////////////////////////////////////////

    private class Actor implements HollywoodActor {
        private String name, maximumActor;
        private boolean[] marked;
        private int[] edgeTo, distTo;
        private int actorLength, maximumDist, connectedActors;
        private double averageDistance;
        private boolean bfsRun;


        public Actor(String name) {
            this.name = name;
            this.bfsRun = false;
            this.actorLength = -1;
            this.maximumDist = 0;
            this.connectedActors = 1;
            this.averageDistance = 0.0;
            runBFS();
        }

        public int compareTo(Actor that) {
            return Double.compare(this.averageDistance, that.averageDistance);
        }

        private void runBFS() {
            bfsRun = true;
            this.marked = new boolean[actorGraph.V()];
            this.edgeTo = new int[actorGraph.V()];
            this.distTo = new int[actorGraph.V()];

            Queue<Integer> q = new Queue<>();
            int thisNumber = hollywoodTree.get(this.name);
            int sum = 0, ma = thisNumber;
            q.enqueue(thisNumber);
            marked[thisNumber] = true;
            distTo[thisNumber] = 0;
            while (!q.isEmpty()) {
                int v = q.dequeue();
                for (int w : actorGraph.adj(v)) {
                    if (!marked[w]) {
                        q.enqueue(w);
                        marked[w] = true;
                        edgeTo[w] = v;
                        //for actors
                        if (w < numActors) {
                            distTo[w] = distTo[v];
                            sum += distTo[w];
                            if (distTo[w] > maximumDist) {
                                maximumDist = distTo[w];
                                ma = w;
                            }
                            connectedActors++;
                        }
                        //for movies
                        else {
                            distTo[w] = distTo[v] + 1;
                        }
                    }
                }
            }
            averageDistance = (double) sum / connectedActors;
            maximumActor = keys[ma];
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public double distanceAverage() {
            if (bfsRun) return this.averageDistance;
            else throw new IllegalArgumentException("BFS has not been run on " + this.name);
        }

        @Override
        public double distanceMaximum() {
            if (bfsRun) return (double) this.maximumDist;
            else throw new IllegalArgumentException("BFS has not been run on " + this.name);
        }

        @Override
        public String actorMaximum() {
            if (bfsRun) return this.maximumActor;
            else throw new IllegalArgumentException("BFS has not been run on " + this.name);
        }

        @Override
        public double actorPathLength(String name) {
            if (name.equals(this.name)) return 0.0;

            actorPath(name);
            if (this.actorLength == 0) {
                return Double.POSITIVE_INFINITY;
            } else {
                return (double) this.actorLength;
            }
        }

        @Override
        public Iterable<String> movies() {
            Stack<String> movies = new Stack<>();
            for (int m : actorGraph.adj(hollywoodTree.get(this.name))) {
                movies.push(keys[m]);
            }
            return movies;
        }

        public int connectedActors() {
            return this.connectedActors;
        }

        @Override
        public Iterable<String> actorPath(String name) {
            int index = hollywoodTree.get(this.name), otherIndex = hollywoodTree.get(name);
            //reset actorPathLength variable
            actorLength = 0;
            if (!components.connected(index, otherIndex)) return null;

            Stack<String> path = new Stack<>();

            //in case user passes in this actor's name
            if (this.name.equals(name)) {
                path.push(name);
                return path;
            }

            path.push(keys[otherIndex]);
            int current = otherIndex;
            while (current != index) {
                current = edgeTo[current]; //at movie
                current = edgeTo[current]; //at actor
                path.push(keys[current] + ";");
                actorLength++;
            }
            return path;
        }

        //EXTRA CREDIT
        @Override
        public Iterable<String> moviePath(String name) {
            int index = hollywoodTree.get(this.name), otherIndex = hollywoodTree.get(name);
            if (!components.connected(index, otherIndex)) return null;
            Stack<String> path = new Stack<>();

            //in case user passes in this actor's name
            if (this.name.equals(name)) {
                path.push(name);
                return path;
            }

            path.push(keys[otherIndex]);
            int current = otherIndex;
            while (current != index) {
                current = edgeTo[current]; //next item
                path.push(keys[current] + ";");
            }
            return path;
        }
    }


    public HollywoodGraph(String filename) {
        hollywoodTree = new RedBlackBST<>();
        fillGraph(filename);
        if (actorGraph == null) throw new IllegalArgumentException("Graph was not properly initialized");
        components = new CCBFS(actorGraph);
    }

    public Actor getActorDetails(String name) {
        if (name == null) throw new IllegalArgumentException("Cannot enter a null value");
        if (hollywoodTree.get(name) == null) throw new IllegalArgumentException("Not a valid actor");
        return new Actor(name);
    }

    private void fillGraph(String filename) {
        In in = new In(filename);
        String[] lines = in.readAllLines();
        for (String line : lines) {
            String[] parts = line.split("/");
            String movie = null;
            for (String part : parts) {
                if (movie == null) movie = part;
                else {
                    if (!hollywoodTree.contains(part)) {
                        hollywoodTree.put(part, hollywoodTree.size());
                    }
                }
            }
        }
        numActors = hollywoodTree.size();
        actorGraph = new Graph(hollywoodTree.size() + lines.length - 1);
        for (String line : lines) {
            String[] parts = line.split("/");
            hollywoodTree.put(parts[0], hollywoodTree.size());
            int v = hollywoodTree.size() - 2;
            for (int i = 1; i < parts.length; i++) {
                int w = hollywoodTree.get(parts[i]);
                actorGraph.addEdge(v, w);
            }
        }

        in.close();

        keys = new String[hollywoodTree.size()];
        for (String name : hollywoodTree.keys()) keys[hollywoodTree.get(name)] = name;
    }

    /**
     * Returns iterable strings of all connected components
     * Return single actor from individual components
     *
     * @return iterable of connected components
     */
    public Iterable<String> connectedComponents() {
        Stack<String> s = new Stack<>();
        boolean[] actorsMarked = new boolean[actorGraph.V()];
        for (int i = 0; i < numActors; i++) {
            if (!actorsMarked[i]) {
                bfs(actorsMarked, i);
                s.push(keys[i] + ";");
            }
        }
        return s;
    }

    private void bfs(boolean[] b, int x) {
        Queue<Integer> q = new Queue<>();
        q.enqueue(x);
        b[x] = true;
        while (!q.isEmpty()) {
            int i = q.dequeue();
            for (int j : actorGraph.adj(i)) {
                if (!b[j]) {
                    q.enqueue(j);
                    b[j] = true;
                }
            }
        }
    }

    public int connectedComponentsCount() {
        return components.count();
    }

    public int connectedActorsCount(String name) {
        return getActorDetails(name).connectedActors();
    }

    public double hollywoodNumber(String name) {
        return getActorDetails(name).distanceAverage();
    }

    static public void main(String[] args) {
        /* put code here to answer readme questions */
        HollywoodGraph h = new HollywoodGraph("data/movies.txt");
        double d = Double.MAX_VALUE;
        for(int i = 0; i < h.numActors; i++) {
            Actor a = h.getActorDetails(h.keys[i]);
            if(a.distanceAverage() < d) {
                d = a.distanceAverage();
                StdOut.println(a.name() + " : " + a.distanceAverage());
            }
        }
    }
}

