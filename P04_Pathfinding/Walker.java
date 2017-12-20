import java.util.Iterator;

/**
 * Walker takes an Iterable of Coords and simulates an individual
 * walking along the path over the given Terrain
 */
public class Walker {

    private Terrain terrain;
    private Stack<Coord> path;
    private Coord current;
    private int step;

    // terrain: the Terrain the Walker traverses
    // path: the sequence of Coords the Walker follows
    public Walker(Terrain terrain, Iterable<Coord> path) {
        this.terrain = terrain;
        this.path = new Stack<>();
        this.step = 0;
        for(Coord c : path) {
            this.path.push(c);
        }
        current = this.path.pop();
    }

    // returns the Walker's current location
    public Coord getLocation() {
        return this.current;
    }

    // returns true if Walker has reached the end Coord (last in path)
    public boolean doneWalking() {
        return this.path.size() < 1;
    }

    // advances the Walker along path
    // byTime: how long the Walker should traverse (may be any non-negative value)
    public void advance(float byTime) {
        float distanceTravelled = 0;
        Coord next;
        while(distanceTravelled < byTime && !doneWalking()) {
            next = this.path.pop();
            distanceTravelled += this.terrain.computeTravelCost(current, next);
            this.current = next;
        }
    }

}
