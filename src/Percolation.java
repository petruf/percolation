import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int gridSize;
    private final boolean[][] grid;
    private int noOpenGrids;
    private final WeightedQuickUnionUF wquf;
    private final WeightedQuickUnionUF wqufBack;
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("row index i out of bounds");
        gridSize = n;
        grid = new boolean[n][n];
        wquf = new WeightedQuickUnionUF((n*n)+2);
        wqufBack = new WeightedQuickUnionUF((n*n)+1);
        noOpenGrids = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > gridSize) throw new IllegalArgumentException("row index i out of bounds");
        if (col <= 0 || col > gridSize) throw new IllegalArgumentException("col index i out of bounds");
    }

    private int xyTo1D(int row, int col) {
        validate(row, col);
        return (row-1) * gridSize + col;
    }

    public void open(int row, int col) {    // open site (row, col) if it is not open already
        validate(row, col);
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            noOpenGrids += 1;
            if (row-1 == 0) {
                wquf.union(0, xyTo1D(row, col));
                wqufBack.union(0, xyTo1D(row, col));
            }
            if (row + 1 == gridSize+1) {
                wquf.union(gridSize * gridSize + 1, xyTo1D(row, col));
            }
            if (row-1 > 0 && isOpen(row-1, col)) { // does grid on the bottom exist and is open?
                wquf.union(xyTo1D(row-1, col), xyTo1D(row, col));
                wqufBack.union(xyTo1D(row-1, col), xyTo1D(row, col));
            }
            if (row+1 <= gridSize && isOpen(row+1, col)) { // does the upper grid exist and is open?
                wquf.union(xyTo1D(row+1, col), xyTo1D(row, col));
                wqufBack.union(xyTo1D(row+1, col), xyTo1D(row, col));
            }
            if (col-1 > 0 && isOpen(row, col-1)) { // does grid on the left exist and is open?
                wquf.union(xyTo1D(row, col-1), xyTo1D(row, col));
                wqufBack.union(xyTo1D(row, col-1), xyTo1D(row, col));
            }
            if (col+1 <= gridSize && isOpen(row, col+1)) { // does grid on the left exist and is open?
                wquf.union(xyTo1D(row, col+1), xyTo1D(row, col));
                wqufBack.union(xyTo1D(row, col+1), xyTo1D(row, col));
            }
        }
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        validate(row, col);
        return wqufBack.connected(0, xyTo1D(row, col));
    }

    public int numberOfOpenSites()       // number of open sites
    {
        return noOpenGrids;
    }

    public boolean percolates()             // does the system percolate?
    {
        return wquf.connected(0, (gridSize*gridSize)+1);
    }

    public static void main(String[] args) {
//        int size = 3;
//        Percolation testPerc = new Percolation(size);
//        testPerc.open(1,2);
//        testPerc.open(2,2);
//        testPerc.open(3,2);
//        for (int i=0; i < testPerc.grid.length;i++) {
//            for (int j=0; j < testPerc.grid[i].length;j++) {
//                int row = i+1;
//                int col = j+1;
//                System.out.println("grid with coordinates " + (row) +""+ (col) + " is " + testPerc.xyTo1D(row,col) + " no UFgrid and is " + testPerc.isOpen(row,col));
//            }
//        }
//        System.out.println("Number of opened grids is " + testPerc.numberOfOpenSites());
//        System.out.println(testPerc.percolates());
    }   // test client (optional)
}
