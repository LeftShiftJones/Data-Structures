public class EdgeCases {
    /***********************************************************************
     *  main() function
     *  Place all of your unit tests here
     *  Hint: created additional functions to help organize your tests
     ***********************************************************************/

    public static void main(String[] args) {
        //array types
        Integer[] sorted = sortedEdgeCases(10000);
        Integer[] mostlySorted = mostlySortedEdgeCases(10000);
        Integer[] reversed = reversedEdgeCase(10000);
        Integer[] oneKey = oneKeyEdgeCase(10000);
        Integer[] twoDistinct = twoDistinctEdgeCase(10000);
        Integer[] zeroArray = zeroArrayEdgeCase();
        Integer[] oneArray = oneArrayEdgeCase();
        Integer[] organArray = organArray(100);
        Integer[] reverseOrganArray = reverseOrganArray(100);


        //copies of arrays
        Integer[] sortedCopy = sorted;
        Integer[] mostlySortedCopy = mostlySorted;
        Integer[] reversedCopy = reversed;
        Integer[] oneKeyCopy = oneKey;
        Integer[] twoDistinctCopy = twoDistinct;
        Integer[] zeroCopy = zeroArray;
        Integer[] oneArrayCopy = oneArray;
        Integer[] organArrayCopy = organArray;
        Integer[] reverseOrganArrayCopy = reverseOrganArray;


        // bubble sort
        Bubble.sort(sortedCopy);
        Bubble.sort(mostlySortedCopy);
        Bubble.sort(reversedCopy);
        Bubble.sort(oneKeyCopy);
        Bubble.sort(twoDistinctCopy);
        Bubble.sort(zeroCopy);
        Bubble.sort(oneArrayCopy);
        Bubble.sort(organArrayCopy);
        Bubble.sort(reverseOrganArrayCopy);

        //assert that arrays are sorted
        StdOut.println("\nChecking Bubblesort...");
        if (!isSorted(sortedCopy)) StdOut.println("Sorted was not sorted");
        if (!isSorted(mostlySortedCopy)) StdOut.println("Mostly Sorted was not sorted");
        if (!isSorted(reversedCopy)) StdOut.println("Reversed was not sorted");
        if (!isSorted(oneKeyCopy)) StdOut.println("One Key was not sorted");
        if (!isSorted(twoDistinctCopy)) StdOut.println("Two Distinct was not sorted");
        if (!isSorted(zeroCopy)) StdOut.println("Zero Items was not sorted");
        if (!isSorted(oneArrayCopy)) StdOut.println("One Item was not sorted");
        if (!isSorted(organArrayCopy)) StdOut.println("Organ was not sorted");
        if (!isSorted(reverseOrganArrayCopy)) StdOut.println("Reverse Organ was not sorted");
        StdOut.println("Passed tests.");

        //reset array values
        sortedCopy = sorted;
        mostlySortedCopy = mostlySorted;
        reversedCopy = reversed;
        oneKeyCopy = oneKey;
        twoDistinctCopy = twoDistinct;
        zeroCopy = zeroArray;
        oneArrayCopy = oneArray;
        organArrayCopy = organArray;
        reverseOrganArrayCopy = reverseOrganArray;

        // selection sort
        Selection.sort(sortedCopy);
        Selection.sort(mostlySortedCopy);
        Selection.sort(reversedCopy);
        Selection.sort(oneKeyCopy);
        Selection.sort(twoDistinctCopy);
        Selection.sort(zeroCopy);
        Selection.sort(oneArrayCopy);
        Selection.sort(organArrayCopy);
        Selection.sort(reverseOrganArrayCopy);

        //assert that arrays are sorted
        StdOut.println("\nChecking Selection sort...");
        if (!isSorted(sortedCopy)) StdOut.println("Sorted was not sorted");
        if (!isSorted(mostlySortedCopy)) StdOut.println("Mostly Sorted was not sorted");
        if (!isSorted(reversedCopy)) StdOut.println("Reversed was not sorted");
        if (!isSorted(oneKeyCopy)) StdOut.println("One Key was not sorted");
        if (!isSorted(twoDistinctCopy)) StdOut.println("Two Distinct was not sorted");
        if (!isSorted(zeroCopy)) StdOut.println("Zero Items was not sorted");
        if (!isSorted(oneArrayCopy)) StdOut.println("One Item was not sorted");
        if (!isSorted(organArrayCopy)) StdOut.println("Organ was not sorted");
        if (!isSorted(reverseOrganArrayCopy)) StdOut.println("Reverse Organ was not sorted");
        StdOut.println("Passed tests.");

        //reset array values
        sortedCopy = sorted;
        mostlySortedCopy = mostlySorted;
        reversedCopy = reversed;
        oneKeyCopy = oneKey;
        twoDistinctCopy = twoDistinct;
        zeroCopy = zeroArray;
        oneArrayCopy = oneArray;
        organArrayCopy = organArray;
        reverseOrganArrayCopy = reverseOrganArray;

        // insertion sort
        Insertion.sort(sortedCopy);
        Insertion.sort(mostlySortedCopy);
        Insertion.sort(reversedCopy);
        Insertion.sort(oneKeyCopy);
        Insertion.sort(twoDistinctCopy);
        Insertion.sort(zeroCopy);
        Insertion.sort(oneArrayCopy);
        Insertion.sort(organArrayCopy);
        Insertion.sort(reverseOrganArrayCopy);

        //assert that arrays are sorted
        StdOut.println("\nChecking Insertion sort...");
        if (!isSorted(sortedCopy)) StdOut.println("Sorted was not sorted");
        if (!isSorted(mostlySortedCopy)) StdOut.println("Mostly Sorted was not sorted");
        if (!isSorted(reversedCopy)) StdOut.println("Reversed was not sorted");
        if (!isSorted(oneKeyCopy)) StdOut.println("One Key was not sorted");
        if (!isSorted(twoDistinctCopy)) StdOut.println("Two Distinct was not sorted");
        if (!isSorted(zeroCopy)) StdOut.println("Zero Items was not sorted");
        if (!isSorted(oneArrayCopy)) StdOut.println("One Item was not sorted");
        if (!isSorted(organArrayCopy)) StdOut.println("Organ was not sorted");
        if (!isSorted(reverseOrganArrayCopy)) StdOut.println("Reverse Organ was not sorted");
        StdOut.println("Passed tests.");

        //reset array values
        sortedCopy = sorted;
        mostlySortedCopy = mostlySorted;
        reversedCopy = reversed;
        oneKeyCopy = oneKey;
        twoDistinctCopy = twoDistinct;
        zeroCopy = zeroArray;
        oneArrayCopy = oneArray;
        organArrayCopy = organArray;
        reverseOrganArrayCopy = reverseOrganArray;


        // Shellsort
        Shell.sort(sortedCopy);
        Shell.sort(mostlySortedCopy);
        Shell.sort(reversedCopy);
        Shell.sort(oneKeyCopy);
        Shell.sort(twoDistinctCopy);
        Shell.sort(zeroCopy);
        Shell.sort(oneArrayCopy);
        Shell.sort(organArrayCopy);
        Shell.sort(reverseOrganArrayCopy);

        //assert that arrays are sorted
        StdOut.println("\nChecking Shellsort...");
        if (!isSorted(sortedCopy)) StdOut.println("Sorted was not sorted");
        if (!isSorted(mostlySortedCopy)) StdOut.println("Mostly Sorted was not sorted");
        if (!isSorted(reversedCopy)) StdOut.println("Reversed was not sorted");
        if (!isSorted(oneKeyCopy)) StdOut.println("One Key was not sorted");
        if (!isSorted(twoDistinctCopy)) StdOut.println("Two Distinct was not sorted");
        if (!isSorted(zeroCopy)) StdOut.println("Zero Items was not sorted");
        if (!isSorted(oneArrayCopy)) StdOut.println("One Item was not sorted");
        if (!isSorted(organArrayCopy)) StdOut.println("Organ was not sorted");
        if (!isSorted(reverseOrganArrayCopy)) StdOut.println("Reverse Organ was not sorted");
        StdOut.println("Passed tests.");

        //reset array values
        sortedCopy = sorted;
        mostlySortedCopy = mostlySorted;
        reversedCopy = reversed;
        oneKeyCopy = oneKey;
        twoDistinctCopy = twoDistinct;
        zeroCopy = zeroArray;
        oneArrayCopy = oneArray;
        organArrayCopy = organArray;
        reverseOrganArrayCopy = reverseOrganArray;

        // quicksort
        Quick.sort(sortedCopy);
        Quick.sort(mostlySortedCopy);
        Quick.sort(reversedCopy);
        Quick.sort(oneKeyCopy);
        Quick.sort(twoDistinctCopy);
        Quick.sort(zeroCopy);
        Quick.sort(oneArrayCopy);
        Quick.sort(organArrayCopy);
        Quick.sort(reverseOrganArrayCopy);


        //assert that arrays are sorted
        StdOut.println("\nChecking Quicksort...");
        if (!isSorted(sortedCopy)) StdOut.println("Sorted was not sorted");
        if (!isSorted(mostlySortedCopy)) StdOut.println("Mostly Sorted was not sorted");
        if (!isSorted(reversedCopy)) StdOut.println("Reversed was not sorted");
        if (!isSorted(oneKeyCopy)) StdOut.println("One Key was not sorted");
        if (!isSorted(twoDistinctCopy)) StdOut.println("Two Distinct was not sorted");
        if (!isSorted(zeroCopy)) StdOut.println("Zero Items was not sorted");
        if (!isSorted(oneArrayCopy)) StdOut.println("One Item was not sorted");
        if (!isSorted(organArrayCopy)) StdOut.println("Organ was not sorted");
        if (!isSorted(reverseOrganArrayCopy)) StdOut.println("Reverse Organ was not sorted");
        StdOut.println("Passed tests.");
    }

    /**
     * Method that creates an organ style array:
     * -------
     * -----
     * ---
     * -
     * ---
     * -----
     * -------
     * @param sizeOfArray
     * @return
     */
    private static Integer[] organArray(int sizeOfArray) {
        Integer[] toReturn = new Integer[sizeOfArray];
        for(int i = 0; i<sizeOfArray/2; i++) {
            toReturn[i] = sizeOfArray/2-i;
        }
        for(int i = sizeOfArray/2; i < sizeOfArray; i++) {
            toReturn[i] = i-sizeOfArray/2;
        }
        return toReturn;
    }

    /**
     * Method returns a reversed organ style array:
     * -
     * ---
     * -----
     * -------
     * -----
     * ---
     * -
     * @param sizeOfArray
     * @return
     */
    public static Integer[] reverseOrganArray(int sizeOfArray) {
        Integer[] toReturn = new Integer[sizeOfArray];
        for(int i = 0; i<sizeOfArray/2; i++) {
            toReturn[i] = i+1;
        }
        for(int i = sizeOfArray/2; i< sizeOfArray; i++) {
            toReturn[i] = sizeOfArray - i;
        }
        return toReturn;
    }

    /**
     * returns an array with a single value
     *
     * @return single-value array
     */
    private static Integer[] oneArrayEdgeCase() {
        Integer[] toReturn = {2};
        return toReturn;
    }

    /**
     * returns an array of size zero
     *
     * @return generated array
     */
    private static Integer[] zeroArrayEdgeCase() {
        Integer[] toReturn = new Integer[0];
        return toReturn;
    }

    /**
     * Returns an array that contains only two distinct values
     *
     * @param sizeOfArray size of array to generate
     * @return two-value array
     */
    private static Integer[] twoDistinctEdgeCase(int sizeOfArray) {
        Integer[] toReturn = new Integer[sizeOfArray];
        for (int i = 0; i < sizeOfArray; i++) {
            if (i % 2 == 0) toReturn[i] = 16;
            else toReturn[i] = 32;
        }
        return toReturn;
    }

    /**
     * Returns an array of size N where all values are the same
     *
     * @param sizeOfArray size of array to generate
     * @return single key array
     */
    private static Integer[] oneKeyEdgeCase(int sizeOfArray) {
        Integer[] toReturn = new Integer[sizeOfArray];
        for (int i = 0; i < sizeOfArray; i++) toReturn[i] = 38;
        return toReturn;
    }

    /**
     * Returns a reverse-sorted array
     *
     * @param sizeOfArray size of array to generate
     * @return reversed array
     */
    private static Integer[] reversedEdgeCase(int sizeOfArray) {
        Integer[] toReturn = new Integer[sizeOfArray];
        for (int i = sizeOfArray - 1; i >= 0; i--) toReturn[i] = i + 1;
        return toReturn;
    }

    /**
     * Returns a mostly sorted array (every 3rd entry is swapped)
     *
     * @param sizeOfArray size of array to generate
     * @return mostly sorted array
     */
    private static Integer[] mostlySortedEdgeCases(int sizeOfArray) {
        Integer[] toReturn = sortedEdgeCases(sizeOfArray);
        for (int i = 3; i < sizeOfArray; i += 3) swap(toReturn, i, i - 3);
        return toReturn;
    }

    /**
     * Returns a sorted array to test sorting algorithms
     *
     * @param sizeOfArray size of array to generate
     * @return sorted array
     */
    private static Integer[] sortedEdgeCases(int sizeOfArray) {
        Integer[] toReturn = new Integer[sizeOfArray];
        for (int i = 0; i < sizeOfArray; i++) toReturn[i] = i + 1;
        return toReturn;
    }

    /**
     * Swaps two elements in an array using a temp variable
     *
     * @param a array in which to swap
     * @param i first variable location
     * @param j second variable location
     */
    private static void swap(Integer[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * Checks to see if a _this_ is strictly less than _other_
     *
     * @param a this
     * @param b other
     * @return if this < other
     */
    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    /**
     * Method to check whether array is sorted
     *
     * @param a Comparable array to check sorted order
     * @return boolean telling whether or not aray is sorted
     */
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) if (less(a[i], a[i - 1])) return false;
        return true;
    }
}
