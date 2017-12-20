import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Steque<Item> implements Iterable<Item> {
    private Node enqueueNode, pushPopNode;
    private int size;

    private class Node {
        Item item;
        Node next;
    }

    public Steque() {
        size = 0;
    }

    // returns the number of items stored
    public int size() {
        return size;
    }

    // returns true iff steque is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // enqueues item to bottom of steque
    public void enqueue(Item item) {
        Node newNode         = new Node();
        newNode.item         = item;
        if (isEmpty()) {
            pushPopNode      = newNode;
            enqueueNode      = newNode;
        } else {
            enqueueNode.next = newNode;
            enqueueNode      = newNode;
        }
        ++size;
    }

    // pushes item to top of steque
    public void push(Item item) {
        Node newNode     = new Node();
        newNode.item     = item;
        if (isEmpty()) {
            pushPopNode  = newNode;
            enqueueNode  = newNode;
        } else {
            newNode.next = pushPopNode;
            pushPopNode  = newNode;
        }
        ++size;
    }

    // pops and returns top item
    public Item pop() throws NoSuchElementException {
        if (!isEmpty()) {
            Item popItem = pushPopNode.item;
            if(enqueueNode == pushPopNode) { //Case for one item left
                pushPopNode = null;
                enqueueNode = null;
            } else {
                pushPopNode = pushPopNode.next;
            }
            --size;
            return popItem;
        }
        throw new NoSuchElementException("There are no elements in the list");
    }

    // returns new Iterator<Item> that iterates over items in steque
    @Override
    public Iterator<Item> iterator() {
        Iterator<Item> i = new Iterator<Item>() {
            private Node current          = pushPopNode;
            private final int currentSize = size();
            private int numIterations     = currentSize;

            @Override
            public boolean hasNext() { return numIterations > 0; }

            @Override
            public Item next() {
                validate();
                if(hasNext()) {
                    Item i  = current.item;
                    current = current.next;
                    --numIterations;
                    return i;
                }
                else if(current == enqueueNode && numIterations == 1) { //redundant comparison, but I wanted to make sure these worked
                    --numIterations;
                    return current.item;
                }
                //all other cases:
                throw new NoSuchElementException("The iterator has finished iterating");
            }

            private void validate() {
                if (currentSize != size()) {
                    throw new ConcurrentModificationException("Error: the size of the Steque has changed since initialization");
                }
            }
        };
        return i;
    }

    // perform unit testing here
    public static void main(String[] args) throws NoSuchElementException {
        StdOut.println("Starting Tests...");
        Steque<String> s = new Steque<>();
        assert(s.size() == 0);
        s.enqueue("o");
        assert(s.pushPopNode == s.enqueueNode);
        assert(s.size() > 0);
        assert(s.pop() == "o");
        assert(s.size() == 0);

        //Test that enqueueing returns the same order: A, B, C
        s.enqueue("A");
        s.enqueue("B");
        s.enqueue("C");
        assert(s.pushPopNode != s.enqueueNode);
        assert(s.size() > 0);
        assert(s.pop() == "A");
        assert(s.pop() == "B");
        assert(s.pop() == "C");
        assert(s.size() == 0);

        //Test that pushing returns the same order: A, B, C
        s.push("A");
        s.push("B");
        s.push("C");
        assert(s.size() > 0);
        assert(s.pop() == "C");
        assert(s.pop() == "B");
        assert(s.pop() == "A");
        assert(s.size() == 0);

        //Test alternating add methods produces "A B C D" (Result on lines 159-162)
        s.push("C");
        s.push("B");
        s.enqueue("D");
        s.push("A");
        s.enqueue("E");
        s.enqueue("F");
        assert(s.size() > 0);

        //Test Iterator function
        Iterator<String> i = s.iterator();
        assert(i.next() == "A");
        assert(i.next() == "B");
        assert(i.next() == "C");
        assert(i.next() == "D");
        assert(i.next() == "E");
        assert(i.next() == "F");

        //Make sure items pop in correct order
        assert(s.pop() == "A");
        assert(s.pop() == "B");
        assert(s.pop() == "C");
        assert(s.pop() == "D");
        assert(s.pop() == "E");
        assert(s.pop() == "F");
        assert(s.size() == 0);

        StdOut.println("Tests Complete.");

    }
}