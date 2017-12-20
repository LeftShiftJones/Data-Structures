public class TreeTest {
    public static void main(String args[]) {

        In in = new In(args[0]);
        double[] d = in.readAllDoubles();
        PSBruteForce<Character> b = new PSBruteForce();
//        PSKDTree<Character> b = new PSKDTree<>();

        for(int i = 0; i < 100000; i+=2) {
            b.put(new Point(d[i], d[i+1]), 'x');
            //StdOut.println("t.put(new Point(" + d[i] + ", " + d[i+1] + "), 'x');" );
        }

        StdOut.println("Starting Calculations...");
        Stopwatch s = new Stopwatch();
        for(int i = 0; i< 100000; i++) {
            Point p = Point.uniform();
            b.nearest(p);
        }
        double time = s.elapsedTime();
        StdOut.println(time);
        StdOut.println((double) 100000 / time);

    }
}
