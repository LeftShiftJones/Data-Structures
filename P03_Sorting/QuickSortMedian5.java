import java.text.DecimalFormat;

/**
 * The following code is *mostly* a copy of Quick class (quick sort) from algs4.jar
 */

public class QuickSortMedian5 {

    /***********************************************************************
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     ***********************************************************************/
    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    // quicksort the subarray from a[lo] to a[hi]
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);   // partition data into parts, returning pivot index
        sort(a, lo, j - 1);               // recursively sort lower part
        sort(a, j + 1, hi);               // recursively sort higher part
    }

    public static void printOut(Comparable[] a) {
        StdOut.println();
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i].toString() + " ");
        }
    }

    // returns index of median element among a[i0], a[i1], a[i2], a[i3], and a[i4]
    // note: exchanges are allowed
    public static int median5(Comparable[] a, int i0, int i1, int i2, int i3, int i4) {
        //create new array of Comparable values
        Comparable[] sortedFive = {a[i0], a[i1], a[i2], a[i3], a[i4]};

        //i < j?
        if (!less(sortedFive[0], sortedFive[1])) exch(sortedFive, 0, 1);

        //k < l?
        if (!less(sortedFive[2], sortedFive[3])) exch(sortedFive, 2, 3);

        //j < l?
        if (!less(sortedFive[1], sortedFive[3])) {
            exch(sortedFive, 0, 2);
            exch(sortedFive, 1, 3);
        }

        //k < m?
        if (!less(sortedFive[2], sortedFive[4])) exch(sortedFive, 2, 4);

        //j < m?
        if (!less(sortedFive[1], sortedFive[4])) {
            exch(sortedFive, 1, 4);
            exch(sortedFive, 0, 2);
        }

        //j < k
        if (!less(sortedFive[1], sortedFive[2])) exch(sortedFive, 1, 2);

        //set values to ordered set and correct indices
        a[i0] = sortedFive[0];
        a[i1] = sortedFive[1];
        a[i2] = sortedFive[2];
        a[i3] = sortedFive[3];
        a[i4] = sortedFive[4];
        //Return middle index (always will be the median)

        return i2;
    }

    // returns index of (approximate) median element between a[lo] to a[hi]
    private static int median(Comparable[] a, int lo, int hi) {
        if (hi - lo + 1 < 5) return lo;
        int i0 = StdRandom.uniform(lo, hi + 1);
        int i1 = StdRandom.uniform(lo, hi + 1);
        int i2 = StdRandom.uniform(lo, hi + 1);
        int i3 = StdRandom.uniform(lo, hi + 1);
        int i4 = StdRandom.uniform(lo, hi + 1);
        return median5(a, i0, i1, i2, i3, i4);
    }

    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.  note: v = a[j]
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;

        exch(a, lo, median(a, lo, hi));
        Comparable v = a[lo];       // v is pivot value

        while (true) {

            // find item on lo to swap
            while (less(a[++i], v))
                if (i == hi) break;

            // find item on hi to swap
            while (less(v, a[--j]))
                if (j == lo) break;      // redundant since a[lo] acts as sentinel

            // check if pointers cross
            if (i >= j) break;

            exch(a, i, j);
        }

        // put partitioning item v at a[j]
        exch(a, lo, j);

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }


    /***********************************************************************
     *  Helper sorting functions
     ***********************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        if (i == j) return; //short circuit swap method if indices are the same
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static Integer[] newArray(int size) {
        Integer[] a = new Integer[size];
        for (int i = 0; i < a.length; i++) {
            a[i] = i + 1;
        }
        StdRandom.shuffle(a);
        return a;
    }

    /***********************************************************************
     *  main() function
     *  Place all of your unit tests here
     *  Hint: created additional functions to help organize your tests
     ***********************************************************************/

    public static void main(String[] args) {
        Integer[] a = newArray(5000);
        DecimalFormat df = new DecimalFormat("#.####");
        Stopwatch s = new Stopwatch();
        sort(a);
        Double time = s.elapsedTime();
        StdOut.println(df.format(time));

    }
}
