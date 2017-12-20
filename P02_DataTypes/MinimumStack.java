import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MinimumStack<Item extends Comparable> implements Iterable<Item> {
    private Node minStackTop, normalStackTop;
    private int size;

    private class Node {
        Item item;
        Node next;
    }

    public MinimumStack() {
        size = 0;
    }

    // returns the number of items stored
    public int size() {
        return size;
    }

    // returns true iff empty
    private boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Pushes item onto stack
     *
     * @param item Item to be added
     */
    public void push(Item item) {
        Node normNode        = new Node();
        normNode.item        = item;
        if (isEmpty()) {
            normalStackTop   = normNode;
            minStackTop      = normNode;
        } else {
            Node minNode = new Node();
            normNode.next    = normalStackTop;
            normalStackTop   = normNode;
            if (item.compareTo(minStackTop.item) == -1) {
                minNode.item = item;
                minNode.next = minStackTop;
                minStackTop  = minNode;
            } else {
                minNode.item = minStackTop.item;
                minNode.next = minStackTop;
                minStackTop  = minNode;
            }
        }
        ++size;
    }

    // pop and return the top item
    public Item pop() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Error: the stack is empty");
        }
        Item top       = normalStackTop.item;
        normalStackTop = normalStackTop.next;
        minStackTop    = minStackTop.next;
        --size;
        return top;
    }

    // returns the minimum item in constant time
    public Item minimum() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("There is no minimum element");
        }
        return minStackTop.item;
    }

    // returns new Iterator<Item> that iterates over items
    @Override
    public Iterator<Item> iterator() {
        Iterator<Item> i = new Iterator<Item>() {
            private Node current = normalStackTop;
            private final int currentSize = size();
            private int iterations = currentSize;

            @Override
            public boolean hasNext() {
                return iterations > 0;
            }

            @Override
            public Item next() {
                validate();
                if (hasNext()) {
                    Item toReturn = current.item;
                    current       = current.next;
                    iterations--;
                    return toReturn;
                }
                //all other cases:
                throw new NoSuchElementException("The iterator has finished iterating");
            }

            private void validate() {
                if (currentSize != size()) {
                    throw new ConcurrentModificationException("The size of the queue has been modified.");
                }
            }
        };
        return i;
    }

    public static void main(String[] args) {
        MinimumStack<Integer> m = new MinimumStack<>();
        StdOut.println("Starting Tests...");
        //add and remove one item
        assert(m.size() == 0);
        m.push(5);
        assert(m.size() > 0);
        assert(m.minimum() == 5);
        assert(m.pop() == 5);
        assert(m.size() == 0);

        //Test minimum tracking
        m.push(5);
        assert(m.minimum() == 5);
        m.push(6);
        assert(m.minimum() == 5);
        m.push(3);
        assert(m.minimum() == 3);
        m.push(1);
        assert(m.minimum() == 1);

        //Test iterator function
        Iterator<Integer> i = m.iterator();
        assert(i.next() == 1);
        assert(i.next() == 3);
        assert(i.next() == 6);
        assert(i.next() == 5);


        m.pop();
        assert(m.minimum() == 3);
        m.pop();
        assert(m.minimum() == 5);
        m.pop();
        assert(m.minimum() == 5);
        m.pop();

        StdOut.println("Tests Complete.");
    }
}
