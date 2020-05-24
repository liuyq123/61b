package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int numSites = 0; /* the number of open sites */
    int size; /* the length of a size of the square */
    boolean[][] openStatus;

    /* to prevent backwash */
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufButTheBottom; /* the bottom node is not included */

    private int top; /* virtual top node */
    private int bottom; /* virtual bottom node */

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        size = N;
        openStatus = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufButTheBottom = new WeightedQuickUnionUF(N * N + 1);
        top = -1;
        bottom = N * N + 1;
    }

    private int xyTo1D(int row, int col) {
        return row * size + col + 1;
    }

    private void checkBound(int row, int col) {
        if (row > size || col > size || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void open(int row, int col) {
        checkBound(row, col);

        if (!isOpen(row, col)) {
            openStatus[row][col] = true;
            numSites++;
        }

        int pos1D = xyTo1D(row, col);
        if (col > 0) {
            int neighborLeft = pos1D - 1;
            uf.union(neighborLeft, pos1D);
            ufButTheBottom.union(neighborLeft, pos1D);
        }
        if (col < size - 1) {
            int neighborRight = pos1D + 1;
            uf.union(neighborRight, pos1D);
            ufButTheBottom.union(neighborRight, pos1D);
        }
        if (row > 0) {
            int neighborUp = pos1D - size;
            uf.union(neighborUp, pos1D);
            ufButTheBottom.union(neighborUp, pos1D);
        }
        if (row < size - 1) {
            int neighborDown = pos1D + size;
            uf.union(neighborDown, pos1D);
            ufButTheBottom.union(neighborDown, pos1D);
        }
    }

    boolean isOpen(int row, int col) {
        checkBound(row, col);
        return openStatus[row][col];
    }

    boolean isFull(int row, int col) {
        checkBound(row, col);
        int pos1D = xyTo1D(row, col);
        return ufButTheBottom.connected(pos1D, top);
    }

    public int numberOfOpenSites() {
        return numSites;
    }

    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    public static void main (String[] args) {

    }
}
