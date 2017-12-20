/**
 * The following code is *mostly* a copy of Quick class (quick sort) from algs4.jar
 */

public class QuickSortMedian3 {

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

    // returns index of median element among a[i0], a[i1], and a[i2]
    // note: exchanges are allowed
    public static int median3(Comparable[] a, int i0, int i1, int i2) {
        Comparable[] toSort = {a[i0], a[i1], a[i2]};
        for (int i = 0; i < 3; i++) {
            int toSwap = i;
            for (int j = i + 1; j < 3; j++) {
                if (less(toSort[j], toSort[toSwap])) {
                    toSwap = j;
                }
            }
            exch(toSort, i, toSwap);
        }

        a[i0] = toSort[0];
        a[i1] = toSort[1];
        a[i2] = toSort[2];
        return i1;
    }

    // returns index of (approximate) median element between a[lo] to a[hi]
    private static int median(Comparable[] a, int lo, int hi) {
        if (hi - lo + 1 < 3) return lo;
        int i0 = StdRandom.uniform(lo, hi + 1);
        int i1 = StdRandom.uniform(lo, hi + 1);
        int i2 = StdRandom.uniform(lo, hi + 1);
        return median3(a, i0, i1, i2);
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
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    public static Integer[] newArray(int size) {
        Integer[] a = new Integer[size];
        for (int i = 0; i < a.length; i++) {
            a[i] = i+1;
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
        Comparable[] array = newArray(1000);
        Stopwatch s = new Stopwatch();
        sort(array);
        System.out.println(s.elapsedTime());
        //for(int i=0; i<array.length; i++) StdOut.println(array[i]);
    }
}
