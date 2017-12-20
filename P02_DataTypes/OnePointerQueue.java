import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OnePointerQueue<Item> implements Iterable<Item> {
    private Node last;
    private int size;

    public OnePointerQueue() {
        size = 0;
        last = null;
    }

    private class Node {
        Item item;
        Node next;
    }

    // returns the number of items stored
    public int size() {
        return size;
    }

    // returns true iff empty
    public boolean isEmpty() {
        return size() == 0;
    }

    // enqueue item to "back"
    public void enqueue(Item item) {
        if (isEmpty()) {
            last      = new Node();
            last.item = item;
            last.next = last; // handle special case
        } else {
            Node oldLast = last;
            last         = new Node();
            last.item    = item;
            last.next    = oldLast.next;
            oldLast.next = last;       // for empty queue
        }
        ++size;
    }

    // dequeue item from "front"
    public Item dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("You can't dequeue, the queue is empty.");
        }

        //Case for one entry
        if (last.next == last) {
            Item item = last.item;
            last   = null;
            --size;
            return item;
        }

        //all other cases:
        Item item = last.next.item;
        last.next = last.next.next;
        if (isEmpty()) {
            last = null;      // handle special case...
        }
        --size;
        return item;
    }

    // returns new Iterator<Item> that iterates over items
    @Override
    public Iterator<Item> iterator() {
        Iterator<Item> i = new Iterator<Item>() {
            Node current          = last.next;
            final int currentSize = size();
            int iterations        = currentSize;

            @Override
            public boolean hasNext() {
                return iterations > 0;
            }

            @Override
            public Item next() {
                validate();
                if (hasNext()) {
                    Item i  = current.item;
                    current = current.next;
                    --iterations;
                    return i;
                }
                //all other cases:
                throw new NoSuchElementException("The iterator has finished iterating");
            }

            /**
             * Helper method to validate that queue size has not changed
             */
            private void validate() {
                if (currentSize != size()) {
                    throw new ConcurrentModificationException("The size of the queue has been modified.");
                }
            }
        };
        return i;

    }

    // perform unit testing here
    public static void main(String[] args) {
        OnePointerQueue<Integer> o = new OnePointerQueue<>();
        StdOut.println("Starting Tests...");
        // Test changing size and that adding/removing works correctly
        assert(o.size() == 0);
        o.enqueue(1);
        assert(o.size() > 0);
        assert(o.dequeue() == 1);
        assert(o.size() == 0);
        assert(o.last == null); //pointer object should be null

        //Test adding and removing multiple elements.
        o.enqueue(2);
        o.enqueue(3);
        o.enqueue(4);
        o.enqueue(5);
        o.enqueue(6);
        assert(o.size() == 5); //5 elements in queue now

        //Test iterator
        Iterator<Integer> i = o.iterator();
        assert(i.next() == 2);
        assert(i.next() == 3);
        assert(i.next() == 4);
        assert(i.next() == 5);
        assert(i.next() == 6);

        //Make sure items are dequeueing in correct order
        assert(o.dequeue() == 2);
        assert(o.dequeue() == 3);
        assert(o.dequeue() == 4);
        assert(o.dequeue() == 5);
        assert(o.dequeue() == 6);
        assert(o.last == null); //pointer object should be null


        StdOut.println("Tests Complete.");
    }
}
