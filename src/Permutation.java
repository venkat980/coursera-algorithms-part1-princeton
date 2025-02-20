import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int length = Integer.parseInt(args[0]);
        RandomizedQueue<String> myRandomizedQueue = new RandomizedQueue<>();
        while(!StdIn.isEmpty()) {
            myRandomizedQueue.enqueue(StdIn.readString());
        }

        for (int index = 0; index < length; index++) {
            StdOut.println(myRandomizedQueue.dequeue());
        }
    }
}
