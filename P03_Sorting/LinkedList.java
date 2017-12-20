import sun.awt.image.ImageWatched;

import java.util.Iterator;

public class LinkedList<Item extends Comparable<Item>> implements Iterable<Item> {
    private Node<Item> first = null;
    private int count = 0;

    private class Node<Item> {
        Item item;
        Node<Item> next;

        public Node(Item i, Node n) {
            item = i;
            next = n;
        }
    }

    public LinkedList() {
    }

    public void prepend(Item item) {
        first = new Node<Item>(item, first);
        count++;
    }

    public Item remove() {
        Item item = first.item;
        first = first.next;
        count--;
        return item;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            Node<Item> current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                Item item = current.item;
                current = current.next;
                return item;
            }
        };
    }

    public boolean isSorted() {
        Node<Item> i = first;
        while (i.next != null) {
            if (less(i.next.item, i.item)) return false;
        }
        return true;
    }

    /***********************************************************************
     * Rearranges the linked list in ascending order, using the natural order
     * and mergesort.
     ***********************************************************************/

    public void sort() {
        if (count == 1 || count == 0) return;

        Node<Item> //Nodes to use for pointers
                last = first,
                lo1 = last,
                hi1 = lo1,
                lo2,
                hi2,
                remaining;

        while (true) {
            //find first run
            hi1 = findRun(lo1);

            //list is sorted because one run made it all the way
            if (lo1 == first && hi1.next == null) break;

            //Only one run, not from start of list
            if(hi1.next == null) {
                lo1 = first;
                continue;
            }

            //Assign second run nodes
            lo2 = hi1.next;
            //find second run
            hi2 = findRun(lo2);

            //Remove pointers from ends
            remaining = hi2.next;
            hi1.next = null;
            hi2.next = null;

            //set last if starting from the first in the linked list
            if(lo1 == first) {
                last = lo1;
            }

            //set last to last item merged
            last = merge(last, lo2);
            //last = merge(last, hi1, lo2, hi2);

            //set remaining
            last.next = remaining;

            //restart runs at start if we've reached the end
            if(remaining == null) {
                last = first;
                lo1  = last;
            } else {
                //otherwise, start runs from next value
                lo1 = remaining;
            }
        }
    }

    /**
     * Finds a run based on a starting node
     *
     * @param start node to start run from
     * @return return last node in the run
     */
    private Node<Item> findRun(Node<Item> start) {
        Node<Item> toReturn = start;
        while(toReturn.next != null && less(toReturn.item, toReturn.next.item)) toReturn = toReturn.next;
        return toReturn;
    }

    /**
     * Efficient Merge
     * @param lo1 First run element
     * @param lo2 Second run element
     * @return Last element in merged run
     */
    private Node<Item> merge(Node<Item> lo1, Node<Item> lo2) {
        Node<Item> toReturn;
        while(true) {
            /*
            Ending condition: first pointer has reached the end of the first array, then merge rest of second to first
             */
            if(lo1.next == null) { //reached the end of the run
                if (lo2 != null) { //If there are more values in the second run...
                    lo1.next = lo2; //... join the runs...
                    while(lo2.next != null) lo2 = lo2.next;
                    toReturn = lo2; //... and return the last value
                } else {
                    toReturn = lo1; //OTHERWISE: the last value will be lo1/hi1
                }
                break;
            } else if(lo2 == null) {
                while(lo1.next != null) lo1 = lo1.next;
                toReturn = lo2;
            }

            /*
            Runs if item is the first of the entire linked list and needs replaced
             */
            if(lo1 == first && less(lo2.item, lo1.item)) {
                Node<Item> temp = lo2;
                lo2             = lo2.next;
                temp.next       = lo1;
                lo1             = temp;
                first           = lo1;
                continue; //don't iterate, next item in second run may still be less than the first
            }

            //runs until our first pointer has reached the end of the first run
            if(lo1.next == null) {
                //add second run if needed (end of run is hi2)
                if(lo2 != null) {
                    lo1.next = lo2;
                    while(lo2.next != null) lo2 = lo2.next;
                    toReturn = lo2;
                } else {
                    //otherwise, the end of the run is hi1
                    while(lo1.next != null) lo1 = lo1.next;
                    toReturn = lo1;
                }
                break;
            }

            /*
            runs if the next item in the first run is less than the first item in the second run
             */
            else if(less(lo2.item, lo1.next.item)) {
                Node<Item> temp = lo2;
                lo2             = lo2.next;
                temp.next       = lo1.next;
                lo1.next        = temp;
            }

            //iterate regardless
            lo1 = lo1.next; //iterate first pointer
        }

        return toReturn;
    }

    private Node<Item> merge(Node<Item> lo1, Node<Item> hi1, Node<Item> lo2, Node<Item> hi2) {
        Node<Item> toReturn;
        while(true) {
            /*
            Ending condition: first pointer has reached the end of the first array, then merge rest of second to first
             */
            if(lo1 == null) { //reached the end of the run
                if (lo2 != null) { //If there are more values in the second run...
                    lo1.next = lo2; //... join the runs...
                    toReturn = hi2; //... and return the last value
                } else {
                    toReturn = hi1; //OTHERWISE: the last value will be lo1/hi1
                }
                break;
            } else if(lo2 == null) return hi1;

            /*
            Runs if item is the first of the entire linked list and needs replaced
             */
            if(lo1 == first && less(lo2.item, lo1.item)) {
                Node<Item> temp = lo2;
                lo2             = lo2.next;
                temp.next       = lo1;
                lo1             = temp;
                first           = lo1;
                continue; //don't iterate, next item in second run may still be less than the first
            }

            //runs until our first pointer has reached the end of the first run
            if(lo1 == hi1) {
                //add second run if needed (end of run is hi2)
                if(lo2 != null) {
                    hi1.next = lo2;
                    toReturn = hi2;
                } else {
                    //otherwise, the end of the run is hi1
                    toReturn = hi1;
                }
                break;
            }

            /*
            runs if the next item in the first run is less than the first item in the second run
             */
            else if(less(lo2.item, lo1.next.item)) {
                Node<Item> temp = lo2;
                lo2             = lo2.next;
                temp.next       = lo1.next;
                lo1.next        = temp;
            }

            //iterate regardless
            lo1 = lo1.next; //iterate first pointer
        }

        return toReturn;
    }

    private boolean less(Item i, Item j) {
        //StdOut.print("\n" + i.toString() + ":" + j.toString()); //print out comparisons
        return i.compareTo(j) < 0 || i.compareTo(j) == 0;
    }

    /***********************************************************************
     *  main() function
     *  Place all of your unit tests here
     *  Hint: created additional functions to help organize your tests
     ***********************************************************************/

    public static void main(String[] args) {
        Integer[] k = new Integer[50];
        for(int i = k.length; i > 0; i--) {
            k[i-1] = i;
        }
        StdRandom.shuffle(k);
        LinkedList<Integer> l = new LinkedList<>();
        for(int i = 0; i < k.length; i++) {
            l.prepend(k[i]);
        }
        //LinkedList<Integer> l = new LinkedList<>();
        l.prepend(1);
        l.prepend(1);
        l.sort();
        Iterator<Integer> i = l.iterator();
        while(i.hasNext()) StdOut.print(i.next().toString() + " ");

    }

}
