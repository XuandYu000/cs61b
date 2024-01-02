package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import static org.junit.Assert.*;

public class Percolation {
    private boolean[][] Grid;
    private int Length;
    private int count;
    private int VirtualTop;
    private int VirtualBottom;
    private WeightedQuickUnionUF DSet;
    private WeightedQuickUnionUF DSetWithoutVB;

    private static final int[] dx = {1, 0, -1, 0};
    private static final int[] dy = {0, 1, 0, -1};

    /*
    * create N-by-N grid, with all sites initially blocked
    * */
    public Percolation(int N) {
        validate(N);
        Grid = new boolean[N][N];
        Length = N;
        count = 0;
        VirtualTop = N * N;
        VirtualBottom = N * N + 1;
        DSet = new WeightedQuickUnionUF(N * N + 2);
        DSetWithoutVB = new WeightedQuickUnionUF(N * N + 1);
        for (int i = 0; i < N; i++) {
            DSet.union(VirtualBottom, ChangeToLine(N - 1, i));
        }
    }

    private void validate(int row, int col) {
       if (row < 0 || row >= Length || col < 0 || col >= Length) {
           throw new IndexOutOfBoundsException("Index row or col is not between 0 and N - 1");
       }
    }

    private void validate(int N) {
        if(N < 0) {
            throw new IllegalArgumentException("N should be positive.");
        }
    }

    private boolean cheakIn(int row, int col) {
        if(row < 0 || row >= Length || col < 0 || col >= Length) {
            return false;
        }
        return true;
    }

    private void Connect(int p, int q) {
        if(!percolates()) {
            DSet.union(p, q);
        }
        DSetWithoutVB.union(p, q);
    }

    private void UpdateUpConnection(int row, int col, int nearR, int nearC) {
        if(!cheakIn(nearR, nearC)) {
            Connect(ChangeToLine(row, col), VirtualTop);
        } else {
            if(isOpen(nearR, nearC)) {
                Connect(ChangeToLine(row, col), ChangeToLine(nearR, nearC));
            }
        }
    }

    private void UpdateOtherConnection(int row, int col, int nearR, int nearC) {
        if(!cheakIn(nearR, nearC)) { return;}
        if(isOpen(nearR, nearC)) {
            Connect(ChangeToLine(row, col), ChangeToLine(nearR, nearC));
        }
    }

    private int ChangeToLine(int row, int col) {
        return row * Length + col;
    }



    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if(!isOpen(row, col)) {
            Grid[row][col] = true;
            count ++;

            // If the nearby blocks is open, this block should union
            for(int i = 0; i < 4; i ++) {
                int nearR = row + dx[i];
                int nearC = col + dy[i];
                if(dx[i] == -1) {UpdateUpConnection(row, col, nearR, nearC);}
                else {
                    UpdateOtherConnection(row, col, nearR, nearC);
                }
//                if(nearX < 0 || nearX >= Length || nearY < 0 || nearY >= Length) {continue;}
//                if(isOpen(nearX, nearY)) {
//                    if(!percolates()) {
//                        DSet.union(ChangeToLine(nearX, nearY), ChangeToLine(row, col));
//                    }
//                    DSetWithoutVB.union(ChangeToLine(nearX, nearY), ChangeToLine(row, col));
//                }
            }
        }
    }

    // is the site(row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return Grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return DSetWithoutVB.connected(VirtualTop, ChangeToLine(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {return count;}

    //  does the system percolate?
    public boolean percolates() {
        return DSet.connected(VirtualTop, VirtualBottom);
    }

    // use for unit testing
    public static void main(String[] args) {
        int size = 5;
        Percolation world = new Percolation(size);
        world.open(4,4);
        world.open(3, 4);
        world.open(2, 4);
        world.open(1, 4);
        world.open(0, 4);

        world.open(4,2);
    }
}
