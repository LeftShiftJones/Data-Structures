import java.nio.channels.SelectableChannel;
import java.text.DecimalFormat;

public class DoublingTest {
    /***********************************************************************
     *  main() function
     *  Place all of your unit tests here
     *  Hint: created additional functions to help organize your tests
     ***********************************************************************/

    public static Integer[] newArray(int size) {
        Integer[] a = new Integer[size];
        for (int i = 0; i < a.length; i++) {
            a[i] = StdRandom.uniform(0, 100000000);
        }
        return a;
    }

    public static void main(String[] args) {
        int size = 1000;
        mergeDoublingTests(size);
        StdOut.println();
        quickDoublingTests(size);
        StdOut.println();
        shellDoublingTests(size);
        StdOut.println();
        quickMed3DoublingTests(size);
        StdOut.println();
        quickMed5DoublingTests(size);
        StdOut.println();
        /*
        bubbleDoublingTests(size);
        StdOut.println();
        insertionDoublingTests(size);
        StdOut.println();
        selectionDoublingTests(size);
        */
    }

    private static void quickMed3DoublingTests(int N) {
        StdOut.println("Quick Sort3:");
        Integer[] b;
        int size = N;
        double ratioAvg = 0.0;
        for (int i = 0; i < 10; i++) {
            b = newArray(size);
            Stopwatch s = new Stopwatch();
            QuickSortMedian3.sort(b);
            double time = s.elapsedTime();
            StdOut.printf("Size: %7d   Time:%7.3f\n", size, time);
            ratioAvg += (time);
            size *= 2;
        }
        StdOut.printf("Average ratio: %7.4e\n", (ratioAvg / 10));
    }

    private static void quickMed5DoublingTests(int N) {
        StdOut.println("Quick Sort5:");
        Integer[] b;
        int size = N;
        double ratioAvg = 0.0;
        for (int i = 0; i < 10; i++) {
            b = newArray(size);
            Stopwatch s = new Stopwatch();
            QuickSortMedian5.sort(b);
            double time = s.elapsedTime();
            StdOut.printf("Size: %7d   Time:%7.3f\n", size, time);
            ratioAvg += (time);
            size *= 2;
        }
        StdOut.printf("Average ratio: %7.4e\n", (ratioAvg / 10));
    }

    private static void quickDoublingTests(int N) {
        StdOut.println("Quick Sort:");
        Integer[] b;
        int size = N;
        double ratioAvg = 0.0;
        for (int i = 0; i < 10; i++) {
            b = newArray(size);
            Stopwatch s = new Stopwatch();
            Quick.sort(b);
            double time = s.elapsedTime();
            StdOut.printf("Size: %7d   Time:%7.3f\n", size, time);
            ratioAvg += (time);
            size *= 2;
        }
        StdOut.printf("Average ratio: %7.4e\n", (ratioAvg / 10));
    }

    /**
     * Doubling test for Shell sort
     */
    private static void shellDoublingTests(int N) {
        StdOut.println("Shell Sort:");
        Integer[] b;
        int size = N;
        double ratioAvg = 0.0;
        for (int i = 0; i < 10; i++) {
            b = newArray(size);
            Stopwatch s = new Stopwatch();
            Shell.sort(b);
            double time = s.elapsedTime();
            StdOut.printf("Size: %7d   Time:%7.3f\n", size, time);
            ratioAvg += (time);
            size *= 2;
        }
        StdOut.printf("Average ratio: %7.4e\n", (ratioAvg / 10));
    }

    /**
     * Doubling test for Insertion Sort
     */
    private static void insertionDoublingTests(int N) {
        StdOut.println("Insertion Sort:");
        Integer[] b;
        int size = N;
        double ratioAvg = 0.0;
        for (int i = 0; i < 10; i++) {
            b = newArray(size);
            Stopwatch s = new Stopwatch();
            Insertion.sort(b);
            double time = s.elapsedTime();
            StdOut.printf("Size: %7d   Time:%7.3f\n", size, time);
            ratioAvg += (time);
            size *= 2;
        }
        StdOut.printf("Average ratio: %7.4e\n", (ratioAvg / 10));
    }

    /**
     * Doubling test for Selection Sort
     */
    private static void selectionDoublingTests(int N) {
        StdOut.println("Selection Sort:");
        Integer[] b;
        int size = N;
        double ratioAvg = 0.0;
        for (int i = 0; i < 10; i++) {
            b = newArray(size);
            Stopwatch s = new Stopwatch();
            Selection.sort(b);
            double time = s.elapsedTime();
            StdOut.printf("Size: %7d   Time:%7.3f\n", size, time);
            ratioAvg += (time);
            size *= 2;
        }
        StdOut.printf("Average ratio: %7.4e\n", (ratioAvg / 10));
    }

    /**
     * Doubling test for Merge Sort
     */
    private static void mergeDoublingTests(int N) {
        StdOut.println("Merge Sort:");
        Integer[] b;
        int size = N;
        double ratioAvg = 0.0;
        for (int i = 0; i < 10; i++) {
            b = newArray(size);
            Stopwatch s = new Stopwatch();
            Merge.sort(b);
            double time = s.elapsedTime();
            StdOut.printf("Size: %7d   Time:%7.3f\n", size, time);
            ratioAvg += (time);
            size *= 2;
        }
            StdOut.printf("Average ratio: %7.4e\n", (ratioAvg / 10));
    }

    /**
     * Doubling test for Merge Sort
     */
    private static void bubbleDoublingTests(int N) {
        StdOut.println("Bubble Sort:");
        Integer[] b;
        int size = N;
        double ratioAvg = 0.0;
        for (int i = 0; i < 10; i++) {
            b = newArray(size);
            Stopwatch s = new Stopwatch();
            Bubble.sort(b);
            double time = s.elapsedTime();
            StdOut.printf("Size: %7d   Time:%7.3f\n", size, time);
            ratioAvg += (time);
            size *= 2;
        }
        StdOut.printf("Average ratio: %7.4e\n", (ratioAvg / 10));
    }

    /**
     * Asserts that array is sorted
     *
     * @param a array to check
     * @return boolean of array sorting
     */
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }

    /**
     * Returns value of _this_ being less than the _other_
     *
     * @param a this
     * @param b other
     * @return this < other
     */
    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }
}
