package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int numSites = 0; /* the number of open sites */
    private int size; /* the length of a size of the square */
    private boolean[][] openStatus;

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
        top = N * N;
        bottom = N * N + 1;
    }

    private int xyTo1D(int row, int col) {
        return row * size + col;
    }

    private void checkBound(int row, int col) {
        if (row > size - 1 || col > size - 1 || row < 0 || col < 0) {
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

        if (row == 0) {
            uf.union(pos1D, top);
            ufButTheBottom.union(pos1D, top);
        }

        if (row == size - 1) {
            uf.union(pos1D, bottom);
        }

        if (col > 0 && isOpen(row, col - 1)) {
            int neighborLeft = pos1D - 1;
            uf.union(neighborLeft, pos1D);
            ufButTheBottom.union(neighborLeft, pos1D);
        }
        if (col < size - 1 && isOpen(row, col + 1)) {
            int neighborRight = pos1D + 1;
            uf.union(neighborRight, pos1D);
            ufButTheBottom.union(neighborRight, pos1D);
        }
        if (row > 0 && isOpen(row - 1, col)) {
            int neighborUp = pos1D - size;
            uf.union(neighborUp, pos1D);
            ufButTheBottom.union(neighborUp, pos1D);
        }
        if (row < size - 1 && isOpen(row + 1, col)) {
            int neighborDown = pos1D + size;
            uf.union(neighborDown, pos1D);
            ufButTheBottom.union(neighborDown, pos1D);
        }
    }

    public boolean isOpen(int row, int col) {
        checkBound(row, col);
        return openStatus[row][col];
    }

    public boolean isFull(int row, int col) {
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

    public static void main(String[] args) {

    }
}
