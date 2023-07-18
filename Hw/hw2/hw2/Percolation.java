package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private  boolean[][] matrix;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufExcludeBottom; // to avoid backwash
    private int size;
    private int top;
    private int bottom;
    private int totalOpen=0;
    private int[][] surroundings = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public Percolation(int N)// create N-by-N grid, with all sites initially blocked
    {
        if(N<=0)
        {
            throw new IllegalArgumentException();
        }
        matrix=new boolean[N][N];
        size=N;
        top=0;
        bottom=N*N+1;
        uf=new WeightedQuickUnionUF(N*N+2);
        ufExcludeBottom=new WeightedQuickUnionUF(N*N+1);
    }

    private int xyTo1D(int x,int y)
    {
        return x*size+y+1;
    }

    // validate the validity of (row, col)
    private void validate(int row, int col) {
        if (row < 0 || col < 0 || row >= size || col >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
    public void open(int row, int col)       // open the site (row, col) if it is not open already
    {
        validate(row, col);
        if(isOpen(row,col))
        {
            return;
        }
        if(row==0)
        {
            uf.union(xyTo1D(row,col),top);
            ufExcludeBottom.union(xyTo1D(row,col),top);
        }
        if(row==size-1)
        {
            uf.union(xyTo1D(row,col),bottom);
        }
        for (int [] dir:surroundings)
        {
            int x=row+dir[0];
            int y=col+dir[1];
            if(x>=0 && x<size && y>=0 && y<size)
            {
                if(isOpen(x,y))
                {
                    uf.union(xyTo1D(x,y),xyTo1D(row,col));
                    ufExcludeBottom.union(xyTo1D(x,y),xyTo1D(row,col));
                }
            }
        }
        matrix[row][col]=true;
        totalOpen+=1;
    }
    public boolean isOpen(int row, int col)  // is the site (row, col) open?
    {
        validate(row,col);
        return matrix[row][col];
    }
    public boolean isFull(int row, int col)  // is the site (row, col) full?
    {
        return ufExcludeBottom.connected(top,xyTo1D(row, col));
    }
    public int numberOfOpenSites()           // number of open sites
    {
        return totalOpen;
    }
    public boolean percolates()              // does the system percolate?
    {
        return uf.connected(top,bottom);
    }
    public static void main(String[] args)   // use for unit testing (not required, but keep this here for the autograder)
    {

    }

}
