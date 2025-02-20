import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] queue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int index = 0; index < size; index++) {
            copy[index] = queue[index];
        }
        queue = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        queue[size++] = item;
        if (size == queue.length) {
            resize(2 * queue.length);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniformInt(size);
        Item item = queue[random];
        queue[random] = queue[--size];
        queue[size] = null;
        if (size > 0 && size == queue.length / 4) {
            resize(queue.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniformInt(size);
        return  queue[random];
    }

    private class RandomizedQequeIterator implements Iterator<Item> {
        private int count = size;
        private final int[] order;

        public RandomizedQequeIterator() {
            order = new int[count];
            for (int index = 0; index < count; index++) {
                order[index] = index;
            }
            StdRandom.shuffle(order);
        }

        public boolean hasNext() {
            return count > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw  new NoSuchElementException();
            }
            return queue[order[--count]];
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> myRandomizedQueue = new RandomizedQueue<>();
        myRandomizedQueue.enqueue("First");
        myRandomizedQueue.enqueue("Second");
        myRandomizedQueue.enqueue("Third");
        StdOut.println("Queue size is - " + myRandomizedQueue.size());
        StdOut.println("Queue is not empty - " + !myRandomizedQueue.isEmpty());
        StdOut.println("Random sample - " + myRandomizedQueue.sample());
        StdOut.println("Random item removed - " + myRandomizedQueue.dequeue());
    }
}
