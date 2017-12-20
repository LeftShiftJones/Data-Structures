import java.lang.IndexOutOfBoundsException;
import java.lang.IllegalArgumentException;
import java.util.Stack;

/**
 * Pathfinder uses A* search to find a near optimal path
 * between to locations with given terrain.
 */

public class Pathfinder {

    private Coord start, end, minRange, maxRange;
    private final int mapSize;
    private int searchSize;
    private float pathHeuristic;
    private Terrain terrain;
    private MinPQ<PFNode> searchedNodes;
    private PFNode[][] visitedNodes;
    private boolean pathFound;
    private PFNode endNode;

    /**
     * PFNode will be the key for MinPQ (used in computePath())
     */
    private class PFNode implements Comparable<PFNode> {

        private Coord location;
        private boolean inUse, valid;
        private float cost;
        private PFNode previous;

        // loc: the location of the PFNode
        // fromNode: how did we get here? (linked list back to start)
        public PFNode(Coord loc, PFNode fromNode) {
            this.location = loc;
            this.inUse = false;
            this.valid = true;
            this.previous = fromNode;

            //set cost
            if (this.previous == null) cost = 0;
            else {
                float tCost = terrain.computeTravelCost(this.location, this.previous.location);
                cost = previous.cost + tCost; // cost equals the previous cost plus the travel distance
            }
        }

        // compares this with that, used to find minimum cost PFNode
        public int compareTo(PFNode that) {
            float f = this.getCost(getHeuristic()) - that.getCost(getHeuristic()); // local cost difference
            if(f < 0) return -1;
            if(f > 0) return 1;
            return 0;
        }

        // returns the cost to travel from starting point to this
        // via the fromNode chain
        public float getCost(float heuristic) {
            return cost + (heuristic * terrain.computeDistance(this.location, end));
        }

        // returns if this PFNode is still valid
        public boolean isValid() {
            return valid;
        }

        // invalidates the PFNode
        public void invalidate() {
            this.valid = false;
        }

        // returns if the PFNode has been used
        public boolean isUsed() {
            return inUse;
        }

        // uses the PFNode
        public void use() {
            inUse = true;
        }

        // returns an Iterable of PFNodes that surround this
        public Iterable<PFNode> neighbors() {
            Stack<PFNode> s = new Stack<>();
            addToStack(this.location.add(1, 0), this, s);
            addToStack(this.location.add(-1, 0), this, s);
            addToStack(this.location.add(0, 1), this, s);
            addToStack(this.location.add(0, -1), this, s);
            return s;
        }

        private void addToStack(Coord c, PFNode p, Stack<PFNode> s) {
            if (p.previous != null) {
                if (c.equals(p.previous.location)) return;
            }
            if (c.isInBounds(minRange, maxRange)) s.push(new PFNode(c, p));
        }
    }

    public Pathfinder(Terrain terrain) {
        this.terrain        = terrain;
        this.mapSize        = terrain.getN();
        this.start          = new Coord(0, 0);
        this.end            = new Coord(mapSize - 1, mapSize - 1);
        this.minRange       = new Coord(0, 0);
        this.maxRange       = new Coord(mapSize - 1, mapSize - 1);
        this.searchedNodes  = new MinPQ<>();
        this.visitedNodes   = new PFNode[mapSize][mapSize];
        this.searchSize     = 0;
        this.pathFound      = false;
    }

    public void setPathStart(Coord loc) {
        if(loc == null) throw new IllegalArgumentException("The location given was invalid");
        if(!loc.isInBounds(minRange, maxRange)) throw new IndexOutOfBoundsException("The location was outside of the bounds");
        this.start = loc;
    }

    public Coord getPathStart() {
        return this.start;
    }

    public void setPathEnd(Coord loc) {
        if(loc == null) throw new IllegalArgumentException("The location given was invalid");
        if(!loc.isInBounds(minRange, maxRange)) throw new IndexOutOfBoundsException("The location was outside of the bounds");
        this.end = loc;
    }

    public Coord getPathEnd() {
        return this.end;
    }

    public void setHeuristic(float v) {
        this.pathHeuristic = v;
        computePath();
    }

    public float getHeuristic() {
        return pathHeuristic;
    }

    public void resetPath() {
        this.searchedNodes  = new MinPQ<>();
        visitedNodes        = new PFNode[mapSize][mapSize];
        pathFound           = false;
        searchSize          = 0;
        endNode             = null;
    }

    /**
     * Method uses a 2D array, a MinPQ, and a terrain variable to
     * find the shortest path from the start coordinate to the end coordinate
     */
    public void computePath() {
        if(start == null || end == null) throw new IllegalArgumentException("Start or end has not been set yet");

        resetPath();

        //Handle absurd case that the start is the end
        if(start.equals(end)) {
            endNode = new PFNode(start, null);
            return;
        }

        //Set start of 2D array and MinPQ
        PFNode current = new PFNode(start, null);
        visitedNodes[current.location.getI()][current.location.getJ()] = current; //set start
        searchedNodes.insert(current); //insert starting node


        while (!foundPath()) {
            //check to see that node it will pick is valid
            if (!searchedNodes.min().isValid()) {
                searchedNodes.delMin(); //delete if it is not
                continue;
            }

            current = searchedNodes.delMin();

            //Check to see if we've found the end
            /*
            This will always be the shortest path because we are taking it off the MinPQ,
            meaning that there will be no other path to the End that is smaller by this point
            */
            if (isEnd(current.location)) {
                endNode     = current;
                pathFound   = true;
                break; //end of search, break loop
            }

            Iterable<PFNode> i = current.neighbors();

            //For each neighbor of current...
            for (PFNode p : i) {


                //and check against the array
                if (wasSearched(p.location)) {
                    PFNode t = visitedNodes[p.location.getI()][p.location.getJ()];

                    if (p.compareTo(t) < 0) {
                        //add them to the MinPQ
                        searchedNodes.insert(p);
                        t.invalidate(); //invalidate current node if we have a smaller one, will eventually de-reference
                        visitedNodes[p.location.getI()][p.location.getJ()] = p;
                    }
                } else {
                    //add them to the MinPQ
                    searchedNodes.insert(p);
                    p.use();
                    visitedNodes[p.location.getI()][p.location.getJ()] = p;
                    searchSize++;
                }

            }

        }
        if (endNode == null)
            throw new IllegalArgumentException("Something went horribly wrong..."); //should never happen
        endNode.use();
    }

    //helper function checks to see if we've found the end
    private boolean isEnd(Coord current) {
        return current.equals(end);
    }

    public boolean foundPath() {
        return pathFound;
    }

    public float getPathCost() {
        if (endNode != null) return endNode.cost;
        return -1;
    }

    public int getSearchSize() {
        return searchSize;
    }

    public Iterable<Coord> getPathSolution() {

        PFNode path = endNode;
        Stack<Coord> s = new Stack<>();
        s.push(path.location);
        while (path.previous != null) {
            s.push(path.previous.location);
            path = path.previous;
            path.use();
        }
        return s;
    }

    public boolean wasSearched(Coord loc) {
        return visitedNodes[loc.getI()][loc.getJ()] != null;
    }
}
