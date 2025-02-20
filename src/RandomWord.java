import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int length = 0;
        String champion = "";
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            if (StdRandom.bernoulli((double) 1 / ++length)) {
                champion = input;
            }
        }
        System.out.println(champion);
    }
}
