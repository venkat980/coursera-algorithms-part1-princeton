import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSites;
    private final boolean[][] grid;
    private final int top;
    private final int bottom;
    private final int length;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf2;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("The grid should be at least of size 1");
        }
        grid = new boolean[n][n];
        openSites = 0;
        top = n * n;
        bottom = top + 1;
        length = n;
        uf = new WeightedQuickUnionUF(top + 2);
        uf2 = new WeightedQuickUnionUF(bottom);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateInput(row, col);
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            openSites++;
        }
        if (row == 1) {
            uf.union(col - 1, top);
            uf2.union(col - 1, top);
        }
        else if (row == length) {
            uf.union((row - 1) * length + col - 1, bottom);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union((row - 1) * length + col - 1, (row - 2) * length + col - 1);
            uf2.union((row - 1) * length + col - 1, (row - 2) * length + col - 1);
        }
        if (row < length && isOpen(row + 1, col)) {
            uf.union((row - 1) * length + col - 1, row  * length + col - 1);
            uf2.union((row - 1) * length + col - 1, row * length + col - 1);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union((row - 1) * length + col - 1, (row - 1) * length + col - 2);
            uf2.union((row - 1) * length + col - 1, (row - 1) * length + col - 2);
        }
        if (col < length && isOpen(row, col + 1)) {
            uf.union((row - 1) * length + col - 1, (row - 1) * length + col);
            uf2.union((row - 1) * length + col - 1, (row - 1) * length + col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateInput(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateInput(row, col);
        return uf2.find((row - 1) * length + col - 1) == uf2.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (length == 1 && openSites == 1) {
            return true;
        }
        return uf.find(top) == uf.find(bottom);
    }

    private void validateInput(int row, int col) {
        if (row < 1 || row > length || col < 1 || col > length) {
            throw new IllegalArgumentException();
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation pc = new Percolation(4);
        StdOut.println(pc.isOpen(2, 2));
        StdOut.println(pc.isFull(2, 2));
        pc.open(2, 2);
        StdOut.println(pc.isOpen(2, 2));
        StdOut.println(pc.isFull(2, 2));
        StdOut.println("Number of open sites = " + pc.numberOfOpenSites());
        StdOut.println(pc.percolates());
        pc.open(1, 2);
        StdOut.println(pc.isFull(2, 2));
        pc.open(3, 2);
        pc.open(4, 2);
        StdOut.println("Number of open sites = " + pc.numberOfOpenSites());
        StdOut.println(pc.percolates());
    }
}
